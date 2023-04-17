import java.awt.event.InputEvent;
import java.util.Random;

public class SunflowerLandBot {
    SunflowerLandConfig config;
    ClickerBot clickerBot;
    int meals = 0;
    boolean firstMeal = true;

    public SunflowerLandBot(SunflowerLandConfig config, ClickerBot clickerBot) {
        this.config = config;
        this.clickerBot = clickerBot;
    }

    public SunflowerLandResult run(int currentWaitForFruit, int currentWaitingMeal) {
        SunflowerLandResult result = new SunflowerLandResult();
        if (config.claimFruits && currentWaitForFruit >= config.waitForNextFruit) {
            clickInTab();
            clickerBot.sleep(1);
            result.totalWaitingTime++;
            for (int[] fruit : config.fruits) {
                clickFruitTree(fruit[0], fruit[1]);
                clickerBot.sleep(1);
                result.totalWaitingTime++;
            }

            for (int[] fruit : config.fruits) {
                clickFruitTree(fruit[0], fruit[1]);
                clickerBot.sleep(1);
                result.totalWaitingTime++;
            }

            swapInventory();

            for (int[] fruit : config.fruits) {
                clickFruitTree(fruit[0], fruit[1]);
                clickerBot.sleep(1);
                result.totalWaitingTime++;
            }

            swapInventory();

            config.waitForNextFruit = config.fullWaitForNextFruit;
            result.resetWaitForFruitTime = true;
        }

        if (config.claimMeals && currentWaitingMeal >= config.waitMeal || firstMeal) {
            clickInTab();
            clickerBot.sleep(1);
            result.totalWaitingTime++;

            if (meals >= config.maxMeals) {
                result.resetWaitingMealTime = true;
            } else {
                if (!firstMeal) {
                    clickFirePit();
                    clickerBot.sleep(1);
                    result.totalWaitingTime++;
                }

                clickMeal();
                result.resetWaitingMealTime = true;
                meals++;
                firstMeal = false;
                System.out.println("Meal: "+ meals);
            }
        }
        clickerBot.sleep(1);
        result.totalWaitingTime++;
        return result;
    }

    public void crops() {
        for (int[] crop : config.crops) {
            clickFruitTree(crop[0], crop[1]);
            clickerBot.sleepM(300);
        }
    }

    private void clickInTab() {
        clickerBot.move(config.sunflowerLandTab[0], config.sunflowerLandTab[1]);
        clickerBot.clickMouse();
    }

    private void clickFirePit() {
        Random rand = new Random();
        int randOffset = 3;
        clickerBot.move(config.firePit[0] + rand.nextInt(randOffset), config.firePit[1] + rand.nextInt(randOffset));
        clickerBot.click(InputEvent.BUTTON1_DOWN_MASK);
    }

    private void clickFruitTree(int x, int y) {
        Random rand = new Random();
        int randOffset = 3;
        clickerBot.move(x + rand.nextInt(randOffset), y + rand.nextInt(randOffset));
        clickerBot.click(InputEvent.BUTTON1_DOWN_MASK);
    }
    private void swapInventory() {
        Random rand = new Random();
        int randOffset = 3;
        clickerBot.move(config.inventory[0] + rand.nextInt(randOffset), config.inventory[1] + rand.nextInt(randOffset));
        clickerBot.click(InputEvent.BUTTON1_DOWN_MASK);
    }

    private void clickMeal() {
        Random rand = new Random();
        int randOffset = 3;

        clickFirePit();

        clickerBot.sleep(rand.nextInt(2)+1);
        clickerBot.move(config.firePitMealButton[0] + rand.nextInt(randOffset), config.firePitMealButton[1] + rand.nextInt(randOffset));
        clickerBot.click(InputEvent.BUTTON1_DOWN_MASK);
    }
}
