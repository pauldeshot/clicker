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

        JSONArray sunflowerLandTabJson = jsonObject.getJSONArray("sunflowerLandTab");

        sunflowerLandTab = new int[sunflowerLandTabJson.length()];
        for (int i = 0; i < sunflowerLandTabJson.length(); i++) {
            sunflowerLandTab[i] = sunflowerLandTabJson.getInt(i);
        }

        JSONObject cropsCoordinates = jsonObject.getJSONObject("cropsCoordinates");
        JSONObject yCoordinates = cropsCoordinates.getJSONObject("y");
        JSONObject xCoordinates = cropsCoordinates.getJSONObject("x");

        cropsRowCoordinate = new HashMap<>();
        Iterator keys = yCoordinates.keys();

        while (keys.hasNext()) {
            String keyStr = (String) keys.next();
            Integer keyInt = Integer.parseInt(keyStr);
            Integer value = yCoordinates.getInt(keyStr);
            cropsRowCoordinate.put(keyInt, value);
        }

        keys = xCoordinates.keys();

        while (keys.hasNext()) {
            String keyStr = (String) keys.next();
            Integer keyInt = Integer.parseInt(keyStr);
            Integer value = yCoordinates.getInt(keyStr);
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

    public int[] blank = {1859, 707};
    public int[] inventory = {1864, 335};
    public int[] sunflower_seed = {647, 505};
    public int[] potato_seed = {705, 502};
    public int[] pumpkin_seed = {768, 503};
    public int[] carrot_seed = {828, 505};
    public int[] cabbage_seed = {889, 502};
    public int[] beetroot_seed = {949, 502};
    public int[] cauliflower_seed = {1011, 504};
    public int[] parsnip_seed = {1064, 502};
    public int[] eggplant_seed = {1128, 505};
    public int[] axe = {652, 670};
    public int[] pickaxe = {709, 672};
    public int[] stone_pickaxe = {768, 672};
    public int[] firePit = {622, 523};
    public int[] mashedPotato = {793, 529};
    public int[] mashedPotatoCookButton = {1142, 731};
    public int[] pumpkinSoup = {858, 528};
    public int[] pumpkinSoupCookButton = {1139, 743};
    public int[] bumpkinBroth = {919, 522};
    public int[] bumpkinBrothCookButton = {1138, 764};

    public Map<String, Integer> cropsTimes;
    public Map<String, Integer> mealsTimes;

    public Map<Integer, Integer> cropsRowCoordinate;
    public Map<Integer, Integer> cropsColumnCoordinate;
}
