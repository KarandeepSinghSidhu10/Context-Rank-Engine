import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockDatabase {

    public List<MainController.Page> loadPages() {
        List<MainController.Page> pages = new ArrayList<>();

        // Java
        pages.add(new MainController.Page(
                "Java Search Engine",
                "example.com/java-search",
                "Implementation of ranking algorithm in java. Covers search engine basics and java programming. Java is widely used for building search and retrieval systems.",
                Arrays.asList("java", "search", "engine", "ranking", "programming")
        ));

        pages.add(new MainController.Page(
                "Search Engine Design",
                "example.com/search-design",
                "How to design and build a search engine from scratch. Architecture and algorithms explained. Search engines use indexing, ranking and retrieval techniques.",
                Arrays.asList("search", "engine", "design", "architecture", "metadata", "retrieval", "indexing")
        ));

        pages.add(new MainController.Page(
                "Ranking Algorithm in Java",
                "example.com/ranking-java",
                "Deep dive into ranking algorithms implemented in java. Sorting and scoring techniques. Ranking is core to search engine and recommendation systems.",
                Arrays.asList("ranking", "algorithm", "java", "sorting", "scoring", "search")
        ));

        pages.add(new MainController.Page(
                "AI Model Optimization",
                "example.com/ai-model",
                "Techniques for optimizing AI models. Covers gradient descent, neural networks and optimization strategies. AI and machine learning models require tuning and optimization.",
                Arrays.asList("ai", "model", "optimization", "machine", "learning", "neural", "gradient")
        ));

        pages.add(new MainController.Page(
                "Machine Learning Ranking",
                "example.com/ml-ranking",
                "Using machine learning for ranking systems. Supervised and unsupervised ranking methods. Machine learning algorithms improve search ranking and recommendation.",
                Arrays.asList("machine", "learning", "ranking", "system", "algorithm", "supervised", "ai")
        ));

        pages.add(new MainController.Page(
                "Java 8 Features",
                "example.com/java8",
                "Overview of Java 8 features including streams, lambdas, and the new date API. Java 8 introduced functional programming concepts to java development.",
                Arrays.asList("java", "8", "features", "streams", "lambda", "functional", "programming")
        ));

        pages.add(new MainController.Page(
                "Database Management Systems",
                "example.com/dbms",
                "Introduction to database systems, SQL queries, indexing, and database design principles. Databases store and retrieve structured data efficiently.",
                Arrays.asList("database", "indexing", "sql", "management", "query", "data", "storage")
        ));

        pages.add(new MainController.Page(
                "Blockchain Consensus",
                "example.com/blockchain",
                "Understanding blockchain consensus mechanisms. Proof of work and proof of stake explained. Blockchain is a distributed ledger technology used in security and finance.",
                Arrays.asList("blockchain", "consensus", "distributed", "ledger", "security", "network", "decentralized")
        ));

        pages.add(new MainController.Page(
                "Engine Performance Basics",
                "example.com/engine-performance",
                "Fundamentals of engine performance tuning and optimization techniques. Performance optimization applies to search engines, databases and software systems.",
                Arrays.asList("engine", "performance", "optimization", "tuning", "system", "software")
        ));

        pages.add(new MainController.Page(
                "Data Mining Algorithm",
                "example.com/data-mining",
                "Exploring data mining algorithms for pattern recognition and knowledge discovery. Data mining uses machine learning and statistical algorithms.",
                Arrays.asList("data", "mining", "algorithm", "pattern", "machine", "learning", "statistics")
        ));

        // Python
        pages.add(new MainController.Page(
                "Python Programming Basics",
                "example.com/python-basics",
                "Introduction to python programming covering variables, loops, functions and data types. Python is a popular programming language for scripting, automation and data science.",
                Arrays.asList("python", "programming", "basics", "variables", "loops", "functions", "scripting")
        ));

        pages.add(new MainController.Page(
                "Python Data Science",
                "example.com/python-data-science",
                "Data science with python using pandas, numpy and matplotlib for data analysis and visualization. Python is the leading language for data science and machine learning.",
                Arrays.asList("python", "data", "science", "pandas", "numpy", "matplotlib", "visualization", "analysis")
        ));

        pages.add(new MainController.Page(
                "Python Machine Learning",
                "example.com/python-ml",
                "Machine learning with python using scikit-learn, tensorflow and keras. Python is widely used for building AI models, neural networks and deep learning systems.",
                Arrays.asList("python", "machine", "learning", "scikit", "tensorflow", "keras", "ai", "neural")
        ));

        // Web Development
        pages.add(new MainController.Page(
                "HTML and CSS Fundamentals",
                "example.com/html-css",
                "Web development basics with HTML structure and CSS styling, flexbox and grid layout. HTML and CSS are the foundation of all web pages and frontend development.",
                Arrays.asList("html", "css", "web", "design", "flexbox", "grid", "layout", "frontend")
        ));

        pages.add(new MainController.Page(
                "JavaScript ES6 Guide",
                "example.com/javascript-es6",
                "Modern javascript ES6 features including arrow functions, promises, async await and modules. JavaScript is the core programming language of web development and frontend.",
                Arrays.asList("javascript", "es6", "functions", "promises", "async", "web", "frontend", "programming")
        ));

        pages.add(new MainController.Page(
                "React Frontend Development",
                "example.com/react",
                "Building user interfaces with React, components, state management, hooks and virtual DOM. React is a javascript library for building modern web and frontend applications.",
                Arrays.asList("react", "frontend", "components", "state", "hooks", "javascript", "web", "ui")
        ));

        // Cloud and DevOps
        pages.add(new MainController.Page(
                "AWS Cloud Computing",
                "example.com/aws-cloud",
                "Amazon Web Services cloud computing covering EC2, S3, Lambda, RDS and cloud architecture. AWS is the leading cloud platform for deploying scalable web and software systems.",
                Arrays.asList("aws", "cloud", "computing", "ec2", "s3", "lambda", "deployment", "architecture")
        ));

        pages.add(new MainController.Page(
                "Docker and Containerization",
                "example.com/docker",
                "Containerization with Docker, building images, running containers and docker compose. Docker is used in devops for consistent deployment of software applications.",
                Arrays.asList("docker", "container", "image", "devops", "compose", "deployment", "software")
        ));

        pages.add(new MainController.Page(
                "Kubernetes Orchestration",
                "example.com/kubernetes",
                "Container orchestration with Kubernetes, pods, deployments, services and cluster management. Kubernetes works with docker for managing cloud deployments at scale.",
                Arrays.asList("kubernetes", "container", "orchestration", "pods", "deployment", "cloud", "docker")
        ));

        // Cybersecurity
        pages.add(new MainController.Page(
                "Cybersecurity Fundamentals",
                "example.com/cybersecurity",
                "Cybersecurity basics covering encryption, firewalls, network security and threat detection. Cybersecurity protects systems, networks and data from attacks and vulnerabilities.",
                Arrays.asList("cybersecurity", "security", "encryption", "firewall", "network", "threat", "protection")
        ));

        pages.add(new MainController.Page(
                "Ethical Hacking and Penetration Testing",
                "example.com/ethical-hacking",
                "Ethical hacking techniques, penetration testing tools, vulnerability scanning and exploitation. Security professionals use ethical hacking to find and fix network vulnerabilities.",
                Arrays.asList("hacking", "penetration", "testing", "vulnerability", "security", "network", "exploit")
        ));

        // Networking
        pages.add(new MainController.Page(
                "Computer Networks and Protocols",
                "example.com/networking",
                "Computer networking fundamentals, TCP IP, HTTP, DNS, routing and network protocols. Networking connects systems and enables web communication and data transfer.",
                Arrays.asList("networking", "tcp", "ip", "http", "dns", "routing", "protocol", "web")
        ));

        // Operating Systems
        pages.add(new MainController.Page(
                "Linux Operating System",
                "example.com/linux",
                "Linux OS fundamentals, shell commands, file system, process management and bash scripting. Linux is widely used in servers, cloud computing and devops environments.",
                Arrays.asList("linux", "os", "shell", "bash", "commands", "process", "cloud", "devops")
        ));

        // Software Engineering
        pages.add(new MainController.Page(
                "Agile and Scrum Methodology",
                "example.com/agile-scrum",
                "Agile software development with Scrum framework, sprints, backlog and team collaboration. Agile methodology improves software development and project management.",
                Arrays.asList("agile", "scrum", "sprint", "backlog", "software", "development", "methodology")
        ));

        pages.add(new MainController.Page(
                "Git Version Control",
                "example.com/git",
                "Version control with Git, branching, merging, pull requests, commits and repository management. Git is essential for software development, devops and team collaboration.",
                Arrays.asList("git", "version", "control", "branch", "merge", "commit", "software", "devops")
        ));

        return pages;
    }
}
