package ClickerBot;

import ClickerBot.Bots.ClickerBot;

public class ClickMain {
    static ClickerBot clickerBot;
    public static void main(String[] args) {
        System.out.println("----- Coordinate printer ------");
        clickerBot = new ClickerBot();

        System.out.println("Program uruchomi się za 2 sekundy.");
        clickerBot.sleep(2);

        int clicks = 500;
        int c = 0;
        while (c <= clicks) {
            c++;
            clickerBot.clickMouse();
            clickerBot.sleepM(300);
        }
    }
}
