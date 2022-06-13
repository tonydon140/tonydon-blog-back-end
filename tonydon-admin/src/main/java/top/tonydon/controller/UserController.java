package top.tonydon.controller;

import top.tonydon.domain.entity.LoginUser;
import top.tonydon.domain.entity.User;
import top.tonydon.domain.vo.UserInfoVo;
import top.tonydon.service.UserService;
import top.tonydon.domain.ResponseResult;
import top.tonydon.enums.HttpCodeEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private PasswordEncoder passwordEncoder;


    @Resource
    private UserService userService;

    @GetMapping("/info")
    public ResponseResult<UserInfoVo> getUserInfo() {
        return userService.getUserInfoByToken();
    }

    /**
     * 获取所有的用户
     *
     * @return 响应数据
     */
    @GetMapping
    public ResponseResult<List<UserInfoVo>> getAll() {
        return userService.getAll();
    }

    /**
     * 删除用户
     *
     * @param id 用户 id
     * @return 响应数据
     */
    @DeleteMapping("/{id}")
    public ResponseResult<Object> remove(@PathVariable Long id) {
        return userService.removeById(id);
    }


    @PostMapping
    public ResponseResult<Object> save(@RequestBody User user) {
        // 1. 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 2. 设置创建人和创建时间
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        user.setCreateBy(loginUser.getUser().getId());
        user.setCreateTime(new Date());

        // 3. 保存用户
        boolean success = userService.save(user);
        if (success)
            return ResponseResult.success();
        else
            return ResponseResult.error(HttpCodeEnum.NO_ID_ERROR);
    }
}
