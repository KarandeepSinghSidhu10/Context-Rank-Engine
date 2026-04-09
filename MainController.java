import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MainController {

    private final MockDatabase database;
    private final ContextScorer scorer;
    private final Ranker ranker;
    private List<Page> pages;

    public MainController() {
        database = new MockDatabase();
        scorer = new ContextScorer();
        ranker = new Ranker();
        pages = new ArrayList<>();
    }

    public void loadData() {
        pages = database.loadPages();
        System.out.println("Data loaded into memory: " + pages.size() + " pages");
    }

    public List<SearchResult> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            System.out.println("Please enter search query");
            return new ArrayList<>();
        }

        List<String> keywords = processQuery(query);

        if (keywords.isEmpty()) {
            return new ArrayList<>();
        }

        List<SearchResult> scoredResults = new ArrayList<>();

        for (Page page : pages) {
            SearchResult result = scorer.scorePage(page, query, keywords);
            if (result.score > 0) {
                scoredResults.add(result);
            }
        }

        return ranker.rank(scoredResults);
    }

    private List<String> processQuery(String query) {
        List<String> stopWords = Arrays.asList(
                "the", "is", "of", "and", "to", "a", "an", "in", "on", "for", "this", "that"
        );

        String cleaned = query.toLowerCase()
                .replaceAll("[^a-zA-Z0-9\\s]", " ")
                .replaceAll("\\s+", " ")
                .trim();

        if (cleaned.isEmpty()) {
            return new ArrayList<>();
        }

        String[] words = cleaned.split(" ");
        Set<String> unique = new LinkedHashSet<>();

        for (String word : words) {
            if (!stopWords.contains(word) && !word.trim().isEmpty()) {
                unique.add(word);
            }
        }

        return new ArrayList<>(unique);
    }

    public void runAllTests() {
        List<TestCase> testCases = new ArrayList<>();

        // BEST
        testCases.add(new TestCase("TC_BEST_001", "Exact title match", "Java Search Engine", "Java Search Engine"));
        testCases.add(new TestCase("TC_BEST_002", "Full phrase match", "search engine design", "Search Engine Design"));
        testCases.add(new TestCase("TC_BEST_003", "Unique keyword search", "blockchain consensus", "Blockchain Consensus"));
        testCases.add(new TestCase("TC_BEST_004", "Ranking java query", "ranking algorithm java", "Ranking Algorithm in Java"));
        testCases.add(new TestCase("TC_BEST_005", "AI model search", "AI model", "AI Model Optimization"));
        testCases.add(new TestCase("TC_BEST_006", "Machine learning search", "machine learning ranking", "Machine Learning Ranking"));
        testCases.add(new TestCase("TC_BEST_007", "Java features search", "java 8", "Java 8 Features"));
        testCases.add(new TestCase("TC_BEST_008", "Metadata-rich query", "metadata retrieval", "Search Engine Design"));
        testCases.add(new TestCase("TC_BEST_009", "Engine keyword", "engine performance", "Engine Performance Basics"));
        testCases.add(new TestCase("TC_BEST_010", "Database indexing", "database indexing", "Database Management Systems"));

        // AVG
        testCases.add(new TestCase("TC_AVG_001", "Partial match", "java ranking", "Ranking Algorithm in Java"));
        testCases.add(new TestCase("TC_AVG_002", "Content match", "optimization", "AI Model Optimization"));
        testCases.add(new TestCase("TC_AVG_003", "Case insensitive", "JAVA", "Java Search Engine"));
        testCases.add(new TestCase("TC_AVG_004", "Mixed case", "JaVa EnGiNe", "Java Search Engine"));
        testCases.add(new TestCase("TC_AVG_005", "Common keyword", "engine", "Java Search Engine"));
        testCases.add(new TestCase("TC_AVG_006", "Two keyword query", "ranking system", "Machine Learning Ranking"));
        testCases.add(new TestCase("TC_AVG_007", "Stop words handling", "the search engine", "Java Search Engine"));
        testCases.add(new TestCase("TC_AVG_008", "Duplicate words", "java java ranking", "Ranking Algorithm in Java"));
        testCases.add(new TestCase("TC_AVG_009", "Numeric query", "java 8", "Java 8 Features"));
        testCases.add(new TestCase("TC_AVG_010", "Medium relevance", "algorithm", "Data Mining Algorithm"));

        // WORST
        testCases.add(new TestCase("TC_WORST_001", "No matching result", "zzzzunknownword", "NO_RESULTS"));
        testCases.add(new TestCase("TC_WORST_002", "Empty input", "", "NO_RESULTS"));
        testCases.add(new TestCase("TC_WORST_003", "Special chars only", "@#$%^&*", "NO_RESULTS"));
        testCases.add(new TestCase("TC_WORST_004", "Very long query", "this is a very long query for java search engine ranking metadata sorting logic", "Java Search Engine"));
        testCases.add(new TestCase("TC_WORST_005", "SQL-like input", "' OR 1=1 --", "NO_RESULTS"));
        testCases.add(new TestCase("TC_WORST_006", "Unsupported symbols", "漢字漢字", "NO_RESULTS"));
        testCases.add(new TestCase("TC_WORST_007", "Random symbols and words", "### java ###", "Java Search Engine"));
        testCases.add(new TestCase("TC_WORST_008", "Only stop words", "the is of and to", "NO_RESULTS"));
        testCases.add(new TestCase("TC_WORST_009", "Spaces only", "     ", "NO_RESULTS"));
        testCases.add(new TestCase("TC_WORST_010", "Unrelated long input", "weather politics football cinema agriculture", "NO_RESULTS"));

        int passCount = 0;

        System.out.println("\n==============================");
        System.out.println("RUNNING TEST CASES");
        System.out.println("==============================");

        for (TestCase tc : testCases) {
            List<SearchResult> results = search(tc.query);

            boolean pass;
            String actual;

            if (tc.expectedTopTitle.equals("NO_RESULTS")) {
                pass = results.isEmpty();
                actual = results.isEmpty() ? "NO_RESULTS" : results.get(0).page.title;
            } else {
                pass = !results.isEmpty() &&
                        results.get(0).page.title.equalsIgnoreCase(tc.expectedTopTitle);
                actual = results.isEmpty() ? "NO_RESULTS" : results.get(0).page.title;
            }

            if (pass) passCount++;

            System.out.println("----------------------------------");
            System.out.println("ID: " + tc.id);
            System.out.println("Description: " + tc.description);
            System.out.println("Query: " + tc.query);
            System.out.println("Expected: " + tc.expectedTopTitle);
            System.out.println("Actual: " + actual);
            System.out.println("Status: " + (pass ? "PASS" : "FAIL"));
        }

        System.out.println("\nTotal Passed: " + passCount + "/" + testCases.size());
    }

    // --------- INNER SUPPORT CLASSES ---------

    public static class Page {
        String title;
        String url;
        String content;
        List<String> metadata;

        public Page(String title, String url, String content, List<String> metadata) {
            this.title = title;
            this.url = url;
            this.content = content;
            this.metadata = metadata;
        }

        public String metadataAsText() {
            return String.join(" ", metadata);
        }
    }

    public static class SearchResult {
        Page page;
        double score;
        List<String> matchedKeywords;
        String reason;

        public SearchResult(Page page, double score, List<String> matchedKeywords, String reason) {
            this.page = page;
            this.score = score;
            this.matchedKeywords = matchedKeywords;
            this.reason = reason;
        }
    }

    public static class TestCase {
        String id;
        String description;
        String query;
        String expectedTopTitle;

        public TestCase(String id, String description, String query, String expectedTopTitle) {
            this.id = id;
            this.description = description;
            this.query = query;
            this.expectedTopTitle = expectedTopTitle;
        }
    }
}