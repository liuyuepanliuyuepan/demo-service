package cn.klmb.crm.module.product.controller.admin.category.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * @author Administrator
 */
@Data
@ToString
@ApiModel("产品类型")
public class CrmProductCategoryBO {

    private String bizId;
    private String pid;
    private String name;
    private String label;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CrmProductCategoryBO> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.label = name;
    }
}
