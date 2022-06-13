package top.tonydon.controller;

import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.User;
import top.tonydon.enums.HttpCodeEnum;
import top.tonydon.exception.SystemException;
import top.tonydon.service.LoginService;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseResult<Object> login(@RequestBody User user) {
        // 如果用户名为空
        if(StrUtil.isBlank(user.getUsername())){
            // 提示必须要传递用户名
            throw new SystemException(HttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }


    @PostMapping("/logout")
    public ResponseResult<Object> logout(){
        return loginService.logout();
    }
}
