package cn.cambridge.coss.web.vo;

import lombok.Data;

@Data
public class ArticleVO {
    private String[] articlePath;
    private String articleContext;
    private Long articleSize;
}
