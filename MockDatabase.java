import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockDatabase {

    public List<MainController.Page> loadPages() {
        List<MainController.Page> pages = new ArrayList<>();

        pages.add(new MainController.Page(
                "Java Search Engine",
                "example.com/java-search",
                "Implementation of ranking algorithm in java. Covers search engine basics and java programming.",
                Arrays.asList("java", "search", "engine", "ranking")
        ));

        pages.add(new MainController.Page(
                "Search Engine Design",
                "example.com/search-design",
                "How to design and build a search engine from scratch. Architecture and algorithms explained.",
                Arrays.asList("search", "engine", "design", "architecture", "metadata", "retrieval")
        ));

        pages.add(new MainController.Page(
                "Ranking Algorithm in Java",
                "example.com/ranking-java",
                "Deep dive into ranking algorithms implemented in java. Sorting and scoring techniques.",
                Arrays.asList("ranking", "algorithm", "java", "sorting")
        ));

        pages.add(new MainController.Page(
                "AI Model Optimization",
                "example.com/ai-model",
                "Techniques for optimizing AI models. Covers gradient descent and optimization strategies.",
                Arrays.asList("ai", "model", "optimization", "machine learning")
        ));

        pages.add(new MainController.Page(
                "Machine Learning Ranking",
                "example.com/ml-ranking",
                "Using machine learning for ranking systems. Supervised and unsupervised ranking methods.",
                Arrays.asList("machine", "learning", "ranking", "system")
        ));

        pages.add(new MainController.Page(
                "Java 8 Features",
                "example.com/java8",
                "Overview of Java 8 features including streams, lambdas, and the new date API.",
                Arrays.asList("java", "8", "features", "streams", "lambda")
        ));

        pages.add(new MainController.Page(
                "Database Management Systems",
                "example.com/dbms",
                "Introduction to database systems, SQL queries, indexing, and database design principles.",
                Arrays.asList("database", "indexing", "sql", "management")
        ));

        pages.add(new MainController.Page(
                "Blockchain Consensus",
                "example.com/blockchain",
                "Understanding blockchain consensus mechanisms. Proof of work and proof of stake explained.",
                Arrays.asList("blockchain", "consensus", "distributed", "ledger")
        ));

        pages.add(new MainController.Page(
                "Engine Performance Basics",
                "example.com/engine-performance",
                "Fundamentals of engine performance tuning and optimization techniques.",
                Arrays.asList("engine", "performance", "optimization", "tuning")
        ));

        pages.add(new MainController.Page(
                "Data Mining Algorithm",
                "example.com/data-mining",
                "Exploring data mining algorithms for pattern recognition and knowledge discovery.",
                Arrays.asList("data", "mining", "algorithm", "pattern")
        ));

        return pages;
    }
}
