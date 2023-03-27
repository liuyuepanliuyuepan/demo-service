package cn.klmb.crm.module.system.controller.admin.codegen;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;
import static cn.klmb.crm.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.framework.common.pojo.PageResult;
import cn.klmb.crm.framework.common.util.servlet.ServletUtils;
import cn.klmb.crm.module.system.controller.admin.codegen.vo.CodegenCreateListReqVO;
import cn.klmb.crm.module.system.controller.admin.codegen.vo.CodegenDetailRespVO;
import cn.klmb.crm.module.system.controller.admin.codegen.vo.CodegenPreviewRespVO;
import cn.klmb.crm.module.system.controller.admin.codegen.vo.CodegenUpdateReqVO;
import cn.klmb.crm.module.system.controller.admin.codegen.vo.table.CodegenTablePageReqVO;
import cn.klmb.crm.module.system.controller.admin.codegen.vo.table.CodegenTableRespVO;
import cn.klmb.crm.module.system.controller.admin.codegen.vo.table.DatabaseTableRespVO;
import cn.klmb.crm.module.system.convert.codegen.CodegenConvert;
import cn.klmb.crm.module.system.entity.codegen.CodegenColumnDO;
import cn.klmb.crm.module.system.entity.codegen.CodegenTableDO;
import cn.klmb.crm.module.system.service.codegen.CodegenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统管理 - 代码生成
 *
 * @author shilinchuan
 * @date 2022/12/30
 */
@Api(tags = "0115.系统管理-代码生成")
@RestController
@RequestMapping("/sys/codegen")
@Validated
public class CodegenController {

    @Resource
    private CodegenService codegenService;

    @GetMapping("/db/table/list")
    @ApiOperation(value = "获得数据库自带的表定义列表", notes = "会过滤掉已经导入 Codegen 的表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "表名，模糊匹配", example = "yudao", dataTypeClass = String.class),
            @ApiImplicitParam(name = "comment", value = "描述，模糊匹配", example = "芋道", dataTypeClass = String.class)
    })
    @PreAuthorize("@ss.hasPermission('sys:codegen:query')")
    public CommonResult<List<DatabaseTableRespVO>> getDatabaseTableList(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "comment", required = false) String comment) {
        return success(codegenService.getDatabaseTableList(name, comment));
    }

    @GetMapping("/table/page")
    @ApiOperation("获得表定义分页")
    @PreAuthorize("@ss.hasPermission('sys:codegen:query')")
    public CommonResult<PageResult<CodegenTableRespVO>> getCodeGenTablePage(@Valid CodegenTablePageReqVO pageReqVO) {
        PageResult<CodegenTableDO> pageResult = codegenService.getCodegenTablePage(pageReqVO);
        return success(CodegenConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/detail")
    @ApiOperation("获得表和字段的明细")
    @ApiImplicitParam(name = "tableId", value = "表编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('sys:codegen:query')")
    public CommonResult<CodegenDetailRespVO> getCodegenDetail(@RequestParam("tableId") Long tableId) {
        CodegenTableDO table = codegenService.getCodegenTablePage(tableId);
        List<CodegenColumnDO> columns = codegenService.getCodegenColumnListByTableId(tableId);
        // 拼装返回
        return success(CodegenConvert.INSTANCE.convert(table, columns));
    }

    @ApiOperation("基于数据库的表结构，创建代码生成器的表和字段定义")
    @PostMapping("/create-list")
    @PreAuthorize("@ss.hasPermission('sys:codegen:create')")
    public CommonResult<List<Long>> createCodegenList(@Valid @RequestBody CodegenCreateListReqVO reqVO) {
        return success(codegenService.createCodegenList(getLoginUserId(), reqVO));
    }

    @ApiOperation("更新数据库的表和字段定义")
    @PutMapping("/update")
    @PreAuthorize("@ss.hasPermission('sys:codegen:update')")
    public CommonResult<Boolean> updateCodegen(@Valid @RequestBody CodegenUpdateReqVO updateReqVO) {
        codegenService.updateCodegen(updateReqVO);
        return success(true);
    }

    @ApiOperation("基于数据库的表结构，同步数据库的表和字段定义")
    @PutMapping("/sync-from-db")
    @ApiImplicitParam(name = "tableId", value = "表编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('sys:codegen:update')")
    public CommonResult<Boolean> syncCodegenFromDB(@RequestParam("tableId") Long tableId) {
        codegenService.syncCodegenFromDB(tableId);
        return success(true);
    }

    @ApiOperation("删除数据库的表和字段定义")
    @DeleteMapping("/delete")
    @ApiImplicitParam(name = "tableId", value = "表编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('sys:codegen:delete')")
    public CommonResult<Boolean> deleteCodegen(@RequestParam("tableId") Long tableId) {
        codegenService.deleteCodegen(tableId);
        return success(true);
    }

    @ApiOperation("预览生成代码")
    @GetMapping("/preview")
    @ApiImplicitParam(name = "tableId", value = "表编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('sys:codegen:preview')")
    public CommonResult<List<CodegenPreviewRespVO>> previewCodegen(@RequestParam("tableId") Long tableId) {
        Map<String, String> codes = codegenService.generationCodes(tableId);
        return success(CodegenConvert.INSTANCE.convert(codes));
    }

    @ApiOperation("下载生成代码")
    @GetMapping("/download")
    @ApiImplicitParam(name = "tableId", value = "表编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('sys:codegen:download')")
    public void downloadCodegen(@RequestParam("tableId") Long tableId,
                                HttpServletResponse response) throws IOException {
        // 生成代码
        Map<String, String> codes = codegenService.generationCodes(tableId);
        // 构建 zip 包
        String[] paths = codes.keySet().toArray(new String[0]);
        ByteArrayInputStream[] ins = codes.values().stream().map(IoUtil::toUtf8Stream).toArray(ByteArrayInputStream[]::new);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipUtil.zip(outputStream, paths, ins);
        // 输出
        ServletUtils.writeAttachment(response, "codegen.zip", outputStream.toByteArray());
    }

}
