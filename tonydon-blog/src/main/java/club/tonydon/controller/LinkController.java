package club.tonydon.controller;

import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.vo.LinkVo;
import club.tonydon.service.LinkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Resource
    private LinkService service;

    @GetMapping("/getAllLink")
    public ResponseResult<List<LinkVo>> getAllLink(){
        List<LinkVo> voList = service.getAllLink();
        return ResponseResult.success(voList);
    }
}
