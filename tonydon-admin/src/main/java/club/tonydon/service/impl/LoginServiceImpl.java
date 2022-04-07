package club.tonydon.service.impl;

import club.tonydon.contant.RedisConsts;
import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.User;
import club.tonydon.domain.entity.LoginUser;
import club.tonydon.domain.vo.LoginUserVo;
import club.tonydon.domain.vo.UserInfoVo;
import club.tonydon.service.LoginService;
import club.tonydon.utils.BeanCopyUtils;
import club.tonydon.utils.RedisUtils;
import cn.hutool.core.lang.UUID;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private AuthenticationManager manager;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 用户登陆
     *
     * @param user 用户
     * @return 返回值
     */
    @Override
    public ResponseResult<Object> login(User user) {
        // 1. 将用户封装为 Authentication
        Authentication beforeAuth =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        // 2. 认证用户，认证如果不通过会在方法内部直接抛出异常，afterAuth 一定是认证通过的
        Authentication afterAuth = manager.authenticate(beforeAuth);
        LoginUser loginUser = (LoginUser) afterAuth.getPrincipal();

        // 3. 根据 UUID 生成 token，并将 token 存入 LoginUser 中
        String token = UUID.randomUUID().toString(true);
        loginUser.setToken(token);

        // 4. 将用户消息存入 redis，使用 token 作为 key
        String key = RedisConsts.LOGIN_PREFIX + token;
        redisUtils.setObject(key, loginUser, RedisConsts.LOGIN_TTL);

        // 5. 把 token 和用户消息进行封装，并返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        LoginUserVo vo = new LoginUserVo(token, userInfoVo);
        return ResponseResult.success(vo);
    }

    @Override
    public ResponseResult<Object> logout() {
        // 1. 从 context 中获取用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 2. 获取 token
        String token = loginUser.getToken();

        // 3. 从 Redis 中删除用户
        redisUtils.removeObject(RedisConsts.LOGIN_PREFIX + token);
        return ResponseResult.success();
    }
}
