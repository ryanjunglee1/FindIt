public class TestWebscraper {
    public static void main(String args[]) {
        WebScraper dc = new WebScraper("washingtondc");
        System.out.println(dc.area);
        GUI gui = new GUI();
        gui.start();
    }
}
