package club.tonydon.service.impl;

import club.tonydon.contant.SysContants;
import club.tonydon.domain.entity.Link;
import club.tonydon.domain.vo.LinkVo;
import club.tonydon.mapper.LinkMapper;
import club.tonydon.service.LinkService;
import club.tonydon.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author tonydon
 * @since 2022-03-20 09:18:12
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    // 获取所有的友链
    @Override
    public List<LinkVo> getAllLink() {
        // 查询已经通过的友链
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Link::getStatus, SysContants.LINK_STATUS_NORMAL);

        // 查询结果
        List<Link> linkList = list(wrapper);

        // 封装vo并返回
        return BeanCopyUtils.copyBeanList(linkList, LinkVo.class);
    }
}

