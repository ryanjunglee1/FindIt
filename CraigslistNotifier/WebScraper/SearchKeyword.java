public class SearchKeyword extends Webscraper{
    private String keyword;
    public SearchKeyword(Search search, String keyword) {
        super(search);
        this.keyword = keyword;
    }
}
