package cn.cambridge.hexohero.web.vo;

import lombok.Data;

@Data
public class ArticleDTO {
    private String[] articlePath;
    private String articleContext;
    private Boolean cascaded;
}
