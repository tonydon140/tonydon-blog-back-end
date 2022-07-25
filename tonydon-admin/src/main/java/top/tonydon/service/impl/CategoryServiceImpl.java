package top.tonydon.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.tonydon.constant.EntityConstants;
import top.tonydon.domain.ResponseResult;
import top.tonydon.domain.entity.Article;
import top.tonydon.domain.entity.Category;
import top.tonydon.domain.entity.User;
import top.tonydon.domain.vo.CategoryVo;
import top.tonydon.domain.vo.PageVo;
import top.tonydon.enums.HttpCodeEnum;
import top.tonydon.mapper.ArticleMapper;
import top.tonydon.mapper.CategoryMapper;;
import top.tonydon.service.CategoryService;
import top.tonydon.util.AdminCache;
import top.tonydon.util.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private AdminCache adminCache;

    @Override
    public ResponseResult<PageVo<CategoryVo>> findPage(Integer pageNum, Integer pageSize) {
        // 1. 分页查询分类
        IPage<Category> iPage = new Page<>(pageNum, pageSize);
        page(iPage);

        // 2. 获取数据
        List<Category> categoryList = iPage.getRecords();
        List<CategoryVo> voList = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);

        // 3. 填充数据
        voList = voList.stream()
                .peek(vo -> {
                    // 设置创建人昵称
                    if (vo.getCreateBy() != null) {
                        User user = adminCache.getUser(vo.getCreateBy());
                        vo.setCreateUsername(user != null ? user.getNickname() : "用户不存在");
                    }
                    // 设置更新人昵称
                    if (vo.getUpdateBy() != null) {
                        User user = adminCache.getUser(vo.getUpdateBy());
                        vo.setUpdateUsername(user != null ? user.getNickname() : "用户不存在");
                    }
                    // 设置父分类名称
                    if (vo.getPid() != -1)
                        vo.setPName(adminCache.getCategory(vo.getPid()).getName());
                }).collect(Collectors.toList());

        // 4. 返回数据
        return ResponseResult.success(new PageVo<>(voList, iPage.getTotal()));
    }


    @Override
    public ResponseResult<Object> remove(Long id) {
        Category category = adminCache.getCategory(id);
        // 不能删除未分类
        if (EntityConstants.NOT_CLASSIFIED_NAME.equals(category.getName())) {
            return ResponseResult.error(HttpCodeEnum.CANNOT_REMOVE_NOT_CLASSIFIED);
        }

        // 查询当前分类是否有子分类
        boolean exists = lambdaQuery().eq(Category::getPid, category.getId()).exists();
        // 如果子分类存在，则不允许删除
        if (exists) {
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
    public ResponseResult<Object> confirmRemove(Long id) {
        // 查询当前分类下的所有文章，将文章的分类设置为未分类
        LambdaUpdateChainWrapper<Article> updateWrapper = new LambdaUpdateChainWrapper<>(articleMapper);
        updateWrapper.eq(Article::getCategoryId, id)
                .set(Article::getCategoryId, EntityConstants.NOT_CLASSIFIED_ID)
                .update();
        // 删除该分类
        removeById(id);
        return ResponseResult.success();
    }
}
