package ClickerBot;

import ClickerBot.Bots.ClickerBot;

public class ClicksMain {
		public static void main(String[] args) {
		System.out.println("----- Sunflower Land Clicker ------");
		clickerBot = new ClickerBot();
		System.out.println("Program uruchomi się za 2 sekundy.");
		clickerBot.sleepM(500);


		SunflowerLandConfig config = new SunflowerLandConfig();
		SunflowerLandBot bot = new SunflowerLandBot(config, clickerBot);

		int x = 0;
		while(x < 400) {
			x++;
			clickerBot.clickMouse();
			clickerBot.sleepM(200);
		};
	}

	static ClickerBot clickerBot;
}