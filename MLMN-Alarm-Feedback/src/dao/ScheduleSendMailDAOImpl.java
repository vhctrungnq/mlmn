package dao;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

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

public class ScheduleSendMailDAOImpl extends SqlMapClientDaoSupport implements ScheduleSendMailDAO{

	private static Logger logger = Logger.getLogger(ScheduleSendMailDAOImpl.class.getName());
			
	private static SchedulerFactory schedulerFactory;
	private static Scheduler scheduler;
	private static DataSource dataSource = null;
	private static CallableStatement cs = null;
	private static Connection con = null;
	@Override
	public void start() {
		
        try {
        	schedulerFactory = new StdSchedulerFactory();
        	Properties props = new Properties();
     		props.setProperty(StdSchedulerFactory.PROP_THREAD_POOL_CLASS,
     				"org.quartz.simpl.SimpleThreadPool");
     		props.put("org.quartz.threadPool.threadCount", "4");
     		props.put("org.quartz.scheduler.instanceName ","MailScheduler");
     		((StdSchedulerFactory) schedulerFactory).initialize(props);
        	scheduler = schedulerFactory.getScheduler();
        	try {
				ScheduleSendMailDAOImpl.loadSystemConfig();
				ScheduleSendMailDAOImpl.loadMailConfig();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SchedulerException e) {
			logger.info("Error start Core report");
		}
        try
        { 
	        //job send mail
        	String sendMailDelay=AL_Global.MAIL_SYSTEM_CONFIG.getProperty(SendMailSetting.SENDMAIL_DELAY);
        	if (sendMailDelay==null)
        	{
        		sendMailDelay ="500";
        	}
        	System.out.println("sendMailDelay :"+sendMailDelay);
	        Integer delaySendMail = Integer.parseInt(sendMailDelay);
	        String sHour= AL_Global.MAIL_SYSTEM_CONFIG.getProperty(SendMailSetting.SENDMAIL_START_HOUR);
	        if (sHour==null)
        	{
	        	sHour ="1";
        	}
	        Integer startHour = Integer.parseInt(sHour);
	
	        Calendar cal = Calendar.getInstance();	
			cal.setTime(new Date());
			/*if (cal.HOUR <startHour.intValue())
			{
				cal.set(Calendar.HOUR_OF_DAY, startHour.intValue());
			}
			else
			{
				cal.add(Calendar.HOUR_OF_DAY, 1);
			}*/
			//cal.set(Calendar.MINUTE, 0);
			//cal.set(Calendar.SECOND, 0);
			
			JobDetail job2 = null;
        	Trigger trigger2 = null;
        	job2 = newJob(SendMailJob.class)
					.withIdentity("SendMailJob", "VMSC2Schedule")
		            .build();
	
	        trigger2 = newTrigger()
	        		.withIdentity("SendMailJob", "VMSC2Schedule")
	        		.startAt(cal.getTime())
	        		.withSchedule(simpleSchedule()
	        		.withIntervalInSeconds(delaySendMail)
	        		.repeatForever())
	        		.forJob(job2)
	        		.build();
	        
	        scheduler.scheduleJob(job2, trigger2);
	        
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
		AL_Global.MAIL_SYSTEM_CONFIG.clear();
		ResultSet result = null;
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
				//System.out.println("Key: "+ key+"---- value: "+value);
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
	//Lay cac gia tri config mail trong c_MAIL_SYSTEM_CONFIG
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
			}
}

