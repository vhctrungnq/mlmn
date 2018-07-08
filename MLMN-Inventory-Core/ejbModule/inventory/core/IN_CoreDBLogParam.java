package inventory.core;

import inventory.util.IN_Setting;
import inventory.util.IN_Util;
import inventory.utils.log.IN_DBLogParam;

import java.util.Date;



public class IN_CoreDBLogParam implements IN_DBLogParam {

    private Date selectionTime = null;
    private Date convertTime = null;
    private Date importTime = null;
    private Date finishTime = null;

    private int selectionCnt = 0;
    private int convertCnt = 0;
    private int importCnt = 0;

    public IN_CoreDBLogParam(Date selectionTime, int selectionCnt,
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
                "TO_DATE('" + IN_Util.getTime(selectionTime) + "', '" + IN_Setting.DB_TIME_FORMAT +
                "'), " + selectionCnt + ",";
        sqls[0] +=
                "TO_DATE('" + IN_Util.getTime(convertTime) + "','" + IN_Setting.DB_TIME_FORMAT +
                "'), " + convertCnt + ",";
        sqls[0] +=
                "TO_DATE('" + IN_Util.getTime(importTime) + "','" + IN_Setting.DB_TIME_FORMAT +
                "'), " + importCnt + ",";
        sqls[0] +=
                "TO_DATE('" + IN_Util.getTime(finishTime) + "','" + IN_Setting.DB_TIME_FORMAT +
                "'))";
        return sqls;
    }
}
