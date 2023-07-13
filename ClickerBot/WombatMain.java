package ClickerBot;

import ClickerBot.Bots.ClickerBot;
import ClickerBot.Bots.WombatBot;
import ClickerBot.Config.WombatConfig;
import ClickerBot.DTO.WombatResult;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WombatMain {
    public static void main(String[] args) {
        System.out.println("----- Wombat clicker ------");
        clickerBot = new ClickerBot();
        System.out.println("Program uruchomi się za 2 sekundy.");
        clickerBot.sleep(2);

        WombatConfig config = new WombatConfig();
        WombatBot bot = new WombatBot(config, clickerBot);

        Lock lock = new ReentrantLock();

        while (true) {
            if (lock.tryLock()) {
                try {
                    WombatResult result = bot.run(currentWaitingRun);
                    clickerBot.sleep(1);
                    currentWaitingRun += result.totalWaitingTime;

                    if (result.resetTime) {
                        currentWaitingRun = 0;
                    }
                } finally {
                    lock.unlock();
                }
            } else {
                clickerBot.sleep(1);
            }
        }
    }

    static int currentWaitingRun = -1;
    static ClickerBot clickerBot;
}