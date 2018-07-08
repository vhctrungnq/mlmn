package dao;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;


import vn.com.vhc.alarm.core.AL_Global;
import vo.FeedbackJob;
import vo.SendMailJob;
import vo.alarm.utils.Fb_Setting;
import vo.alarm.utils.SendMailSetting;


public class ScheduleServiceDAOImpl extends SqlMapClientDaoSupport implements ScheduleServiceDAO{

	private static Logger logger = Logger.getLogger(ScheduleServiceDAOImpl.class.getName());
			
	private static SchedulerFactory schedulerFactory;
	private static Scheduler scheduler;
	private static DataSource dataSource = null;
	@Override
	public void start() {
		
        try {
        	schedulerFactory = new StdSchedulerFactory();
        	scheduler = schedulerFactory.getScheduler();
		} catch (SchedulerException e) {
			logger.info("Error start Core report");
		}
        
        try {
        	JobDetail job = null;
        	Trigger trigger = null;
        	
        	try {
				ScheduleServiceDAOImpl.loadSystemConfig();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        Integer delay = 1;
	        if (AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_DELAY_THREAD_KEY)!=null)
	        	{
	        		delay =Integer.parseInt(AL_Global.SYSTEM_CONFIG.getProperty(Fb_Setting.FEEDBACK_DELAY_THREAD_KEY));
	        	}
    		// job
	        job = newJob(FeedbackJob.class)
					.withIdentity("FeedbackJob", "VMSC2Schedule")
		            .build();
	
	        trigger = newTrigger()
	        		.withIdentity("FeedbackJob", "VMSC2Schedule")
	        		.startAt(new Date())
	        		.withSchedule(simpleSchedule()
	        		.withIntervalInSeconds(delay)
	        		//.withIntervalInSeconds(delay * 60)
	        		.repeatForever())
	        		.forJob(job)
	        		.build();
	        
	        scheduler.scheduleJob(job, trigger);
	        
	        //logger.info("Setting schedule for feedback success");	
        
		} catch (SchedulerException e) {
			logger.warn("Error to setting schedule" + e.toString());
			return;
		}
        
        try {
			scheduler.start();
		} catch (SchedulerException e) {
			logger.warn("Error to start schedule" + e.toString());
			return;
		}
	}
	
	@Override
	public void stop() {
		try {
			scheduler.shutdown(false);
			
			SchedulerMetaData metaData = scheduler.getMetaData();
			//logger.info("Executed: " + metaData.getNumberOfJobsExecuted() + " jobs.");
			
			schedulerFactory = null;
			scheduler = null;
			
		} catch (SchedulerException e) {
			logger.warn("Error shutdown schedule");
		}
	}
	
	@Override
	public boolean serviceStarted() {
		try {
			return scheduler.isStarted();
		} catch (Exception e) {
			logger.warn("Error get service started: " + e.toString());
			return false;
		}
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
			ResultSet result = st.executeQuery("Select PARAM_NAME, PARAM_VALUE from C_SYSTEM_CONFIGS_MLMN");
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
