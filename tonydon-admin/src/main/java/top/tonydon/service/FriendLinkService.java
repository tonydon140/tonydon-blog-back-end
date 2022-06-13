package top.tonydon.service;

import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.FriendLink;
import top.tonydon.domain.vo.FriendLinkVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface FriendLinkService extends IService<FriendLink> {
    ResponseResult<List<FriendLinkVo>> getAll();

    ResponseResult<Object> passById(String id);
}
