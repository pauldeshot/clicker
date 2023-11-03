package ClickerBot;

import ClickerBot.Bots.ClickerBot;
import ClickerBot.Bots.SunflowerLandBot;
import ClickerBot.Bots.WombatBot;
import ClickerBot.Config.SunflowerLandConfig;
import ClickerBot.Config.WombatConfig;
import ClickerBot.DTO.Building;
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
            Crops.Cabbage,
            Crops.Cabbage,
            Crops.Carrot,
            Crops.Carrot,
            Crops.Pumpkin,
            Crops.Pumpkin,
            Crops.Pumpkin,
        };

        String mealFirePit = FirePitMeals.Popcorn;
        String mealSmoothieShack = FruitDrinks.PurpleSmoothie;

        boolean farmCrops = false;
        boolean cookFirePitMeal = true;
        boolean cookSmoothieShackMeal = true;
        boolean collectResources = true;
        boolean collectFertilisers = true;
        boolean wombat = true;

        int wombatRuns = 0;
        int delayCrops = 0;
//        int delayCrops = 1 * 60 * 60 + 45 * 60;

        int mealsFirePitTarget = 40;
        int mealsSmoothieShackTarget = 40;

        Map<Integer, String> cropsQueue = getQueue(crops);

        int currentCrop = 0;
        int resourceWait = 13 * 60 + 15;

        Date nextCrop = getTimePlusSecond(delayCrops);
        Date nextResource = new Date();
        Date nextWombatRun = new Date();
        Date nextCheckFarm = new Date();
        Date nextFirePitMeal = null;
        Date nextSmoothieShackMeal = null;
        Date nextComposterSmall = null;
        Date nextComposterMedium = null;
        Date nextComposterLarge = null;
        Date nextTreasure = getNextTreasureTime();


        int waitWombat = 4 * 60 + 50;

        FarmData globalFarmData = null;

        while (true) {
            Date currentDate = new Date();
            // alert
            if (
                shouldBeAlert(nextCrop, farmCrops) ||
                (nextFirePitMeal != null && shouldBeAlert(nextFirePitMeal, cookFirePitMeal)) ||
                (nextSmoothieShackMeal != null && shouldBeAlert(nextSmoothieShackMeal, cookSmoothieShackMeal)) ||
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

            if ((collectFertilisers || cookFirePitMeal || cookSmoothieShackMeal) && currentDate.compareTo(nextCheckFarm) >= 0) {
                FarmData farmData = bot.checkFarm();
                Map<String, Building> buildings = farmData.buildings;

                long readyAt = buildings.get("Premium Composter").readyAt;
                if (readyAt > 0) {
                    nextComposterLarge = addSecondsToDate(new Date(readyAt), 10);
                } else if (!buildings.get("Premium Composter").isProducing) {
                    nextComposterLarge = new Date();
                }
                System.out.println("Next large composter: " + nextComposterLarge);

                readyAt = buildings.get("Turbo Composter").readyAt;
                if (readyAt > 0) {
                    nextComposterMedium = addSecondsToDate(new Date(readyAt), 10);
                } else if (!buildings.get("Turbo Composter").isProducing) {
                    nextComposterMedium = new Date();
                }
                System.out.println("Next medium composter: " + nextComposterMedium);

                readyAt = buildings.get("Compost Bin").readyAt;
                if (readyAt > 0) {
                    nextComposterSmall = addSecondsToDate(new Date(readyAt), 10);
                } else if (!buildings.get("Compost Bin").isProducing) {
                    nextComposterSmall = new Date();
                }
                System.out.println("Next small composter: " + nextComposterSmall);

                readyAt = buildings.get("Fire Pit").readyAt;
                if (readyAt > 0) {
                    nextFirePitMeal = addSecondsToDate(new Date(readyAt), 10);
                } else if (!buildings.get("Fire Pit").isProducing) {
                    nextFirePitMeal = new Date();
                }
                System.out.println("Next next Fire Pit: " + nextFirePitMeal);

                readyAt = buildings.get("Smoothie Shack").readyAt;
                if (readyAt > 0) {
                    nextSmoothieShackMeal = addSecondsToDate(new Date(readyAt), 10);
                } else if (!buildings.get("Smoothie Shack").isProducing) {
                    nextSmoothieShackMeal = new Date();
                }
                System.out.println("Next next Smoothie Shack: " + nextSmoothieShackMeal);

                nextCheckFarm = getTimePlusSecond(10 * 60);
            }

            if (collectFertilisers && nextComposterSmall != null && currentDate.compareTo(nextComposterSmall) >= 0) {
                dotAlert.red();
                bot.clickInTab();
                bot.collectSmallComposter();
                nextComposterSmall = null;
            }

            if (collectFertilisers && nextComposterMedium != null && currentDate.compareTo(nextComposterMedium) >= 0) {
                dotAlert.red();
                bot.clickInTab();
                bot.collectMediumComposter();
                nextComposterMedium = null;
            }

            if (collectFertilisers && nextComposterLarge != null && currentDate.compareTo(nextComposterLarge) >= 0) {
                dotAlert.red();
                bot.clickInTab();
                bot.collectLargeComposter();
                nextComposterLarge = null;
            }


            if (cookFirePitMeal && nextFirePitMeal != null && currentDate.compareTo(nextFirePitMeal) >= 0) {
                dotAlert.red();
                bot.clickInTab();
                bot.collectMealFirePit();
                bot.cookMealFirePit(mealFirePit);
                mealsFirePitCount++;
                if (mealsFirePitCount >= mealsFirePitTarget) {
                    cookFirePitMeal = false;
                } else {
                    nextFirePitMeal = null;
                }
            }

            if (cookSmoothieShackMeal && nextSmoothieShackMeal != null && currentDate.compareTo(nextSmoothieShackMeal) >= 0) {
                dotAlert.red();
                bot.clickInTab();
                bot.collectSmootieShack();
                bot.cookSmootieShack(mealSmoothieShack);
                mealsSmoothieShackCount++;
                if (mealsSmoothieShackCount >= mealsSmoothieShackTarget) {
                    cookSmoothieShackMeal = false;
                } else {
                    nextSmoothieShackMeal = null;
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

    private static Date addSecondsToDate(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    static ClickerBot clickerBot;
}