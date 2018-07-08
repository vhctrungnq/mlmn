package vn.com.vhc.sts.core;

import java.util.Date;

import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.utils.log.STS_DBLogParam;


public class STS_CoreDBLogParam implements STS_DBLogParam {

    private Date selectionTime = null;
    private Date convertTime = null;
    private Date importTime = null;
    private Date finishTime = null;

    private int selectionCnt = 0;
    private int convertCnt = 0;
    private int importCnt = 0;

    public STS_CoreDBLogParam(Date selectionTime, int selectionCnt,
                          Date convertTime, int convertCnt, Date importTime,
                          int importCnt, Date finishTime) {
        this.selectionTime = selectionTime;
        this.selectionCnt = selectionCnt;

        this.convertTime = convertTime;
        this.convertCnt = convertCnt;

        this.importTime = importTime;
        this.importCnt = importCnt;

        this.finishTime = finishTime;
    }

    public String[] getDMLs() {
        String[] sqls = new String[1];
        sqls[0] =
                "INSERT INTO C_CORE_LOGS (SELECTION_TIME,SELECTION_COUNT,CONVERT_TIME,CONVERT_COUNT," +
                "IMPORT_TIME,IMPORT_COUNT,FINISH_TIME) VALUES (";
        sqls[0] +=
                "TO_DATE('" + STS_Util.getTime(selectionTime) + "', '" + STS_Setting.DB_TIME_FORMAT +
                "'), " + selectionCnt + ",";
        sqls[0] +=
                "TO_DATE('" + STS_Util.getTime(convertTime) + "','" + STS_Setting.DB_TIME_FORMAT +
                "'), " + convertCnt + ",";
        sqls[0] +=
                "TO_DATE('" + STS_Util.getTime(importTime) + "','" + STS_Setting.DB_TIME_FORMAT +
                "'), " + importCnt + ",";
        sqls[0] +=
                "TO_DATE('" + STS_Util.getTime(finishTime) + "','" + STS_Setting.DB_TIME_FORMAT +
                "'))";
        return sqls;
    }
}
