package inventory.download;
 
import inventory.core.IN_Global;
import inventory.util.IN_Setting;
import inventory.util.task.IN_TaskInfo;
import inventory.util.task.IN_TaskService;
import inventory.utils.log.IN_TelnetLog;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class IN_DownloadServicesTelnet extends IN_TaskService {

	private static Logger logger = Logger
			.getLogger(IN_DownloadServicesTelnet.class.getName());
	
	private int maxExecutor = -1;
	private int retryNumber = -1;
	private String destinationPath = IN_Global.SYSTEM_CONFIG.getProperty(IN_Setting.BASE_LOCAL_DIR);  
	//sprivate String delayTelnet = IN_Global.SYSTEM_CONFIG.getProperty(IN_Setting.INVENTORY_DELAY_TELNET);
	private DataSource ds = null; 
	IN_TelnetClient cls; 
	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH-mm-ss"; 
	private String status = "";
	
	public IN_DownloadServicesTelnet(DataSource ds) {
		this("Download Service", ds);
	}

	public IN_DownloadServicesTelnet(String name, DataSource ds) {
		super(name);
		this.ds = ds;
	}

	public void doInit() { 
		try {
			String maxThread = IN_Global.SYSTEM_CONFIG
					.getProperty(IN_Setting.MAX_DOWNLOAD_THREAD_KEY);
			this.maxExecutor = Integer.parseInt(maxThread);
		} catch (NumberFormatException nfe) {
			logger.warn("Invalid '" + IN_Setting.MAX_DOWNLOAD_THREAD_KEY
					+ "' config in C_SYSTEM_CONFIGS_MLMN");
		}

		try {
			String maxRetry = IN_Global.SYSTEM_CONFIG
					.getProperty(IN_Setting.MAX_DOWNLOAD_RETRY_KEY);
			this.retryNumber = Integer.parseInt(maxRetry);
		} catch (NumberFormatException nfe) {
			logger.warn("Invalid '" + IN_Setting.MAX_DOWNLOAD_RETRY_KEY
					+ "' config in C_SYSTEM_CONFIGS_MLMN");
		}

		this.maxExecutor = (this.maxExecutor <= 0) ? 1 : this.maxExecutor;
		this.retryNumber = (this.retryNumber < 0) ? 0 : this.retryNumber;

		initTaskQueues();
	}

	public void doFinallize() {
		this.maxExecutor = -1;
		this.retryNumber = -1;
	}

	public int getMaxExecutorThread() {
		return this.maxExecutor;
	}

	public void initTaskQueues() {
		Connection conn = null;
		try {
			conn = this.ds.getConnection();
			Statement st = conn.createStatement();

			String queryStr = " select ID,VENDOR,NETWORK,NE_TYPE,NE,IP_ADDRESS,TELNET_PORT,TELNET_USER, " +
					" TELNET_PASSWORD,TELNET_TYPE,LOGIN_TIME,TELNET_COMMAND,STATUS,MODULE,DESCRIPTION,TELNET_COUNT " +
					" from C_TELNET_SERVERS where upper(STATUS) = 'Y' and MODULE = 'INVENTORY' order by ORDERING asc";

			ResultSet rs = st.executeQuery(queryStr);
			while (rs.next()) { 
				IN_DownloadInfo downloadInfo = new IN_DownloadInfo();
				
				downloadInfo.setId(rs.getInt("ID"));
				downloadInfo.setVendor(rs.getString("VENDOR"));
				downloadInfo.setNetwork(rs.getString("NETWORK"));
				downloadInfo.setNeType(rs.getString("NE_TYPE"));
				downloadInfo.setNe(rs.getString("NE"));
				downloadInfo.setIpAddress(rs.getString("IP_ADDRESS"));
				downloadInfo.setTelnetPort(rs.getInt("TELNET_PORT"));
				downloadInfo.setTelnetUser(rs.getString("TELNET_USER"));
				downloadInfo.setTelnetPassword(rs.getString("TELNET_PASSWORD"));
				downloadInfo.setTelnetType(rs.getString("TELNET_TYPE"));
				downloadInfo.setLoginTime(rs.getTimestamp("LOGIN_TIME"));
				downloadInfo.setTelnetCommand(rs.getString("TELNET_COMMAND"));
				downloadInfo.setStatus(rs.getString("STATUS"));
				downloadInfo.setModule(rs.getString("MODULE"));
				downloadInfo.setDescription(rs.getString("DESCRIPTION"));
				downloadInfo.setCount(rs.getInt("TELNET_COUNT"));
				
				downloadInfo.setTaskInfo("Server:"
						+ rs.getString("NE") + "----IP:"
						+ rs.getString("IP_ADDRESS")+ "\r\n");
				//System.out.println(rs.getString("NE"));
				addTask(downloadInfo);
			}

			rs.close();
			st.close();
		} catch (SQLException e) {
			logger.warn("File list to convert is empty" + e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.warn("Cannot close connection to database");
				}
			}
		}
	}

	public void doTask(IN_TaskInfo input) {
		IN_DownloadInfo beanInfo = (IN_DownloadInfo) input;

		String p_Vendor, p_Network, p_NeType, p_Ne, p_IpAddress, p_TelnetUser, p_TelnetPass,
				p_TelnetType, p_TelnetCommand, p_Status, p_Module, p_Description; 
		
		Integer p_TelnetPort,p_Id, p_TelnetCount;
		Date p_LoginTime;
		
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String SysDate = sdf.format(new Date());
		
		String p_SDate = "", p_STime = "", p_Edate = "";
		Date Edate = new Date();
		try {
			Edate = sdf.parse(SysDate);
			p_Edate = sdf.format(Edate).split(" ")[0];
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		p_Id = beanInfo.getId();
		p_Vendor = beanInfo.getVendor();
		p_Network = beanInfo.getNetwork();
		p_NeType = beanInfo.getNeType();
		p_Ne = beanInfo.getNe();
		p_IpAddress = beanInfo.getIpAddress();
		p_TelnetPort = beanInfo.getTelnetPort();
		p_TelnetUser = beanInfo.getTelnetUser();
		p_TelnetPass = beanInfo.getTelnetPassword();
		p_TelnetType = beanInfo.getTelnetType();
		p_LoginTime = beanInfo.getLoginTime();
		p_TelnetCommand = beanInfo.getTelnetCommand();
		p_Status = beanInfo.getStatus();
		p_Module = beanInfo.getModule();
		p_Description = beanInfo.getDescription();
		p_TelnetCount = beanInfo.getCount();
		
		p_SDate = sdf.format(p_LoginTime).split(" ")[0];
		p_STime = sdf.format(p_LoginTime).split(" ")[1];
		String strMessage = "";
		strMessage = "MODULE: "+p_Module+"VENDOR: " + p_Vendor + " NE_TYPE:" + p_NeType
						+ " IP: " + p_IpAddress + " USER_NAME:" + p_TelnetUser;
		logger.info(strMessage);   
		try { 
			IN_TelnetLog inTelnetLog = null;
			if(!p_SDate.equals(p_Edate)){
				if(p_Vendor.contains("NOKIA")){
					inTelnetLog = new IN_TelnetLog();
					
					cls = new IN_TelnetClient();
					cls.set_DestinationPath(destinationPath);
					cls.set_Debug(false);
					
					if(p_NeType.equalsIgnoreCase("BSC")){
						//Telnet lay thong tin license va port,card NSN BSC
						inTelnetLog = cls.TelnetNSN_BSC(p_NeType, p_Ne, p_SDate,p_STime, p_IpAddress, p_TelnetUser, p_TelnetPass);
					}else if(p_NeType.equalsIgnoreCase("RNC")){
						//Telnet lay thong tin license va port,card NSN RNC
						inTelnetLog = cls.TelnetNSN_RNC(p_NeType, p_Ne, p_SDate,p_STime, p_IpAddress, p_TelnetUser, p_TelnetPass);
					}else if(p_NeType.equalsIgnoreCase("SGSN")){
						//Telnet lay thong tin license va port,card NSN SGSN
						inTelnetLog = cls.TelnetNSN_SGSN(p_NeType, p_Ne, p_SDate,p_STime, p_IpAddress, p_TelnetUser, p_TelnetPass);
					}
				}else if(p_Vendor.contains("ERICSSON")){
					inTelnetLog = new IN_TelnetLog();
					
					cls = new IN_TelnetClient();
					cls.set_DestinationPath(destinationPath);
					cls.set_Debug(false);
					if(p_NeType.equalsIgnoreCase("RNC")){
						//Telnet lay thong tin license software va port card Ericsson RNC
						inTelnetLog = cls.TelnetEricsson_RNC(p_NeType, p_Ne, p_SDate,p_STime, p_IpAddress, p_TelnetUser, p_TelnetPass);
					} 
				}
				//Update thoi gian login time voi nhung server telnet khong loi
				upTime(p_Id,inTelnetLog.get_isConnect(),inTelnetLog.get_description()); 
			} 
		} catch (Exception ce) { 
			logger.info("Error Thead Download: " + ce.getMessage());
		} finally {
			increaseDoneTasks();
		}
	}

	private void upTime(int p_Id, String _isConnect, String _description) {
		Connection conn = null; 
		try { 
			conn = this.ds.getConnection();
			Statement st = conn.createStatement();
			String queryStr = "";
			
			if(_isConnect.equalsIgnoreCase("CONNECTED")){
				queryStr = "update C_TELNET_SERVERS set LOGIN_TIME =  SYSDATE " + 
						", LAST_CONNECT = SYSDATE"+
						", MONITER_STATUS = '"+_isConnect+"'" + 
						", DESCRIPTION = '"+_description+"'" +
						" where ID = '" + p_Id + "'";
			}else{
				queryStr = "update C_TELNET_SERVERS set LAST_CONNECT =  SYSDATE " +
						", MONITER_STATUS = '"+_isConnect+"'" + 
						", DESCRIPTION = '"+_description+"'" +
						" where ID = '" + p_Id + "'";
			}  

			st.execute(queryStr); 
			st.close();

		} catch (SQLException e) {
			logger.warn("Try upTime: " + e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.warn("Cannot close connection to database");
				}
			}
		}
	}
	
	public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
     
}
