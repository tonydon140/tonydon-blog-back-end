package club.tonydon.service;

import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.FriendLink;
import club.tonydon.domain.vo.FriendLinkVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface FriendLinkService extends IService<FriendLink> {
    ResponseResult<List<FriendLinkVo>> getAll();

    ResponseResult<Object> passById(String id);
}
