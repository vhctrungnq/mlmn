package vo.alarm.utils.feedback;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import dao.ScheduleServiceDAO;

/**
 * Schedule Init
 */
public class ScheduleInit {
	@Autowired
	private ScheduleServiceDAO scheduleServiceDAO;
	
	@PostConstruct
    public void start() {
		
		// start Schedule service
		scheduleServiceDAO.start();
    }
}
