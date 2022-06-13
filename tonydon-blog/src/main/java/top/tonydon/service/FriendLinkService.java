package top.tonydon.service;

import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.FriendLink;
import top.tonydon.domain.vo.FriendsLinkVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-03-20 09:18:12
 */
public interface FriendLinkService extends IService<FriendLink> {

    ResponseResult<List<FriendsLinkVo>> getNormalLink();


    ResponseResult<Object> saveApply(FriendLink friendLink);
}

