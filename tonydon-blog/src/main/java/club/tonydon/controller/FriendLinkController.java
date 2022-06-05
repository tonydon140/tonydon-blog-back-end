package club.tonydon.controller;

import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.FriendLink;
import club.tonydon.domain.vo.FriendsLinkVo;
import club.tonydon.service.FriendLinkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/friends-link")
public class FriendLinkController {

    @Resource
    private FriendLinkService friendLinkService;

    // 获取友链
    @GetMapping()
    public ResponseResult<List<FriendsLinkVo>> getAllLink(){
        return friendLinkService.getNormalLink();
    }

    // 申请友链
    @PostMapping
    public ResponseResult<Object> applyLink(@RequestBody FriendLink friendLink){
        return friendLinkService.saveApply(friendLink);
    }

}
