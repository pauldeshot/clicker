package ClickerBot.Config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WombatConfig {
    // Konfiguracja dotycząca czasu czekania
    public int waitRun;
    public int maxRuns;
    public int guildMode;
    public int collectCandy;
    public int[] runButton;
    public int[] rewardButton;
    public int[] wombatTab;
    public int[] refresh;
    public int[] treasure;
    public int[] requestAll;
    public int[] helpAll;
    public int[] claimCandyButton;
    public int[] singTransactionButton;
    public int[] runWithoutCandy;
    public int[] dontAskAgain;

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
        guildMode = (int) jsonObject.get("guildMode");
        collectCandy = (int) jsonObject.get("collectCandy");
        runButton = getCoordinate(jsonObject, "runButton");
        rewardButton = getCoordinate(jsonObject, "rewardButton");
        wombatTab = getCoordinate(jsonObject, "wombatTab");
        refresh = getCoordinate(jsonObject, "refresh");
        treasure = getCoordinate(jsonObject, "treasure");
        requestAll = getCoordinate(jsonObject, "requestAll");
        helpAll = getCoordinate(jsonObject, "helpAll");
        claimCandyButton = getCoordinate(jsonObject, "claimCandyButton");
        dontAskAgain = getCoordinate(jsonObject, "dontAskAgain");
        runWithoutCandy = getCoordinate(jsonObject, "runWithoutCandy");
        singTransactionButton = getCoordinate(jsonObject, "singTransactionButton");
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
