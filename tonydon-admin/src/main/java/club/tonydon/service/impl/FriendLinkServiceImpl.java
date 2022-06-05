package club.tonydon.service.impl;

import club.tonydon.constant.SystemConstants;
import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.FriendLink;
import club.tonydon.domain.vo.FriendLinkVo;
import club.tonydon.mapper.FriendLinkMapper;
import club.tonydon.service.FriendLinkService;
import club.tonydon.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
                .set(FriendLink::getStatus, SystemConstants.LINK_STATUS_NORMAL)
                .update();
        return ResponseResult.success();
    }
}
