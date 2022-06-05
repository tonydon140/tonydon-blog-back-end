package club.tonydon.service;

import club.tonydon.domain.entity.User;
import club.tonydon.domain.ResponseResult;

public interface LoginService {
    ResponseResult<Object> login(User user);

    ResponseResult<Object> logout();
}
