package ai.api.examples;


import java.util.Timer;
import java.util.TimerTask;
import java.awt.Toolkit;

/**
 * Author: Crunchify.com
 */

public class CrunchifyTimerExample {
	Toolkit toolkit;
	Timer timer;

	public CrunchifyTimerExample() {
		toolkit = Toolkit.getDefaultToolkit();
		timer = new Timer();
		timer.schedule(new CrunchifyReminder(), 0, // initial delay
				20 * 1000); // subsequent rate
	}

	class CrunchifyReminder extends TimerTask {
		int loop = 5;

		public void run() {
			if (loop > 0) {
				toolkit.beep();
//				System.out.format("Knock Knock..!\n");
				TestpopupReminder popupReminder = new TestpopupReminder();
				popupReminder.main(null);
				loop--;
			} else {
				toolkit.beep();
				System.out.format("\nThat's it.. Done..!");
				timer.cancel();
			}
		}
	}

	public static void main(String args[]) {
		new CrunchifyTimerExample();
		System.out.format("Task scheduled..!%n \n");
	}
}


