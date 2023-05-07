public class WombatMain {
	public static void main(String[] args) {
		System.out.println("----- Wombat clicker ------");
		clickerBot = new ClickerBot();
		System.out.println("Program uruchomi się za 2 sekundy.");
		clickerBot.sleep(2);

		WombatConfig config = new WombatConfig();
		WombatBot bot = new WombatBot(config, clickerBot);

		while (true) {
			WombatResult result = bot.run(currentWaitingRun);
			currentWaitingRun += result.totalWaitingTime;

			if (result.resetTime) {
				currentWaitingRun = 0;
			}
		}
	}

	static int currentWaitingRun = -1;
	static ClickerBot clickerBot;
}