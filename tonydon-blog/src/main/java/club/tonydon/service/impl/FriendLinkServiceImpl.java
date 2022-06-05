package club.tonydon.service.impl;

import club.tonydon.constant.SystemConstants;
import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.FriendLink;
import club.tonydon.domain.vo.FriendsLinkVo;
import club.tonydon.mapper.FriendLinkMapper;
import club.tonydon.service.FriendLinkService;
import club.tonydon.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author tonydon
 * @since 2022-03-20 09:18:12
 */
@Service("linkService")
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {

    // 获取所有的友链
    @Override
    public ResponseResult<List<FriendsLinkVo>> getNormalLink() {
        // 查询已经通过的友链
        LambdaQueryWrapper<FriendLink> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FriendLink::getStatus, SystemConstants.LINK_STATUS_NORMAL);

        // 查询结果
        List<FriendLink> linkList = list(wrapper);

        // 封装vo并返回
        return ResponseResult.success(BeanCopyUtils.copyBeanList(linkList, FriendsLinkVo.class));
    }

    @Override
    public ResponseResult<Object> saveApply(FriendLink friendLink) {
        // 1. 查询友链链接，判断是否重复
        Long count = lambdaQuery().eq(FriendLink::getAddress, friendLink.getAddress()).count();
        if(count != 0) return ResponseResult.error();

        // 2. 保存友链申请
        friendLink.setCreateTime(new Date());
        save(friendLink);
        return ResponseResult.success();
    }
}

