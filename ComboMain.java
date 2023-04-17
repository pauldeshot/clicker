public class ComboMain {
	public static void main(String[] args) {
		System.out.println("----- Wombat clicker ------");
		clickerBot = new ClickerBot();
		System.out.println("Program uruchomi się za 2 sekundy.");
		clickerBot.sleep(2);

		WombatConfig w_config = new WombatConfig(4 * 60 + 22, 200);
		WombatBot w_bot = new WombatBot(w_config, clickerBot);

		SunflowerLandConfig sfl_config = new SunflowerLandConfig(
			54,
			4 * 60 * 60 + 60 * 60,
			200
		);
		SunflowerLandBot sfl_bot = new SunflowerLandBot(sfl_config, clickerBot);

		while (true) {
			WombatResult w_result = w_bot.run(currentWaitingRun);

			currentWaitingRun += w_result.totalWaitingTime;
			currentWaitForFruit += w_result.totalWaitingTime;
			currentWaitingMeal += w_result.totalWaitingTime;
			if (w_result.resetTime) {
				currentWaitingRun = 0;
			}

			SunflowerLandResult sfl_result = sfl_bot.run(currentWaitForFruit, currentWaitingMeal);
			currentWaitForFruit += sfl_result.totalWaitingTime;
			currentWaitingMeal += sfl_result.totalWaitingTime;
			currentWaitingRun += sfl_result.totalWaitingTime;
			if (sfl_result.resetWaitingMealTime) {
				currentWaitingMeal = 0;
			}
			if (sfl_result.resetWaitForFruitTime) {
				currentWaitForFruit = 0;
			}
		}
	}

	static int currentWaitingRun = -1;
	static int currentWaitForFruit = 0;
	static int currentWaitingMeal = 0;
	static ClickerBot clickerBot;
}