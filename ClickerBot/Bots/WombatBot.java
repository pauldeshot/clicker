package ClickerBot.Bots;

import ClickerBot.Config.WombatConfig;
import ClickerBot.DTO.WombatResult;

import java.util.Date;

public class WombatBot {

    WombatConfig config;
    ClickerBot clickerBot;
    int runs = 0;

    int lastRefreshHour;
    int lastRefreshMinute = 23;
    private boolean helpRequested = false;

    public WombatBot(WombatConfig config, ClickerBot clickerBot) {
        this.config = config;
        this.clickerBot = clickerBot;
        Date date = new Date();
        this.lastRefreshHour = date.getHours() + 1;
    }

    public WombatResult run (int currentWaitingRun) {
        WombatResult result = new WombatResult();

        if (currentWaitingRun >= config.waitRun || currentWaitingRun == -1) {
            clickerBot.sleep(1);
            clickInTab();
            if (currentWaitingRun > 0) {
                clickerBot.sleep(3);
                result.totalWaitingTime += 3;
                claim();
                clickerBot.sleep(1);
                result.totalWaitingTime += 1;
                claim();
                clickerBot.sleep(3);
                result.totalWaitingTime += 3;
            }
            startRun();
            this.helpRequested = false;
            result.resetTime = true;
            runs++;
            System.out.println("Run: "+ runs);
        }

        if (this.config.guildMode == 1 && !this.helpRequested) {
            clickerBot.sleep(1);
            clickInTab();
            this.helpRequested = true;
            requestHelp(2);
            result.totalWaitingTime += 10;
        }

        result.totalWaitingTime++;
        return result;
    }

    public void claimTreasure () {
        treasureClaim();
        clickerBot.sleep(90);
    }

    private void treasureClaim() {
        clickerBot.move(config.treasure[0], config.treasure[1]);
        clickerBot.clickMouse();
        clickerBot.sleep(3);
        clickerBot.move(config.treasure[0], config.treasure[1]);
        clickerBot.clickMouse();
        clickerBot.sleep(3);
    }

    private void startRun() {
        clickerBot.move(config.runButton[0], config.runButton[1]);
        clickerBot.clickMouse();
        clickerBot.sleep(3);
        clickerBot.move(config.dontAskAgain[0], config.dontAskAgain[1]);
        clickerBot.clickMouse();
        clickerBot.sleep(3);
        clickerBot.move(config.runWithoutCandy[0], config.runWithoutCandy[1]);
        clickerBot.clickMouse();
    }

    private void requestHelp(int waitingTime) {
        clickerBot.sleep(waitingTime);
        clickerBot.move(config.helpAll[0], config.helpAll[1]);
        clickerBot.clickMouse();

        clickerBot.sleep(waitingTime);
        clickerBot.move(config.requestAll[0], config.requestAll[1]);
        clickerBot.clickMouse();
    }
    private void refresh() {
        clickerBot.move(config.refresh[0], config.refresh[1]);
        clickerBot.clickMouse();
        clickerBot.sleep(30);
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
