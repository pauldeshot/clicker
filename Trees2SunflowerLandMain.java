public class Trees2SunflowerLandMain {
	public static void main(String[] args) {
		System.out.println("----- Sunflower Land Clicker ------");
		clickerBot = new ClickerBot();
		System.out.println("Program uruchomi się za 2 sekundy.");
		clickerBot.sleepM(500);


		SunflowerLandConfig config = new SunflowerLandConfig();
		SunflowerLandBot bot = new SunflowerLandBot(config, clickerBot);

		while (true) {
			bot.trees();
			clickerBot.sleepM(1000);
			bot.inventory("Pickaxe");
			clickerBot.sleepM(1000);
			bot.stones();
			clickerBot.sleepM(1000);
			bot.inventory("Axe");
			clickerBot.sleep(36 * 60 + 15);
		}
	}

	static ClickerBot clickerBot;
}