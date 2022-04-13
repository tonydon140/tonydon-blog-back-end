package club.tonydon.service.impl;

import club.tonydon.contant.SysContants;
import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.FriendsLink;
import club.tonydon.domain.vo.FriendsLinkVo;
import club.tonydon.mapper.FriendsMapper;
import club.tonydon.service.FriendsLinkService;
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
public class FriendsLinkServiceImpl extends ServiceImpl<FriendsMapper, FriendsLink> implements FriendsLinkService {

    // 获取所有的友链
    @Override
    public ResponseResult<List<FriendsLinkVo>> getAllLink() {
        // 查询已经通过的友链
        LambdaQueryWrapper<FriendsLink> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FriendsLink::getStatus, SysContants.LINK_STATUS_NORMAL);

        // 查询结果
        List<FriendsLink> linkList = list(wrapper);

        // 封装vo并返回
        return ResponseResult.success(BeanCopyUtils.copyBeanList(linkList, FriendsLinkVo.class));
    }

    @Override
    public ResponseResult<Object> saveApply(FriendsLink friendsLink) {
        // 1. 查询友链链接，判断是否重复
        Long count = lambdaQuery().eq(FriendsLink::getAddress, friendsLink.getAddress()).count();
        if(count != 0) return ResponseResult.error();

        // 2. 保存友链申请
        friendsLink.setCreateTime(new Date());
        save(friendsLink);
        return ResponseResult.success();
    }
}

