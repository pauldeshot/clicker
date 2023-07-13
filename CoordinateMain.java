public class CoordinateMain {
    static ClickerBot clickerBot;
    public static void main(String[] args) {
        System.out.println("----- Coordinate printer ------");
        clickerBot = new ClickerBot();

        System.out.println("Program uruchomi się za 2 sekundy.");
        clickerBot.sleep(2);


        clickerBot.printCoordinate();
    }
}
