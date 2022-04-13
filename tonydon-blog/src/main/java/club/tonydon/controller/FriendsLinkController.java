package club.tonydon.controller;

import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.FriendsLink;
import club.tonydon.domain.vo.FriendsLinkVo;
import club.tonydon.service.FriendsLinkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/friends-link")
public class FriendsLinkController {

    @Resource
    private FriendsLinkService friendsLinkService;

    // 获取友链
    @GetMapping()
    public ResponseResult<List<FriendsLinkVo>> getAllLink(){
        return friendsLinkService.getAllLink();
    }

    // 申请友链
    @PostMapping
    public ResponseResult<Object> applyLink(@RequestBody FriendsLink friendsLink){
        return friendsLinkService.saveApply(friendsLink);
    }

}
