package ClickerBot.Enums;

import java.util.HashMap;
import java.util.Map;

public class FruitDrinks {
    public static String PurpleSmoothie = "Purple Smoothie";

    public static Map<String, Integer> getList() {
        Map<String, Integer> list = new HashMap<>();
        list.put(PurpleSmoothie, 0);
        return list;
    }
}
