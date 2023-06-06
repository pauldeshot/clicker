import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TreesSunflowerLandMain {
	public static void main(String[] args) {
		System.out.println("----- Sunflower Land Clicker ------");
		clickerBot = new ClickerBot();
		System.out.println("Program uruchomi się za 2 sekundy.");
		clickerBot.sleepM(500);


		SunflowerLandConfig config = new SunflowerLandConfig();
		SunflowerLandBot bot = new SunflowerLandBot(config, clickerBot);
		Lock lock = new ReentrantLock();

		while (true) {
			if (lock.tryLock()) {
				try {
					lock.lock();
					bot.trees();
				} finally {
					lock.unlock();
				}
				break;
			}
			clickerBot.sleep(1);
		}

	}

	static ClickerBot clickerBot;
}