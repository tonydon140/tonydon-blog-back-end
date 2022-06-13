package top.tonydon.service;

import top.tonydon.domain.entity.User;
import top.tonydon.domain.vo.UserInfoVo;
import top.tonydon.domain.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-03-26 09:53:27
 */
public interface UserService extends IService<User> {

    ResponseResult<UserInfoVo> getUserInfoByToken();

    ResponseResult<List<UserInfoVo>> getAll();

    ResponseResult<Object> removeById(Long id);
}

