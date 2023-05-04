public class StonesSunflowerLandMain {
	public static void main(String[] args) {
		System.out.println("----- Sunflower Land Clicker ------");
		clickerBot = new ClickerBot();
		System.out.println("Program uruchomi się za 2 sekundy.");
		clickerBot.sleepM(500);


		SunflowerLandConfig config = new SunflowerLandConfig();
		SunflowerLandBot bot = new SunflowerLandBot(config, clickerBot);

		bot.stones();
	}

	static ClickerBot clickerBot;
}