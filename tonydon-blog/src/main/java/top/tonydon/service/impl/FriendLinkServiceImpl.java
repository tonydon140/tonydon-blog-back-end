package top.tonydon.service.impl;

import top.tonydon.constant.BlogRedisConstants;
import top.tonydon.constant.EntityConstants;
import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.FriendLink;
import top.tonydon.domain.vo.FriendLinkVo;
import top.tonydon.mapper.FriendLinkMapper;
import top.tonydon.service.FriendLinkService;
import top.tonydon.util.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.tonydon.util.BlogCache;
import top.tonydon.util.MailUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 友链(Link)表服务实现类
 *
 * @author tonydon
 * @since 2022-03-20 09:18:12
 */
@Service("linkService")
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {

    @Resource
    private MailUtils mailUtils;

    @Resource
    private BlogCache blogCache;

    // 获取所有的友链
    @Override
    public ResponseResult<List<FriendLinkVo>> getNormalLink() {
        // 从缓存中查询
        List<FriendLinkVo> voList = blogCache.getWithPenetration(BlogRedisConstants.CACHE_FRIEND_LINK_KEY, FriendLinkVo.class, () -> {
            // 查询已经通过的友链
            LambdaQueryWrapper<FriendLink> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(FriendLink::getStatus, EntityConstants.LINK_STATUS_NORMAL);
            // 查询结果
            return BeanCopyUtils.copyBeanList(list(wrapper), FriendLinkVo.class);
        }, BlogRedisConstants.CACHE_FRIEND_LINK_TTL, TimeUnit.MINUTES);

        // 返回结果
        return ResponseResult.success(voList);
    }

    @Override
    public ResponseResult<Object> saveApply(FriendLink friendLink) {
        // 1. 查询友链链接，判断是否重复
        Long count = lambdaQuery().eq(FriendLink::getAddress, friendLink.getAddress()).count();
        if (count != 0) return ResponseResult.error();

        // 2. 保存友链申请
        save(friendLink);
        // 开启新的线程发送邮件
        new Thread(() -> mailUtils.sendFriendLinkMail(friendLink)).start();
        return ResponseResult.success();
    }
}

