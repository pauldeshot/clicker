public class WombatBot {

    WombatConfig config;
    ClickerBot clickerBot;
    int runs = 0;

    public WombatBot(WombatConfig config, ClickerBot clickerBot) {
        this.config = config;
        this.clickerBot = clickerBot;
    }

    public WombatResult run (int currentWaitingRun) {
        WombatResult result = new WombatResult();

        if (runs > config.maxRuns) {
            return result;
        }

        if (currentWaitingRun >= config.waitRun || currentWaitingRun == -1) {
            clickInTab();
            if (currentWaitingRun > 0) {
                clickerBot.sleep(3);
                result.totalWaitingTime += 3;
                claim();
                clickerBot.sleep(3);
                result.totalWaitingTime += 3;
            }
            startRun();
            result.resetTime = true;
            runs++;
            System.out.println("Run: "+ runs);
        }
        clickerBot.sleep(1);
        result.totalWaitingTime++;
        return result;
    }

    private void startRun() {
        clickerBot.move(config.runButton[0], config.runButton[1]);
        clickerBot.clickMouse();
    }
    private void claim() {
        clickerBot.move(config.rewardButton[0], config.rewardButton[1]);
        clickerBot.clickMouse();
    }

    private void clickInTab() {
        clickerBot.move(config.wombatTab[0], config.wombatTab[1]);
        clickerBot.clickMouse();
    }
}
