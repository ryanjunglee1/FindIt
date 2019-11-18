import java.util.Scanner;

public class SearchKeyword extends WebScraper{
    private String keyword;
    public SearchKeyword(Search search, Scanner scan, String keyword) {
        super(search, scan);
        this.keyword = keyword;
    }
}
