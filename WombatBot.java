import java.util.Calendar;
import java.util.Date;

public class WombatBot {

    WombatConfig config;
    ClickerBot clickerBot;
    int runs = 0;

    int lastRefreshHour;
    int lastRefreshMinute = 23;

    public WombatBot(WombatConfig config, ClickerBot clickerBot) {
        this.config = config;
        this.clickerBot = clickerBot;
        Date date = new Date();
        this.lastRefreshHour = date.getHours() + 1;
    }

    public WombatResult run (int currentWaitingRun) {
        WombatResult result = new WombatResult();

        if (runs > config.maxRuns) {
            return result;
        }

        Date date = new Date();
        int minute = date.getMinutes();
        int hour = date.getHours();

        if (hour > lastRefreshHour && minute > lastRefreshMinute) {
            lastRefreshHour = hour;
            lastRefreshMinute = minute;

            clickInTab();
            refresh();
            clickerBot.sleep(15);
            result.totalWaitingTime += 15;
        }

        if (currentWaitingRun >= config.waitRun || currentWaitingRun == -1) {
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
            requestHelp(2);
            result.totalWaitingTime += 10;
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

    private void requestHelp(int waitingTime) {
        clickerBot.sleep(waitingTime);
        clickerBot.move(config.menu[0], config.menu[1]);
        clickerBot.clickMouse();
        clickerBot.sleep(waitingTime);
        clickerBot.move(config.clans[0], config.clans[1]);
        clickerBot.clickMouse();
        clickerBot.sleep(waitingTime);
        clickerBot.move(config.members[0], config.members[1]);
        clickerBot.clickMouse();
        clickerBot.sleep(waitingTime);
        clickerBot.move(config.requestAll[0], config.requestAll[1]);
        clickerBot.clickMouse();
        clickerBot.sleep(waitingTime);
        clickerBot.move(config.backToHome[0], config.backToHome[1]);
        clickerBot.clickMouse();
    }
    private void refresh() {
        clickerBot.move(config.refresh[0], config.refresh[1]);
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
