package cn.klmb.crm.framework.common.util.data;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.klmb.crm.framework.common.pojo.BiParams;
import cn.klmb.crm.framework.common.util.servlet.ApplicationContextHolder;
import cn.klmb.crm.framework.web.core.util.WebFrameworkUtils;
import cn.klmb.crm.module.system.service.dept.SysDeptService;
import cn.klmb.crm.module.system.service.user.SysUserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhangzhiwei
 */
public class BiTimeUtil {

    public static BiTimeEntity analyzeType(BiParams biParams) {
        BiTimeEntity biTimeEntity;
        if (biParams.getYear() != null) {
            biTimeEntity = analyzeTimeByYear(biParams);
        } else {
            biTimeEntity = analyzeTime(biParams);
        }
        biTimeEntity.setUserIds(analyzeAuth(biParams));
        return biTimeEntity;
    }

    public static List<String> analyzeAuth(BiParams biParams) {
        List<String> userIdList = new ArrayList<>();
        String deptId = biParams.getDeptId();
        String userId = biParams.getUserId();
        Integer isUser = biParams.getIsUser();
        if (isUser == 0) {
            SysUserService sysUserService = ApplicationContextHolder.getBean(SysUserService.class);
            SysDeptService sysDeptService = ApplicationContextHolder.getBean(SysDeptService.class);

            if (deptId == null) {
                deptId = sysUserService.getByBizId(WebFrameworkUtils.getLoginUserId()).getDeptId();
            }
            List<String> deptIdList = sysDeptService.queryChildDept(deptId);
            deptIdList.add(deptId);
            userIdList.addAll(sysUserService.queryUserByDeptIds(deptIdList));
        } else {
            if (userId == null) {
                userIdList.addAll(ApplicationContextHolder.getBean(SysUserService.class)
                        .queryChildUserId(WebFrameworkUtils.getLoginUserId()));
                userIdList.add(WebFrameworkUtils.getLoginUserId());
            } else {
                userIdList.add(userId);
            }
        }
        return userIdList;
    }


