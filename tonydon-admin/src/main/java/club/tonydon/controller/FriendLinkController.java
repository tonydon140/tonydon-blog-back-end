package club.tonydon.controller;

import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.vo.FriendLinkVo;
import club.tonydon.service.FriendLinkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/friend-link")
public class FriendLinkController {

    @Resource
    FriendLinkService friendLinkService;

    @GetMapping
    ResponseResult<List<FriendLinkVo>> findAllLink() {
        return friendLinkService.getAll();
    }

    @PutMapping("/{id}")
    ResponseResult<Object> pass(@PathVariable String id) {
        return friendLinkService.passById(id);
    }

    @DeleteMapping("/{id}")
    ResponseResult<Object> remove(@PathVariable String id) {
        if (friendLinkService.removeById(id))
            return ResponseResult.success();
        else
            return ResponseResult.error();
    }
}
