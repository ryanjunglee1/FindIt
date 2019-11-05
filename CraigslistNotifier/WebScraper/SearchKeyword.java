public class SearchKeyword extends WebScraper{
    private String keyword;
    public SearchKeyword(WebScraper webScraper, String keyword) {
        super(webScraper.getArea(), webScraper.getSubArea(), webScraper.getCategory());
        this.keyword = keyword;
        
    }
}
