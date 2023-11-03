package ClickerBot;

import ClickerBot.Bots.ClickerBot;
import ClickerBot.Bots.WombatBot;
import ClickerBot.Config.WombatConfig;
import ClickerBot.DTO.WombatResult;

import java.util.Calendar;
import java.util.Date;
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

        int finishedRuns = 0;
        Date nextTreasure = getNextTreasureTime();


        while (true) {
            if (lock.tryLock()) {
                try {
                    if (finishedRuns >= 180) {
                        clickerBot.sleep(60);
                        continue;
                    }

                    Date currentDate = new Date();
                    if (currentDate.compareTo(nextTreasure) >= 0) {
                        finishedRuns = 0;
                        bot.claimTreasure();
                        nextTreasure = getNextTreasureTime();
                    }

                    WombatResult result = bot.run(currentWaitingRun);
                    clickerBot.sleep(1);
                    currentWaitingRun += result.totalWaitingTime;

                    if (result.resetTime) {
                        finishedRuns++;
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

    private static Date getNextTreasureTime() {
        Date currentTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);

        if (calendar.get(Calendar.HOUR_OF_DAY) < 2 || (calendar.get(Calendar.HOUR_OF_DAY) == 2 && calendar.get(Calendar.MINUTE) < 10)) {
            calendar.set(Calendar.HOUR_OF_DAY, 2);
            calendar.set(Calendar.MINUTE, 15);
        } else {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 2);
            calendar.set(Calendar.MINUTE, 15);
        }
        return calendar.getTime();
    }
}