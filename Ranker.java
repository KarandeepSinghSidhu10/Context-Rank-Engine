import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ranker {

    public List<MainController.SearchResult> rank(List<MainController.SearchResult> results) {
        Collections.sort(results, new Comparator<MainController.SearchResult>() {
            @Override
            public int compare(MainController.SearchResult r1, MainController.SearchResult r2) {

                int scoreCompare = Double.compare(r2.score, r1.score);
                if (scoreCompare != 0) {
                    return scoreCompare;
                }

                int keywordCompare = Integer.compare(
                        r2.matchedKeywords.size(),
                        r1.matchedKeywords.size()
                );
                if (keywordCompare != 0) {
                    return keywordCompare;
                }

                return r1.page.title.compareToIgnoreCase(r2.page.title);
            }
        });

        return results;
    }
}