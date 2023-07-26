package ClickerBot.Bots;

import ClickerBot.Config.SunflowerLandConfig;
import ClickerBot.DTO.CropsInfo;
import ClickerBot.DTO.FarmData;
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

    private void moveAndClick(int x, int y) {
        Random rand = new Random();
        int randOffset = 3;
        clickerBot.move(x + rand.nextInt(randOffset), y + rand.nextInt(randOffset));
        clickerBot.click(InputEvent.BUTTON1_DOWN_MASK);
    }

    private void clickMeal(String meal) {
        Random rand = new Random();
        int randOffset = 3;

        int mealX = config.mashedPotato[0] + rand.nextInt(randOffset);
        int mealY = config.mashedPotato[1] + rand.nextInt(randOffset);
        int cookButtonX = config.mashedPotatoCookButton[0] + rand.nextInt(randOffset);
        int cookButtonY = config.mashedPotatoCookButton[1] + rand.nextInt(randOffset);

        if (meal.equals("Pumpkin Soup")) {
            mealX = config.pumpkinSoup[0] + rand.nextInt(randOffset);
            mealY = config.pumpkinSoup[1] + rand.nextInt(randOffset);
            cookButtonX = config.pumpkinSoupCookButton[0] + rand.nextInt(randOffset);
            cookButtonY = config.pumpkinSoupCookButton[1] + rand.nextInt(randOffset);
        }

        if (meal.equals("Bumpkin Broth")) {
            mealX = config.bumpkinBroth[0] + rand.nextInt(randOffset);
            mealY = config.bumpkinBroth[1] + rand.nextInt(randOffset);
            cookButtonX = config.bumpkinBrothCookButton[0] + rand.nextInt(randOffset);
            cookButtonY = config.bumpkinBrothCookButton[1] + rand.nextInt(randOffset);
        }

        clickerBot.sleepM(300);
        clickerBot.move(mealX, mealY);
        clickerBot.clickMouse();
        System.out.println("das");

        clickerBot.sleepM(300);
        clickerBot.move(cookButtonX, cookButtonY);
        clickerBot.clickMouse();
        System.out.println("das 12 31");
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

        if (item.equals("Eggplant")) {
            clickerBot.move(config.eggplant_seed[0], config.eggplant_seed[1]);
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
        FarmData farmData = new FarmData();
        farmData.crops = new TreeMap<>(tmpCrops);

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

    public void collectMeal(String meal, boolean firstMeal) {
        if (!firstMeal) {
            clickerBot.sleepM(500);
            clickFirePit();
        }
        clickerBot.sleepM(500);
        clickFirePit();
        clickerBot.sleepM(500);
        clickMeal(meal);
    }
}
