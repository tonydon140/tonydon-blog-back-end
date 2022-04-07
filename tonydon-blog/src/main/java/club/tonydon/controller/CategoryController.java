package club.tonydon.controller;

import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.vo.CategoryVo;
import club.tonydon.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService service;

    @GetMapping("/getAll")
    public ResponseResult<List<CategoryVo>> getAll() {
        return service.getAll();
    }

}
