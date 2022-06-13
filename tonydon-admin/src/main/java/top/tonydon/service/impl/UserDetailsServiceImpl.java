package top.tonydon.service.impl;

import top.tonydon.domain.entity.User;
import top.tonydon.domain.entity.LoginUser;
import top.tonydon.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SpringSecurity 的用户校验
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 根据用户名查询用户信息
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(wrapper);

        // 2. 判断是否查到用户，若没有查到，直接抛出异常
        if (user == null) throw new RuntimeException("用户不存在");

        // 3. todo 查询用户权限

        // 4. 返回用户
        return new LoginUser(user);
    }
}
