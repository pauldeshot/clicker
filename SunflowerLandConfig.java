public class SunflowerLandConfig {
    public boolean claimFruits = false;
    public boolean claimMeals = true;

    public int[] sunflowerLandTab = {129,18};
    public int waitMeal = 54;
    public int fullWaitForNextFruit = 8 * 60 * 60 + 60;
    public  int waitForNextFruit = 135 * 60;
    public  int maxMeals = 220;

    public SunflowerLandConfig(int waitMeal, int waitForNextFruit, int maxMeals) {
        this.waitMeal = waitMeal;
        this.waitForNextFruit = waitForNextFruit;
        this.maxMeals = maxMeals;
    }

    public SunflowerLandConfig() {
    }

    // Konfiguracja dotycząca położenia elementów
    public  int[][] fruits = {
            {330,151},
            {554,152},
            {634,141},
            {1041,143},
            {624,222},
            {1050,590},
            {1048,810}
    };

    public int[][] crops = {
            {740,133},
            {780,133},
            {820,133},
            {860,133},
            {900,133},
            {930,133},
            {970,133},
            {1015,133},
            {1050,133},
            {1080,133},

            {740,169},
            {780,169},
            {820,169},
            {860,169},
            {900,169},
            {930,169},
            {970,169},
            {1015,169},
            {1050,169},
            {1080,169},

            {740,285},
            {780,285},
            {820,285},
            {860,285},
            {900,285},
            {930,285},
            {970,285},
            {1015,285},
            {1050,285},
            {1080,285},

            {780,318},
            {820,318},
            {860,318},
            {900,318},
            {930,318},
            {970,318},
            {1015,318},
            {1050,318},
            {1080,318},
    };

    public  int[] inventory = {1506,331};
    public  int[] firePit = {572,442};
    public  int[] firePitMealButton = {769,499};
}