    public static BiTimeEntity analyzeTime(BiParams biParams) {
        Date beginDate = DateUtil.date();
        Date endDate = DateUtil.date();
        int cycleNum = 12;
        String sqlDateFormat = "%Y%m";
        String dateFormat = "yyyyMM";
        String type = biParams.getType();
        String startTime = biParams.getStartTime();
        String endTime = biParams.getEndTime();
        if (StrUtil.isNotEmpty(type)) {
            if (type.matches("^[\\d]{8}$")) {
                sqlDateFormat = "%Y%m%d";
                dateFormat = "yyyyMMdd";
            }
            switch (type) {
                case "year":
                    beginDate = DateUtil.beginOfYear(DateUtil.date());
                    endDate = DateUtil.endOfYear(DateUtil.date());
                    break;
                case "lastYear":
                    beginDate = DateUtil.beginOfYear(DateUtil.offsetMonth(DateUtil.date(), -12));
                    endDate = DateUtil.endOfYear(DateUtil.offsetMonth(DateUtil.date(), -12));
                    break;
                case "quarter":
                    beginDate = DateUtil.beginOfQuarter(DateUtil.date());
                    endDate = DateUtil.endOfQuarter(DateUtil.date());
                    cycleNum = 3;
                    break;
                case "lastQuarter":
                    beginDate = DateUtil.beginOfQuarter(DateUtil.offsetMonth(DateUtil.date(), -3));
                    endDate = DateUtil.endOfQuarter(DateUtil.offsetMonth(DateUtil.date(), -3));
                    cycleNum = 3;
                    break;
                case "month":
                    beginDate = DateUtil.beginOfMonth(DateUtil.date());
                    endDate = DateUtil.endOfMonth(DateUtil.date());
                    sqlDateFormat = "%Y%m%d";
                    dateFormat = "yyyyMMdd";
                    cycleNum = (int) DateUtil.between(beginDate, endDate, DateUnit.DAY) + 1;
                    break;
                case "lastMonth":
                    beginDate = DateUtil.beginOfMonth(DateUtil.offsetMonth(DateUtil.date(), -1));
                    endDate = DateUtil.endOfMonth(DateUtil.offsetMonth(DateUtil.date(), -1));
                    sqlDateFormat = "%Y%m%d";
                    dateFormat = "yyyyMMdd";
                    cycleNum = (int) DateUtil.between(beginDate, endDate, DateUnit.DAY) + 1;
                    break;
                case "week":
                    beginDate = DateUtil.beginOfWeek(DateUtil.date());
                    endDate = DateUtil.endOfWeek(DateUtil.date());
                    sqlDateFormat = "%Y%m%d";
                    dateFormat = "yyyyMMdd";
                    cycleNum = 7;
                    break;
                case "lastWeek":
                    beginDate = DateUtil.beginOfWeek(DateUtil.offsetWeek(DateUtil.date(), -1));
                    endDate = DateUtil.endOfWeek(DateUtil.offsetWeek(DateUtil.date(), -1));
                    sqlDateFormat = "%Y%m%d";
                    dateFormat = "yyyyMMdd";
                    cycleNum = 7;
                    break;
                case "today":
                    beginDate = DateUtil.beginOfDay(DateUtil.date());
                    endDate = DateUtil.endOfDay(DateUtil.date());
                    sqlDateFormat = "%Y%m%d";
                    dateFormat = "yyyyMMdd";
                    cycleNum = 1;
                    break;
                case "yesterday":
                    beginDate = DateUtil.beginOfDay(
                            new Date(System.currentTimeMillis() - 86400000));
                    endDate = DateUtil.endOfDay(new Date(System.currentTimeMillis() - 86400000));
                    sqlDateFormat = "%Y%m%d";
                    dateFormat = "yyyyMMdd";
                    cycleNum = 1;
                    break;
                case "nextYear":
                    beginDate = DateUtil.beginOfYear(DateUtil.date());
                    beginDate = DateUtil.offset(beginDate, DateField.YEAR, 1);
                    endDate = DateUtil.endOfYear(DateUtil.date());
                    endDate = DateUtil.offset(endDate, DateField.YEAR, 1);
                    break;
                case "firstHalfYear":
                    beginDate = DateUtil.beginOfYear(DateUtil.date());
                    endDate = DateUtil.offsetMonth(DateUtil.endOfYear(DateUtil.date()), -6);
                    break;
                case "nextHalfYear":
                    beginDate = DateUtil.offsetMonth(DateUtil.beginOfYear(DateUtil.date()), 6);
                    endDate = DateUtil.endOfYear(DateUtil.date());
                    break;
                case "nextQuarter":
                    beginDate = DateUtil.beginOfQuarter(DateUtil.date().offset(DateField.MONTH, 3));
                    endDate = DateUtil.endOfQuarter(DateUtil.date().offset(DateField.MONTH, 3));
                    break;
                case "nextMonth":
                    beginDate = DateUtil.beginOfMonth(DateUtil.date().offset(DateField.MONTH, 1));
                    endDate = DateUtil.beginOfMonth(DateUtil.date().offset(DateField.MONTH, 1));
                    sqlDateFormat = "%Y%m%d";
                    dateFormat = "yyyyMMdd";
                    break;
                case "nextWeek":
                    beginDate = DateUtil.beginOfWeek(
                            DateUtil.date().offset(DateField.DAY_OF_YEAR, 7));
                    endDate = DateUtil.endOfWeek(DateUtil.date().offset(DateField.DAY_OF_YEAR, 7));
                    sqlDateFormat = "%Y%m%d";
                    dateFormat = "yyyyMMdd";
                    break;
                case "tomorrow":
                    beginDate = DateUtil.beginOfDay(
                            DateUtil.date().offset(DateField.DAY_OF_YEAR, 1));
                    endDate = DateUtil.endOfDay(DateUtil.date().offset(DateField.DAY_OF_YEAR, 1));
                    sqlDateFormat = "%Y%m%d";
                    dateFormat = "yyyyMMdd";
                    break;
                case "previous7day":
                    beginDate = DateUtil.beginOfDay(
                            DateUtil.date().offset(DateField.DAY_OF_YEAR, -7));
                    endDate = DateUtil.endOfDay(DateUtil.date().offset(DateField.DAY_OF_YEAR, -1));
                    sqlDateFormat = "%Y%m%d";
                    dateFormat = "yyyyMMdd";
                    break;
                case "previous30day":
                    beginDate = DateUtil.beginOfDay(
                            DateUtil.date().offset(DateField.DAY_OF_YEAR, -30));
                    endDate = DateUtil.endOfDay(DateUtil.date().offset(DateField.DAY_OF_YEAR, -1));
                    sqlDateFormat = "%Y%m%d";
                    dateFormat = "yyyyMMdd";
                    break;
                case "future7day":
                    beginDate = DateUtil.beginOfDay(
                            DateUtil.date().offset(DateField.DAY_OF_YEAR, 1));
                    endDate = DateUtil.endOfDay(DateUtil.date().offset(DateField.DAY_OF_YEAR, 7));
                    sqlDateFormat = "%Y%m%d";
                    dateFormat = "yyyyMMdd";
                    break;
                case "future30day":
                    beginDate = DateUtil.beginOfDay(
                            DateUtil.date().offset(DateField.DAY_OF_YEAR, 1));
                    endDate = DateUtil.endOfDay(DateUtil.date().offset(DateField.DAY_OF_YEAR, 30));
                    sqlDateFormat = "%Y%m%d";
                    dateFormat = "yyyyMMdd";
                    break;
                default:
                    break;
            }
        } else if (StrUtil.isNotEmpty(startTime) && StrUtil.isNotEmpty(endTime)) {
            Date start;
            Date end;
            if (startTime.length() == 6) {
                start = DateUtil.parse(startTime, "yyyyMM");
                end = DateUtil.endOfMonth(DateUtil.parse(endTime, "yyyyMM"));
            } else {
                start = DateUtil.parse(startTime);
                end = DateUtil.parse(endTime);
            }
            Integer startMonth = Integer.valueOf(DateUtil.format(start, "yyyyMM"));
            Integer endMonth = Integer.valueOf(DateUtil.format(end, "yyyyMM"));
            if (startMonth.equals(endMonth)) {
                sqlDateFormat = "%Y%m%d";
                dateFormat = "yyyyMMdd";
                Long diffDay = DateUtil.between(start, end, DateUnit.DAY);
                cycleNum = diffDay.intValue() + 1;
            } else {
                sqlDateFormat = "%Y%m";
                dateFormat = "yyyyMM";
                int diffYear =
                        Integer.valueOf(endMonth.toString().substring(0, 4)) - Integer.valueOf(
                                startMonth.toString().substring(0, 4));
                int diffMonth = endMonth % 100 - startMonth % 100 + 1;
                cycleNum = diffYear * 12 + diffMonth;
            }
            beginDate = start;
            endDate = end;
        }
        Integer beginTime = Integer.valueOf(DateUtil.format(beginDate, dateFormat));
        Integer finalTime = Integer.valueOf(DateUtil.format(endDate, dateFormat));
        return new BiTimeEntity(sqlDateFormat, dateFormat, beginDate, endDate, cycleNum, beginTime,
                finalTime, new ArrayList<>());
    }

