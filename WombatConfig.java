public class WombatConfig {
    // Konfiguracja dotycząca czasu czekania
    public int waitRun = 4 * 60 + 22;
    public int maxRuns = 200;
    // Konfiguracja dotycząca położenia elementów
    public int[] runButton = {754,544};
    public int[] rewardButton = {778,679};
    public int[] wombatTab = {315,14};
    public int[] treasure = {1418,144};
    public int[] treasureCollect = {806,678};
    public int[] treasureCollect2 = {792,682};

    public WombatConfig(int waitRun, int maxRuns) {
        this.waitRun = waitRun;
        this.maxRuns = maxRuns;
    }

    public WombatConfig() {
    }
}
