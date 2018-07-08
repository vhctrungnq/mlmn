package controller.alarm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.alarm.core.AL_Global;
import vo.SYS_PARAMETER;
import vo.alarm.utils.SendMailSetting;

import dao.CSystemConfigsDAO;
import dao.FbProcessDAO;
import dao.SYS_PARAMETERDAO;
import dao.ScheduleSendMailDAO;
import dao.ScheduleSendMailDAOImpl;

/**
 * Function        : Cau hinh tien trinh
 * Created By      : BUIQUANG
 * Create Date     : 
 * Modified By     : 
 * Modify Date     : 8/1/2014
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/sendmail/cau-hinh-tien-trinh/*")
public class TienTrinhMailController {

	public TienTrinhMailController(){}
	@Autowired
	private ScheduleSendMailDAO scheduleSendMailDAO;
	@Autowired
	private FbProcessDAO fbProcessDAO;
	@Autowired
	private CSystemConfigsDAO cSystemConfigsDAO;
	@Autowired
 	private SYS_PARAMETERDAO sysParameterDao;
	
	private static DataSource dataSource = null;
	private static Logger logger = Logger.getLogger(ScheduleSendMailDAOImpl.class.getName());
	
	@RequestMapping(value = "list")
    public String list(@RequestParam(required = false) String status,
		   			   @RequestParam(required = false) String delay,
		   			   @RequestParam(required = false) String startHour,
		   			   @RequestParam(required = false) String endHour,
		   			   @RequestParam(required = false) String action, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		
		TienTrinhMailController.loadSystemMailConfig();
	//	TienTrinhMailController.loadMailConfig();
		
		List<SYS_PARAMETER> getStatusFbList = sysParameterDao.getStatusSendMail();
 		model.addAttribute("getStatusFbList", getStatusFbList);
		
		if(status != null && delay != null && startHour!= null && endHour != null){
			cSystemConfigsDAO.updateSystemConfigMail(status, delay, startHour, endHour);
		}
		
		if(status == null)
			status = AL_Global.MAIL_SYSTEM_CONFIG.getProperty(SendMailSetting.SENDMAIL_STATUS);
		if(delay == null)
			delay = AL_Global.MAIL_SYSTEM_CONFIG.getProperty(SendMailSetting.SENDMAIL_DELAY);
		
		if(startHour == null)
			startHour = AL_Global.SYSTEM_CONFIG.getProperty(SendMailSetting.SENDMAIL_START_HOUR);
		if(endHour == null)
			endHour = AL_Global.SYSTEM_CONFIG.getProperty(SendMailSetting.SENDMAIL_END_HOUR);
		
		if (action == null) {
		} else if (action.equals("start")) {
			scheduleSendMailDAO.start();
		} else {
			scheduleSendMailDAO.stop();
		}
		
		boolean started = scheduleSendMailDAO.serviceStarted();
		
		if (started) {
			action = "stop";
		} else {
			action = "start";
		}
		
		model.addAttribute("started", started);
		model.addAttribute("action", action);
		model.addAttribute("status", status);
		model.addAttribute("delay", delay);
		model.addAttribute("startHour", startHour);
		model.addAttribute("endHour", endHour);
		
		return "jspalarm/cauHinhTienTrinh";
	}
	//ket noi database
		static {
			InitialContext ctx;
			try {
				ctx = new InitialContext();
				dataSource = (DataSource) ctx.lookup("java:/vmsc6_alarm");
				logger.info("Init Datasource success.");
			} catch (NamingException e) {
				logger.error("Cannot init Datasource: " + e.getMessage());
			}
		}
		
	/*	//Lay cac gia tri config mail trong c_MAIL_SYSTEM_CONFIG
		public static void loadMailConfig() throws SQLException {
			Connection con = null;
			CallableStatement cs = null;
			ResultSet result = null;
			AL_Global.MAIL_CONFIG.clear();
			try {
				//con = getDBConnection();
				con = dataSource.getConnection();  
				cs = con.prepareCall(
	                "{call PK_AL_SEND_MAIL.PR_SYS_CONFIG_MASTER_SEND(?)}"
	            );
	        	cs.registerOutParameter(1, OracleTypes.CURSOR);
	        	cs.executeQuery();
	        	result = (ResultSet) cs.getObject(1);
		       //in du lieu trong result set ra 
	        	String key = null;
				String value = null;
				while (result.next()) {
					key = result.getString(1);
					value = result.getString(2);
					System.out.println("Key: "+ key+"---- value: "+value);
					if (key != null && value != null) {
						AL_Global.MAIL_CONFIG.setProperty(key, value);
					}
				}
				
			} finally {
				if (result != null) {
					try
					{
						result.close();
					}
					catch (SQLException ex)
	                {
	                }
				}
				if (cs != null) {
					try
					{
						cs.close();
					}
					catch (SQLException ex)
	                {
	                }
				}
				if (con != null) {
					try
					{
						con.close();
					}
					catch (SQLException ex)
	                {
	                }
				}

			}
		}*/
		//Lay gia tri config cau hinh gui mail trong c_MAIL_SYSTEM_CONFIG
		public static void loadSystemMailConfig() throws SQLException {
			Connection con = null;
			AL_Global.MAIL_SYSTEM_CONFIG.clear();
			ResultSet result = null;
			CallableStatement cs = null;
			try {
				//con = getDBConnection();			
				con = dataSource.getConnection();  
				cs = con.prepareCall(
		                "{call PK_AL_SEND_MAIL.PR_SYS_CONFIG_SEND(?)}"
	            );
	        	cs.registerOutParameter(1, OracleTypes.CURSOR);
	        	cs.executeQuery();
	        	result = (ResultSet) cs.getObject(1);
				String key = null;
				String value = null;
				while (result.next()) {
					key = result.getString(1);
					value = result.getString(2);
					System.out.println("Key: "+ key+"---- value: "+value);
					if (key != null && value != null) {
						AL_Global.MAIL_SYSTEM_CONFIG.setProperty(key, value);
					}
				}
				
			} finally {
				if (result != null) {
					try
					{
						result.close();
					}
					catch (SQLException ex)
	                {
	                }
				}
				if (cs != null) {
					try
					{
						cs.close();
					}
					catch (SQLException ex)
	                {
	                }
				}
				if (con != null) {
					try
					{
						con.close();
					}
					catch (SQLException ex)
	                {
	                }
				}

			}
		}
		
		public String getMailFlag() {
			String propFileName = System.getProperty("jboss.server.home.dir") + "/conf/system_mlmn.properties";
			InputStream propsStream = null;
			try {
				propsStream = new FileInputStream(propFileName);
				Properties props = new Properties();
				props.load(propsStream);
				propsStream.close();

				return props.getProperty("MailFlag");
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}

			return null;
		}
}
