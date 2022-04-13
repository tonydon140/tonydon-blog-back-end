package club.tonydon.service;

import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.FriendsLink;
import club.tonydon.domain.vo.FriendsLinkVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-03-20 09:18:12
 */
public interface FriendsLinkService extends IService<FriendsLink> {

    ResponseResult<List<FriendsLinkVo>> getAllLink();

    ResponseResult<Object> saveApply(FriendsLink friendsLink);
}