    public static BiTimeEntity analyzeTimeByYear(BiParams biParams) {
        Date date = DateUtil.parse(biParams.getYear().toString(), "yyyy");
        Date beginDate = DateUtil.beginOfYear(date);
        Date endDate = DateUtil.endOfYear(date);
        int cycleNum = 12;
        String sqlDateFormat = "%Y%m";
        String dateFormat = "yyyyMM";
        String type = biParams.getType();
        String startTime = biParams.getStartTime();
        String endTime = biParams.getEndTime();
        if (StrUtil.isNotEmpty(type)) {
            if (type.matches("^[\\d]{8}$")) {
                sqlDateFormat = "%Y%m%d";
                dateFormat = "yyyyMMdd";
            }
            switch (type) {
                case "year":
                    beginDate = DateUtil.beginOfYear(date);
                    endDate = DateUtil.endOfYear(date);
                    break;
                case "lastYear":
                    beginDate = DateUtil.beginOfYear(DateUtil.offsetMonth(date, -12));
                    endDate = DateUtil.endOfYear(DateUtil.offsetMonth(date, -12));
                    break;
                case "quarter":
                    beginDate = DateUtil.beginOfQuarter(date);
                    endDate = DateUtil.endOfQuarter(date);
                    cycleNum = 3;
                    break;
                case "lastQuarter":
                    beginDate = DateUtil.beginOfQuarter(DateUtil.offsetMonth(date, -3));
                    endDate = DateUtil.endOfQuarter(DateUtil.offsetMonth(date, -3));
                    cycleNum = 3;
                    break;
                case "month":
                    beginDate = DateUtil.beginOfMonth(DateUtil.date());
                    endDate = DateUtil.endOfMonth(DateUtil.date());
                    sqlDateFormat = "%Y%m%d";
                    dateFormat = "yyyyMMdd";
                    cycleNum = (int) DateUtil.between(beginDate, endDate, DateUnit.DAY) + 1;
                    break;
                case "lastMonth":
                    beginDate = DateUtil.beginOfMonth(DateUtil.offsetMonth(DateUtil.date(), -1));
                    endDate = DateUtil.endOfMonth(DateUtil.offsetMonth(DateUtil.date(), -1));
                    sqlDateFormat = "%Y%m%d";
                    dateFormat = "yyyyMMdd";
                    cycleNum = (int) DateUtil.between(beginDate, endDate, DateUnit.DAY) + 1;
                    break;
                case "week":
                    beginDate = DateUtil.beginOfWeek(DateUtil.date());
                    endDate = DateUtil.endOfWeek(DateUtil.date());
                    sqlDateFormat = "%Y%m%d";
                    dateFormat = "yyyyMMdd";
                    cycleNum = 7;
                    break;
                case "lastWeek":
                    beginDate = DateUtil.beginOfWeek(DateUtil.offsetWeek(DateUtil.date(), -1));
                    endDate = DateUtil.endOfWeek(DateUtil.offsetWeek(DateUtil.date(), -1));
                    sqlDateFormat = "%Y%m%d";
                    dateFormat = "yyyyMMdd";
                    cycleNum = 7;
                    break;
                case "today":
                    beginDate = DateUtil.beginOfDay(DateUtil.date());
                    endDate = DateUtil.endOfDay(DateUtil.date());
                    sqlDateFormat = "%Y%m%d";
                    dateFormat = "yyyyMMdd";
                    cycleNum = 1;
                    break;
                case "yesterday":
                    beginDate = DateUtil.beginOfDay(
                            new Date(System.currentTimeMillis() - 86400000));
                    endDate = DateUtil.endOfDay(new Date(System.currentTimeMillis() - 86400000));
                    sqlDateFormat = "%Y%m%d";
                    dateFormat = "yyyyMMdd";
                    cycleNum = 1;
                    break;
                case "nextYear":
                    beginDate = DateUtil.beginOfYear(DateUtil.date());
                    beginDate = DateUtil.offset(beginDate, DateField.YEAR, 1);
                    endDate = DateUtil.endOfYear(DateUtil.date());
                    endDate = DateUtil.offset(endDate, DateField.YEAR, 1);
                    break;
                case "firstHalfYear":
                    beginDate = DateUtil.beginOfYear(DateUtil.date());
                    endDate = DateUtil.offsetMonth(DateUtil.endOfYear(DateUtil.date()), -6);
                    break;
                case "nextHalfYear":
                    beginDate = DateUtil.offsetMonth(DateUtil.beginOfYear(DateUtil.date()), 6);
                    endDate = DateUtil.endOfYear(DateUtil.date());
                    break;
                case "nextQuarter":
                    beginDate = DateUtil.beginOfQuarter(DateUtil.date().offset(DateField.MONTH, 3));
                    endDate = DateUtil.endOfQuarter(DateUtil.date().offset(DateField.MONTH, 3));
                    break;
                case "nextMonth":
                    beginDate = DateUtil.beginOfMonth(DateUtil.date().offset(DateField.MONTH, 1));
                    endDate = DateUtil.beginOfMonth(DateUtil.date().offset(DateField.MONTH, 1));
                    break;
                case "nextWeek":
                    beginDate = DateUtil.beginOfWeek(
                            DateUtil.date().offset(DateField.DAY_OF_YEAR, 7));
                    endDate = DateUtil.endOfWeek(DateUtil.date().offset(DateField.DAY_OF_YEAR, 7));
                    break;
                case "tomorrow":
                    beginDate = DateUtil.beginOfDay(
                            DateUtil.date().offset(DateField.DAY_OF_YEAR, 1));
                    endDate = DateUtil.endOfDay(DateUtil.date().offset(DateField.DAY_OF_YEAR, 1));
                    break;
                case "previous7day":
                    beginDate = DateUtil.beginOfDay(
                            DateUtil.date().offset(DateField.DAY_OF_YEAR, -7));
                    endDate = DateUtil.endOfDay(DateUtil.date().offset(DateField.DAY_OF_YEAR, -1));
                    break;
                case "previous30day":
                    beginDate = DateUtil.beginOfDay(
                            DateUtil.date().offset(DateField.DAY_OF_YEAR, -30));
                    endDate = DateUtil.endOfDay(DateUtil.date().offset(DateField.DAY_OF_YEAR, -1));
                    break;
                case "future7day":
                    beginDate = DateUtil.beginOfDay(
                            DateUtil.date().offset(DateField.DAY_OF_YEAR, 1));
                    endDate = DateUtil.endOfDay(DateUtil.date().offset(DateField.DAY_OF_YEAR, 7));
                    break;
                case "future30day":
                    beginDate = DateUtil.beginOfDay(
                            DateUtil.date().offset(DateField.DAY_OF_YEAR, 1));
                    endDate = DateUtil.endOfDay(DateUtil.date().offset(DateField.DAY_OF_YEAR, 30));
                    break;
                default:
                    break;
            }
        } else if (StrUtil.isNotEmpty(startTime) && StrUtil.isNotEmpty(endTime)) {
            Date start;
            Date end;
            if (startTime.length() == 6) {
                start = DateUtil.parse(startTime, "yyyyMM");
                end = DateUtil.endOfMonth(DateUtil.parse(endTime, "yyyyMM"));
            } else {
                start = DateUtil.parse(startTime);
                end = DateUtil.parse(endTime);
            }
            Integer startMonth = Integer.valueOf(DateUtil.format(start, "yyyyMM"));
            Integer endMonth = Integer.valueOf(DateUtil.format(end, "yyyyMM"));
            if (startMonth.equals(endMonth)) {
                sqlDateFormat = "%Y%m%d";
                dateFormat = "yyyyMMdd";
                Long diffDay = DateUtil.between(start, end, DateUnit.DAY);
                cycleNum = diffDay.intValue() + 1;
            } else {
                sqlDateFormat = "%Y%m";
                dateFormat = "yyyyMM";
                int diffYear =
                        Integer.valueOf(endMonth.toString().substring(0, 4)) - Integer.valueOf(
                                startMonth.toString().substring(0, 4));
                int diffMonth = endMonth % 100 - startMonth % 100 + 1;
                cycleNum = diffYear * 12 + diffMonth;
            }
            beginDate = start;
            endDate = end;
        }
        Integer beginTime = Integer.valueOf(DateUtil.format(beginDate, dateFormat));
        Integer finalTime = Integer.valueOf(DateUtil.format(endDate, dateFormat));
        return new BiTimeEntity(sqlDateFormat, dateFormat, beginDate, endDate, cycleNum, beginTime,
                finalTime, new ArrayList<>());
    }


