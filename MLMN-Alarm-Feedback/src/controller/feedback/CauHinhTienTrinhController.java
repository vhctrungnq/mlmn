package controller.feedback;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.alarm.core.AL_Global;
import vo.SYS_PARAMETER;
import vo.alarm.utils.Fb_Setting;

import dao.FbProcessDAO;
import dao.SYS_PARAMETERDAO;
import dao.ScheduleServiceDAO;
import dao.ScheduleServiceDAOImpl;
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
@RequestMapping("/feedback/cau-hinh-tien-trinh/*")
public class CauHinhTienTrinhController {

	public CauHinhTienTrinhController(){}
	@Autowired
	private ScheduleServiceDAO scheduleServiceDAO;
	@Autowired
	private FbProcessDAO fbProcessDAO;
	@Autowired
 	private SYS_PARAMETERDAO sysParameterDao;
	
	private static DataSource dataSource = null;
	private static Logger logger = Logger.getLogger(ScheduleServiceDAOImpl.class.getName());
	
	@RequestMapping(value = "list")
    public String list(@RequestParam(required = false) String status,
		   			   @RequestParam(required = false) Integer repeats,
		   			   @RequestParam(required = false) String hourData,
		   			   @RequestParam(required = false) String hourDefault,
		   			   @RequestParam(required = false) String action, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		CauHinhTienTrinhController.loadSystemConfig();
		
		List<SYS_PARAMETER> getStatusFbList = sysParameterDao.getStatusOfCentralFb();
 		model.addAttribute("getStatusFbList", getStatusFbList);
		
		if(status != null && repeats != null && hourData!= null && hourDefault != null){
			fbProcessDAO.updateCoreService(status, repeats.toString(), hourData, hourDefault);
		}
		
		if(status == null)
			status = AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_STATE_KEY);
		
		if(repeats == null)
			repeats = Integer.parseInt(AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_DELAY_THREAD_KEY));
		
		if(hourData == null)
			hourData = AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_TAKE_DATA_BY_HOUR_DATA);
		if(hourDefault == null)
			hourDefault = AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_TAKE_DATA_BY_HOUR_DEFAULT);
		
		if (action == null) {
		} else if (action.equals("start")) {
			scheduleServiceDAO.start();
		} else {
			scheduleServiceDAO.stop();
		}
		
		boolean started = scheduleServiceDAO.serviceStarted();
		
		if (started) {
			action = "stop";
		} else {
			action = "start";
		}
		
		model.addAttribute("started", started);
		model.addAttribute("action", action);
		model.addAttribute("status", status);
		model.addAttribute("repeats", repeats);
		model.addAttribute("hourData", hourData);
		model.addAttribute("hourDefault", hourDefault);
		model.addAttribute("fromDate", AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_TAKE_DATA_FROM_DATE));
		model.addAttribute("toDate", AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_TAKE_DATA_TO_DATE));
		
		return "jspfeedback/cauHinhTienTrinh";
	}
	
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
	
	public static void loadSystemConfig() throws SQLException {
		Connection con = null;
		AL_Global.SYSTEM_CONFIG.clear();

		try {
			con = dataSource.getConnection();
			Statement st = con.createStatement();
			ResultSet result = st.executeQuery("Select PARAM_NAME, PARAM_VALUE from c_system_configs");
			String key = null;
			String value = null;
			while (result.next()) {
				key = result.getString(1);
				value = result.getString(2);
				if (key != null && value != null) {
					AL_Global.SYSTEM_CONFIG.setProperty(key, value);
				}
			}
			result.close();
			st.close();
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}
}
