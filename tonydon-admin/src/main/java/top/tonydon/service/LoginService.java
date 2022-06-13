package top.tonydon.service;

import top.tonydon.domain.entity.User;
import top.tonydon.domain.ResponseResult;

public interface LoginService {
    ResponseResult<Object> login(User user);

    ResponseResult<Object> logout();
}
