import java.awt.event.InputEvent;
import java.util.Random;

public class SunflowerLandMain {
	// Konfiguracja dotycząca czasu czekania


	public static void main(String[] args) {
		System.out.println("----- Sunflower Land Clicker ------");
		clickerBot = new ClickerBot();
		System.out.println("Program uruchomi się za 2 sekundy.");
		clickerBot.sleep(2);


		SunflowerLandConfig config = new SunflowerLandConfig();
		SunflowerLandBot bot = new SunflowerLandBot(config, clickerBot);

		while (true) {
			SunflowerLandResult result = bot.run(currentWaitForFruit, currentWaitingMeal);

			currentWaitForFruit += result.totalWaitingTime;
			currentWaitingMeal += result.totalWaitingTime;
			if (result.resetWaitingMealTime) {
				currentWaitingMeal = 0;
			}
			if (result.resetWaitForFruitTime) {
				currentWaitForFruit = 0;
			}
		}
	}

	static int currentWaitForFruit = 0;
	static int currentWaitingMeal = 0;
	static ClickerBot clickerBot;
}