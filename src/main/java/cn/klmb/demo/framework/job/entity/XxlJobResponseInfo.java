package cn.klmb.demo.framework.job.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class XxlJobResponseInfo {

    private Integer code;
    private String msg;
    private String content;
}

