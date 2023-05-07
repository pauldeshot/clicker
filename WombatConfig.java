public class WombatConfig {
    // Konfiguracja dotycząca czasu czekania
    public int waitRun = 5 * 60 + 1;
//    public int waitRun = 13 * 60;
//  public int waitRun = 52 * 60;
    public int maxRuns = 200;
    // Konfiguracja dotycząca położenia elementów
    public int[] runButton = {737,492};
    public int[] rewardButton = {778,638};

//    public int[] wombatTab = {107,15};
    public int[] wombatTab = {370,12};
    public int[] refresh = {87,52};
    public int[] treasure = {1440,150};

    public int[] menu = {1482,788};
    public int[] clans = {1406,575};
    public int[] members = {659,789};
    public int[] requestAll = {753,230};
    public int[] helpAll = {754,750};
    public int[] backToHome = {32,163};

    public WombatConfig(int waitRun, int maxRuns) {
        this.waitRun = waitRun;
        this.maxRuns = maxRuns;
    }

    public WombatConfig() {
    }
}
