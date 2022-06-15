package top.tonydon.controller;

import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.vo.CategoryVo;
import top.tonydon.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/getAll")
    public ResponseResult<List<CategoryVo>> getAll() {
        return categoryService.findAll();
    }

}
