package cn.klmb.crm.module.member.controller.admin.user.vo;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 客户Excel导入
 *
 * @author liuyuepan
 * @date 2023/05/08
 */
@Data
public class MemberUserImportReqVO {

    @NotNull(message = "请选择课表文件")
    @ApiModelProperty(value = "课表文件")
    private MultipartFile file;

    @NotNull(message = "是否为公海客户不能为空")
    @ApiModelProperty(value = "是否为公海客户")
    private Boolean isPool;

}
