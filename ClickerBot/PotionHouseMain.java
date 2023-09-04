package ClickerBot;

import ClickerBot.Bots.ClickerBot;
import ClickerBot.Bots.SunflowerLandBot;
import ClickerBot.Config.SunflowerLandConfig;
import ClickerBot.DTO.FarmData;
import ClickerBot.DTO.PotionColumn;
import ClickerBot.DTO.PotionHouse;
import ClickerBot.DTO.PotionHouseAttempt;

import java.util.*;

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


    public static List<List<String>> generateCombinations(List<String> elements, int combinationLength) {
        List<List<String>> result = new ArrayList<>();
        generateCombinationsRecursively(elements, combinationLength, new ArrayList<>(), result);
        return result;
    }

    private static void generateCombinationsRecursively(List<String> elements, int combinationLength, List<String> currentCombination, List<List<String>> result) {
        if (combinationLength == 0) {
            result.add(new ArrayList<>(currentCombination));
            return;
        }

        for (String element : elements) {
            currentCombination.add(element);
            generateCombinationsRecursively(elements, combinationLength - 1, currentCombination, result);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }

    public static void removeCombinationsWithColor(List<List<String>> combinations, String colorToRemove) {
        Iterator<List<String>> iterator = combinations.iterator();

        while (iterator.hasNext()) {
            List<String> combination = iterator.next();

            if (combination.contains(colorToRemove)) {
                iterator.remove();
            }
        }

//        System.out.println("Left combinations (bomb / incorrect): " + combinations.toArray().length);
    }

    public static List<List<String>> filterCombinationsWithColorAtPosition(List<List<String>> combinations, String targetColor, int targetPosition) {
        List<List<String>> filteredCombinations = new ArrayList<>();
        targetPosition -= 1;
        for (List<String> combination : combinations) {
            if (combination.size() > targetPosition && combination.get(targetPosition).equals(targetColor)) {
                filteredCombinations.add(combination);
            }
        }
//        System.out.println("Left combinations (correct): " + filteredCombinations.toArray().length);
        return filteredCombinations;
    }

    public static void removeCombinationsWithColorAtPosition(List<List<String>> combinations, String colorToRemove, int targetPosition) {
        Iterator<List<String>> iterator = combinations.iterator();
        targetPosition -= 1;
        while (iterator.hasNext()) {
            List<String> combination = iterator.next();

            if (combination.size() > targetPosition && combination.get(targetPosition).equals(colorToRemove)) {
                iterator.remove();
            }
        }
//        System.out.println("Left combinations (incorect): " + combinations.toArray().length + " - " + colorToRemove);
    }

    public static List<String> pickRandomCombination(List<List<String>> combinations) {
        if (combinations.isEmpty()) {
            throw new IllegalArgumentException("The list of combinations is empty.");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(combinations.size());

        return combinations.get(randomIndex);
    }

    public static List<List<String>> filterCombinationsWithColor(List<List<String>> combinations, String colorToFind) {
        List<List<String>> filteredCombinations = new ArrayList<>();

        for (List<String> combination : combinations) {
            if (combination.contains(colorToFind)) {
                filteredCombinations.add(combination);
            }
        }

        return filteredCombinations;
    }

    public static void main(String[] args) {
        System.out.println("----- Coordinate printer ------");
        clickerBot = new ClickerBot();

        System.out.println("Program uruchomi się za 2 sekundy.");
        clickerBot.sleep(2);

        List<String> elixirColors = new ArrayList<>();
        elixirColors.add("Bloom Boost");
        elixirColors.add("Dream Drip");
        elixirColors.add("Earth Essence");
        elixirColors.add("Flower Power");
        elixirColors.add("Silver Syrup");
        elixirColors.add("Happy Hooch");
        elixirColors.add("Organic Oasis");

        while (true) {
            System.out.println("New game");
            List<List<String>> combinations = generateCombinations(elixirColors, 4);
            nextGame(clickerBot, combinations);
        }
    }

    private static void nextGame(ClickerBot clickerBot, List<List<String>> combinations) {
        SunflowerLandConfig config = new SunflowerLandConfig();
        SunflowerLandBot bot = new SunflowerLandBot(config, clickerBot);

        clickerBot.moveAndClick(config.startGameButton[0], config.startGameButton[1]);

        clickInPotion("Bloom Boost", config);
        clickInPotion("Earth Essence", config);
        clickInPotion("Silver Syrup", config);
        clickInPotion("Organic Oasis", config);

        clickerBot.moveAndClick(config.mixPotionButton[0], config.mixPotionButton[1]);
        clickerBot.sleep(3);

        // attempt 2
        clickInPotion("Dream Drip", config);
        clickInPotion("Flower Power", config);
        clickInPotion("Happy Hooch", config);
        clickRandom(config);
        clickerBot.moveAndClick(config.mixPotionButton[0], config.mixPotionButton[1]);

        // waiting for save current row
        clickerBot.sleep(12);
        FarmData farmData = bot.checkFarm();
        PotionHouse potionHouse = farmData.potionHouse;

//        PotionHouse potionHouse = examplePotionHouse();
        String bomb = searchBombInAttempt(potionHouse.attempt1);

        // attempt 1
        if (bomb != null) {
            removeCombinationsWithColor(combinations, bomb);
        }

        if (potionHouse.attempt1.column1.status.equals("correct")) {
            combinations = filterCombinationsWithColorAtPosition(combinations, potionHouse.attempt1.column1.potion, 1);
        }
        if (potionHouse.attempt1.column2.status.equals("correct")) {
            combinations = filterCombinationsWithColorAtPosition(combinations, potionHouse.attempt1.column2.potion, 2);
        }
        if (potionHouse.attempt1.column3.status.equals("correct")) {
            combinations = filterCombinationsWithColorAtPosition(combinations, potionHouse.attempt1.column3.potion, 3);
        }
        if (potionHouse.attempt1.column4.status.equals("correct")) {
            combinations = filterCombinationsWithColorAtPosition(combinations, potionHouse.attempt1.column4.potion, 4);
        }

        // looking for almost
        if (potionHouse.attempt1.column1.status.equals("almost")) {
            combinations = filterCombinationsWithColor(combinations, potionHouse.attempt1.column1.potion);
            removeCombinationsWithColorAtPosition(combinations, potionHouse.attempt1.column1.potion, 1);
        }
        if (potionHouse.attempt1.column2.status.equals("almost")) {
            combinations = filterCombinationsWithColor(combinations, potionHouse.attempt1.column2.potion);
            removeCombinationsWithColorAtPosition(combinations, potionHouse.attempt1.column2.potion, 2);
        }
        if (potionHouse.attempt1.column3.status.equals("almost")) {
            combinations = filterCombinationsWithColor(combinations, potionHouse.attempt1.column3.potion);
            removeCombinationsWithColorAtPosition(combinations, potionHouse.attempt1.column3.potion, 3);
        }
        if (potionHouse.attempt1.column4.status.equals("almost")) {
            combinations = filterCombinationsWithColor(combinations, potionHouse.attempt1.column4.potion);
            removeCombinationsWithColorAtPosition(combinations, potionHouse.attempt1.column4.potion, 4);
        }

        // looking for incorrect
        if (potionHouse.attempt1.column1.status.equals("incorrect")) {
            removeCombinationsWithColor(combinations, potionHouse.attempt1.column1.potion);
        }
        if (potionHouse.attempt1.column2.status.equals("incorrect")) {
            removeCombinationsWithColor(combinations, potionHouse.attempt1.column2.potion);
        }
        if (potionHouse.attempt1.column3.status.equals("incorrect")) {
            removeCombinationsWithColor(combinations, potionHouse.attempt1.column3.potion);
        }
        if (potionHouse.attempt1.column4.status.equals("incorrect")) {
            removeCombinationsWithColor(combinations, potionHouse.attempt1.column4.potion);
        }

        // attempt 2
        if (bomb == null) {
            bomb = searchBombInAttempt(potionHouse.attempt2);
            if (bomb != null) {
                removeCombinationsWithColor(combinations, bomb);
            }

        }

        if (potionHouse.attempt2.column1.status.equals("correct")) {
            combinations = filterCombinationsWithColorAtPosition(combinations, potionHouse.attempt2.column1.potion, 1);
        }
        if (potionHouse.attempt2.column2.status.equals("correct")) {
            combinations = filterCombinationsWithColorAtPosition(combinations, potionHouse.attempt2.column2.potion, 2);
        }
        if (potionHouse.attempt2.column3.status.equals("correct")) {
            combinations = filterCombinationsWithColorAtPosition(combinations, potionHouse.attempt2.column3.potion, 3);
        }
        if (potionHouse.attempt2.column4.status.equals("correct")) {
            combinations = filterCombinationsWithColorAtPosition(combinations, potionHouse.attempt2.column4.potion, 4);
        }

        // looking for almost
        if (potionHouse.attempt2.column1.status.equals("almost")) {
            combinations = filterCombinationsWithColor(combinations, potionHouse.attempt2.column1.potion);
            removeCombinationsWithColorAtPosition(combinations, potionHouse.attempt2.column1.potion, 1);
        }
        if (potionHouse.attempt2.column2.status.equals("almost")) {
            combinations = filterCombinationsWithColor(combinations, potionHouse.attempt2.column2.potion);
            removeCombinationsWithColorAtPosition(combinations, potionHouse.attempt2.column2.potion, 2);
        }
        if (potionHouse.attempt2.column3.status.equals("almost")) {
            combinations = filterCombinationsWithColor(combinations, potionHouse.attempt2.column3.potion);
            removeCombinationsWithColorAtPosition(combinations, potionHouse.attempt2.column3.potion, 3);
        }
        if (potionHouse.attempt2.column4.status.equals("almost")) {
            combinations = filterCombinationsWithColor(combinations, potionHouse.attempt2.column4.potion);
            removeCombinationsWithColorAtPosition(combinations, potionHouse.attempt2.column4.potion, 4);
        }

        // looking for incorrect
        if (potionHouse.attempt2.column1.status.equals("incorrect")) {
            removeCombinationsWithColor(combinations, potionHouse.attempt2.column1.potion);
        }
        if (potionHouse.attempt2.column2.status.equals("incorrect")) {
            removeCombinationsWithColor(combinations, potionHouse.attempt2.column2.potion);
        }
        if (potionHouse.attempt2.column3.status.equals("incorrect")) {
            removeCombinationsWithColor(combinations, potionHouse.attempt2.column3.potion);
        }
        if (potionHouse.attempt2.column4.status.equals("incorrect")) {
            removeCombinationsWithColor(combinations, potionHouse.attempt2.column4.potion);
        }

        // final try
        System.out.println("Left combinations: " + combinations.toArray().length);
        List<String> finalCombination = pickRandomCombination(combinations);

        for (String element : finalCombination) {
            clickInPotion(element, config);
        }

        clickerBot.moveAndClick(config.mixPotionButton[0], config.mixPotionButton[1]);
        clickerBot.sleep(12);
    }

    private static void clickRandom(
            SunflowerLandConfig config
    ) {
        Random random = new Random();
        int potion = random.nextInt(7);
        clickInPotion(potions[potion], config);
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

    private static PotionHouse examplePotionHouse() {
        PotionHouse potionHouse = new PotionHouse();
        potionHouse.attempt1 = new PotionHouseAttempt();
        potionHouse.attempt1.column1 = new PotionColumn();
        potionHouse.attempt1.column1.potion = "Bloom Boost";
        potionHouse.attempt1.column1.status = "incorrect";
        potionHouse.attempt1.column2 = new PotionColumn();
        potionHouse.attempt1.column2.potion = "Earth Essence";
        potionHouse.attempt1.column2.status = "bomb";
        potionHouse.attempt1.column3 = new PotionColumn();
        potionHouse.attempt1.column3.potion = "Silver Syrup";
        potionHouse.attempt1.column3.status = "correct";
        potionHouse.attempt1.column4 = new PotionColumn();
        potionHouse.attempt1.column4.potion = "Organic Oasis";
        potionHouse.attempt1.column4.status = "incorrect";


        potionHouse.attempt2 = new PotionHouseAttempt();
        potionHouse.attempt2.column1 = new PotionColumn();
        potionHouse.attempt2.column1.potion = "Dream Drip";
        potionHouse.attempt2.column1.status = "incorrect";
        potionHouse.attempt2.column2 = new PotionColumn();
        potionHouse.attempt2.column2.potion = "Flower Power";
        potionHouse.attempt2.column2.status = "correct";
        potionHouse.attempt2.column3 = new PotionColumn();
        potionHouse.attempt2.column3.potion = "Happy Hooch";
        potionHouse.attempt2.column3.status = "almost";
        potionHouse.attempt2.column4 = new PotionColumn();
        potionHouse.attempt2.column4.potion = "Flower Power";
        potionHouse.attempt2.column4.status = "almost";

        return potionHouse;
    }
}
