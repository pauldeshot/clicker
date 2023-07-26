package ClickerBot;

import ClickerBot.Bots.ClickerBot;

public class CoordinateMain {
    static ClickerBot clickerBot;
    public static void main(String[] args) {
        System.out.println("----- Coordinate printer ------");
        clickerBot = new ClickerBot();

        System.out.println("Program uruchomi się za 2 sekundy.");
        clickerBot.sleep(2);

        while (true) {
            System.out.println("Wait 2s.");
            clickerBot.sleep(4);
            clickerBot.printCoordinate();
        }
    }
}
