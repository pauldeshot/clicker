package ClickerBot.Config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SunflowerLandConfig {

    public SunflowerLandConfig() {
        setTimes();
        String path = "config.json";
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = new JSONObject(content);

        farmId = (int) jsonObject.get("farmId");

        sunflowerLandTab = getCoordinate(jsonObject, "sunflowerLandTab");
        blank = getCoordinate(jsonObject, "blank");
        inventory = getCoordinate(jsonObject, "inventory");

        JSONObject cropsCoordinates = jsonObject.getJSONObject("cropsCoordinates");
        JSONObject yCoordinates = cropsCoordinates.getJSONObject("y");
        JSONObject xCoordinates = cropsCoordinates.getJSONObject("x");

        Iterator keys = yCoordinates.keys();
        cropsRowCoordinate = new HashMap<>();
        while (keys.hasNext()) {
            String keyStr = (String) keys.next();
            Integer keyInt = Integer.parseInt(keyStr);
            Integer value = yCoordinates.getInt(keyStr);
            cropsRowCoordinate.put(keyInt, value);
        }

        keys = xCoordinates.keys();
        cropsColumnCoordinate = new HashMap<>();
        while (keys.hasNext()) {
            String keyStr = (String) keys.next();
            Integer keyInt = Integer.parseInt(keyStr);
            Integer value = xCoordinates.getInt(keyStr);
            cropsColumnCoordinate.put(keyInt, value);
        }


        JSONArray resourcesJson = jsonObject.getJSONArray("resources");

        resources = new int[resourcesJson.length()][];
        for (int i = 0; i < resourcesJson.length(); i++) {
            JSONArray innerJsonArray = resourcesJson.getJSONArray(i);
            resources[i] = new int[innerJsonArray.length()];
            for (int j = 0; j < innerJsonArray.length(); j++) {
                resources[i][j] = innerJsonArray.getInt(j);
            }
        }

        JSONObject seeds = jsonObject.getJSONObject("seeds");
        sunflower_seed = getCoordinate(seeds, "sunflower");
        potato_seed = getCoordinate(seeds, "potato");
        pumpkin_seed = getCoordinate(seeds, "pumpkin");
        carrot_seed = getCoordinate(seeds, "carrot");
        cabbage_seed = getCoordinate(seeds, "cabbage");
        beetroot_seed = getCoordinate(seeds, "beetroot");
        cauliflower_seed = getCoordinate(seeds, "cauliflower");
        parsnip_seed = getCoordinate(seeds, "parsnip");
        eggplant_seed = getCoordinate(seeds, "eggplant");

        firePit = getCoordinate(jsonObject, "firePit");

        JSONObject meals = jsonObject.getJSONObject("meals");
        mashedPotato = getCoordinate(meals, "mashedPotato");
        mashedPotatoCookButton = getCoordinate(meals, "mashedPotatoCookButton");
        pumpkinSoup = getCoordinate(meals, "pumpkinSoup");
        pumpkinSoupCookButton = getCoordinate(meals, "pumpkinSoupCookButton");
        bumpkinBroth = getCoordinate(meals, "bumpkinBroth");
        bumpkinBrothCookButton = getCoordinate(meals, "bumpkinBrothCookButton");
    }

    public int[] getCoordinate(JSONObject arr, String element) {
        JSONArray sunflower = arr.getJSONArray(element);
        int[] coordinate = new int[sunflower.length()];
        for (int i = 0; i < sunflower.length(); i++) {
            coordinate[i] = sunflower.getInt(i);
        }
        return coordinate;
    }

    public int farmId;

    public int[] sunflowerLandTab;

    private void setTimes() {
        cropsTimes = new HashMap<>();
        mealsTimes = new HashMap<>();

        mealsTimes.put("Mashed Potato", 54);
        mealsTimes.put("Pumpkin Soup", 2 * 60 + 42);
        mealsTimes.put("Bumpkin Broth", 18 * 60);


        cropsTimes.put("Sunflower", 46);
        cropsTimes.put("Potato", 3 * 60 + 41);
        cropsTimes.put("Pumpkin", 22 * 60);
        cropsTimes.put("Carrot", 44 * 60);
        cropsTimes.put("Cabbage", 60 * 60 + 28 * 60);
        cropsTimes.put("Beetroot", 2 * 60 * 60 + 55 * 60);
        cropsTimes.put("Cauliflower", 5 * 60 * 60 + 49 * 60);
        cropsTimes.put("Parsnip", 4 * 60 * 60 + 22 * 60);
        cropsTimes.put("Eggplant", 8 * 60 * 60 + 43 * 60);
        cropsTimes.put("Radish", 17 * 60 * 60 + 27 * 60);
        cropsTimes.put("Wheat", 17 * 60 * 60 + 27 * 60);
        cropsTimes.put("Kale", 26 * 60 * 60 + 60 * 60);
    }

    public int[][] resources;
    public int[] blank;
    public int[] inventory;
    public int[] sunflower_seed;
    public int[] potato_seed;
    public int[] pumpkin_seed;
    public int[] carrot_seed;
    public int[] cabbage_seed;
    public int[] beetroot_seed;
    public int[] cauliflower_seed;
    public int[] parsnip_seed;
    public int[] eggplant_seed;
    public int[] firePit;
    public int[] mashedPotato;
    public int[] mashedPotatoCookButton;
    public int[] pumpkinSoup;
    public int[] pumpkinSoupCookButton;
    public int[] bumpkinBroth;
    public int[] bumpkinBrothCookButton;

    public Map<String, Integer> cropsTimes;
    public Map<String, Integer> mealsTimes;

    public Map<Integer, Integer> cropsRowCoordinate;
    public Map<Integer, Integer> cropsColumnCoordinate;
}
