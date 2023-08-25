package ClickerBot;

import ClickerBot.Bots.ClickerBot;
import ClickerBot.Bots.SunflowerLandBot;
import ClickerBot.Config.SunflowerLandConfig;
import ClickerBot.DTO.FarmData;
import ClickerBot.DTO.PotionHouseAttempt;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PotionHouseMain {
    static ClickerBot clickerBot;

    static String[] potions = {
            "Bloom Boost",
            "Dream Drip",
            "Earth Essence",
            "Flower Power",
            "Silver Syrup",
            "Happy Hooch",
            "Organic Oasis"
    };

    public static void main(String[] args) {
        System.out.println("----- Coordinate printer ------");
        clickerBot = new ClickerBot();

        System.out.println("Program uruchomi się za 2 sekundy.");
        clickerBot.sleep(2);


        while (true) {
            System.out.println("New game");
            nextGame(clickerBot);
        }
    }

    private static void nextGame(ClickerBot clickerBot) {
        SunflowerLandConfig config = new SunflowerLandConfig();
        SunflowerLandBot bot = new SunflowerLandBot(config, clickerBot);

        clickerBot.moveAndClick(config.startGameButton[0], config.startGameButton[1]);

        clickerBot.moveAndClick(config.potion1[0], config.potion1[1]);
        clickerBot.moveAndClick(config.addToMixButton[0], config.addToMixButton[1]);

        clickerBot.moveAndClick(config.potion3[0], config.potion3[1]);
        clickerBot.moveAndClick(config.addToMixButton[0], config.addToMixButton[1]);

        clickerBot.moveAndClick(config.potion5[0], config.potion5[1]);
        clickerBot.moveAndClick(config.addToMixButton[0], config.addToMixButton[1]);

        clickerBot.moveAndClick(config.potion7[0], config.potion7[1]);
        clickerBot.moveAndClick(config.addToMixButton[0], config.addToMixButton[1]);

        clickerBot.moveAndClick(config.mixPotionButton[0], config.mixPotionButton[1]);

        // waiting for save current row
        clickerBot.sleep(15);


        String[] correctRow = {
                null,
                null,
                null,
                null,
        };

        String[] almost = {
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
        };

        String[] incorrect = {
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
        };

        FarmData farmData = bot.checkFarm();
        String bomb = searchBombInAttempt(farmData.potionHouse.attempt1);
        if (farmData.potionHouse.attempt1.column1.status.equals("correct")) {
            correctRow[0] = farmData.potionHouse.attempt1.column1.potion;
        }
        if (farmData.potionHouse.attempt1.column2.status.equals("correct")) {
            correctRow[1] = farmData.potionHouse.attempt1.column2.potion;
        }
        if (farmData.potionHouse.attempt1.column3.status.equals("correct")) {
            correctRow[2] = farmData.potionHouse.attempt1.column3.potion;
        }
        if (farmData.potionHouse.attempt1.column4.status.equals("correct")) {
            correctRow[3] = farmData.potionHouse.attempt1.column4.potion;
        }

        // looking for almost
        if (farmData.potionHouse.attempt1.column1.status.equals("almost")) {
            almost[0] = farmData.potionHouse.attempt1.column1.potion;
        }
        if (farmData.potionHouse.attempt1.column2.status.equals("almost")) {
            almost[1] = farmData.potionHouse.attempt1.column2.potion;
        }
        if (farmData.potionHouse.attempt1.column3.status.equals("almost")) {
            almost[2] = farmData.potionHouse.attempt1.column3.potion;
        }
        if (farmData.potionHouse.attempt1.column4.status.equals("almost")) {
            almost[3] = farmData.potionHouse.attempt1.column4.potion;
        }
        

        // looking for incorrect
        if (farmData.potionHouse.attempt1.column1.status.equals("incorrect")) {
            incorrect[0] = farmData.potionHouse.attempt1.column1.potion;
        }
        if (farmData.potionHouse.attempt1.column2.status.equals("incorrect")) {
            incorrect[1] = farmData.potionHouse.attempt1.column2.potion;
        }
        if (farmData.potionHouse.attempt1.column3.status.equals("incorrect")) {
            incorrect[2] = farmData.potionHouse.attempt1.column3.potion;
        }
        if (farmData.potionHouse.attempt1.column4.status.equals("incorrect")) {
            incorrect[3] = farmData.potionHouse.attempt1.column4.potion;
        }

        // attempt 2
        //col 1
        clickInPotion("Dream Drip", config);
        //col 2
        clickInPotion("Flower Power", config);
        //col 3
        clickInPotion("Happy Hooch", config);
        //col 2
        clickRandom(config, bomb, incorrect);

        clickerBot.moveAndClick(config.mixPotionButton[0], config.mixPotionButton[1]);

        // waiting for save current row
        clickerBot.sleep(15);
        farmData = bot.checkFarm();
        if (bomb == null) {
            bomb = searchBombInAttempt(farmData.potionHouse.attempt2);
        }

        if (correctRow[0] == null && farmData.potionHouse.attempt2.column1.status.equals("correct")) {
            correctRow[0] = farmData.potionHouse.attempt2.column1.potion;
        }
        if (correctRow[1] == null && farmData.potionHouse.attempt2.column2.status.equals("correct")) {
            correctRow[1] = farmData.potionHouse.attempt2.column2.potion;
        }
        if (correctRow[2] == null && farmData.potionHouse.attempt2.column3.status.equals("correct")) {
            correctRow[2] = farmData.potionHouse.attempt2.column3.potion;
        }
        if (correctRow[3] == null && farmData.potionHouse.attempt2.column4.status.equals("correct")) {
            correctRow[3] = farmData.potionHouse.attempt2.column4.potion;
        }

        // looking for almost
        if (farmData.potionHouse.attempt2.column1.status.equals("almost")) {
            almost[4] = farmData.potionHouse.attempt2.column1.potion;
        }
        if (farmData.potionHouse.attempt2.column2.status.equals("almost")) {
            almost[5] = farmData.potionHouse.attempt2.column2.potion;
        }
        if (farmData.potionHouse.attempt2.column3.status.equals("almost")) {
            almost[6] = farmData.potionHouse.attempt2.column3.potion;
        }
        if (farmData.potionHouse.attempt2.column4.status.equals("almost")) {
            almost[7] = farmData.potionHouse.attempt2.column4.potion;
        }


        // looking for incorrect
        if (farmData.potionHouse.attempt2.column1.status.equals("incorrect")) {
            incorrect[4] = farmData.potionHouse.attempt2.column1.potion;
        }
        if (farmData.potionHouse.attempt2.column2.status.equals("incorrect")) {
            incorrect[5] = farmData.potionHouse.attempt2.column2.potion;
        }
        if (farmData.potionHouse.attempt2.column3.status.equals("incorrect")) {
            incorrect[6] = farmData.potionHouse.attempt2.column3.potion;
        }
        if (farmData.potionHouse.attempt2.column4.status.equals("incorrect")) {
            incorrect[7] = farmData.potionHouse.attempt2.column4.potion;
        }
        

        // final try
        //col 1
        if (correctRow[0] != null) {
            clickInPotion(correctRow[0], config);
        } else {
            clickRandom(config, bomb, incorrect);
        }
        //col 2
        if (correctRow[1] != null) {
            clickInPotion(correctRow[1], config);
        } else {
            clickRandom(config, bomb, incorrect);
        }
        //col 3
        if (correctRow[2] != null) {
            clickInPotion(correctRow[2], config);
        } else {
            clickRandom(config, bomb, incorrect);
        }
        //col 2
        if (correctRow[3] != null) {
            clickInPotion(correctRow[3], config);
        } else {
            clickRandom(config, bomb, incorrect);
        }

        clickerBot.moveAndClick(config.mixPotionButton[0], config.mixPotionButton[1]);
        clickerBot.sleep(15);
    }

    private static void clickRandom(SunflowerLandConfig config, String bomb, String[] incorrect) {
        Random random = new Random();
        int potion = random.nextInt(7);

        if (bomb == null) {
            clickInPotion(potions[potion], config);
        } else {
            while (contains(incorrect, potions[potion]) || bomb.equals(potions[potion])) {
                potion = random.nextInt(7);
            }
            clickInPotion(potions[potion], config);
        }
    }

    public static boolean contains(String[] array, String value) {
        for (String s : array) {
            if (s != null && s.equals(value)) {
                return true;
            }
        }
        return false;
    }

    private static void clickInPotion(String potion, SunflowerLandConfig config) {
        if (potion.equals("Bloom Boost")) {
            clickerBot.moveAndClick(config.potion1[0], config.potion1[1]);
            clickerBot.moveAndClick(config.addToMixButton[0], config.addToMixButton[1]);
        }
        if (potion.equals("Dream Drip")) {
            clickerBot.moveAndClick(config.potion2[0], config.potion2[1]);
            clickerBot.moveAndClick(config.addToMixButton[0], config.addToMixButton[1]);
        }
        if (potion.equals("Earth Essence")) {
            clickerBot.moveAndClick(config.potion3[0], config.potion3[1]);
            clickerBot.moveAndClick(config.addToMixButton[0], config.addToMixButton[1]);
        }
        if (potion.equals("Flower Power")) {
            clickerBot.moveAndClick(config.potion4[0], config.potion4[1]);
            clickerBot.moveAndClick(config.addToMixButton[0], config.addToMixButton[1]);
        }
        if (potion.equals("Silver Syrup")) {
            clickerBot.moveAndClick(config.potion5[0], config.potion5[1]);
            clickerBot.moveAndClick(config.addToMixButton[0], config.addToMixButton[1]);
        }
        if (potion.equals("Happy Hooch")) {
            clickerBot.moveAndClick(config.potion6[0], config.potion6[1]);
            clickerBot.moveAndClick(config.addToMixButton[0], config.addToMixButton[1]);
        }
        if (potion.equals("Organic Oasis")) {
            clickerBot.moveAndClick(config.potion7[0], config.potion7[1]);
            clickerBot.moveAndClick(config.addToMixButton[0], config.addToMixButton[1]);
        }
    }

    private static String searchBombInAttempt(PotionHouseAttempt attempt) {
        String bomb = null;
        if (attempt.column1.status.equals("bomb")) {
            bomb = attempt.column1.potion;
        }
        if (attempt.column2.status.equals("bomb")) {
            bomb = attempt.column2.potion;
        }
        if (attempt.column3.status.equals("bomb")) {
            bomb = attempt.column3.potion;
        }
        if (attempt.column4.status.equals("bomb")) {
            bomb = attempt.column4.potion;
        }

        return bomb;
    }
}
