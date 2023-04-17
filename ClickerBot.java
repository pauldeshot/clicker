import java.awt.*;
import java.awt.event.InputEvent;

public class ClickerBot {
	private Robot robot;

	private Utils utils;
	
	public ClickerBot() {
		utils = new Utils();
		try {
			robot = new Robot();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickMouse() {
		this.click(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public void click(int button) {
		try {
			robot.mousePress(button);
			robot.delay(100);
			robot.mouseRelease(button);
			robot.delay(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void move(int x, int y) {
		robot.mouseMove(x, y);
	}

	public int getX() {
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		return (int) b.getX();
	}

	public int getY() {
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		return (int) b.getY();
	}

	public void sleep(int x) {
		utils.sleep(x);
	}

	public void sleepM(int x) {
		utils.sleepM(x);
	}

	public void printCoordinate() {
		int x = getX();
		int y = getY();
		System.out.println("Position X: " + x);
		System.out.println("Position Y: " + y);
		System.out.println("{"+x+","+y+"}");
	}
}

