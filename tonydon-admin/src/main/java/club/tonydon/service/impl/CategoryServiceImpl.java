package club.tonydon.service.impl;

import club.tonydon.constant.SystemConstants;
import club.tonydon.domain.ResponseResult;
import club.tonydon.domain.entity.Article;
import club.tonydon.domain.entity.Category;
import club.tonydon.domain.entity.User;
import club.tonydon.domain.vo.CategoryVo;
import club.tonydon.enums.HttpCodeEnum;
import club.tonydon.mapper.ArticleMapper;
import club.tonydon.mapper.CategoryMapper;
import club.tonydon.mapper.UserMapper;
import club.tonydon.service.CategoryService;
import club.tonydon.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
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
