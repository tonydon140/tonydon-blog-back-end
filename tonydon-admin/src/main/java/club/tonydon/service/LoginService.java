package club.tonydon.service;

import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.User;

public interface LoginService {
    ResponseResult<Object> login(User user);

    ResponseResult<Object> logout();
}
