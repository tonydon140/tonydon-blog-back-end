package top.tonydon.service.impl;

import top.tonydon.constant.SystemConstants;
import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.Article;
import top.tonydon.domain.entity.Category;
import top.tonydon.domain.entity.User;
import top.tonydon.domain.vo.CategoryVo;
import top.tonydon.enums.HttpCodeEnum;
import top.tonydon.mapper.ArticleMapper;
import top.tonydon.mapper.CategoryMapper;
import top.tonydon.mapper.UserMapper;
import top.tonydon.service.CategoryService;
import top.tonydon.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
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

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public ResponseResult<List<CategoryVo>> getAll() {
        // 1. 查询所有的分类，并填充用户名
        List<Category> categoryList = list();
        categoryList = categoryList.stream()
                .peek(category -> {
                    // 设置创建人昵称
                    if (category.getCreateBy() != null) {
                        User user = userMapper.selectById(category.getCreateBy());
                        category.setCreateUsername(user != null ? user.getNickname() : "用户不存在");
                    }
                    // 设置更新人昵称
                    if (category.getUpdateBy() != null) {
                        User user = userMapper.selectById(category.getUpdateBy());
                        category.setUpdateUsername(user != null ? user.getNickname() : "用户不存在");
                    }
                    // 设置父分类名称
                    if (category.getPid() != -1)
                        category.setPName(getById(category.getPid()).getName());
                }).collect(Collectors.toList());
        // 2. 转换为 vo 并返回
        List<CategoryVo> voList = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        return ResponseResult.success(voList);
    }


    @Override
    public ResponseResult<Object> removeCategory(Long id) {
        Category category = getById(id);
        // 不能删除未分类
        if (SystemConstants.NOT_CLASSIFIED_NAME.equals(category.getName())) {
            return ResponseResult.error(HttpCodeEnum.CANNOT_REMOVE_NOT_CLASSIFIED);
        }

        // 查询当前分类是否有子分类
        boolean exists = lambdaQuery().eq(Category::getPid, category.getId()).exists();
        // 如果子分类存在，则不允许删除
        if (exists){
            return ResponseResult.error();
        }

        // 查询该分类下是否存在文章
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getCategoryId, category.getId());
        // 存在文章，提醒用户是否删除
        if (articleMapper.exists(wrapper)) {
            return ResponseResult.error(HttpCodeEnum.CATEGORY_HAS_ARTICLE);
        }

        removeById(id);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> confirmRemoveCategory(Long id) {
        // 查询当前分类下的所有文章，将文章的分类设置为未分类
        LambdaUpdateChainWrapper<Article> updateWrapper = new LambdaUpdateChainWrapper<>(articleMapper);
        updateWrapper.eq(Article::getCategoryId, id)
                .set(Article::getCategoryId, SystemConstants.NOT_CLASSIFIED_ID)
                .update();
        // 删除该分类
        removeById(id);
        return ResponseResult.success();
    }
}
