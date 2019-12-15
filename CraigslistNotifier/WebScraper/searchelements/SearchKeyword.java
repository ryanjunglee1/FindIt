package searchelements;
import java.util.Scanner;

import scraping.WebScraper;

public class SearchKeyword extends WebScraper{
    private String keyword;
    public SearchKeyword(Search search, Scanner scan, String keyword) {
        super(search, scan);
        this.keyword = keyword;
    }
}
