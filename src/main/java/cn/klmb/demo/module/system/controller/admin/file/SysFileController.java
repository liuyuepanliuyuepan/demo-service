package cn.klmb.demo.module.system.controller.admin.file;

import static cn.klmb.demo.framework.common.pojo.CommonResult.success;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.demo.framework.base.core.pojo.KlmbPage;
import cn.klmb.demo.framework.common.pojo.CommonResult;
import cn.klmb.demo.framework.common.util.servlet.ServletUtils;
import cn.klmb.demo.module.system.controller.admin.file.vo.file.SysFilePageReqVO;
import cn.klmb.demo.module.system.controller.admin.file.vo.file.SysFileRespVO;
import cn.klmb.demo.module.system.controller.admin.file.vo.file.SysFileUploadReqVO;
import cn.klmb.demo.module.system.convert.file.SysFileConvert;
import cn.klmb.demo.module.system.dto.file.SysFileQueryDTO;
import cn.klmb.demo.module.system.entity.file.SysFileDO;
import cn.klmb.demo.module.system.service.file.SysFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * 系统管理 - 文件管理
 *
 * @author liuyuepan
 * @date 2022/12/5
 */
@Slf4j
@Api(tags = "0110.系统管理-文件管理")
@RestController
@RequestMapping("/sys/file")
@Validated
public class SysFileController {

    private final SysFileService sysFileService;

    public SysFileController(SysFileService sysFileService) {
        this.sysFileService = sysFileService;
    }

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public CommonResult<SysFileRespVO> uploadFile(SysFileUploadReqVO uploadReqVO) throws Exception {
        MultipartFile file = uploadReqVO.getFile();
        String path = uploadReqVO.getPath();
        SysFileDO sysFileDO = sysFileService.saveFile(
                StrUtil.isNotBlank(uploadReqVO.getResourceName()) ? uploadReqVO.getResourceName()
                        : file.getOriginalFilename(), path,
                IoUtil.readBytes(file.getInputStream()));
        return success(SysFileConvert.INSTANCE.convert(sysFileDO));
    }

    @PostMapping("/upload/static")
    @ApiOperation("上传文件（静态服务器）")
    public CommonResult<SysFileRespVO> uploadFileStatic(SysFileUploadReqVO uploadReqVO)
            throws Exception {
        MultipartFile file = uploadReqVO.getFile();
        String path = uploadReqVO.getPath();
        SysFileDO sysFileDO = sysFileService.saveFileStatic(file.getOriginalFilename(), path,
                IoUtil.readBytes(file.getInputStream()));
        return success(SysFileConvert.INSTANCE.convert(sysFileDO));
    }

    @DeleteMapping("/delete/{bizId}")
    @ApiOperation("删除文件")
    @ApiImplicitParam(name = "bizId", value = "编号", required = true, dataTypeClass = String.class)
    @PreAuthorize("@ss.hasPermission('system:file:delete')")
    public CommonResult<Boolean> deleteFile(@PathVariable String bizId) throws Exception {
        sysFileService.deleteFile(bizId);
        return success(true);
    }

//    @GetMapping("/{configId}/get/**")
//    @PermitAll
//    @ApiOperation("下载文件（URL）")
//    @ApiImplicitParam(name = "configId", value = "配置编号", required = true, dataTypeClass = String.class)
//    public void getFileContent(HttpServletRequest request, HttpServletResponse response,
//            @PathVariable("configId") String configId) throws Exception {
//        // 获取请求的路径
//        String path = StrUtil.subAfter(request.getRequestURI(), "/get/", false);
//        path = URLDecoder.decode(path, StandardCharsets.UTF_8);
//        if (StrUtil.isEmpty(path)) {
//            throw new IllegalArgumentException("结尾的 path 路径必须传递");
//        }
//
//        // 读取内容
//        byte[] content = sysFileService.getFileContent(configId, path);
//        if (content == null) {
//            log.warn("[getFileContent][configId({}) path({}) 文件不存在]", configId, path);
//            response.setStatus(HttpStatus.NOT_FOUND.value());
//            return;
//        }
//        ServletUtils.writeAttachment(response, StrUtil.subAfter(path, "/", true), content);
//    }

    @GetMapping("/get/{bizId}")
    @ApiOperation("下载文件（bizId）")
    @ApiImplicitParam(name = "bizId", value = "文件ID", required = true, dataTypeClass = String.class)
    public void getFileContentByBizId(HttpServletResponse response,
            @PathVariable("bizId") String bizId) throws Exception {
        SysFileDO sysFileDO = sysFileService.getByBizId(bizId);
        if (sysFileDO == null) {
            log.warn("[getFileContentByBizId][bizId({}) configId({}) path({}) 文件不存在]",
                    sysFileDO.getBizId(), sysFileDO.getConfigId(), sysFileDO.getPath());
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        // 读取内容
        byte[] content = sysFileService.getFileContent(sysFileDO.getConfigId(),
                sysFileDO.getPath());
        if (content == null) {
            log.warn("[getFileContentByBizId][bizId({}) configId({}) path({}) 文件不存在]",
                    sysFileDO.getBizId(), sysFileDO.getConfigId(), sysFileDO.getPath());
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        ServletUtils.writeAttachment(response, sysFileDO.getName(), content);
    }

    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PreAuthorize("@ss.hasPermission('system:file-config:query')")
    public CommonResult<KlmbPage<SysFileRespVO>> page(@Valid SysFilePageReqVO reqVO) {
        KlmbPage<SysFileDO> klmbPage = KlmbPage.<SysFileDO>builder().pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize()).build();
        SysFileQueryDTO queryDTO = SysFileConvert.INSTANCE.convert(reqVO);
        KlmbPage<SysFileDO> page = sysFileService.page(queryDTO, klmbPage);
        return success(SysFileConvert.INSTANCE.convert(page));
    }

    @GetMapping("/get")
    @Operation(summary = "根据bizId获得文件信息")
    @Parameter(name = "bizId", description = "业务id", required = true)
    @PreAuthorize("@ss.hasPermission('system:notify-template:query')")
    public CommonResult<SysFileRespVO> getFileInfo(
            @RequestParam("bizId") String bizId) {
        SysFileDO sysFileDO = sysFileService.getByBizId(bizId);
        return success(SysFileConvert.INSTANCE.convert(sysFileDO));
    }

}

