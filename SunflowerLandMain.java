import java.awt.event.InputEvent;
import java.util.Random;

public class SunflowerLandMain {
	// Konfiguracja dotycząca czasu czekania
	static int waitMeal = 20 * 60;
	static int fullWaitForNextFruit = 6 * 60 * 60 + 60;
	static int waitForNextFruit = 3 * 60 * 60 + 52 * 60;
	static int maxMeals = 150;

	// Konfiguracja dotycząca położenia elementów

	static int[][] fruits = {
			{347, 126},
			{426, 144},
			{838, 145},
			{838, 598},
	};

	static int[] inventory = {1504, 362};
	static int[] firePit = {561, 430};
	static int[] firePitMealButton = {869, 593};

	public static void main(String[] args) {
		System.out.println("----- Sunflower Land Clicker ------");
		clickerBot = new ClickerBot();
		System.out.println("Program uruchomi się za 2 sekundy.");
		clickerBot.sleep(2);

		int meals = 0;
		boolean firstMeal = true;

		while (true) {
			if (currentWaitForFruit >= waitForNextFruit) {
				sleepAndIncrement(1);

				for (int[] fruit : fruits) {
					clickFruitTree(fruit[0], fruit[1]);
					sleepAndIncrement(1);
				}

				for (int[] fruit : fruits) {
					clickFruitTree(fruit[0], fruit[1]);
					sleepAndIncrement(1);
				}

				swapInventory();

				for (int[] fruit : fruits) {
					clickFruitTree(fruit[0], fruit[1]);
					sleepAndIncrement(1);
				}

				swapInventory();

				waitForNextFruit = fullWaitForNextFruit;
				currentWaitForFruit = 0;
			}

			if (currentWaitingMeal >= waitMeal || firstMeal) {
				if (meals >= maxMeals) {
					currentWaitingMeal = 0;
					continue;
				}

				if (!firstMeal) {
					clickFirePit();
					sleepAndIncrement(1);
				}

				clickMeal();
				currentWaitingMeal = 0;
				meals++;
				firstMeal = false;
				System.out.println("Meal: "+ meals);
			}
			sleepAndIncrement(1);
		}
	}

	private static void clickFirePit() {
		Random rand = new Random();
		int randOffset = 3;
		clickerBot.move(firePit[0] + rand.nextInt(randOffset), firePit[1] + rand.nextInt(randOffset));
		clickerBot.click(InputEvent.BUTTON1_DOWN_MASK);
	}

	private static void clickFruitTree(int x, int y) {
		Random rand = new Random();
		int randOffset = 3;
		clickerBot.move(x + rand.nextInt(randOffset), y + rand.nextInt(randOffset));
		clickerBot.click(InputEvent.BUTTON1_DOWN_MASK);
	}
	private static void swapInventory() {
		Random rand = new Random();
		int randOffset = 3;
		clickerBot.move(inventory[0] + rand.nextInt(randOffset), inventory[1] + rand.nextInt(randOffset));
		clickerBot.click(InputEvent.BUTTON1_DOWN_MASK);
	}

	private static void clickMeal() {
		Random rand = new Random();
		int randOffset = 3;

		clickFirePit();

		sleepAndIncrement(rand.nextInt(2)+1);
		clickerBot.move(firePitMealButton[0] + rand.nextInt(randOffset), firePitMealButton[1] + rand.nextInt(randOffset));
		clickerBot.click(InputEvent.BUTTON1_DOWN_MASK);
	}

	private static void sleepAndIncrement(int x) {
		try {
			Thread.sleep(x * 1000L);
			currentWaitingMeal++;
			currentWaitForFruit++;
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	static int currentWaitForFruit = 0;
	static int currentWaitingMeal = 0;
	static ClickerBot clickerBot;
}