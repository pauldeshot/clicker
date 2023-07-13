package ClickerBot.Config;

import java.util.HashMap;
import java.util.Map;

public class SunflowerLandConfig {
    public boolean claimFruits = false;
    public boolean claimMeals = true;


        public int[] sunflowerLandTab = {107,15};
//    public int[] sunflowerLandTab = {370,12};

    public int waitMeal = 54;
//    public int waitMeal = 18 * 60;
    public int fullWaitForNextFruit = 8 * 60 * 60 + 60;
    public  int waitForNextFruit = 135 * 60;
    public  int maxMeals = 180;

    public SunflowerLandConfig(int waitMeal, int waitForNextFruit, int maxMeals) {
        this.waitMeal = waitMeal;
        this.waitForNextFruit = waitForNextFruit;
        this.maxMeals = maxMeals;

        setTimes();
    }

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


        // crops rows
        cropsRowCoordinate = new HashMap<>();
        cropsRowCoordinate.put(14, 162);
        cropsRowCoordinate.put(13, 198);
        cropsRowCoordinate.put(12, 236);
        cropsRowCoordinate.put(11, 272);
        cropsRowCoordinate.put(10, 313);

        // crops columns
        cropsColumnCoordinate = new HashMap<>();
        cropsColumnCoordinate.put(1, 896);
        cropsColumnCoordinate.put(2, 934);
        cropsColumnCoordinate.put(3, 972);
        cropsColumnCoordinate.put(4, 1011);
        cropsColumnCoordinate.put(5, 1048);
        cropsColumnCoordinate.put(6, 1082);
        cropsColumnCoordinate.put(7, 1121);
        cropsColumnCoordinate.put(8, 1159);
        cropsColumnCoordinate.put(9, 1197);
        cropsColumnCoordinate.put(10, 1233);
        cropsColumnCoordinate.put(11, 1274);
        cropsColumnCoordinate.put(12, 1310);
        cropsColumnCoordinate.put(13, 1350);
    }

    public SunflowerLandConfig() {

        setTimes();
    }

    // Konfiguracja dotycząca położenia elementów
    public  int[][] fruits = {
            {760,422},
            {835,422},
            {910,425},
            {985,425},
            {1060,425},
            {1135,425},
            {1210,425},
            {1285,425},
            {1360,425},
    };

    public  int[][] trees = {
            {829,561},
            {910,561},
            {985,561},
            {1060,561},
            {1135,561},
            {1210,561},
            {1285,561},
            {1360,561},

            {910,638},
            {985,638},
            {1060,638},
            {1135,638},
            {1210,638},
            {1285,638},
            {1360,638},
    };

    public  int[][] stones = {
            {550,171},
            {588,171},
            {626,171},
            {664,171},
            {702,171},
            {740,171},
            {778,171},

            {550,210},
            {588,210},
            {626,210},
            {664,210},
            {702,210},
            {740,210},
            {778,210},
    };

    public  int[][] iron = {
            {542,283},
            {580,283},
            {618,283},
            {655,283},

            {542,322},
            {580,322},
            {618,322},
            {655,322}
    };

    public  int[] blank = {842,173};
    public  int[] inventory = {1507,279};
    public  int[] sunflower_seed = {456,364};
    public  int[] potato_seed = {507,365};
    public  int[] pumpkin_seed = {575,350};
    public  int[] carrot_seed = {610,362};
    public  int[] cabbage_seed = {669,366};
    public  int[] beetroot_seed = {726,370};
    public  int[] parsnip_seed = {832,367};
    public  int[] cauliflower_seed = {779,366};
    public  int[] axe = {455,514};
    public  int[] pickaxe = {509,515};
    public  int[] stone_pickaxe = {564,515};
    public  int[] firePit = {362,445};
    public  int[] mashedPotato = {584,378};
    public  int[] mashedPotatoCookButton = {896,577};
    public  int[] pumpkinSoup = {640,378};
    public  int[] pumpkinSoupCookButton = {895,582};
    public  int[] bumpkinBroth = {694,370};
    public  int[] bumpkinBrothCookButton = {898,600};

    public Map<String, Integer> cropsTimes;
    public Map<String, Integer> mealsTimes;

    public Map<Integer, Integer> cropsRowCoordinate;
    public Map<Integer, Integer> cropsColumnCoordinate;
}
