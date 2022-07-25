package top.tonydon.service;

import top.tonydon.domain.vo.CategoryVo;
import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import top.tonydon.domain.vo.PageVo;

import java.util.List;

public interface CategoryService extends IService<Category> {

    ResponseResult<PageVo<CategoryVo>> findPage(Integer pageNum, Integer pageSize);

    ResponseResult<Object> remove(Long id);

    ResponseResult<Object> confirmRemove(Long id);
}
