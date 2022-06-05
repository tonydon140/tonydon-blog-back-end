package club.tonydon.service.impl;

import club.tonydon.constant.SystemConstants;
import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.LoginUser;
import club.tonydon.domain.vo.UserInfoVo;
import club.tonydon.enums.HttpCodeEnum;
import club.tonydon.mapper.UserMapper;
import club.tonydon.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import club.tonydon.domain.entity.User;
import club.tonydon.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public ResponseResult<UserInfoVo> getUserInfoByToken() {
        // 1. 从 Context 中获取用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser userDetails = (LoginUser) authentication.getPrincipal();

        // 2. 封装为 UserInfoVo 并返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(userDetails.getUser(), UserInfoVo.class);
        return ResponseResult.success(userInfoVo);
    }

    /**
     * 查询所有的用户
     * @return UserInfoVo 列表
     */
    @Override
    public ResponseResult<List<UserInfoVo>> getAll() {
        // 1. 查询状态正常的用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getStatus, SystemConstants.USER_STATUS_NORMAL);
        List<User> list = list(wrapper);
        // 2. 转换为 vo 并返回
        List<UserInfoVo> voList = BeanCopyUtils.copyBeanList(list, UserInfoVo.class);
        return ResponseResult.success(voList);
    }

    /**
     * 根据用户 id 删除用户
     * 当用户只有一个时，抛出异常
     * @param id 用户 id
     * @return 响应数据
     */
    @Override
    public ResponseResult<Object> removeById(Long id) {
        // 1. 查询正常用户的数量
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getStatus, SystemConstants.USER_STATUS_NORMAL);
        Long count = baseMapper.selectCount(wrapper);
        // 2. 判断数量是否等于 1
        if (count == 1){
            return ResponseResult.error(HttpCodeEnum.ONLY_ONE_USER_ERROR);
        }
        // 3. 进行删除用户
        boolean update = lambdaUpdate().eq(User::getId, id).set(User::getDelFlag, SystemConstants.DEL_DELETED).update();
        if(update){
            return ResponseResult.success();
        }else{
            return ResponseResult.error(HttpCodeEnum.NO_ID_ERROR);
        }
    }
}

