package top.tonydon;

import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.tonydon.domain.entity.Category;
import top.tonydon.service.CategoryService;


import javax.annotation.Resource;
import java.time.LocalDateTime;

@SpringBootTest
public class AdminTest {

    @Resource
    CategoryService categoryService;

    @Test
    public void test()  {
        for (int i = 0; i < 10; i++) {
            Category category = new Category();
            category.setName("分类" + i);
            category.setDescription("测试分类");
            categoryService.save(category);
        }

    }

}
