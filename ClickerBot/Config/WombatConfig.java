package ClickerBot.Config;

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
    }
}
