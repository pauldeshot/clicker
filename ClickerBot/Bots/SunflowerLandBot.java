package ClickerBot.Bots;

import ClickerBot.Config.SunflowerLandConfig;
import ClickerBot.DTO.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.event.InputEvent;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class SunflowerLandBot {
    SunflowerLandConfig config;
    ClickerBot clickerBot;

    public SunflowerLandBot(SunflowerLandConfig config, ClickerBot clickerBot) {
        this.config = config;
        this.clickerBot = clickerBot;
    }

    public void crops(FarmData farmData, boolean doubleClick) {
        for (String key : farmData.crops.keySet()) {
            CropsInfo cropsInfo = farmData.crops.get(key);

            int x = config.cropsColumnCoordinate.get(cropsInfo.x);
            int y = config.cropsRowCoordinate.get(cropsInfo.y);

            if (!cropsInfo.hasReward) {
                moveAndClick(x, y);
                clickerBot.sleepM(250);
                if (doubleClick) {
                    moveAndClick(x, y);
                    clickerBot.sleepM(100);
                }
            }
        }
    }

    public void save() {
        clickerBot.sleepM(300);
        moveAndClick(config.saveButton[0], config.saveButton[1]);
        clickerBot.sleepM(300);
    }

    public void resources() {
        for (int[] resource : config.resources) {
            moveAndClick(resource[0], resource[1]);
            clickerBot.sleepM(300);
            moveAndClick(resource[0], resource[1]);
            clickerBot.sleepM(300);
            moveAndClick(resource[0], resource[1]);
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

    private void clickSmoothieShack() {
        Random rand = new Random();
        int randOffset = 3;
        clickerBot.move(config.smoothieShack[0] + rand.nextInt(randOffset), config.smoothieShack[1] + rand.nextInt(randOffset));
        clickerBot.click(InputEvent.BUTTON1_DOWN_MASK);
    }

    private void moveAndClick(int x, int y) {
        Random rand = new Random();
        int randOffset = 3;
        clickerBot.move(x + rand.nextInt(randOffset), y + rand.nextInt(randOffset));
        clickerBot.click(InputEvent.BUTTON1_DOWN_MASK);
    }

    private void clickMeal(String meal) {
        Random rand = new Random();
        int randOffset = 3;

        int cookButtonX = config.mashedPotatoCookButton[0] + rand.nextInt(randOffset);
        int cookButtonY = config.mashedPotatoCookButton[1] + rand.nextInt(randOffset);

        if (meal.equals("Pumpkin Soup")) {
            cookButtonX = config.pumpkinSoupCookButton[0] + rand.nextInt(randOffset);
            cookButtonY = config.pumpkinSoupCookButton[1] + rand.nextInt(randOffset);
        }

        if (meal.equals("Bumpkin Broth")) {
            cookButtonX = config.bumpkinBrothCookButton[0] + rand.nextInt(randOffset);
            cookButtonY = config.bumpkinBrothCookButton[1] + rand.nextInt(randOffset);
        }

        if (meal.equals("Popcorn")) {
            cookButtonX = config.popcornButton[0] + rand.nextInt(randOffset);
            cookButtonY = config.popcornButton[1] + rand.nextInt(randOffset);
        }

        if (meal.equals("Purple Smoothie")) {
            cookButtonX = config.purpleSmoothieButton[0] + rand.nextInt(randOffset);
            cookButtonY = config.purpleSmoothieButton[1] + rand.nextInt(randOffset);
        }

        clickerBot.sleepM(300);
        clickerBot.move(cookButtonX, cookButtonY);
        clickerBot.clickMouse();

        clickerBot.sleepM(300);
        clickerBot.move(config.blank[0], config.blank[1]);
        clickerBot.clickMouse();
    }

    public String inventory(String item) {
        clickerBot.move(config.inventory[0], config.inventory[1]);
        clickerBot.clickMouse();
        clickerBot.sleepM(1000);

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

        if (item.equals("Eggplant")) {
            clickerBot.move(config.eggplant_seed[0], config.eggplant_seed[1]);
            clickerBot.clickMouse();
        }

        if (item.equals("Corn")) {
            clickerBot.move(config.corn_seed[0], config.corn_seed[1]);
            clickerBot.clickMouse();
        }

        clickerBot.sleepM(300);
        clickerBot.move(config.blank[0], config.blank[1]);
        clickerBot.clickMouse();
        clickerBot.sleepM(1000);

        return item;
    }

    private PotionHouse convertJsonToPotionHouse(JSONObject jsonObject) {
        JSONObject game = jsonObject.getJSONObject("game");
        JSONArray attempts = game.getJSONArray("attempts");

        PotionHouse potionHouse = new PotionHouse();
        if (!attempts.isNull(0)) {
            potionHouse.attempt1 = convertJsonArrayToAttempt(attempts.getJSONArray(0));
        }
        if (!attempts.isNull(1)) {
            potionHouse.attempt2 = convertJsonArrayToAttempt(attempts.getJSONArray(1));
        }

        return potionHouse;
    }

    private PotionHouseAttempt convertJsonArrayToAttempt(JSONArray array) {
        PotionHouseAttempt attempt = new PotionHouseAttempt();

        attempt.column1 = convertJsonObjectToPotionColumn(array.getJSONObject(0));
        attempt.column2 = convertJsonObjectToPotionColumn(array.getJSONObject(1));
        attempt.column3 = convertJsonObjectToPotionColumn(array.getJSONObject(2));
        attempt.column4 = convertJsonObjectToPotionColumn(array.getJSONObject(3));

        return attempt;
    }

    private PotionColumn convertJsonObjectToPotionColumn(JSONObject obj) {
        PotionColumn column = new PotionColumn();

        column.potion = obj.getString("potion");
        column.status = obj.getString("status");

        return column;
    }

    public FarmData checkFarm() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.sunflower-land.com/visit/" + config.farmId))
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
        Map<String, CropsInfo> tmpCrops = new HashMap<>();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            JSONObject crop = crops.getJSONObject(key);
            int x = (int) crop.get("x");
            int y = (int) crop.get("y");

            boolean hasReward = false;
            if (crop.has("crop")) {
                hasReward = crop.getJSONObject("crop").has("reward");
            }

            String id = "id:"+x+y;
            CropsInfo cropsInfo = new CropsInfo();
            cropsInfo.x = x;
            cropsInfo.y = y;
            cropsInfo.hasReward = hasReward;

            tmpCrops.put(id, cropsInfo);
        }

        Map<String, Mineral> tmpMinerals = new HashMap<>();

        JSONObject stones = state.getJSONObject("stones");
        keys = stones.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            JSONObject stone = stones.getJSONObject(key);
            int x = (int) stone.get("x");
            int y = (int) stone.get("y");

            String id = "id:"+x+y;
            Mineral mineral = new Mineral();
            mineral.x = x;
            mineral.y = y;

            tmpMinerals.put(id, mineral);
        }

        JSONObject irons = state.getJSONObject("iron");
        keys = irons.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            JSONObject iron = irons.getJSONObject(key);
            int x = (int) iron.get("x");
            int y = (int) iron.get("y");


            String id = "id:"+x+y;
            Mineral mineral = new Mineral();
            mineral.x = x;
            mineral.y = y;

            tmpMinerals.put(id, mineral);
        }

        JSONObject golds = state.getJSONObject("gold");
        keys = golds.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            JSONObject gold = golds.getJSONObject(key);
            int x = (int) gold.get("x");
            int y = (int) gold.get("y");

            String id = "id:"+x+y;
            Mineral mineral = new Mineral();
            mineral.x = x;
            mineral.y = y;

            tmpMinerals.put(id, mineral);
        }

        FarmData farmData = new FarmData();
        farmData.crops = new TreeMap<>(tmpCrops);
        farmData.minerals = new TreeMap<>(tmpMinerals);

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


        //potion house data
        farmData.potionHouse = convertJsonToPotionHouse(state.getJSONObject("potionHouse"));

        farmData.seeds = seeds;
        return farmData;
    }

    public void collectMealFirePit(String meal, boolean firstMeal) {
        if (!firstMeal) {
            clickerBot.sleepM(500);
            clickFirePit();
        }
        clickerBot.sleepM(500);
        clickFirePit();
        clickerBot.sleepM(500);
        clickMeal(meal);
    }

    public void collectSmootieShack(String meal, boolean firstMeal) {
        if (!firstMeal) {
            clickerBot.sleepM(500);
            clickSmoothieShack();
        }
        clickerBot.sleepM(500);
        clickSmoothieShack();
        clickerBot.sleepM(500);
        clickMeal(meal);
    }

    public void minerals(Map<String, Mineral> minerals) {
        for (String key : minerals.keySet()) {
            Mineral mineral = minerals.get(key);

            int x = config.cropsColumnCoordinate.get(mineral.x);
            int y = config.cropsRowCoordinate.get(mineral.y);

            moveAndClick(x, y);
            clickerBot.sleepM(300);
            moveAndClick(x, y);
            clickerBot.sleepM(300);
            moveAndClick(x, y);
            clickerBot.sleepM(300);
        }
    }
}
