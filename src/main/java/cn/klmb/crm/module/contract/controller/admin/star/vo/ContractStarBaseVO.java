package cn.klmb.crm.module.contract.controller.admin.star.vo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
 * 合同标星关系 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ContractStarBaseVO {

    @ApiModelProperty(value = "合同ID", required = true, example = "1045")
    @NotNull(message = "合同ID不能为空")
    private String contractId;

    @ApiModelProperty(value = "用户id", required = true, example = "12884")
    @NotNull(message = "用户id不能为空")
    private String userId;

}
