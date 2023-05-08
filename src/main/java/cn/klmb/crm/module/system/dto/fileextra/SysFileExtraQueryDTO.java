package cn.klmb.crm.module.system.dto.fileextra;

import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery;
import cn.klmb.crm.framework.base.core.annotations.DtoFieldQuery.Operator;
import cn.klmb.crm.framework.base.core.pojo.KlmbBaseQueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * CRM附件关联 DO
 *
 * @author 超级管理员
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysFileExtraQueryDTO extends KlmbBaseQueryDTO {

    /**
     * 类型，同crm类型
     */
    @DtoFieldQuery
    private Integer type;
    /**
     * 对应类型主键ID
     */
    @DtoFieldQuery
    private String typeId;
    /**
     * 文件名
     */
    @DtoFieldQuery(queryType = Operator.LIKE)
    private String name;
    /**
     * 附件大小
     */
    @DtoFieldQuery
    private Integer size;
    /**
     * 来源(目前写死附件上传)
     */
    @DtoFieldQuery
    private String source;

    /**
     * 文件id
     */
    @DtoFieldQuery
    private String fileId;

}
