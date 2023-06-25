package cn.klmb.demo.module.system.controller.admin.codegen.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

@ApiModel("管理后台 - 基于数据库的表结构，创建代码生成器的表和字段定义 Request VO")
@Data
public class CodegenCreateListReqVO {

    @ApiModelProperty(value = "表名数组", required = true, example = "[1, 2, 3]")
    @NotNull(message = "表名数组不能为空")
    private List<String> tableNames;

}
