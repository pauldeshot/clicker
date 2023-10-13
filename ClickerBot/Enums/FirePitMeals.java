package ClickerBot.Enums;

import java.util.HashMap;
import java.util.Map;

public class FirePitMeals {
    public static String MashedPotato = "Mashed Potato";
    public static String PumpkinSoup = "Pumpkin Soup";
    public static String BumpkinBroth = "Bumpkin Broth";
    public static String Popcorn = "Popcorn";

    public static Map<String, Integer> getList() {
        Map<String, Integer> list = new HashMap<>();
        list.put(MashedPotato, 0);
        list.put(PumpkinSoup, 0);
        list.put(BumpkinBroth, 0);
        list.put(Popcorn, 0);
        return list;
    }
}
