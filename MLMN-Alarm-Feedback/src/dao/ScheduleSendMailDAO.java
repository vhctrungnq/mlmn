package dao;

public interface ScheduleSendMailDAO {

	void start();

	void stop();

	boolean serviceStarted();
}
