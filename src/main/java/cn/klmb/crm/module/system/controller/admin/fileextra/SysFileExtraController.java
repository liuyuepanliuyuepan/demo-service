package cn.klmb.crm.module.system.controller.admin.fileextra;

import static cn.klmb.crm.framework.common.pojo.CommonResult.success;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.base.core.pojo.KlmbPage;
import cn.klmb.crm.framework.base.core.pojo.KlmbScrollPage;
import cn.klmb.crm.framework.common.pojo.CommonResult;
import cn.klmb.crm.module.system.controller.admin.fileextra.vo.SysFileExtraPageReqVO;
import cn.klmb.crm.module.system.controller.admin.fileextra.vo.SysFileExtraReqVO;
import cn.klmb.crm.module.system.controller.admin.fileextra.vo.SysFileExtraRespVO;
import cn.klmb.crm.module.system.controller.admin.fileextra.vo.SysFileExtraSaveReqVO;
import cn.klmb.crm.module.system.controller.admin.fileextra.vo.SysFileExtraScrollPageReqVO;
import cn.klmb.crm.module.system.controller.admin.fileextra.vo.SysFileExtraUpdateReqVO;
import cn.klmb.crm.module.system.convert.fileextra.SysFileExtraConvert;
import cn.klmb.crm.module.system.dto.fileextra.SysFileExtraQueryDTO;
import cn.klmb.crm.module.system.entity.file.SysFileDO;
import cn.klmb.crm.module.system.entity.fileextra.SysFileExtraDO;
import cn.klmb.crm.module.system.entity.user.SysUserDO;
import cn.klmb.crm.module.system.service.file.SysFileService;
import cn.klmb.crm.module.system.service.fileextra.SysFileExtraService;
import cn.klmb.crm.module.system.service.user.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * CRM附件关联 Controller
 *
 * @author 超级管理员
 */
@Api(tags = "0801. CRM附件关联")
@RestController
@RequestMapping("/sys/file-extra")
@Validated
@Slf4j
public class SysFileExtraController {

    private final SysFileExtraService sysFileExtraService;

    private final SysFileService sysFileService;

    private final SysUserService sysUserService;

    public SysFileExtraController(SysFileExtraService sysFileExtraService,
            SysFileService sysFileService, SysUserService sysUserService) {
        this.sysFileExtraService = sysFileExtraService;
        this.sysFileService = sysFileService;
        this.sysUserService = sysUserService;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "上传附件")
    @PermitAll
    public CommonResult<String> save(@Valid @RequestBody SysFileExtraSaveReqVO saveReqVO) {
        SysFileExtraDO saveDO = SysFileExtraConvert.INSTANCE.convert(saveReqVO);
        String bizId = "";
        SysFileDO sysFileDO = sysFileService.getByBizId(saveDO.getFileId());
        if (ObjectUtil.isNotNull(sysFileDO)) {
            saveDO.setName(sysFileDO.getName());
            saveDO.setSize(sysFileDO.getSize());
            saveDO.setSource("附件上传");
        }
        if (sysFileExtraService.saveDO(saveDO)) {
            bizId = saveDO.getBizId();
        }
        return success(bizId);
    }

