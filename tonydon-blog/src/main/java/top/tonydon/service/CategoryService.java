package top.tonydon.service;

import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.Category;
import top.tonydon.domain.vo.CategoryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-03-18 17:24:36
 */
public interface CategoryService extends IService<Category> {

    ResponseResult<List<CategoryVo>> getAll();

}

