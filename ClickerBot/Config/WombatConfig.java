package ClickerBot.Config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WombatConfig {
    // Konfiguracja dotycząca czasu czekania
    public int waitRun = 5 * 60 + 1;
//    public int waitRun = 15 * 60;
//  public int waitRun = 52 * 60;
    public int maxRuns = 200;
    // Konfiguracja dotycząca położenia elementów
    public int[] runButton = {750,493};
    public int[] rewardButton = {796,638};
    public int[] wombatTab = {370,12};
    public int[] refresh = {87,52};
    public int[] treasure = {1440,150};
    public int[] requestAll = {758,532};
    public int[] helpAll = {1423,786};

    public WombatConfig(int waitRun, int maxRuns) {
        this.waitRun = waitRun;
        this.maxRuns = maxRuns;
    }

    public WombatConfig() {

        String path = "wombat.json";
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = new JSONObject(content);

        waitRun = (int) jsonObject.get("waitRun");
        maxRuns = (int) jsonObject.get("maxRuns");
        runButton = getCoordinate(jsonObject, "runButton");
        rewardButton = getCoordinate(jsonObject, "rewardButton");
        wombatTab = getCoordinate(jsonObject, "wombatTab");
        refresh = getCoordinate(jsonObject, "refresh");
        treasure = getCoordinate(jsonObject, "treasure");
        requestAll = getCoordinate(jsonObject, "requestAll");
        helpAll = getCoordinate(jsonObject, "helpAll");
    }

    public int[] getCoordinate(JSONObject arr, String element) {
        JSONArray sunflower = arr.getJSONArray(element);
        int[] coordinate = new int[sunflower.length()];
        for (int i = 0; i < sunflower.length(); i++) {
            coordinate[i] = sunflower.getInt(i);
        }
        return coordinate;
    }
}