    @DeleteMapping(value = "/delete/{bizId}")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "主键", dataTypeClass = String.class, paramType = "path")})
    @PermitAll
    public CommonResult<Boolean> deleteByBizId(@PathVariable String bizId) {
        SysFileExtraDO sysFileExtraDO = sysFileExtraService.getByBizId(bizId);
        //同步删除sys_file中的文件
        if (ObjectUtil.isNotNull(sysFileExtraDO) && StrUtil.isNotBlank(
                sysFileExtraDO.getFileId())) {
            try {
                sysFileService.deleteFile(sysFileExtraDO.getFileId());
            } catch (Exception e) {
                log.info("删除附件失败");
            }
        }
        sysFileExtraService.removeByBizIds(Collections.singletonList(bizId));
        return success(true);
    }

    @PutMapping(value = "/rename")
    @ApiOperation(value = "重命名")
    @PermitAll
    public CommonResult<Boolean> update(@Valid @RequestBody SysFileExtraUpdateReqVO updateReqVO) {
        SysFileExtraDO sysFileExtraDO = sysFileExtraService.getByBizId(updateReqVO.getBizId());

        SysFileExtraDO updateDO = SysFileExtraConvert.INSTANCE.convert(updateReqVO);
        sysFileExtraService.updateDO(updateDO);
        //同步修改sys-file表中的文件名
        sysFileService.updateDO(
                SysFileDO.builder().name(updateDO.getName()).bizId(sysFileExtraDO.getFileId())
                        .build());
        return success(true);
    }


    @GetMapping({"/page"})
    @ApiOperation(value = "分页查询")
    @PermitAll
    public CommonResult<KlmbPage<SysFileExtraRespVO>> page(@Valid SysFileExtraPageReqVO reqVO) {
        KlmbPage<SysFileExtraDO> klmbPage = KlmbPage.<SysFileExtraDO>builder()
                .pageNo(reqVO.getPageNo())
                .pageSize(reqVO.getPageSize())
                .build();
        SysFileExtraQueryDTO queryDTO = SysFileExtraConvert.INSTANCE.convert(reqVO);
        KlmbPage<SysFileExtraDO> page = sysFileExtraService.page(queryDTO, klmbPage);
        List<SysFileExtraDO> content = page.getContent();
        if (CollUtil.isNotEmpty(content)) {
            content.forEach(e -> {
                SysUserDO sysUserDO = sysUserService.getByBizId(e.getCreator());
                e.setCreatorName(ObjectUtil.isNotNull(sysUserDO) ? sysUserDO.getNickname() : null);

                SysFileDO sysFileDO = sysFileService.getByBizId(e.getFileId());
                e.setFileUrl(ObjectUtil.isNotNull(sysFileDO) ? sysFileDO.getUrl() : null);
                e.setFileType(ObjectUtil.isNotNull(sysFileDO) ? sysFileDO.getType() : null);
            });
        }
        return success(SysFileExtraConvert.INSTANCE.convert(page));
    }


    @GetMapping({"/page-scroll"})
    @ApiOperation(value = "滚动分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lastBizId", value = "业务id", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "pageSize", value = "每页数量，默认10", paramType = "query", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "asc", value = "是否为正序", paramType = "query", dataTypeClass = Boolean.class)})
    @PermitAll
    public CommonResult<KlmbScrollPage<SysFileExtraRespVO>> pageScroll(
            @Valid SysFileExtraScrollPageReqVO reqVO) {
        KlmbScrollPage<SysFileExtraDO> klmbPage = KlmbScrollPage.<SysFileExtraDO>builder()
                .lastBizId(reqVO.getLastBizId())
                .pageSize(reqVO.getPageSize())
                .asc(reqVO.getAsc())
                .build();
        SysFileExtraQueryDTO queryDTO = SysFileExtraConvert.INSTANCE.convert(reqVO);
        KlmbScrollPage<SysFileExtraDO> page = sysFileExtraService.pageScroll(
                queryDTO, klmbPage);
        List<SysFileExtraDO> content = page.getContent();
        if (CollUtil.isNotEmpty(content)) {
            content.forEach(e -> {
                SysUserDO sysUserDO = sysUserService.getByBizId(e.getCreator());
                e.setCreatorName(ObjectUtil.isNotNull(sysUserDO) ? sysUserDO.getNickname() : null);

                SysFileDO sysFileDO = sysFileService.getByBizId(e.getFileId());
                e.setFileUrl(ObjectUtil.isNotNull(sysFileDO) ? sysFileDO.getUrl() : null);
                e.setFileType(ObjectUtil.isNotNull(sysFileDO) ? sysFileDO.getType() : null);
            });
        }
        KlmbScrollPage<SysFileExtraRespVO> respPage = new KlmbScrollPage<>();
        respPage = SysFileExtraConvert.INSTANCE.convert(page);
        return success(respPage);
    }

    @GetMapping({"/list"})
    @ApiOperation(value = "列表查询")
    @PermitAll
    public CommonResult<List<SysFileExtraRespVO>> list(@Valid SysFileExtraReqVO reqVO) {
        SysFileExtraQueryDTO queryDTO = SysFileExtraConvert.INSTANCE.convert(reqVO);
        List<SysFileExtraDO> content = sysFileExtraService.list(queryDTO);
        if (CollUtil.isNotEmpty(content)) {
            content.forEach(e -> {
                SysUserDO sysUserDO = sysUserService.getByBizId(e.getCreator());
                e.setCreatorName(ObjectUtil.isNotNull(sysUserDO) ? sysUserDO.getNickname() : null);

                SysFileDO sysFileDO = sysFileService.getByBizId(e.getFileId());
                e.setFileUrl(ObjectUtil.isNotNull(sysFileDO) ? sysFileDO.getUrl() : null);
                e.setFileType(ObjectUtil.isNotNull(sysFileDO) ? sysFileDO.getType() : null);
            });
        }
        return success(SysFileExtraConvert.INSTANCE.convert(content));
    }

}
