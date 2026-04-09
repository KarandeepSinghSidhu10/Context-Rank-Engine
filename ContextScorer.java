import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContextScorer {

    public MainController.SearchResult scorePage(MainController.Page page, String originalQuery, List<String> keywords) {
        int titleMatches = countMatches(page.title, keywords);
        int metadataMatches = countMetadataMatches(page.metadata, keywords);
        int contentMatches = countMatches(page.content, keywords);

        int totalMatches = titleMatches + metadataMatches + contentMatches;

        if (totalMatches == 0) {
            return new MainController.SearchResult(
                    page,
                    0.0,
                    new ArrayList<>(),
                    "No keyword matched"
            );
        }

        // SOFTMAX DYNAMIC WEIGHT ALLOCATION
        double expTitle = Math.exp(titleMatches);
        double expMetadata = Math.exp(metadataMatches);
        double expContent = Math.exp(contentMatches);

        double sumExp = expTitle + expMetadata + expContent;

        double wTitle = expTitle / sumExp;
        double wMetadata = expMetadata / sumExp;
        double wContent = expContent / sumExp;

        // FINAL SCORE WITHOUT ANY BONUS
        double finalScore = (wTitle * titleMatches)
                + (wMetadata * metadataMatches)
                + (wContent * contentMatches);

        List<String> matchedKeywords = extractMatchedKeywords(page, keywords);

        String reason = String.format(
                Locale.US,
                "Softmax weights: title=%.3f, metadata=%.3f, content=%.3f. " +
                "Match counts: title=%d, metadata=%d, content=%d. " +
                "FinalScore=%.3f",
                wTitle, wMetadata, wContent,
                titleMatches, metadataMatches, contentMatches,
                finalScore
        );

        return new MainController.SearchResult(page, finalScore, matchedKeywords, reason);
    }

    private int countMatches(String text, List<String> keywords) {
        int count = 0;
        String lowerText = text.toLowerCase();

        for (String keyword : keywords) {
            if (lowerText.contains(keyword.toLowerCase())) {
                count++;
            }
        }
        return count;
    }

    private int countMetadataMatches(List<String> metadata, List<String> keywords) {
        int count = 0;

        for (String keyword : keywords) {
            for (String meta : metadata) {
                if (meta.toLowerCase().contains(keyword.toLowerCase())) {
                    count++;
                    break;
                }
            }
        }

        return count;
    }

    private List<String> extractMatchedKeywords(MainController.Page page, List<String> keywords) {
        List<String> matched = new ArrayList<>();
        String combined = (page.title + " " + page.content + " " + page.metadataAsText()).toLowerCase();

        for (String keyword : keywords) {
            if (combined.contains(keyword.toLowerCase())) {
                matched.add(keyword);
            }
        }

        return matched;
    }
}