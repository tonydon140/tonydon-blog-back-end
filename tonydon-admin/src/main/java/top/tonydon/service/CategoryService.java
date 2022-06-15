package top.tonydon.service;

import top.tonydon.domain.vo.CategoryVo;
import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CategoryService extends IService<Category> {
    ResponseResult<List<CategoryVo>> getAll();

    ResponseResult<Object> removeCategory(Long id);

    ResponseResult<Object> confirmRemoveCategory(Long id);
}
