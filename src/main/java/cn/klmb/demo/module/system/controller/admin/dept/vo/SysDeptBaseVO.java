package cn.klmb.demo.module.system.controller.admin.dept.vo;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 部门 Base VO，提供给添加、修改、详细的子 VO 使用 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class SysDeptBaseVO {

    @ApiModelProperty(value = "部门名称", required = true, example = "快乐萌宝")
    @NotBlank(message = "部门名称不能为空")
    @Size(max = 30, message = "部门名称长度不能超过30个字符")
    private String name;

    @ApiModelProperty(value = "部门名称（简称）", required = true, example = "快乐萌宝")
    @Size(max = 30, message = "部门名称（简称）长度不能超过30个字符")
    private String nameShort;

    @ApiModelProperty(value = "父部门树 ID", example = "uuid")
    private String treeParentId;

    @ApiModelProperty(value = "显示顺序不能为空", required = true, example = "1")
    @NotNull(message = "显示顺序不能为空")
    private Integer sort;

    @ApiModelProperty(value = "负责人的用户编号", example = "uuid")
    private String leaderUserId;

    @ApiModelProperty(value = "联系电话", example = "18600000000")
    @Size(max = 11, message = "联系电话长度不能超过11个字符")
    private String phone;

    @ApiModelProperty(value = "邮箱", example = "123@123.cn")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    @ApiModelProperty(value = "状态", required = true, example = "1", notes = "见 CommonStatusEnum 枚举")
    @NotNull(message = "状态不能为空")
//    @InEnum(value = CommonStatusEnum.class, message = "修改状态必须是 {value}")
    private Integer status;

}
