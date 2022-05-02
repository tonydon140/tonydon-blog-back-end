package club.tonydon.service.impl;

import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.Category;
import club.tonydon.domain.vo.CategoryVo;
import club.tonydon.mapper.CategoryMapper;
import club.tonydon.mapper.UserMapper;
import club.tonydon.service.CategoryService;
import club.tonydon.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private UserMapper userMapper;

    @Override
    public ResponseResult<List<CategoryVo>> getAll() {
        // 1. 查询所有的分类，并填充用户名
        List<Category> categoryList = list();
        categoryList = categoryList.stream()
                .peek(category -> {
                    if (category.getCreateBy() != null)
                        category.setCreateUsername(userMapper.selectById(category.getCreateBy()).getUsername());
                    if (category.getUpdateBy() != null)
                        category.setUpdateUsername(userMapper.selectById(category.getUpdateBy()).getUsername());
                    if (category.getPid() != -1)
                        category.setPName(getById(category.getPid()).getName());
                }).collect(Collectors.toList());
        // 2. 转换为 vo 并返回
        List<CategoryVo> voList = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        return ResponseResult.success(voList);
    }

    /**
     * 保存一个分类
     *
     * @param category 分类
     * @return 响应数据
     */
    @Override
    public ResponseResult<Object> saveCategory(Category category) {
        // 1. 设置创建时间
        category.setCreateTime(new Date());
        // 2. 保存分类，响应数据
        if (save(category)) return ResponseResult.success();
        else return ResponseResult.error();
    }
}
