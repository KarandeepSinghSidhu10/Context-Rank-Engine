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

        List<MainController.SearchResult> ranked = ranker.rank(scoredResults);
        return ranked.subList(0, Math.min(3, ranked.size()));
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
        testCases.add(new TestCase("TC_BEST_001", "Exact title match", "Java Search Engine", "Java Search Engine", Arrays.asList("Java Search Engine", "Ranking Algorithm in Java", "Java 8 Features")));
        testCases.add(new TestCase("TC_BEST_002", "Full phrase match", "search engine design", "Search Engine Design", Arrays.asList("Search Engine Design", "Java Search Engine", "Engine Performance Basics")));
        testCases.add(new TestCase("TC_BEST_003", "Blockchain consensus", "blockchain consensus", "Blockchain Consensus", Arrays.asList("Blockchain Consensus", "Cybersecurity Fundamentals", "Ethical Hacking and Penetration Testing")));
        testCases.add(new TestCase("TC_BEST_004", "Ranking java query", "ranking algorithm java", "Ranking Algorithm in Java", Arrays.asList("Ranking Algorithm in Java", "Java Search Engine", "Machine Learning Ranking")));
        testCases.add(new TestCase("TC_BEST_005", "AI model search", "AI model", "AI Model Optimization", Arrays.asList("AI Model Optimization", "Machine Learning Ranking", "Python Machine Learning")));
        testCases.add(new TestCase("TC_BEST_006", "Machine learning search", "machine learning ranking", "Machine Learning Ranking", Arrays.asList("Machine Learning Ranking", "AI Model Optimization", "Data Mining Algorithm")));
        testCases.add(new TestCase("TC_BEST_007", "Java features search", "java 8", "Java 8 Features", Arrays.asList("Java 8 Features", "Java Search Engine", "Ranking Algorithm in Java")));
        testCases.add(new TestCase("TC_BEST_008", "Metadata-rich query", "metadata retrieval", "Search Engine Design", Arrays.asList("Search Engine Design", "Java Search Engine", "Ranking Algorithm in Java")));
        testCases.add(new TestCase("TC_BEST_009", "Engine keyword", "engine performance", "Engine Performance Basics", Arrays.asList("Engine Performance Basics", "Java Search Engine", "Search Engine Design")));
        testCases.add(new TestCase("TC_BEST_010", "Database indexing", "database indexing", "Database Management Systems", Arrays.asList("Database Management Systems", "Search Engine Design", "Python Data Science")));

        // AVG
        testCases.add(new TestCase("TC_AVG_001", "Python search", "python programming", "Python Programming Basics", Arrays.asList("Python Programming Basics", "Python Data Science", "Python Machine Learning")));
        testCases.add(new TestCase("TC_AVG_002", "Web development search", "web frontend", "React Frontend Development", Arrays.asList("React Frontend Development", "HTML and CSS Fundamentals", "JavaScript ES6 Guide")));
        testCases.add(new TestCase("TC_AVG_003", "Cloud search", "cloud deployment", "AWS Cloud Computing", Arrays.asList("AWS Cloud Computing", "Kubernetes Orchestration", "Docker and Containerization")));
        testCases.add(new TestCase("TC_AVG_004", "Security search", "security network", "Cybersecurity Fundamentals", Arrays.asList("Cybersecurity Fundamentals", "Ethical Hacking and Penetration Testing", "Computer Networks and Protocols")));
        testCases.add(new TestCase("TC_AVG_005", "Docker search", "docker container", "Docker and Containerization", Arrays.asList("Docker and Containerization", "Kubernetes Orchestration", "AWS Cloud Computing")));
        testCases.add(new TestCase("TC_AVG_006", "Linux devops search", "linux devops", "Linux Operating System", Arrays.asList("Linux Operating System", "Docker and Containerization", "Git Version Control")));
        testCases.add(new TestCase("TC_AVG_007", "Git software search", "git software", "Git Version Control", Arrays.asList("Git Version Control", "Agile and Scrum Methodology", "Docker and Containerization")));
        testCases.add(new TestCase("TC_AVG_008", "Case insensitive java", "JAVA", "Java Search Engine", Arrays.asList("Java Search Engine", "Ranking Algorithm in Java", "Java 8 Features")));
        testCases.add(new TestCase("TC_AVG_009", "Numeric query", "java 8", "Java 8 Features", Arrays.asList("Java 8 Features", "Java Search Engine", "Ranking Algorithm in Java")));
        testCases.add(new TestCase("TC_AVG_010", "Algorithm search", "algorithm data", "Data Mining Algorithm", Arrays.asList("Data Mining Algorithm", "Ranking Algorithm in Java", "Database Management Systems")));

        // WORST
        testCases.add(new TestCase("TC_WORST_001", "No matching result", "zzzzunknownword", "NO_RESULTS", new ArrayList<>()));
        testCases.add(new TestCase("TC_WORST_002", "Empty input", "", "NO_RESULTS", new ArrayList<>()));
        testCases.add(new TestCase("TC_WORST_003", "Special chars only", "@#$%^&*", "NO_RESULTS", new ArrayList<>()));
        testCases.add(new TestCase("TC_WORST_004", "Very long query", "this is a very long query for java search engine ranking metadata sorting logic", "Java Search Engine", Arrays.asList("Java Search Engine", "Ranking Algorithm in Java", "Search Engine Design")));
        testCases.add(new TestCase("TC_WORST_005", "SQL-like input", "' OR 1=1 --", "NO_RESULTS", new ArrayList<>()));
        testCases.add(new TestCase("TC_WORST_006", "Unsupported symbols", "漢字漢字", "NO_RESULTS", new ArrayList<>()));
        testCases.add(new TestCase("TC_WORST_007", "Random symbols and words", "### java ###", "Java Search Engine", Arrays.asList("Java Search Engine", "Ranking Algorithm in Java", "Java 8 Features")));
        testCases.add(new TestCase("TC_WORST_008", "Only stop words", "the is of and to", "NO_RESULTS", new ArrayList<>()));
        testCases.add(new TestCase("TC_WORST_009", "Spaces only", "     ", "NO_RESULTS", new ArrayList<>()));
        testCases.add(new TestCase("TC_WORST_010", "Unrelated long input", "weather politics football cinema agriculture", "NO_RESULTS", new ArrayList<>()));

        int passCount = 0;

        System.out.println("\n==============================");
        System.out.println("RUNNING TEST CASES");
        System.out.println("==============================");

        for (TestCase tc : testCases) {
            List<SearchResult> results = search(tc.query);

            boolean pass;
            String actual;
            List<String> actualTitles = new ArrayList<>();
            for (SearchResult r : results) actualTitles.add(r.page.title);

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
            System.out.println("Expected Top 1: " + tc.expectedTopTitle);
            System.out.println("Expected Top 3: " + tc.expectedTop3);
            System.out.println("Actual Top 1: " + actual);
            System.out.println("Actual Top 3: " + actualTitles);
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
        List<String> expectedTop3;

        public TestCase(String id, String description, String query, String expectedTopTitle, List<String> expectedTop3) {
            this.id = id;
            this.description = description;
            this.query = query;
            this.expectedTopTitle = expectedTopTitle;
            this.expectedTop3 = expectedTop3;
        }
    }
}