package club.tonydon.controller;

import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.Category;
import club.tonydon.domain.vo.CategoryVo;
import club.tonydon.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping
    public ResponseResult<List<CategoryVo>> getAll(){
        return categoryService.getAll();
    }

    @PostMapping
    public ResponseResult<Object> save(@RequestBody Category category){
        return categoryService.saveCategory(category);
    }

    /**
     * 根据 id 删除分类
     * @param id 分类 id
     * @return 响应数据
     */
    @DeleteMapping("/{id}")
    public ResponseResult<Object> remove(@PathVariable Long id){
        boolean success = categoryService.removeById(id);
        if(success) return ResponseResult.success();
        else return ResponseResult.error();
    }



}
