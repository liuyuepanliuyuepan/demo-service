package cn.klmb.crm.framework.job.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * 创建任务所需的参数
 *
 * @author liuyuepan
 * @date 2023/4/11
 */
@SuperBuilder
@Data
public class XxlJobChangeTaskDTO {

    /**
     * 负责人id
     */
    private String ownerUserId;

    /**
     * 消息来源的主键id (比如：客户、联系人的主键id)
     */
    private String bizId;


    /**
     * 下次联系时间
     */
    private LocalDateTime nextTime;

    /**
     * 消息来源的名称 (比如：客户、联系人的名字)
     */
    private String name;

    /**
     * 1新增，2编辑，3删除
     */
    private Integer operateType;

    /**
     * messageType 例如：客户、联系人
     */
    private String messageType;

    /**
     * 联系人类型  {@link cn.klmb.crm.module.system.enums.CrmEnum}
     */
    private Integer contactsType;


    private String appName;


    private String title;

    /**
     * 执行器任务handler
     */
    private String executorHandler;

    /**
     * 作者
     */
    private String author;

}
