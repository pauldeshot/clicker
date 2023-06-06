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

        cropsTimes.put("Sunflower", 46);
        cropsTimes.put("Potato", 3 * 60 + 41);
        cropsTimes.put("Pumpkin", 22 * 60);
        cropsTimes.put("Carrot", 44 * 60);
        cropsTimes.put("Cabbage", 60 + 28 * 60);
        cropsTimes.put("Beetroot", 2 * 60 + 55 * 60);
        cropsTimes.put("Cauliflower", 5 * 60 + 49 * 60);
        cropsTimes.put("Parsnip", 4 * 60 + 22 * 60);
        cropsTimes.put("Eggplant", 8 * 60 + 43 * 60);
        cropsTimes.put("Radish", 17 * 60 + 27 * 60);
        cropsTimes.put("Wheat", 17 * 60 + 27 * 60);
        cropsTimes.put("Kale", 26 * 60 + 60 * 60);
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

    public int[][] crops = {
            {1000,129},
            {1038,129},
            {1076,129},
            {1114,129},
            {1152,129},
            {1190,129},
            {1228,129},
            {1266,129},
            {1304,129},
            {1342,129},

            {1000,166},
            {1076,166},
            {1114,166},
            {1152,166},
            {1190,166},
            {1228,166},
            {1266,166},
            {1304,166},
            {1342,166},

            {1004,242},
            {1038,242},
            {1076,242},
            {1114,242},

            {1000,278},
            {1038,278},
            {1076,278},
            {1114,278},
            {1152,278},
            {1190,278},
            {1228,278},
            {1266,278},
            {1304,278},
            {1342,278},

            {1000,317},
            {1038,317},
            {1076,317},
            {1114,317},
            {1152,317},
            {1190,317},
            {1228,317},
            {1266,317},
            {1304,317},
            {1342,317},
    };

    public int[][] cropsPosition = {
            {4,15},
            {5,15},
            {6,15},
            {7,15},
            {8,15},
            {9,15},
            {10,15},
            {11,15},
            {12,15},
            {13,15},

            {4,14},
            {6,14},
            {7,14},
            {8,14},
            {9,14},
            {10,14},
            {11,14},
            {12,14},
            {13,14},

            {4,12},
            {5,12},
            {6,12},
            {7,12},

            {4,11},
            {5,11},
            {6,11},
            {7,11},
            {8,11},
            {9,11},
            {10,11},
            {11,11},
            {12,11},
            {13,11},

            {4, 10},
            {5,10},
            {6,10},
            {7,10},
            {8,10},
            {9,10},
            {10,10},
            {11,10},
            {12,10},
            {13,10},
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
    public  int[] axe = {455,514};
    public  int[] pickaxe = {509,515};
    public  int[] stone_pickaxe = {564,515};
    public  int[] firePit = {362,445};
    public  int[] firePitMealButton = {882,580};
//    public  int[] firePitMealButton = {791,529};

    public Map<String, Integer> cropsTimes;
}
