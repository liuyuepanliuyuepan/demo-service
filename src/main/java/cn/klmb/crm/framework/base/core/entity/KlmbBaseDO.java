package cn.klmb.crm.framework.base.core.entity;


import cn.klmb.crm.framework.jackson.core.databind.LocalDateTimeDeserializer;
import cn.klmb.crm.framework.jackson.core.databind.LocalDateTimeSerializer;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DO基类
 *
 * @author shilinchuan
 * @date 2022/11/29
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class KlmbBaseDO {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 业务id(uuid)
     */
    @TableField(fill = FieldFill.INSERT)
    private String bizId;

    /**
     * 创建时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建者id
     */
    @TableField(fill = FieldFill.INSERT)
    private String creator;

    /**
     * 更新者id
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 是否删除
     */
    @TableLogic
    private Boolean deleted;
}
