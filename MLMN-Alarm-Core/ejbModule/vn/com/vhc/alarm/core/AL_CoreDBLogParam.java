package vn.com.vhc.alarm.core;

import java.util.Date;

import vn.com.vhc.alarm.util.AL_Setting;
import vn.com.vhc.alarm.util.AL_Util;
import vn.com.vhc.alarm.utils.log.AL_DBLogParam;


public class AL_CoreDBLogParam implements AL_DBLogParam {

    private Date selectionTime = null;
    private Date convertTime = null;
    private Date importTime = null;
    private Date finishTime = null;

    private int selectionCnt = 0;
    private int convertCnt = 0;
    private int importCnt = 0;

    public AL_CoreDBLogParam(Date selectionTime, int selectionCnt,
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
                "TO_DATE('" + AL_Util.getTime(selectionTime) + "', '" + AL_Setting.DB_TIME_FORMAT +
                "'), " + selectionCnt + ",";
        sqls[0] +=
                "TO_DATE('" + AL_Util.getTime(convertTime) + "','" + AL_Setting.DB_TIME_FORMAT +
                "'), " + convertCnt + ",";
        sqls[0] +=
                "TO_DATE('" + AL_Util.getTime(importTime) + "','" + AL_Setting.DB_TIME_FORMAT +
                "'), " + importCnt + ",";
        sqls[0] +=
                "TO_DATE('" + AL_Util.getTime(finishTime) + "','" + AL_Setting.DB_TIME_FORMAT +
                "'))";
        return sqls;
    }
}
