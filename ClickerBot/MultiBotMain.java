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

    private static boolean tryClaimTreasure = false;
    private static int finishedRuns = 0;

    private static DotAlert dotAlert;

    public static void main(String[] args) {
        dotAlert = new DotAlert();
        dotAlert.yellow();

        System.out.println("----- SFL && Wombat Clicker ------");

        clickerBot = new ClickerBot();
        System.out.println("Program starts in 2 seconds.");
        clickerBot.sleepM(2000);

        SunflowerLandConfig config = new SunflowerLandConfig();
        SunflowerLandBot bot = new SunflowerLandBot(config, clickerBot);

        WombatConfig wombatConfig = new WombatConfig();
        WombatBot wombatBot = new WombatBot(wombatConfig, clickerBot);

        Map<String, Integer> mealsFirePitCount = FirePitMeals.getList();
        Map<String, Integer> mealsFruitCount = FruitDrinks.getList();

        String[] crops = {
            Crops.Sunflower,
        };

        String[] mealsFirePit = {FirePitMeals.Popcorn};
        String[] mealsFruit = {FruitDrinks.PurpleSmoothie};

        boolean farmCrops = true;
        boolean cookFirePitMeal = false;
        boolean cookFruitMeal = false;
        boolean collectResources = false;
        boolean wombat = false;

        int delayCrops = 0;
//        int delayCrops = 8 * 60 * 60 + 40 * 60;

        Map<String, Integer> mealsFirePitTarget = new HashMap<>();
        mealsFirePitTarget.put(FirePitMeals.Popcorn, 15);

        Map<String, Integer> mealsFruitTarget = new HashMap<>();
        mealsFruitTarget.put(FruitDrinks.PurpleSmoothie, 20);


        Map<Integer, String> cropsQueue = getQueue(crops);
        Map<Integer, String> mealsFirePitQueue = getQueue(mealsFirePit);
        Map<Integer, String> mealsFruitQueue = getQueue(mealsFruit);

        int currentCrop = 0;
        int currentMealFirePit = 0;
        int currentMealFruit = 0;
        int resourceWait = 13 * 60 + 15;

        Date nextCrop = getTimePlusSecond(delayCrops);
        Date nextResource = new Date();
        Date nextWombatRun = new Date();
        Date nextFirePitMeal = new Date();
        Date nextFruitMeal = new Date();

        int waitWombat = 5 * 60;
        boolean firstFirePitMeal = true;
        boolean firstFruitMeal = true;

        while (true) {
            Date currentDate = new Date();
            // alert
            if (
                shouldBeAlert(nextCrop, farmCrops) ||
                shouldBeAlert(nextFirePitMeal, cookFirePitMeal) ||
                shouldBeAlert(nextFruitMeal, cookFruitMeal) ||
                shouldBeAlert(nextResource, collectResources) ||
                shouldBeAlert(nextWombatRun, wombat)
            ) {
                dotAlert.yellow();
            } else {
                dotAlert.green();
            }

            if (farmCrops && cropsQueue.containsKey(currentCrop) && currentDate.compareTo(nextCrop) >= 0) {
                dotAlert.red();
                bot.clickInTab();
                FarmData farmData = bot.checkFarm();
                bot.inventory(cropsQueue.get(currentCrop));
                nextCrop = getTimePlusSecond(config.cropsTimes.get(cropsQueue.get(currentCrop)) + 5);
                bot.crops(farmData, true);
                currentCrop++;
                System.out.println("Next crops: " + nextCrop.toString());
            }

            if (collectResources && currentDate.compareTo(nextResource) >= 0) {
                dotAlert.red();
                bot.clickInTab();
                bot.resources();
                nextResource = getTimePlusSecond(resourceWait);
                System.out.println("Next resources: " + nextResource.toString());
            }

            if (cookFirePitMeal &&
                    mealsFirePitQueue.containsKey(currentMealFirePit) &&
                    currentDate.compareTo(nextFirePitMeal) >= 0) {
                dotAlert.red();
                if (mealsFirePitQueue.containsKey(currentMealFirePit) &&
                        mealsFirePitCount.get(mealsFirePitQueue.get(currentMealFirePit)) >= mealsFirePitTarget.get(mealsFirePitQueue.get(currentMealFirePit))) {
                    currentMealFirePit++;
                    nextFirePitMeal = new Date();
                } else {
                    bot.clickInTab();
                    bot.collectMealFirePit(mealsFirePitQueue.get(currentMealFirePit), firstFirePitMeal);
                    mealsFirePitCount.put(mealsFirePitQueue.get(currentMealFirePit), mealsFirePitCount.get(mealsFirePitQueue.get(currentMealFirePit)) + 1);
                    firstFirePitMeal = false;
                    nextFirePitMeal = getTimePlusSecond(config.mealsTimes.get(mealsFirePitQueue.get(currentMealFirePit)) + 2);
                    System.out.println("Next meal fire pit: " + nextFirePitMeal.toString());
                }
            }

            if (cookFruitMeal &&
                    mealsFruitQueue.containsKey(currentMealFruit) &&
                    currentDate.compareTo(nextFruitMeal) >= 0) {
                dotAlert.red();
                if (mealsFruitQueue.containsKey(currentMealFruit) &&
                        mealsFruitCount.get(mealsFruitQueue.get(currentMealFruit)) >= mealsFruitTarget.get(mealsFruitQueue.get(currentMealFruit))) {
                    currentMealFruit++;
                    nextFruitMeal = new Date();
                } else {
                    bot.clickInTab();
                    bot.collectMealFruit(mealsFruitQueue.get(currentMealFruit), firstFruitMeal);
                    mealsFruitCount.put(mealsFruitQueue.get(currentMealFruit), mealsFruitCount.get(mealsFruitQueue.get(currentMealFruit)) + 1);
                    firstFruitMeal = false;
                    nextFruitMeal = getTimePlusSecond(config.mealsTimes.get(mealsFruitQueue.get(currentMealFruit)) + 2);
                    System.out.println("Next meal fruit: " + nextFruitMeal.toString());
                }
            }

            if (wombat) {
                if (!tryClaimTreasure) {
                    dotAlert.red();
                    wombatBot.claimTreasure();
                    tryClaimTreasure = true;
                }

                if (currentDate.compareTo(nextWombatRun) >= 0) {
                    dotAlert.red();
                    wombatBot.run(60 * 60 * 24);
                    nextWombatRun = getTimePlusSecond(waitWombat);
                    System.out.println("Next wombat: " + nextWombatRun.toString());

                    finishedRuns++;
                    if (finishedRuns % 5 == 0) {
                        tryClaimTreasure = false;
                    }
                }
            }

            clickerBot.sleep(1);
        }
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