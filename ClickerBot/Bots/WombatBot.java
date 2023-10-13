package ClickerBot.Bots;

import ClickerBot.Config.WombatConfig;
import ClickerBot.DTO.WombatResult;

import java.util.Date;
import java.util.Random;

public class WombatBot {

    WombatConfig config;
    ClickerBot clickerBot;
    int runs = 0;
    int fiveMinRuns = 0;
    int oneHourRuns = 0;
    private boolean helpRequested = false;

    public WombatBot(WombatConfig config, ClickerBot clickerBot) {
        this.config = config;
        this.clickerBot = clickerBot;
    }

    public WombatResult run (int currentWaitingRun) {
        WombatResult result = new WombatResult();

        if (config.maxRuns >= runs && currentWaitingRun >= config.waitRun || currentWaitingRun == -1) {
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
            Random rand = new Random();
            clickerBot.sleep(rand.nextInt(5));
            startRun();
            this.helpRequested = false;
            result.resetTime = true;
            runs++;
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

    public WombatResult run5MinModeRuns (int currentWaitingRun) {
        WombatResult result = new WombatResult();

         if (config.fiveMinMaxRunsConfig >= fiveMinRuns && currentWaitingRun >= config.waitRun || currentWaitingRun == -1) {
            clickerBot.sleep(new Random().nextInt(config.maxDelayBetweenRunsInSeconds));
            clickInTab();
            collectReward();
            selectRunTypeAndStart(true)
            this.helpRequested = false;
            result.resetTime = true;
            fiveMinRuns++;
            System.out.println("Five minutes run: "+ fiveMinRuns + "and one hour run: " + oneHourRuns);
        }

        helpGuildTeammates(result);

        result.totalWaitingTime++;
        return result;
    }

    public WombatResult runOneHourModeRuns (int currentWaitingRun) {
        WombatResult result = new WombatResult();

         if (config.oneHourMaxRunsConfig >= oneHourRuns && currentWaitingRun >= (config.waitRun*12) || currentWaitingRun == -1) {
            clickerBot.sleep(new Random().nextInt(config.maxDelayBetweenRunsInSeconds));
            clickInTab();
            collectReward();
            selectRunTypeAndStart();
            this.helpRequested = false;
            result.resetTime = true;
            oneHourRuns++;
            System.out.println("Five minutes run: "+ fiveMinRuns + "and one hour run: " + oneHourRuns);
        }

        helpGuildTeammates(result);

        result.totalWaitingTime++;
        return result;
    }

    public void claimTreasure () {
        clickInTab();
        treasureClaim();
    }

    private void treasureClaim() {
        clickerBot.move(config.treasure[0], config.treasure[1]);
        clickerBot.clickMouse();
        clickerBot.sleep(3);
        clickerBot.move(config.treasure[0], config.treasure[1]);
        clickerBot.clickMouse();
        clickerBot.sleep(3);
        runs = 0;
    }

    private void startRun() {
        clickerBot.move(config.runButton[0], config.runButton[1]);
        clickerBot.clickMouse();
        clickerBot.sleep(3);
        candyCollecting()
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

    private void candyCollecting() {
        if (config.collectCandy == 1) {
            clickerBot.move(config.claimCandyButton[0], config.claimCandyButton[1]);
            clickerBot.clickMouse();
            clickerBot.sleep(3);
            clickerBot.move(config.singTransactionButton[0], config.singTransactionButton[1]);
            clickerBot.clickMouse();
        } else {
            clickerBot.move(config.dontAskAgain[0], config.dontAskAgain[1]);
            clickerBot.clickMouse();
            clickerBot.sleep(3);
            clickerBot.move(config.runWithoutCandy[0], config.runWithoutCandy[1]);
            clickerBot.clickMouse();
        }
    }

    private void selectRunTypeAndStart(boolean isFiveMinRun) {
        clickerBot.move(config.durationChangeButton[0], config.durationChangeButton[1]);
        clickerBot.clickMouse();
        clickerBot.move(isFiveMinRun ? config.fiveMinSelectableOption[0] : config.oneHourSelectableOption[0], isFiveMinRun ? config.fiveMinSelectableOption[1] : config.oneHourSelectableOption[1]);
        clickerBot.clickMouse();
        clickerBot.move(config.startRunFromChangingDurationButton[0], config.startRunFromChangingDurationButton[1]);
        clickerBot.clickMouse();
        clickerBot.sleep(3);
        candyCollecting();
    }

    private void collectReward(int currentWaitingRun, WombatResult result) {
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
    }

    private void helpGuildTeammates(WombatResult result) {
        if (this.config.guildMode == 1 && !this.helpRequested) {
            clickerBot.sleep(1);
            clickInTab();
            this.helpRequested = true;
            requestHelp(2);
            result.totalWaitingTime += 10;
        }
    }
}
