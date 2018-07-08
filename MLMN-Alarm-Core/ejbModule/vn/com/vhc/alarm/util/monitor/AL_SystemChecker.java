package vn.com.vhc.alarm.util.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.log4j.Logger;


public class AL_SystemChecker {
    private static Logger logger =
        Logger.getLogger(AL_SystemChecker.class.getName());
    private Pattern cpuIdlPattern = Pattern.compile("Cpu.*?([0-9\\.]+)%id");
    private Pattern winHddPattern = Pattern.compile(".*?([0-9]+) bytes free");
    private Pattern linuxHddPattern =
        Pattern.compile(".*?\\s+([0-9]+)\\s+([0-9]+)\\s+([0-9]+).+%.+");
    private static final String OS_NAME =
        System.getProperty("os.name").toLowerCase();

    public AL_SystemChecker() {
        super();
    }

    public static void main(String[] args) {
        AL_SystemChecker checker = new AL_SystemChecker();
        String[] mountPoints = new String[] { "/u01", "/u02" };
        Properties props = checker.checkHDDFreeSpace(mountPoints);
        System.out.println("Free HDD: " + props);
        double free = checker.checkCPULoad();
        System.out.println("Current cpu load = " + free);
        int[] mem = checker.checkFreeMemory();
        System.out.println("Max heap: " + mem[0] + ", free: " + mem[1]);
    }


    protected String checkDatabaseState(DataSource datasource,
                                        String checkStatement) {
        Connection con = null;
        Statement stmt = null;
        try {
            con = datasource.getConnection();
            stmt = con.createStatement();

            stmt.execute(checkStatement);
            stmt.close();
        } catch (SQLException e) {
            return e.getMessage();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    logger.warn("Close connection error: " +
                                   e.getMessage());
                }
            }
        }

        return "OK";
    }

    protected Properties checkDatabaseTableSpace(DataSource datasource) throws SQLException {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        Properties props = new Properties();
        try {
            con = datasource.getConnection();
            //query data from user_tablespaces table
            stmt = con.createStatement();
            rs =
 stmt.executeQuery("SELECT tablespace_name, ROUND(SUM(BYTES)/(1024*1024), 0) as FREE FROM user_free_space GROUP BY tablespace_name");

            while (rs.next()) {
                props.put(rs.getString(1), rs.getInt(2));
            }

            rs.close();
            stmt.close();
        } finally {
            if (con != null) {
                con.close();
            }
        }

        return props;
    }

    protected Properties checkHDDFreeSpace(String... mountpoints) {
        Properties props = new Properties();
        int free = 0;
        String check;
        String tmp;

        Process p = null;
        String line = null;
        Matcher m;
        BufferedReader reader = null;
        String[] cmdAttrs = null;
        if (mountpoints != null) {
            for (String mountpoint : mountpoints) {
                if (mountpoint != null && mountpoint.trim().length() > 0) {
                    check = mountpoint.trim();
                    try {
                        if (OS_NAME.indexOf("linux") > -1 ||
                            OS_NAME.indexOf("unix") > -1 ||
                            OS_NAME.indexOf("sun") > -1 ||
                            OS_NAME.indexOf("solaris") > -1 ||
                            OS_NAME.indexOf("mac os x") > -1) {
                            cmdAttrs = new String[] { "df", "-k", check };
                            p = Runtime.getRuntime().exec(cmdAttrs);
                            reader =
                                    new BufferedReader(new InputStreamReader(p.getInputStream()));
                            do {
                                line = reader.readLine();
                                if (line != null) {
                                    m = linuxHddPattern.matcher(line);
                                    if (m.find()) {
                                        free =
Integer.parseInt(m.group(3)) >>> 10;
                                        break;
                                    }
                                }
                            } while (line != null);
                        } else if (OS_NAME.indexOf("window") > -1) {
                            if (check.indexOf(":") != -1) {
                                tmp =
check.substring(0, check.indexOf(":") + 1);
                            } else {
                                tmp = check;
                            }

                            cmdAttrs =
                                    new String[] { "cmd.exe", "/C", "dir /-c " +
                                                   tmp };
                            p = Runtime.getRuntime().exec(cmdAttrs);
                            reader =
                                    new BufferedReader(new InputStreamReader(p.getInputStream()));
                            do {
                                line = reader.readLine();
                                if (line != null) {
                                    m = winHddPattern.matcher(line);
                                    if (m.find()) {
                                        free =
                                        	Integer.parseInt(m.group(1)) >>> 20;
                                        break;
                                    }
                                }
                            } while (line != null);
                        }
                    } catch (IOException e) {
                        logger.warn("Check free space in mount point '" +
                                       mountpoint + "' failure: " +
                                       e.getMessage());
                        free = 0;
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (Exception ex) {
                                logger.warn("Close process stream error: " +
                                               ex.getMessage());
                            }
                        }

                        if (p != null) {
                            p.destroy();
                        }
                    }

                    props.put(check, free);
                }
            }
        }

        return props;
    }

    protected double checkCPULoad() {
        Process p = null;
        String line = null;
        Matcher m;
        BufferedReader reader = null;
        //Implement for Linux OS
        if (OS_NAME.indexOf("linux") > -1) {
            try {
                String[] cmdAttrs = new String[] { "top", "-b -n 1" };
                p = Runtime.getRuntime().exec(cmdAttrs);
                reader =
                        new BufferedReader(new InputStreamReader(p.getInputStream()));
                do {
                    line = reader.readLine();
                    if (line != null) {
                        m = cpuIdlPattern.matcher(line);
                        if (m.find()) {
                            return (100 - Double.parseDouble(m.group(1)));
                        }
                    }
                } while (line != null);
            } catch (IOException e) {
                logger.warn("Check CPU load failure: " + e.getMessage());
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception ex) {
                        logger.warn("Close Inputstream of process error: " +
                                       ex.getMessage());
                    }
                }

                if (p != null) {
                    p.destroy();
                }
            }
        }
        //Not implement for other OS yet
        logger.info("Cannot check CPU load for OS " + OS_NAME + "!");
        return 0;
    }

    protected int[] checkFreeMemory() {
        int[] state = new int[2];
        MemoryUsage heap =
            ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        int maxHeap = (int)(heap.getMax() / (1024 * 1024));
        int used = (int)(heap.getUsed() / (1024 * 1024));

        state[0] = maxHeap;
        state[1] = maxHeap - used;

        return state;
    }
}
