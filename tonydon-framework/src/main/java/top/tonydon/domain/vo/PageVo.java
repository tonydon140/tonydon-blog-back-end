package top.tonydon.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo<T> {
    private List<T> rows;   // 当前页面数据
    private Long total;  // 总数据量
}
