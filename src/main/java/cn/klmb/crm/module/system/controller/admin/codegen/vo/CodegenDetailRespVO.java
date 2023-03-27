package cn.klmb.crm.module.system.controller.admin.codegen.vo;

import cn.klmb.crm.module.system.controller.admin.codegen.vo.column.CodegenColumnRespVO;
import cn.klmb.crm.module.system.controller.admin.codegen.vo.table.CodegenTableRespVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@ApiModel("管理后台 - 代码生成表和字段的明细 Response VO")
@Data
public class CodegenDetailRespVO {

    @ApiModelProperty("表定义")
    private CodegenTableRespVO table;

    @ApiModelProperty("字段定义")
    private List<CodegenColumnRespVO> columns;

}
