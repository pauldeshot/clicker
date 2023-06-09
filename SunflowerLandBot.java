import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.awt.event.InputEvent;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
                moveAndClick(fruit[0], fruit[1]);
                clickerBot.sleep(1);
                result.totalWaitingTime++;
            }

            for (int[] fruit : config.fruits) {
                moveAndClick(fruit[0], fruit[1]);
                clickerBot.sleep(1);
                result.totalWaitingTime++;
            }

            swapInventory();

            for (int[] fruit : config.fruits) {
                moveAndClick(fruit[0], fruit[1]);
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
                System.out.println("Meal: " + meals);
            }
        }
        clickerBot.sleep(1);
        result.totalWaitingTime++;
        return result;
    }

    public void crops() {

        Map<Integer, Map<Integer, Boolean>> rewards = new HashMap<>();
        crops(rewards, false);
    }

    public void crops(Map<Integer, Map<Integer, Boolean>> rewards, boolean doubleClick) {
        for (int i = 0; i < config.crops.length; i++) {
            int x = config.cropsPosition[i][0];
            int y = config.cropsPosition[i][1];

            if (!rewards.containsKey(x) || !rewards.get(x).get(y)) {
                moveAndClick(config.crops[i][0], config.crops[i][1]);
                clickerBot.sleepM(250);
                if (doubleClick) {
                    moveAndClick(config.crops[i][0], config.crops[i][1]);
                    clickerBot.sleepM(100);
                }
            }
        }
    }

    public void stones() {
        for (int[] stone : config.stones) {
            moveAndClick(stone[0], stone[1]);
            clickerBot.sleepM(300);
            moveAndClick(stone[0], stone[1]);
            clickerBot.sleepM(300);
            moveAndClick(stone[0], stone[1]);
            clickerBot.sleepM(300);
        }
    }

    public void iron() {
        for (int[] iron : config.iron) {
            moveAndClick(iron[0], iron[1]);
            clickerBot.sleepM(300);
            moveAndClick(iron[0], iron[1]);
            clickerBot.sleepM(300);
            moveAndClick(iron[0], iron[1]);
            clickerBot.sleepM(300);
        }
    }

    public void trees() {
        for (int[] tree : config.trees) {
            moveAndClick(tree[0], tree[1]);
            clickerBot.sleepM(300);
            moveAndClick(tree[0], tree[1]);
            clickerBot.sleepM(300);
            moveAndClick(tree[0], tree[1]);
            clickerBot.sleepM(300);
        }
    }

    public void clickInTab() {
        clickerBot.move(config.sunflowerLandTab[0], config.sunflowerLandTab[1]);
        clickerBot.clickMouse();
    }

    private void clickFirePit() {
        Random rand = new Random();
        int randOffset = 3;
        clickerBot.move(config.firePit[0] + rand.nextInt(randOffset), config.firePit[1] + rand.nextInt(randOffset));
        clickerBot.click(InputEvent.BUTTON1_DOWN_MASK);
    }

    private void moveAndClick(int x, int y) {
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

        clickerBot.sleep(rand.nextInt(2) + 1);
        clickerBot.move(config.firePitMealButton[0] + rand.nextInt(randOffset), config.firePitMealButton[1] + rand.nextInt(randOffset));
        clickerBot.click(InputEvent.BUTTON1_DOWN_MASK);
    }

    public String inventory(String item) {
        clickerBot.move(config.inventory[0], config.inventory[1]);
        clickerBot.clickMouse();
        clickerBot.sleepM(300);

        if (item.equals("Sunflower")) {
            clickerBot.move(config.sunflower_seed[0], config.sunflower_seed[1]);
            clickerBot.clickMouse();
        }

        if (item.equals("Potato")) {
            clickerBot.move(config.potato_seed[0], config.potato_seed[1]);
            clickerBot.clickMouse();
        }

        if (item.equals("Pumpkin")) {
            clickerBot.move(config.pumpkin_seed[0], config.pumpkin_seed[1]);
            clickerBot.clickMouse();
        }

        if (item.equals("Carrot")) {
            clickerBot.move(config.carrot_seed[0], config.carrot_seed[1]);
            clickerBot.clickMouse();
        }

        if (item.equals("Cabbage")) {
            clickerBot.move(config.cabbage_seed[0], config.cabbage_seed[1]);
            clickerBot.clickMouse();
        }

        if (item.equals("Parsnip")) {
            clickerBot.move(config.parsnip_seed[0], config.parsnip_seed[1]);
            clickerBot.clickMouse();
        }

        if (item.equals("Beetroot")) {
            clickerBot.move(config.beetroot_seed[0], config.beetroot_seed[1]);
            clickerBot.clickMouse();
        }

        if (item.equals("Cauliflower")) {
            clickerBot.move(config.cauliflower_seed[0], config.cauliflower_seed[1]);
            clickerBot.clickMouse();
        }

        if (item.equals("Axe")) {
            clickerBot.move(config.axe[0], config.axe[1]);
            clickerBot.clickMouse();
        }

        if (item.equals("Pickaxe")) {
            clickerBot.move(config.pickaxe[0], config.pickaxe[1]);
            clickerBot.clickMouse();
        }

        if (item.equals("Stone Pickaxe")) {
            clickerBot.move(config.stone_pickaxe[0], config.stone_pickaxe[1]);
            clickerBot.clickMouse();
        }

        clickerBot.sleepM(300);
        clickerBot.move(config.blank[0], config.blank[1]);
        clickerBot.clickMouse();
        clickerBot.sleepM(1000);

        return item;
    }

    public FarmData checkFarm() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.sunflower-land.com/visit/151364"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assert response != null;

        String strJSON = response.body();

        JSONObject obj = new JSONObject(strJSON);
        JSONObject state = obj.getJSONObject("state");
        JSONObject crops = state.getJSONObject("crops");

        Iterator keys = crops.keys();

        Map<Integer, Map<Integer, Boolean>> rewards = new HashMap<>();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            JSONObject crop = crops.getJSONObject(key);
            int x = (int) crop.get("x");
            int y = (int) crop.get("y");

            if (x == 5 && y == 14) {
                System.out.println("JEST");
            }

            boolean hasReward = false;
            if (crop.has("crop")) {
                hasReward = crop.getJSONObject("crop").has("reward");
            }

            if (rewards.containsKey(x)) {
                Map<Integer, Boolean> rewardX = rewards.get(x);
                rewardX.put(y, hasReward);
                rewards.put(x, rewardX);
            } else {
                Map<Integer, Boolean> rewardX = new HashMap<>();
                rewardX.put(y, hasReward);
                rewards.put(x, rewardX);
            }
        }

        FarmData farmData = new FarmData();
        farmData.rewards = rewards;

        JSONObject inventory = state.getJSONObject("inventory");

        Map<String, Integer> seeds = new HashMap<>();

        if (inventory.has("Sunflower Seed")) {
            seeds.put("Sunflower", inventory.getInt("Sunflower Seed"));
        } else {
            seeds.put("Sunflower", 0);
        }
        if (inventory.has("Potato Seed")) {
            seeds.put("Potato", inventory.getInt("Potato Seed"));
        } else {
            seeds.put("Potato", 0);
        }
        if (inventory.has("Pumpkin Seed")) {
            seeds.put("Pumpkin", inventory.getInt("Pumpkin Seed"));
        } else {
            seeds.put("Pumpkin", 0);
        }
        if (inventory.has("Carrot Seed")) {
            seeds.put("Carrot", inventory.getInt("Carrot Seed"));
        } else {
            seeds.put("Carrot", 0);
        }
        if (inventory.has("Cabbage Seed")) {
            seeds.put("Cabbage", inventory.getInt("Cabbage Seed"));
        } else {
            seeds.put("Cabbage", 0);
        }
        if (inventory.has("Beetroot Seed")) {
            seeds.put("Beetroot", inventory.getInt("Beetroot Seed"));
        } else {
            seeds.put("Beetroot", 0);
        }
        if (inventory.has("Cauliflower Seed")) {
            seeds.put("Cauliflower", inventory.getInt("Cauliflower Seed"));
        } else {
            seeds.put("Cauliflower", 0);
        }
        if (inventory.has("Parsnip Seed")) {
            seeds.put("Parsnip", inventory.getInt("Parsnip Seed"));
        } else {
            seeds.put("Parsnip", 0);
        }
        if (inventory.has("Eggplant Seed")) {
            seeds.put("Eggplant", inventory.getInt("Eggplant Seed"));
        } else {
            seeds.put("Eggplant", 0);
        }
        if (inventory.has("Radish Seed")) {
            seeds.put("Radish", inventory.getInt("Radish Seed"));
        } else {
            seeds.put("Radish", 0);
        }
        if (inventory.has("Wheat Seed")) {
            seeds.put("Wheat", inventory.getInt("Wheat Seed"));
        } else {
            seeds.put("Wheat", 0);
        }
        if (inventory.has("Kale Seed")) {
            seeds.put("Kale", inventory.getInt("Kale Seed"));
        } else {
            seeds.put("Kale", 0);
        }


        farmData.seeds = seeds;
        return farmData;
    }

    public void collectMeal() {
        clickerBot.sleepM(500);
        clickFirePit();
        clickerBot.sleepM(500);
        clickFirePit();
        clickerBot.sleepM(500);
        clickMeal();
    }
}