    public static Integer estimateTime(Integer beginTime) {
        if (beginTime < 1000000 && beginTime % 100 == 12) {
            beginTime = beginTime + 89;
        } else if (beginTime > 1000000) {
            int month = DateUtil.month(DateUtil.parse(beginTime.toString(), "yyyyMMdd")) + 1;
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10) {
                if (beginTime % 100 == 31) {
                    beginTime = beginTime + 70;
                } else {
                    beginTime++;
                }
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                if (beginTime % 100 == 30) {
                    beginTime = beginTime + 71;
                } else {
                    beginTime++;
                }
            } else if (month == 2) {
                int year = DateUtil.year(DateUtil.parse(beginTime.toString(), "yyyyMMdd"));
                if (DateUtil.isLeapYear(year) && beginTime % 100 == 29) {
                    beginTime = beginTime + 72;
                } else if (!DateUtil.isLeapYear(year) && beginTime % 100 == 28) {
                    beginTime = beginTime + 73;
                } else {
                    beginTime++;
                }
            } else if (month == 12) {
                if (beginTime % 100 == 31) {
                    beginTime = beginTime + 8870;
                } else {
                    beginTime++;
                }
            }
        } else {
            beginTime++;
        }
        return beginTime;
    }

    public static Integer analyzeType(String type) {
        int status;
        if (type == null) {
            return 11;
        }
        switch (type) {
            case "year":
                status = 9;
                break;
            case "lastYear":
                status = 10;
                break;
            case "quarter":
                status = 7;
                break;
            case "lastQuarter":
                status = 8;
                break;
            case "month":
                status = 5;
                break;
            case "lastMonth":
                status = 6;
                break;
            case "week":
                status = 3;
                break;
            case "lastWeek":
                status = 4;
                break;
            case "today":
                status = 1;
                break;
            case "yesterday":
                status = 2;
                break;
            default:
                status = 11;
                break;
        }
        return status;
    }

    public static String[] getAddress() {
        return new String[]{"北京", "上海", "天津", "广东", "浙江",
                "海南", "福建", "湖南", "湖北", "重庆", "辽宁", "吉林",
                "黑龙江", "河南", "河北", "陕西", "甘肃", "宁夏", "西藏",
                "新疆", "青海", "四川", "广西", "贵州", "江西", "江苏",
                "云南", "内蒙古", "山东", "山西", "安徽"};
    }

    /**
     * 计算与上次的比较
     *
     * @param record 类型
     */
    public static BiTimeEntity prevAnalyzeType(BiParams record) {
        Date beginDate = DateUtil.date();
        Date endDate = DateUtil.date();
        int cycleNum = 12;
        String sqlDateFormat = "%Y%m";
        String dateFormat = "yyyyMM";
        String deptId = record.getDeptId();
        String userId = record.getUserId();
        String type = record.getType();
        if (StrUtil.isNotEmpty(type)) {
            if ("year".equals(type)) {
                beginDate = DateUtil.beginOfYear(DateUtil.offsetMonth(DateUtil.date(), -12));
                endDate = DateUtil.endOfYear(DateUtil.offsetMonth(DateUtil.date(), -12));
            } else if ("lastYear".equals(type)) {
                beginDate = DateUtil.beginOfYear(DateUtil.offsetMonth(DateUtil.date(), -24));
                endDate = DateUtil.endOfYear(DateUtil.offsetMonth(DateUtil.date(), -24));
            } else if ("quarter".equals(type)) {
                beginDate = DateUtil.beginOfQuarter(DateUtil.offsetMonth(DateUtil.date(), -3));
                endDate = DateUtil.endOfQuarter(DateUtil.offsetMonth(DateUtil.date(), -3));
                cycleNum = 3;
            } else if ("lastQuarter".equals(type)) {
                beginDate = DateUtil.beginOfQuarter(DateUtil.offsetMonth(DateUtil.date(), -6));
                endDate = DateUtil.endOfQuarter(DateUtil.offsetMonth(DateUtil.date(), -6));
                cycleNum = 3;
            } else if ("month".equals(type)) {
                beginDate = DateUtil.beginOfMonth(DateUtil.offsetMonth(DateUtil.date(), -1));
                endDate = DateUtil.endOfMonth(DateUtil.offsetMonth(DateUtil.date(), -1));
                sqlDateFormat = "%Y%m%d";
                dateFormat = "yyyyMMdd";
                cycleNum = (int) DateUtil.between(beginDate, endDate, DateUnit.DAY) + 1;
            } else if ("lastMonth".equals(type)) {
                beginDate = DateUtil.beginOfMonth(DateUtil.offsetMonth(DateUtil.date(), -2));
                endDate = DateUtil.endOfMonth(DateUtil.offsetMonth(DateUtil.date(), -2));
                sqlDateFormat = "%Y%m%d";
                dateFormat = "yyyyMMdd";
                cycleNum = (int) DateUtil.between(beginDate, endDate, DateUnit.DAY) + 1;
            } else if ("week".equals(type)) {
                beginDate = DateUtil.beginOfWeek(DateUtil.offsetWeek(DateUtil.date(), -1));
                endDate = DateUtil.endOfWeek(DateUtil.offsetWeek(DateUtil.date(), -1));
                sqlDateFormat = "%Y%m%d";
                dateFormat = "yyyyMMdd";
                cycleNum = 7;
            } else if ("lastWeek".equals(type)) {
                beginDate = DateUtil.beginOfWeek(DateUtil.offsetWeek(DateUtil.date(), -2));
                endDate = DateUtil.endOfWeek(DateUtil.offsetWeek(DateUtil.date(), -2));
                sqlDateFormat = "%Y%m%d";
                dateFormat = "yyyyMMdd";
                cycleNum = 7;
            } else if ("today".equals(type)) {
                beginDate = DateUtil.offsetDay(DateUtil.date(), -1);
                endDate = DateUtil.offsetDay(DateUtil.date(), -1);
                sqlDateFormat = "%Y%m%d";
                dateFormat = "yyyyMMdd";
                cycleNum = 1;
            } else if ("yesterday".equals(type)) {
                beginDate = DateUtil.offsetDay(DateUtil.date(), -2);
                endDate = DateUtil.offsetDay(DateUtil.date(), -2);
                sqlDateFormat = "%Y%m%d";
                dateFormat = "yyyyMMdd";
                cycleNum = 1;
            }
        }
        Integer beginTime = Integer.valueOf(DateUtil.format(beginDate, dateFormat));
        Integer finalTime = Integer.valueOf(DateUtil.format(endDate, dateFormat));
        BiTimeEntity timeEntity = new BiTimeEntity(sqlDateFormat, dateFormat, beginDate, endDate,
                cycleNum, beginTime, finalTime, new ArrayList<>());
        if (userId != null) {
            timeEntity.getUserIds().add(userId);
        } else if (deptId != null) {
            SysUserService sysUserService = ApplicationContextHolder.getBean(SysUserService.class);
            SysDeptService sysDeptService = ApplicationContextHolder.getBean(SysDeptService.class);

            List<String> data = sysDeptService.queryChildDept(deptId);
            timeEntity.getUserIds().addAll(sysUserService.queryUserByDeptIds(data));
        }
        return timeEntity;
    }


    @Data
    @Accessors(chain = true)
    public static class BiTimeEntity {

        /**
         * sql日期格式化
         */
        private String sqlDateFormat;

        /**
         * 日期格式化
         */
        private String dateFormat;

        /**
         * 开始时间
         */
        private Date beginDate;

        /**
         * 结束时间
         */
        private Date endDate;

        /**
         * 周期
         */
        private Integer cycleNum;

        /**
         * 开始时间 字符串格式 如20200101
         */
        private Integer beginTime;

        /**
         * 结束时间 字符串格式 如20200101
         */
        private Integer finalTime;

        /**
         * user列表
         */
        private List<String> userIds = new ArrayList<>();
        private Integer page;
        private Integer limit;

        public BiTimeEntity(String sqlDateFormat, String dateFormat, Date beginDate, Date endDate,
                Integer cycleNum, Integer beginTime, Integer finalTime, List<String> userIds) {
            this.sqlDateFormat = sqlDateFormat;
            this.dateFormat = dateFormat;
            this.beginDate = beginDate;
            this.endDate = endDate;
            this.cycleNum = cycleNum;
            this.beginTime = beginTime;
            this.finalTime = finalTime;
            this.userIds = userIds;
        }

        public BiTimeEntity() {
        }

        public Map<String, Object> toMap() {
            return BeanUtil.beanToMap(this);
        }
    }
}
