package club.tonydon.service;

import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.vo.UserInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;
import club.tonydon.domain.entity.User;

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

