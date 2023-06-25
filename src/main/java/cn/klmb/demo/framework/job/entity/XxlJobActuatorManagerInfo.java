package cn.klmb.demo.framework.job.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class XxlJobActuatorManagerInfo {

    private Integer recordsFiltered;
    private Integer recordsTotal;
    private List<XxlJobGroup> data;
}

