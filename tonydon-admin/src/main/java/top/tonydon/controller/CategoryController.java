package top.tonydon.controller;

import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.Category;
import top.tonydon.domain.vo.CategoryVo;
import top.tonydon.domain.vo.PageVo;
import top.tonydon.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/{pageNum}/{pageSize}")
    public ResponseResult<PageVo<CategoryVo>> findPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        return categoryService.findPage(pageNum, pageSize);
    }

    @PostMapping
    public ResponseResult<Object> save(@RequestBody Category category) {
        return ResponseResult.success(categoryService.save(category));
    }

    /**
     * 根据 id 删除分类
     *
     * @param id 分类 id
     * @return 响应数据
     */
    @DeleteMapping("/{id}")
    public ResponseResult<Object> remove(@PathVariable Long id) {
        return categoryService.remove(id);
    }


    @DeleteMapping("/confirm/{id}")
    public ResponseResult<Object> confirmRemove(@PathVariable Long id) {
        return categoryService.confirmRemove(id);
    }

}
