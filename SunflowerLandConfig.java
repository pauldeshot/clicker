public class SunflowerLandConfig {

    public int[] sunflowerLandTab = {129,18};
    public int waitMeal = 15 * 60;
    public int fullWaitForNextFruit = 8 * 60 * 60 + 60;
    public  int waitForNextFruit = 135 * 60;
    public  int maxMeals = 69;

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

    public  int[] inventory = {1506,331};
    public  int[] firePit = {762,446};
    public  int[] firePitMealButton = {890,594};
}
