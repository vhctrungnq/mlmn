package vo.alarm.utils;

import inventory.core.IN_CoreServiceLocal;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import vn.com.vhc.alarm.core.AL_CoreServiceLocal;


public class MyServlet{
	/*private static final long serialVersionUID = 1L;*/
	private static final Logger logger = Logger.getLogger(MyServlet.class);
	
	@Autowired
	private AL_CoreServiceLocal aL_CoreService;
	@Autowired
	private IN_CoreServiceLocal in_CoreService;
	@PostConstruct
    public void start() {
	  //Khoi dong core Alarm
	  try {
		   logger.info("Starting alarm service..");
		   aL_CoreService.start();
	  } catch (Exception ex) {
		   logger.error("Cannot start alarm Service", ex);
	  }
	  //Khoi dong core Inventory
	  try {
		   logger.info("Starting inventory service..");
		   //in_CoreService.start();
	  } catch (Exception ex) {
		   logger.error("Cannot start inventory service", ex);
	  }
  
    }
}
