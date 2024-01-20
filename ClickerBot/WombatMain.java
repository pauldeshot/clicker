package ClickerBot;

import ClickerBot.Bots.ClickerBot;
import ClickerBot.Bots.WombatBot;
import ClickerBot.Config.WombatConfig;
import ClickerBot.DTO.WombatResult;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Scanner;

public class WombatMain {
    public static void main(String[] args) {
        System.out.println("----- Wombat clicker ------");

        WombatConfig config = new WombatConfig();
        clickerBot = new ClickerBot();
        WombatBot bot = new WombatBot(config, clickerBot);

        Lock lock = new ReentrantLock();
        
        Date nextTreasure = getNextTreasureTime();

        Scanner userFirstDecisionInput = new Scanner(System.in);

        boolean isSetCurrentRun = false;
        while(true) {

            if(isSetCurrentRun) {
                break;
            }
            
            System.out.println("Do you want to set current run or start from 0? Yes/No ?");

            String input = userFirstDecisionInput.nextLine().toLowerCase();

            if (!input.isEmpty() && (input.equals("yes") || input.equals("no"))) {
                if (input.equals("yes")) {
                    Scanner userSetCurrentRunInput = new Scanner(System.in);
                    while(true) {
                        System.out.println("Set your current run.");
                        String inputForSettingRun = userFirstDecisionInput.nextLine();
                        try {
                            int intValue = Integer.parseInt(inputForSettingRun);
                            if (intValue > 0 && intValue < config.maxRuns) {
                                config.currentRunNumber = intValue;
                                isSetCurrentRun = true;
                                break;
                            }
                            else {
                                System.out.println("Value is not in expectable range.");
                            }
                            
                        } catch (NumberFormatException e) {
                            System.out.println("Input String cannot be parsed to Integer.");
                        }
                    }
                }
                else {
                    config.currentRunNumber = 0;
                    break;
                }
            }
            else {
                System.out.println("Incorrect value please set corret one! Yes or No");
            }
        }

        int finishedRuns = config.currentRunNumber;
        System.out.println("App will start in 2 seconds.");
        clickerBot.sleep(2);

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
                        StringBuilder stringInfo = new StringBuilder(30); 
                        stringInfo.append(String.format("\rThe number of the last completed run is: %d", finishedRuns));
                        System.out.print(stringInfo);
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
