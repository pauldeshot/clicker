import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CropsCronSunflowerLandMain {

    public static void main(String[] args) {
        System.out.println("----- Sunflower Land Clicker ------");
        clickerBot = new ClickerBot();
        System.out.println("Program uruchomi się za 2 sekundy.");
        clickerBot.sleepM(500);

        SunflowerLandConfig config = new SunflowerLandConfig();
        SunflowerLandBot bot = new SunflowerLandBot(config, clickerBot);

        WombatConfig wombatConfig = new WombatConfig();
        WombatBot wombatBot = new WombatBot(wombatConfig, clickerBot);

        Map<Integer, String> queue = new HashMap<>();

        String[] q = {
//            "Cauliflower",
//            "Parsnip",
//            "Cabbage",
            "Carrot",
            "Pumpkin",
            "Potato",
            "Sunflower"
        };

        for (int i = 0; i < q.length; i++) {
            queue.put(i, q[i]);
        }

        boolean farmCrops = false;
        boolean cookMeal = false;
        boolean collectResources = true;
        boolean wombat = true;

        int current = 0;
        String currentInventory = null;

        int waitTree = 36 * 60 + 15;
        int waitStone = 36 * 60 + 15;
        int waitIron = 36 * 60 + 15;

        Date nextCrop = new Date();
        Date nextTree = new Date();
        Date nextStone = new Date();
        Date nextIron = new Date();

        Date nextWombatRun = new Date();
        int waitWombat = 5 * 60;

        Date nextMeal = new Date();
        int waitMeal = 54;

        while (true) {
            Date currentDate = new Date();

            if  (cookMeal && currentDate.compareTo(nextMeal) >= 0) {
                bot.clickInTab();
                bot.collectMeal();
                nextMeal = getTimePlusSecond(waitMeal);
            }

            if (farmCrops && queue.containsKey(current) && currentDate.compareTo(nextCrop) >= 0) {
                bot.clickInTab();
                FarmData farmData = bot.checkFarm();

//                System.out.println("Current inventory: " + currentInventory);
//                if (!queue.get(current).equals(currentInventory)) {
                bot.inventory(queue.get(current));
//                    System.out.println("change inventory: " + currentInventory);
//                }

                nextCrop = getTimePlusSecond(config.cropsTimes.get(queue.get(current)) + 5);
                bot.crops(farmData.rewards, true);

                if (farmData.seeds.get(queue.get(current)) - 43 <= 0) {
                    current++;
                    nextCrop = new Date();
                }
                System.out.println("Next crops: " + nextCrop.toString());
            }

            if (collectResources && currentDate.compareTo(nextTree) >= 0) {
                bot.clickInTab();
                currentInventory = bot.inventory("Axe");
                bot.trees();
                nextTree = getTimePlusSecond(waitTree);
                System.out.println("Next tree: " + nextTree.toString());
            }

            if (collectResources && currentDate.compareTo(nextIron) >= 0) {
                bot.clickInTab();
                currentInventory = bot.inventory("Stone Pickaxe");
                bot.iron();
                nextIron = getTimePlusSecond(waitIron);
                System.out.println("Next iron: " + nextIron.toString());
            }

            if (collectResources && currentDate.compareTo(nextStone) >= 0) {
                bot.clickInTab();
                currentInventory = bot.inventory("Pickaxe");
                bot.stones();
                nextStone = getTimePlusSecond(waitStone);
                System.out.println("Next stone: " + nextStone.toString());
            }

            if (wombat) {
                if (currentDate.compareTo(nextWombatRun) >= 0) {
                    wombatBot.run(60 * 60 * 24);
                    nextWombatRun = getTimePlusSecond(waitWombat);
                    System.out.println("Next wombat: " + nextWombatRun.toString());
                }
            }

            clickerBot.sleep(1);
        }
    }

    private static Date getTimePlusSecond(int seconds) {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    static ClickerBot clickerBot;
}