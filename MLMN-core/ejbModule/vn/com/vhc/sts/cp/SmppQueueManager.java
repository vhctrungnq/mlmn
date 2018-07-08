package vn.com.vhc.sts.cp;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import vn.com.vhc.sts.utils.STS_DBUtils;

public class SmppQueueManager extends Thread {

	private volatile boolean stop = false;

	private static final int NTHREDS = 10;
	private static final ExecutorService threadPool = Executors
			.newFixedThreadPool(NTHREDS);

	public SmppQueueManager() {
	}

	public void requestStop() {
		stop = true;
	}

	@Override
	public void run() {
		while (!stop) {
			List<SmppQueue> smppList = STS_DBUtils.getSmppQueue();

			for (SmppQueue smppQueue : smppList) {
				Runnable cpProcess = new CpProcess(smppQueue);
				threadPool.execute(cpProcess);
				
				STS_DBUtils.updateSmppQueueStatus(smppQueue);
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}
}
