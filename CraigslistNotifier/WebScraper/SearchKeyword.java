public class SearchKeyword extends WebScraper{
    protected String keyword;
    public SearchKeyword(String area, String subArea, String topic, String keyword) {
        super(area, subArea, topic);
        this.keyword = keyword;
    }
}
