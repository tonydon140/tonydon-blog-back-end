package club.tonydon.service;

import club.tonydon.domain.entity.Link;
import club.tonydon.domain.vo.LinkVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-03-20 09:18:12
 */
public interface LinkService extends IService<Link> {

    List<LinkVo> getAllLink();
}

