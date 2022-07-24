package top.tonydon.controller;

import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.FriendLink;
import top.tonydon.domain.vo.FriendLinkVo;
import top.tonydon.service.FriendLinkService;
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
    public ResponseResult<List<FriendLinkVo>> getAllLink(){
        return friendLinkService.getNormalLink();
    }

    // 申请友链
    @PostMapping
    public ResponseResult<Object> applyLink(@RequestBody FriendLink friendLink){
        return friendLinkService.saveApply(friendLink);
    }

}
