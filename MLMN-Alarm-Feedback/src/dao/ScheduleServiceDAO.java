package dao;

public interface ScheduleServiceDAO {

	void start();

	void stop();

	boolean serviceStarted();
}
