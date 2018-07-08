package vo.alarm.utils;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import dao.ScheduleSendMailDAO;
public class ScheduleSendMail {
	
	private static final Logger logger = Logger.getLogger(ScheduleSendMail.class);
	@Autowired
	private ScheduleSendMailDAO scheduleSendMailDAO;
	
	@PostConstruct
    public void start() {
		 //Khoi dong core send mail auto
		  try {
			   logger.info("Starting send mail auto service..");
			// start Schedule service
				scheduleSendMailDAO.start();
		  } catch (Exception ex) {
			   logger.error("Cannot start send mail auto Service", ex);
		  }
		
    }
}
