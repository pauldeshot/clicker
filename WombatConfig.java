public class WombatConfig {
    // Konfiguracja dotycząca czasu czekania
    public int waitRun = 4 * 60 + 22;
//    public int waitRun = 13 * 60;
//  public int waitRun = 52 * 60;
    public int maxRuns = 200;
    // Konfiguracja dotycząca położenia elementów
    public int[] runButton = {622,488}; //{754,544};
    public int[] rewardButton = {598,624}; //{778,679};
    public int[] wombatTab = {131,14}; //{315,14};
    public int[] refresh = {86,53};
    public int[] treasure = {1418,144};
    public int[] treasureCollect = {806,678};
    public int[] treasureCollect2 = {792,682};

    public int[] menu = {1226,645};
    public int[] clans = {1163,430};
    public int[] members = {533,651};
    public int[] requestAll = {649,225};
    public int[] backToHome = {37,158};

    public WombatConfig(int waitRun, int maxRuns) {
        this.waitRun = waitRun;
        this.maxRuns = maxRuns;
    }

    public WombatConfig() {
    }
}
