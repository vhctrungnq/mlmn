package vn.com.vhc.alarm.cni;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import vn.com.vhc.alarm.util.AL_Setting;



public class AL_TestImport {

    public AL_TestImport() {
        System.out.println("//--------------- THE START IMPORT--------------//");

        //        final Context context;
        //        try {
        //            context = getInitialContext();
        //            DataSource ds = (DataSource)context.lookup("jdbc/pms2DS");
        //            if (ds != null) {
        //                System.out.println("start time: " +
        //                                   CNISetting.getCurrentTime());
        //
        //                //ImportDirectlyService ids = new ImportDirectlyService(ds);
        //                //ids.processTasks();
        //                countFileColumn();
        //
        //                System.out.println("end time: " + CNISetting.getCurrentTime());
        //                //this.demo(ds);
        //            } else {
        //                System.out.println("Khong tao duoc DataSource");
        //            }
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }

        //recreateMappingData();
        //testFileImporter();
        //createMapping();
        //String p = "([S,M]{1}[0-9,A-z]{6}).(.*)";
       // String fileName = "AlarmLog.2015111206";
        //boolean b = patternFile(fileName, p);
        testPattern();

        System.out.println("//---------------- THE END IMPORT --------------//");
    }

    public static void main(String[] args) {
        new AL_TestImport();
    }

    @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	private static Context getInitialContext() throws NamingException {
        Hashtable env = new Hashtable();
        // WebLogic Server 10.x connection details
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://192.168.20.8:8080");
        return new InitialContext(env);
    }
    
    private void testPattern() {
        String p = "(NSN_2G_CFG_C2)_([0-9]{8}[0-9]{2}[0-9]{2})(.*)";

        String fileName =
            "NSN_2G_CFG_C2_201702100550.csv";

        Pattern pattern = Pattern.compile(p);
        Matcher m = pattern.matcher(fileName);
        if (m.find()) {
            for (int i = 0; i < m.groupCount(); i++) {
                System.out.println(m.group(i).trim());
            } 
        }else{
        	System.out.println("error");
        } 
    }

    @SuppressWarnings("unused")
	private void test() {
        String pattern = "1278083468";

        String temp =
            "Result Time,Granularity Period,Object Name,Reliability,1278073417,1278073418,1278073419,1278073420,1278073421,1278073422,1278073423,1278073424,1278073425,1278073426,1278073427,1278073428,1278073429,1278073430,1278073431,1278073432,1278073433,1278073434,1278073435,1278073436,1278073437,1278073438,1278073439,1278073440,1278073441,1278073442,1278073443,1278073444,1278073445,1278073446,1278073447,1278073448,1278073449,1278073450,1278073451,1278073452,1278073453,1278073454,1278073455,1278073456,1278073457,1278073458,1278073459,1278073460,1278073461,1278073462,1278073463,1278073464,1278073465,1278073466,1278073467,1278073468,1278073469,1278073470,1278073471,1278073472,1278073473,1278073474,1278073475,1278073476,1278073477,1278073478,1278073479,1278073480,1278073481,1278073482,1278073483,1278073484,1278073485,1278073486,1278073487,1278073488,1278073489,1278073490,1278073491,1278073492,1278073493,1278073494,1278073495,1278073496,1278073497,1278073498,1278073499,1278073500";
        String list[] = temp.split(",");
        for (int i = 0; i < list.length; i++) {
            if (list[i].equalsIgnoreCase(pattern)) {
                System.out.println("Index: " + i);
                break;
            }
        }
    }

    private int getColumnIndex(String[] columnNames, String col) {
        for (int i = 0; i < columnNames.length; i++) {
            if (col.equalsIgnoreCase(columnNames[i])) {
                return i;
            }
        }
        return -1;
    }

    @SuppressWarnings("unused")
	private void createMapping() {
        String patternId = "89";
        String tableName = "R_A_MFS";
        String timeFormat = "yyyy-MM-dd";
        String separator = "\t";
        String header =
            "DAY	HOUR	BSS	FABRIC	BTS	CI	LAC	MCC	MNC	BS_PBCCH_BLKS	BS_PRACH_BLKS	CSSF	DSF	EGPRS_LLC_THROUGHPUT_THR_1	EGPRS_LLC_THROUGHPUT_THR_10	EGPRS_LLC_THROUGHPUT_THR_11	EGPRS_LLC_THROUGHPUT_THR_2	EGPRS_LLC_THROUGHPUT_THR_3	EGPRS_LLC_THROUGHPUT_THR_4	EGPRS_LLC_THROUGHPUT_THR_5	EGPRS_LLC_THROUGHPUT_THR_6	EGPRS_LLC_THROUGHPUT_THR_7	EGPRS_LLC_THROUGHPUT_THR_8	EGPRS_LLC_THROUGHPUT_THR_9	GPRS_LLC_THROUGHPUT_THR_1	GPRS_LLC_THROUGHPUT_THR_10	GPRS_LLC_THROUGHPUT_THR_2	GPRS_LLC_THROUGHPUT_THR_3	GPRS_LLC_THROUGHPUT_THR_4	GPRS_LLC_THROUGHPUT_THR_5	GPRS_LLC_THROUGHPUT_THR_6	GPRS_LLC_THROUGHPUT_THR_7	GPRS_LLC_THROUGHPUT_THR_8	GPRS_LLC_THROUGHPUT_THR_9	MAX_PDCH	MIN_PDCH	P10	P100C	P100D	P100E	P105C	P105D	P105E	P105F	P105G	P105H	P105I	P105J	P105K	P105L	P11	P129A	P129B	P129C	P129D	P13	P14	P146	P147	P15	P16	P160	P161	P162	P163	P164	P165	P200	P203	P204	P207	P208	P209	P20A	P20B	P20C	P20D	P20F	P20G	P20H	P20I	P20J	P20K	P20L	P20M	P20N	P210	P211	P212	P213	P214	P215	P216	P217	P218	P219	P21A	P21B	P21C	P21D	P21F	P21G	P21H	P21I	P21J	P21K	P21L	P21M	P21N	P22	P220	P221	P222	P223	P224	P225	P226	P227	P228	P229	P231	P232	P24	P26	P27	P28	P302B_1	P302B_2	P302C_1	P302C_2	P302C_3	P302C_4	P303A	P303B	P30A	P30B	P30C	P30D	P310A	P310B	P310C	P310D	P335	P336	P35	P350A	P350B	P351A	P351B	P352A	P352B	P36	P385A	P385B	P38B	P38C	P38E	P38F	P39	P396A	P396B	P397	P399	P40	P400	P401	P403A	P403B	P403C	P403D	P404A	P404B	P404C	P404D	P405A	P405B	P405C	P405D	P406A	P406B	P406C	P406D	P407A	P407B	P407C	P407D	P408A	P408B	P408C	P408D	P409	P410	P411	P412	P413	P414BIS	P415BIS	P416BIS	P417	P419	P420	P421	P422	P423A	P423B	P423C	P423D	P424A	P424B	P424C	P424D	P425A	P425B	P425C	P425D	P426A	P426B	P426C	P426D	P43	P431B	P431C	P432B	P432C	P433A	P433B	P433C	P433D	P434A	P434B	P434C	P434D	P435A	P435B	P435C	P435D	P436	P437A	P437B	P438A	P438B	P438C	P438D	P439	P43A	P43B	P43C	P43D	P44	P440A	P440B	P440C	P441A	P441B	P441C	P44A	P44B	P44C	P44D	P451A	P451B	P452	P453A_1	P453A_10	P453A_2	P453A_3	P453A_4	P453A_5	P453A_6	P453A_7	P453A_8	P453A_9	P453B_1	P453B_10	P453B_2	P453B_3	P453B_4	P453B_5	P453B_6	P453B_7	P453B_8	P453B_9	P454A_1	P454A_10	P454A_2	P454A_3	P454A_4	P454A_5	P454A_6	P454A_7	P454A_8	P454A_9	P454B_1	P454B_10	P454B_2	P454B_3	P454B_4	P454B_5	P454B_6	P454B_7	P454B_8	P454B_9	P455A_1	P455A_10	P455A_2	P455A_3	P455A_4	P455A_5	P455A_6	P455A_7	P455A_8	P455A_9	P455B_1	P455B_10	P455B_2	P455B_3	P455B_4	P455B_5	P455B_6	P455B_7	P455B_8	P455B_9	P456	P457	P458	P459	P460B	P460C	P461	P462	P463	P464	P465	P466	P469	P470	P471	P480	P481	P482	P488	P489	P49	P490	P491	P492	P493	P494	P495	P496	P497	P498	P499	P501	P502	P503	P504	P505	P506	P507	P508	P509	P510	P511	P512	P513	P514	P515	P516	P518	P519	P52A	P52B	P52C	P52D	P531A_1	P531A_10	P531A_11	P531A_2	P531A_3	P531A_4	P531A_5	P531A_6	P531A_7	P531A_8	P531A_9	P531B_1	P531B_10	P531B_11	P531B_2	P531B_3	P531B_4	P531B_5	P531B_6	P531B_7	P531B_8	P531B_9	P532A_1	P532A_10	P532A_2	P532A_3	P532A_4	P532A_5	P532A_6	P532A_7	P532A_8	P532A_9	P532B_1	P532B_10	P532B_2	P532B_3	P532B_4	P532B_5	P532B_6	P532B_7	P532B_8	P532B_9	P533A_1	P533A_10	P533A_11	P533A_12	P533A_13	P533A_2	P533A_3	P533A_4	P533A_5	P533A_6	P533A_7	P533A_8	P533A_9	P533B_1	P533B_10	P533B_11	P533B_12	P533B_13	P533B_2	P533B_3	P533B_4	P533B_5	P533B_6	P533B_7	P533B_8	P533B_9	P534A_1	P534A_10	P534A_11	P534A_12	P534A_13	P534A_2	P534A_3	P534A_4	P534A_5	P534A_6	P534A_7	P534A_8	P534A_9	P534B_1	P534B_10	P534B_11	P534B_12	P534B_13	P534B_2	P534B_3	P534B_4	P534B_5	P534B_6	P534B_7	P534B_8	P534B_9	P535A_1	P535A_10	P535A_11	P535A_12	P535A_13	P535A_2	P535A_3	P535A_4	P535A_5	P535A_6	P535A_7	P535A_8	P535A_9	P535B_1	P535B_10	P535B_11	P535B_12	P535B_13	P535B_2	P535B_3	P535B_4	P535B_5	P535B_6	P535B_7	P535B_8	P535B_9	P53A	P53B	P53C	P558	P559	P55A	P55B	P55C	P55D	P55E	P55F	P55G	P55H	P55I	P55J	P55K	P55L	P55M	P560	P561	P562	P563	P564	P565	P566	P567	P568	P569	P570	P571	P572	P573	P574	P575	P576	P577	P578	P579	P57A	P57B	P57C	P57D	P57E	P57F	P57G	P57H	P57I	P57J	P57K	P57L	P57M	P59	P595	P596	P597	P598	P599	P60	P61	P61A	P61B	P62A	P62B	P62C	P62D	P65	P66	P67	P72C	P72D	P73C	P73D	P74	P75	P800	P801	P802	P803	P804	P805	P806	P807	P808	P809	P810	P811	P812	P813	P814	P815	P816	P817	P818	P819	P820	P821	P822	P823	P824	P825	P826	P827	P828	P829	P830	P831	P832	P833	P834	P835	P836	P837	P9	P90A	P90B	P90C	P90D	P90E	P90F	P90G	P91A	P91B	P91C	P91D	P91E	P91F	P91G	P95	P96	P97	P98A	P98B	P98C	P98D	P98E	P98F	P99	PD_DL_PDCH_UNIT_ALLOC_THR_1	PD_DL_PDCH_UNIT_ALLOC_THR_10	PD_DL_PDCH_UNIT_ALLOC_THR_2	PD_DL_PDCH_UNIT_ALLOC_THR_3	PD_DL_PDCH_UNIT_ALLOC_THR_4	PD_DL_PDCH_UNIT_ALLOC_THR_5	PD_DL_PDCH_UNIT_ALLOC_THR_6	PD_DL_PDCH_UNIT_ALLOC_THR_7	PD_DL_PDCH_UNIT_ALLOC_THR_8	PD_DL_PDCH_UNIT_ALLOC_THR_9	PD_DL_TBF_DURATION_THR_1	PD_DL_TBF_DURATION_THR_10	PD_DL_TBF_DURATION_THR_2	PD_DL_TBF_DURATION_THR_3	PD_DL_TBF_DURATION_THR_4	PD_DL_TBF_DURATION_THR_5	PD_DL_TBF_DURATION_THR_6	PD_DL_TBF_DURATION_THR_7	PD_DL_TBF_DURATION_THR_8	PD_DL_TBF_DURATION_THR_9	PD_DL_TBF_VOLUME_THR_1	PD_DL_TBF_VOLUME_THR_10	PD_DL_TBF_VOLUME_THR_2	PD_DL_TBF_VOLUME_THR_3	PD_DL_TBF_VOLUME_THR_4	PD_DL_TBF_VOLUME_THR_5	PD_DL_TBF_VOLUME_THR_6	PD_DL_TBF_VOLUME_THR_7	PD_DL_TBF_VOLUME_THR_8	PD_DL_TBF_VOLUME_THR_9	PD_UL_PDCH_UNIT_ALLOC_THR_1	PD_UL_PDCH_UNIT_ALLOC_THR_10	PD_UL_PDCH_UNIT_ALLOC_THR_2	PD_UL_PDCH_UNIT_ALLOC_THR_3	PD_UL_PDCH_UNIT_ALLOC_THR_4	PD_UL_PDCH_UNIT_ALLOC_THR_5	PD_UL_PDCH_UNIT_ALLOC_THR_6	PD_UL_PDCH_UNIT_ALLOC_THR_7	PD_UL_PDCH_UNIT_ALLOC_THR_8	PD_UL_PDCH_UNIT_ALLOC_THR_9	PD_UL_TBF_DURATION_THR_1	PD_UL_TBF_DURATION_THR_10	PD_UL_TBF_DURATION_THR_2	PD_UL_TBF_DURATION_THR_3	PD_UL_TBF_DURATION_THR_4	PD_UL_TBF_DURATION_THR_5	PD_UL_TBF_DURATION_THR_6	PD_UL_TBF_DURATION_THR_7	PD_UL_TBF_DURATION_THR_8	PD_UL_TBF_DURATION_THR_9	PD_UL_TBF_VOLUME_THR_1	PD_UL_TBF_VOLUME_THR_10	PD_UL_TBF_VOLUME_THR_2	PD_UL_TBF_VOLUME_THR_3	PD_UL_TBF_VOLUME_THR_4	PD_UL_TBF_VOLUME_THR_5	PD_UL_TBF_VOLUME_THR_6	PD_UL_TBF_VOLUME_THR_7	PD_UL_TBF_VOLUME_THR_8	PD_UL_TBF_VOLUME_THR_9";

        String[] columnNames = header.split(separator);
        if (columnNames.length == 0) {
            System.out.println("Column is empty !");
            return;
        }

        System.out.println("COUNT: " + columnNames.length);

        Hashtable<String, String> dataTypes = new Hashtable<String, String>();
        List<String> listInsCommand = new ArrayList<String>();

        Connection conn = null;
        try {
            conn = AL_Setting.getDBConnection();
            Statement st = conn.createStatement();

            String query =
                "select column_name, data_type from ALL_TAB_COLUMNS \n" +
                "where table_name = '" + tableName + "' order by COLUMN_ID";
            ResultSet rs = st.executeQuery(query);
            String dt = "";
            String col = "";
            while (rs.next()) {
                col = rs.getString("COLUMN_NAME");
                if (col == null || col.length() == 0) {
                    continue;
                }
                dt = rs.getString("DATA_TYPE");
                if (dt == null || dt.length() == 0) {
                    continue;
                }
                dataTypes.put(col, dt);
            }
            rs.close();
            //
            String insertStr = "";
            int count = 0;
            int index = -1;
            String key = "";
            String dType = "";
            Enumeration<String> enums = dataTypes.keys();
            while (enums.hasMoreElements()) {
                key = enums.nextElement();
                index = getColumnIndex(columnNames, key);
                if (index == -1) {
                    System.out.println(key + ": Column name is not exist");
                    continue;
                }
                dType = dataTypes.get(key);
                //                insertStr =
                //                        "insert into I_IMPORT_MAPPING(RAW_TABLE, TABLE_COLUMN,\n" +
                //                        "FILE_COLUMN, DATA_TYPE, DATA_FORMAT, PATTERN_ID, CREATE_BY) values(";
                //                insertStr += "'" + tableName + "', '" + key + "', " + index;
                //                insertStr += ", '" + dType;
                //                insertStr +=
                //                        "', '" + (dType.equalsIgnoreCase("DATE") ? timeFormat :
                //                                  "");
                //                insertStr += "', " + patternId;
                //                insertStr += ", 'DATTQ')";
                insertStr =
                        "update I_IMPORT_MAPPING set FILE_COLUMN = " + index +
                        " where RAW_TABLE = 'R_A_MFS' and PATTERN_ID = 89 and TABLE_COLUMN = '" +
                        key + "'";
                count += 1;

                listInsCommand.add(insertStr);
            }

            if (listInsCommand.size() == 0) {
                System.out.println("Khong co cau lenh insert nao duoc tao");
                //return;
            }
            if (listInsCommand.size() != dataTypes.size()) {
                System.out.println("Khong tao du cau lenh insert: " +
                                   dataTypes.size());
                /// return;
            }
            // exec cau lenh insert
            for (String str : listInsCommand) {
                System.out.println(str);
                st.execute(str);
            }
            System.out.println("Create mapping is success !");
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
    }

    @SuppressWarnings({ "unused", "null" })
	private void recreateMappingData() {
        Connection conn;
        Hashtable<String, Vector<View>> hashMapping =
            new Hashtable<String, Vector<View>>();
        Vector<String> lstKey = new Vector<String>();
        try {
            conn = AL_Setting.getDBConnection();
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery("select * from I_IMPORT_MAPPING");
            while (rs.next()) {
                String rawTable = rs.getString("RAW_TABLE");
                if (rawTable == null && rawTable.trim().length() == 0) {
                    continue;
                }
                View v = new View();
                v.tableColumn = rs.getString("TABLE_COLUMN");
                v.fileColumn = rs.getString("FILE_COLUMN");
                v.dataType = rs.getString("DATA_TYPE");
                v.dataFormat = rs.getString("DATA_FORMAT");
                v.patternId = rs.getInt("PATTERN_ID");

                if (hashMapping.containsKey(rawTable)) {
                    Vector<View> mapItem = hashMapping.get(rawTable);
                    mapItem.add(v);
                } else {
                    Vector<View> mapItem = new Vector<View>();
                    mapItem.add(v);
                    hashMapping.put(rawTable, mapItem);
                    lstKey.add(rawTable);
                }
            }

            System.out.println("Task count: " + hashMapping.size());

            for (int i = 0; i < lstKey.size(); i++) {
                String rawTable = lstKey.get(i);

                List<Integer> ls = this.getPatternId(rawTable);
                //System.out.println("Handle: " + rawTable + " --- " +
                //                   ls.size());

                if (ls.isEmpty()) {
                    continue;
                }
                if (ls.size() == 1) {
                    //this.updateRecord(rawTable, ls.get(0));
                    //System.out.println("update: " + rawTable + " -- " + ls.get(0));
                    continue;
                } // IIM_PK
                if (ls.size() > 1) {
                    this.updateRecord(rawTable, ls.get(0));
                    //System.out.println("update: " + rawTable + " -- " + ls.get(0));
                    System.out.println(rawTable);
                    ls.remove(0);
                    Vector<View> item = hashMapping.get(rawTable);
                    if (item != null) {
                        //System.out.println("insert: " + ls.size());
                        this.insertRecord(item, rawTable, ls);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertRecord(Vector<View> vct, String rawTable,
                              List<Integer> ids) {
        if (ids.size() == 0) {
            System.out.println("khong insert them duoc dong du lieu nao: " +
                               rawTable);
        }
        Connection conn = null;
        try {
            conn = AL_Setting.getDBConnection();
            Statement st = conn.createStatement();

            for (int i = 0; i < ids.size(); i++) {
                int p = ids.get(i);
                for (View v : vct) {
                    try {
                        if (v.dataFormat == null) {
                            v.dataFormat = "";
                        }
                        String insert =
                            "insert into I_IMPORT_MAPPING(RAW_TABLE, TABLE_COLUMN, " +
                            "FILE_COLUMN, DATA_TYPE, DATA_FORMAT, PATTERN_ID) " +
                            "values('" + rawTable + "', '" + v.tableColumn +
                            "', " + v.fileColumn + ", '" + v.dataType +
                            "', '" + v.dataFormat + "', " + p + ")";

                        System.out.println("Insert string: " + insert);

                        st.execute(insert);

                        System.out.println("insert at: " + rawTable);
                    } catch (Exception sql) {
                        //sql.printStackTrace();
                    }
                }
            }

            st.close();
        } catch (SQLException sqle) {
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                }
            }
        }

    }

    private void updateRecord(String rawTable, int patternId) {
        Connection conn = null;
        try {
            conn = AL_Setting.getDBConnection();
            Statement st = conn.createStatement();

            String updateStr =
                "update I_IMPORT_MAPPING set PATTERN_ID = " + patternId +
                " where RAW_TABLE = '" + rawTable + "'";
            st.executeUpdate(updateStr);

            System.out.println("update for: " + rawTable);

            st.close();
        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                }
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Integer> getPatternId(String rawtable) {
        List<Integer> ls = new ArrayList();

        Connection conn = null;
        try {
            conn = AL_Setting.getDBConnection();
            Statement st = conn.createStatement();
            ResultSet rs =
                st.executeQuery("select PATTERN_ID from C_FILE_PATTERNS where RAW_TABLE = '" +
                                rawtable + "'");
            while (rs.next()) {
                int p = rs.getInt("PATTERN_ID");
                if (p > 0) {
                    ls.add(p);
                }
            }
            rs.close();
            st.close();
        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                }
            }
        }

        return ls;
    }

    class View {
        public int patternId = 0;
        public String tableColumn = "";
        public String fileColumn = "";
        public String dataType = "";
        public String dataFormat = "";
    }


    @SuppressWarnings("unused")
	private void demo(DataSource ds) {
        Hashtable<String, Integer> hashData = new Hashtable<String, Integer>();

        Connection conn = null;
        try {
            conn = ds.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs =
                st.executeQuery("select FILE_ID, FILE_NAME from C_RAW_FILES_MLMN where RAW_TABLE = 'R_A_CELRT110'");
            while (rs.next()) {
                hashData.put(rs.getString("FILE_NAME"), rs.getInt("FILE_ID"));
            }
            rs.close();
            st.close();
        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                }
            }
        }

        String path = "/home/oracle/pmsdata/upload/alcatel";
        File dir = new File(path);

        File files[] = dir.listFiles();
        if (files != null && files.length > 0) {
            int count = 0;
            for (File f : files) {
                if (hashData.containsKey(f.getName())) {
                    count++;

                    if (this.update(ds, hashData.get(f.getName()))) {
                        System.out.println(hashData.get(f.getName()) + " - " +
                                           f.getName());
                    }
                }
            }
            System.out.println("----------------------------------------------");
            System.out.println("Count: " + count);
            System.out.println("Tong so file trong thu muc: " + files.length);
        }
    }

    private boolean update(DataSource ds, int id) {
        boolean r = false;
        Connection conn = null;
        try {
            conn = ds.getConnection();
            Statement st = conn.createStatement();

            String updateString =
                "update C_RAW_FILES_MLMN set IMPORT_FLAG = 0 where FILE_ID = " + id;
            r = st.execute(updateString);
            st.close();
        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                }
            }
        }

        return r;
    }


    @SuppressWarnings("unused")
	private void countFileColumn() {
        String filePath = "D:/RESEARCH";
        String fileName =
            "iM2000_C2_BSS_pmresult_1275071432_60_200908050538_200908050638.csv";

        File file = new File(filePath, fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            int count = 0;
            String line = "";
            while ((line = reader.readLine()) != null) {
                //line = line.trim();
                String[] values = line.split(",");
                if (count == 0) {
                    count = values.length;
                    System.out.println("count column: " + count);
                    System.out.println("data: " + line);
                } else {
                    if (count != values.length) {
                        count = values.length;
                        System.out.println("count column: " + count);
                        System.out.println("data: " + line);
                    }
                }
                //System.out.println(values[19]);

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings({ "unused", "unchecked" })
	private void multiThread() {
        int maxThread = 1;
        List<String> list = new ArrayList<String>();
        list.add("BAGG02EE-CELLQOSEG_BAGG02E_20090806_19.load");
        list.add("BAGG02EE-CELLQOSEG_BAGG02E_20090806_22.load");
        list.add("BAGG02EE-CELLQOSEG_BAGG02E_20090807_01.load");
        list.add("BAGG02EE-CELLQOSEG_BAGG02E_20090807_04.load");
        list.add("BAGG02EE-CELLQOSG_BAGG02E_20090806_00.load");
        list.add("BAGG02EE-CELLQOSG_BAGG02E_20090806_03.load");
        list.add("BAGG02EE-CELLQOSG_BAGG02E_20090806_06.load");

        ExecutorService executor = Executors.newFixedThreadPool(maxThread);
        for (int i = 0; i < maxThread; i++) {
            executor.submit(new ImportWorker(AL_Setting.getDBConnection(), list,
                                             i));
        }
        executor.shutdown();
    }


    @SuppressWarnings("rawtypes")
	class ImportWorker implements Callable {
        boolean stop = false;
        Connection conn = null;
        List<String> listFile = null;
        String fileName = "";
        int num;

        ImportWorker(Connection conn, List<String> listFile, int num) {
            this.conn = conn;
            this.listFile = listFile;
            this.num = num;
        }

        public Object call() {
            CallableStatement callSt;

            System.out.println("Task " + num + " is working.");
            int i = 0;
            //Random rand = new Random();
            //int index = rand.nextInt(5);
            while (i < listFile.size()) {
                try {
                    String fileName = listFile.get(i);
                    System.out.println("FILE_NAME: " + fileName);
                    //callSt = conn.prepareCall(procParam);
                    callSt =
                            conn.prepareCall("{ call IMPORT.IMPORT_FILE(?,?,?,?,?,?,?) }");

                    callSt.setString(1, "59");
                    callSt.setString(2, "161616" + num);
                    callSt.setString(3, fileName);
                    callSt.setString(4, "ERICSSON_DATA");
                    callSt.setString(5, "R_E_CELLQOSEG");
                    callSt.setString(6, "");
                    callSt.setString(7, "@");
                    callSt.execute();
                    callSt.close();
                } catch (SQLException sqlEx) {
                    //sqlEx.printStackTrace();
                    System.out.println(sqlEx.getMessage());
                } finally {
                    //try {
                    //    Thread.sleep(75000);
                    //} catch (Exception e) {
                    //}
                }
                i++;
            }
            try {
                this.conn.close();
            } catch (Exception e) {
            }
            return null;
        }

        public void setStop() {
            stop = true;
        }

        public String getThreadName() {
            return "Thread <FtpWorker:" + Thread.currentThread().getId() + ">";
        }
    }
}
