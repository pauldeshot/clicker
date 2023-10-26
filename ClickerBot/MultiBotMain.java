package ClickerBot;

import ClickerBot.Bots.ClickerBot;
import ClickerBot.Bots.SunflowerLandBot;
import ClickerBot.Bots.WombatBot;
import ClickerBot.Config.SunflowerLandConfig;
import ClickerBot.Config.WombatConfig;
import ClickerBot.DTO.FarmData;
import ClickerBot.Enums.Crops;
import ClickerBot.Enums.FirePitMeals;
import ClickerBot.Enums.FruitDrinks;
import ClickerBot.UI.DotAlert;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MultiBotMain {
    private static DotAlert dotAlert;

    public static void main(String[] args) {
        System.out.println("----- SFL && Wombat Clicker ------");
        clickerBot = new ClickerBot();

        dotAlert = new DotAlert();
        dotAlert.yellow();

        System.out.println("Program starts in 2 seconds.");
        clickerBot.sleepM(2000);

        SunflowerLandConfig config = new SunflowerLandConfig();
        SunflowerLandBot bot = new SunflowerLandBot(config, clickerBot);

        WombatConfig wombatConfig = new WombatConfig();
        WombatBot wombatBot = new WombatBot(wombatConfig, clickerBot);

        int mealsFirePitCount = 0;
        int mealsSmoothieShackCount = 0;

        String[] crops = {
            Crops.Sunflower,
            Crops.Sunflower,
            Crops.Sunflower,
            Crops.Sunflower,
            Crops.Sunflower,
            Crops.Sunflower,
            Crops.Sunflower,
            Crops.Sunflower,
            Crops.Sunflower,
            Crops.Sunflower,
            Crops.Sunflower,
            Crops.Sunflower
        };

        String mealFirePit = FirePitMeals.Popcorn;
        String mealSmoothieShack = FruitDrinks.PurpleSmoothie;

        boolean farmCrops = true;
        boolean cookFirePitMeal = true;
        boolean cookSmoothieShackMeal = true;
        boolean collectResources = true;
        boolean wombat = true;

        int wombatRuns = 0;
        int delayCrops = 0;
        //      int delayCrops = 1 * 60 * 60 + 40 * 60;

        int mealsFirePitTarget = 40;
        int mealsSmoothieShackTarget = 40;

        Map<Integer, String> cropsQueue = getQueue(crops);

        int currentCrop = 0;
        int resourceWait = 13 * 60 + 15;

        Date nextCrop = getTimePlusSecond(delayCrops);
        Date nextResource = new Date();
        Date nextWombatRun = new Date();
        Date nextFirePitMeal = new Date();
        Date nextSmoothieShackMeal = new Date();
        Date nextTreasure = getNextTreasureTime();


        int waitWombat = 4 * 60 + 50;
        boolean firstFirePitMeal = true;
        boolean firstSmoothieShackMeal = true;

        FarmData globalFarmData = null;

        while (true) {
            Date currentDate = new Date();
            // alert
            if (
                shouldBeAlert(nextCrop, farmCrops) ||
                shouldBeAlert(nextFirePitMeal, cookFirePitMeal) ||
                shouldBeAlert(nextSmoothieShackMeal, cookSmoothieShackMeal) ||
                shouldBeAlert(nextResource, collectResources) ||
                shouldBeAlert(nextWombatRun, wombat && wombatRuns <= 180)
            ) {
                dotAlert.yellow();
            } else {
                dotAlert.green();
            }

            if (farmCrops && cropsQueue.containsKey(currentCrop) && currentDate.compareTo(nextCrop) >= 0) {
                dotAlert.red();
                bot.clickInTab();
                FarmData farmData = bot.checkFarm();
                if (globalFarmData == null) {
                    globalFarmData = farmData;
                }
                bot.inventory(cropsQueue.get(currentCrop));
                nextCrop = getTimePlusSecond(config.cropsTimes.get(cropsQueue.get(currentCrop)) + 10);
                bot.crops(farmData, true);
                bot.save();
                currentCrop++;
                System.out.println("Next crops: " + nextCrop.toString());
            }

            if (collectResources && currentDate.compareTo(nextResource) >= 0) {
                dotAlert.red();

                if (globalFarmData == null) {
                    globalFarmData = bot.checkFarm();
                }

                bot.clickInTab();
                bot.resources();
                bot.minerals(globalFarmData.minerals);
                nextResource = getTimePlusSecond(resourceWait);
                System.out.println("Next resources: " + nextResource.toString());
            }

            if (cookFirePitMeal && currentDate.compareTo(nextFirePitMeal) >= 0) {
                dotAlert.red();
                bot.clickInTab();
                bot.collectMealFirePit(mealFirePit, firstFirePitMeal);
                mealsFirePitCount++;
                firstFirePitMeal = false;
                if (mealsFirePitCount >= mealsFirePitTarget) {
                    cookFirePitMeal = false;
                } else {
                    nextFirePitMeal = getTimePlusSecond(config.mealsTimes.get(mealFirePit) + 15);
                    System.out.println("Next Fire Pit meal: " + nextFirePitMeal.toString());
                }
            }

            if (cookSmoothieShackMeal && currentDate.compareTo(nextSmoothieShackMeal) >= 0) {
                dotAlert.red();
                bot.clickInTab();
                bot.collectSmootieShack(mealSmoothieShack, firstSmoothieShackMeal);
                mealsSmoothieShackCount++;
                firstSmoothieShackMeal = false;
                if (mealsSmoothieShackCount >= mealsSmoothieShackTarget) {
                    cookSmoothieShackMeal = false;
                } else {
                    nextSmoothieShackMeal = getTimePlusSecond(config.mealsTimes.get(mealSmoothieShack) + 15);
                    System.out.println("Next Smoothie Shack meal: " + nextSmoothieShackMeal.toString());
                }
            }

            if (wombat) {
                if (currentDate.compareTo(nextTreasure) >= 0) {
                    dotAlert.red();
                    wombatBot.claimTreasure();
                    wombatRuns = 0;
                    nextTreasure = getNextTreasureTime();
                    System.out.println("Next wombat treasure: " + nextTreasure.toString());
                }

                if (currentDate.compareTo(nextWombatRun) >= 0 && wombatRuns <= 180) {
                    dotAlert.red();
                    wombatBot.run(60 * 60 * 24);
                    wombatRuns++;
                    nextWombatRun = getTimePlusSecond(waitWombat);
                    System.out.println("Next wombat: " + nextWombatRun.toString());
                }
            }

            clickerBot.sleep(1);
        }
    }

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

    public static Map<Integer, String> getQueue(String[] queue) {
        Map<Integer, String> queueList = new HashMap<>();
        for (int i = 0; i < queue.length; i++) {
            queueList.put(i, queue[i]);
        }
        return queueList;
    }

    public static boolean shouldBeAlert(Date nextDate, boolean globalConfigSwitch) {
        Date currentDate = new Date();
        return ((nextDate.getTime() - currentDate.getTime()) / 1000) < 10 && globalConfigSwitch;
    }

    private static Date getTimePlusSecond(int seconds) {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    static ClickerBot clickerBot;
}