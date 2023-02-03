public class WombatMain {
	// Konfiguracja dotycząca czasu czekania
	static int waitRun = 4 * 60 + 22;

	// Konfiguracja dotycząca położenia elementów
	static int[] runButton = {738, 543};
	static int[] rewardButton = {819, 675};
	public static void main(String[] args) {
		System.out.println("----- Wombat clicker ------");
		clickerBot = new ClickerBot();
		System.out.println("Program uruchomi się za 2 sekundy.");
		clickerBot.sleep(2);

		int runs = 0;
		while (true) {
			if (currentWaitingRun >= waitRun || currentWaitingRun == 0) {
				if (currentWaitingRun > 0) {
					sleepAndIncrement(3);
					claim();
					sleepAndIncrement(3);
				}
				run();
				currentWaitingRun = 0;
				runs++;
				System.out.println("Run: "+ runs);
			}
			sleepAndIncrement(1);
		}
	}

	private static void run() {
		clickerBot.move(runButton[0], runButton[1]);
		clickerBot.clickMouse();
	}
	private static void claim() {
		clickerBot.move(rewardButton[0], rewardButton[1]);
		clickerBot.clickMouse();
	}

	public static void sleepAndIncrement(int x) {
		clickerBot.sleep(x);
		currentWaitingRun += x;
	}

	static int currentWaitingRun = 0;
	static ClickerBot clickerBot;
}