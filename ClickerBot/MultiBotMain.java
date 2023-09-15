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

        Map<String, Integer> mealsCount = new HashMap<>();
        mealsCount.put("Mashed Potato", 0);
        mealsCount.put("Pumpkin Soup", 0);
        mealsCount.put("Bumpkin Broth", 0);
        mealsCount.put("Popcorn", 0);

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
                Potato,
                Potato,
                Sunflower,
                Sunflower,
                Sunflower,
                Sunflower,
                Sunflower,
                Sunflower,
                Sunflower,
                Sunflower,
                Sunflower,
                Sunflower,
                Sunflower
        };

        String[] meals = {
//                "Mashed Potato",
//                "Pumpkin Soup",
                "Bumpkin Broth",
//                "Popcorn"
        };

        Map<String, Integer> mealsTarget = new HashMap<>();
        mealsTarget.put("Mashed Potato", 100);
        mealsTarget.put("Pumpkin Soup", 200);
        mealsTarget.put("Bumpkin Broth", 90);
        mealsTarget.put("Popcorn", 60);

        Map<Integer, String> cropsQueue = new HashMap<>();
        Map<Integer, String> mealsQueue = new HashMap<>();

        for (int i = 0; i < crops.length; i++) {
            cropsQueue.put(i, crops[i]);
        }

        for (int i = 0; i < meals.length; i++) {
            mealsQueue.put(i, meals[i]);
        }

        boolean farmCrops = true;
        boolean cookMeal = false;
        boolean collectResources = false;
        boolean wombat = true;

        int currentCrop = 0;
        int currentMeal = 0;

        int resourceWait = 10 * 60 + 15;

        Date nextCrop = getTimePlusSecond(0);
//        Date nextCrop = getTimePlusSecond(3 * 60 * 60 + 3 * 60);
        Date nextResource = new Date();

        Date nextWombatRun = new Date();
        int waitWombat = 5 * 60;

        Date nextMeal = new Date();
        boolean firstMeal = true;

        while (true) {
            Date currentDate = new Date();

            if (farmCrops && cropsQueue.containsKey(currentCrop) && currentDate.compareTo(nextCrop) >= 0) {
                bot.clickInTab();
                FarmData farmData = bot.checkFarm();
                bot.inventory(cropsQueue.get(currentCrop));
                nextCrop = getTimePlusSecond(config.cropsTimes.get(cropsQueue.get(currentCrop)) + 5);
                bot.crops(farmData, true);
                currentCrop++;
//                if (farmData.seeds.get(cropsQueue.get(currentCrop)) - 43 <= 0) {
//                    System.out.println("Chang seed: " + cropsQueue.get(currentCrop));
//                    nextCrop = new Date();
//                }
                System.out.println("Next crops: " + nextCrop.toString());
            }

            if (collectResources && currentDate.compareTo(nextResource) >= 0) {
                bot.clickInTab();
                bot.resources();
                nextResource = getTimePlusSecond(resourceWait);
                System.out.println("Next tree: " + nextResource.toString());
            }

            if (cookMeal &&
                    mealsQueue.containsKey(currentMeal) &&
                    currentDate.compareTo(nextMeal) >= 0) {

                if (mealsQueue.containsKey(currentMeal) &&
                        mealsCount.get(mealsQueue.get(currentMeal)) >= mealsTarget.get(mealsQueue.get(currentMeal))) {
                    currentMeal++;
                    nextMeal = new Date();
                } else {
                    bot.clickInTab();
                    bot.collectMeal(mealsQueue.get(currentMeal), firstMeal);
                    mealsCount.put(mealsQueue.get(currentMeal), mealsCount.get(mealsQueue.get(currentMeal)) + 1);
                    firstMeal = false;
                    nextMeal = getTimePlusSecond(config.mealsTimes.get(mealsQueue.get(currentMeal)) + 2);
                    System.out.println("Next meal: " + nextMeal.toString());
                }
            }

            if (wombat) {
                if (currentDate.compareTo(nextWombatRun) >= 0) {
                    wombatBot.run(60 * 60 * 24);
                    nextWombatRun = getTimePlusSecond(waitWombat);
                    System.out.println("Next wombat: " + nextWombatRun.toString());
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