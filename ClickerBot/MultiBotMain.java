package ClickerBot;

import ClickerBot.Bots.ClickerBot;
import ClickerBot.Bots.SunflowerLandBot;
import ClickerBot.Bots.WombatBot;
import ClickerBot.Config.SunflowerLandConfig;
import ClickerBot.Config.WombatConfig;
import ClickerBot.DTO.FarmData;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MultiBotMain {

    private static boolean tryClaimTreasure = false;
    private static int finishedRuns = 0;

    public static void main(String[] args) {
        System.out.println("----- Sunflower Land Clicker ------");
        System.out.println("----- Wombat Clicker ------");

        clickerBot = new ClickerBot();
        System.out.println("Program uruchomi się za 2 sekundy.");
        clickerBot.sleepM(2000);

        SunflowerLandConfig config = new SunflowerLandConfig();
        SunflowerLandBot bot = new SunflowerLandBot(config, clickerBot);

        WombatConfig wombatConfig = new WombatConfig();
        WombatBot wombatBot = new WombatBot(wombatConfig, clickerBot);

        String PurpleSmoothie = "Purple Smoothie";

        Map<String, Integer> mealsFirePitCount = new HashMap<>();
        mealsFirePitCount.put("Mashed Potato", 0);
        mealsFirePitCount.put("Pumpkin Soup", 0);
        mealsFirePitCount.put("Bumpkin Broth", 0);
        mealsFirePitCount.put("Popcorn", 0);

        Map<String, Integer> mealsFruitCount = new HashMap<>();
        mealsFruitCount.put(PurpleSmoothie, 0);

        String Corn = "Corn";
        String Eggplant = "Eggplant";
        String Cauliflower = "Cauliflower";
        String Parsnip = "Parsnip";
        String Beetroot = "Beetroot";
        String Cabbage = "Cabbage";
        String Carrot = "Carrot";
        String Pumpkin = "Pumpkin";
        String Potato = "Potato";
        String Sunflower = "Sunflower";

        String[] crops = {
            Cauliflower,
                Cabbage,
                Cabbage,
                Carrot,
                Carrot,
                Carrot,
        };

        String[] mealsFirePit = {
//                "Mashed Potato",
//                "Pumpkin Soup",
//                "Bumpkin Broth",
                "Popcorn"
        };

        String[] mealsFruit = {
                PurpleSmoothie
        };

        Map<String, Integer> mealsFirePitTarget = new HashMap<>();
        mealsFirePitTarget.put("Mashed Potato", 400);
        mealsFirePitTarget.put("Pumpkin Soup", 150);
        mealsFirePitTarget.put("Bumpkin Broth", 80);
        mealsFirePitTarget.put("Popcorn", 30);

        Map<String, Integer> mealsFruitTarget = new HashMap<>();
        mealsFruitTarget.put(PurpleSmoothie, 20);

        Map<Integer, String> cropsQueue = new HashMap<>();
        Map<Integer, String> mealsFirePitQueue = new HashMap<>();
        Map<Integer, String> mealsFruitQueue = new HashMap<>();

        for (int i = 0; i < crops.length; i++) {
            cropsQueue.put(i, crops[i]);
        }

        for (int i = 0; i < mealsFirePit.length; i++) {
            mealsFirePitQueue.put(i, mealsFirePit[i]);
        }

        for (int i = 0; i < mealsFruit.length; i++) {
            mealsFruitQueue.put(i, mealsFruit[i]);
        }

        boolean farmCrops = false;
        boolean cookFirePitMeal = false;
        boolean cookFruitMeal = false;
        boolean collectResources = true;
        boolean wombat = true;

        int currentCrop = 0;
        int currentMealFirePit = 0;
        int currentMealFruit = 0;
        int resourceWait = 13 * 60 + 15;

        Date nextCrop = getTimePlusSecond(0);
//        Date nextCrop = getTimePlusSecond( 8 * 60 * 60 + 40 * 60);
        Date nextResource = new Date();
        Date nextWombatRun = new Date();
        Date nextFirePitMeal = new Date();
        Date nextFruitMeal = new Date();

        int waitWombat = 5 * 60;
        boolean firstFirePitMeal = true;
        boolean firstFruitMeal = true;
        while (true) {
            Date currentDate = new Date();

            if (farmCrops && cropsQueue.containsKey(currentCrop) && currentDate.compareTo(nextCrop) >= 0) {
                bot.clickInTab();
                FarmData farmData = bot.checkFarm();
                bot.inventory(cropsQueue.get(currentCrop));
                nextCrop = getTimePlusSecond(config.cropsTimes.get(cropsQueue.get(currentCrop)) + 5);
                bot.crops(farmData, true);
                currentCrop++;
                System.out.println("Next crops: " + nextCrop.toString());
            }

            if (collectResources && currentDate.compareTo(nextResource) >= 0) {
                bot.clickInTab();
                bot.resources();
                nextResource = getTimePlusSecond(resourceWait);
                System.out.println("Next tree: " + nextResource.toString());
            }

            if (cookFirePitMeal &&
                    mealsFirePitQueue.containsKey(currentMealFirePit) &&
                    currentDate.compareTo(nextFirePitMeal) >= 0) {

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
                    wombatBot.claimTreasure();
                    tryClaimTreasure = true;
                }

                if (currentDate.compareTo(nextWombatRun) >= 0) {
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

    private static Date getTimePlusSecond(int seconds) {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    static ClickerBot clickerBot;
}