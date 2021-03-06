package top.tonydon.service.impl;

import top.tonydon.constant.EntityConstants;
import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.FriendLink;
import top.tonydon.domain.vo.FriendLinkVo;
import top.tonydon.mapper.FriendLinkMapper;
import top.tonydon.service.FriendLinkService;
import top.tonydon.util.BeanCopyUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService  {

    @Override
    public ResponseResult<List<FriendLinkVo>> getAll() {
        // 查询所有友链
        List<FriendLinkVo> voList = BeanCopyUtils.copyBeanList(list(), FriendLinkVo.class);
        // 返回数据
        return ResponseResult.success(voList);
    }

    @Override
    public ResponseResult<Object> passById(String id) {
        lambdaUpdate()
                .eq(FriendLink::getId, id)
                .set(FriendLink::getStatus, EntityConstants.LINK_STATUS_NORMAL)
                .set(FriendLink::getCheckTime, LocalDateTime.now())
                .update();
        return ResponseResult.success();
    }
}
