package club.tonydon.service;

import club.tonydon.domain.vo.CategoryVo;
import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CategoryService extends IService<Category> {
    ResponseResult<List<CategoryVo>> getAll();

    ResponseResult<Object> saveCategory(Category category);
}
