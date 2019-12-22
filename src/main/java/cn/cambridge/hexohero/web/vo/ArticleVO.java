package cn.cambridge.hexohero.web.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ArticleVO implements Serializable {
    private static final long serialVersionUID = -7675323655819817845L;

    private String articleFileName;
    private String[] articlePath;
    private String articleContext;
    private Long articleSize;
    private Date updateTime;
}
