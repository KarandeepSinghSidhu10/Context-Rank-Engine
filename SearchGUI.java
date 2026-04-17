import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchGUI extends JFrame {

    private MainController controller;
    private JTextField searchField;
    private JPanel resultsContainer;

    public SearchGUI() {
        controller = new MainController();
        controller.loadData();

        setTitle("Context-Rank-Engine");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // --- TOP PANEL (Search Bar) ---
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(15, 10));
        // Add a subtle bottom border to the top panel
        topPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(235, 235, 235)),
                BorderFactory.createEmptyBorder(25, 40, 20, 40)
        ));
        topPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Engine");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(66, 133, 244)); // Google Blue

        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(223, 225, 229), 1, true),
                new EmptyBorder(8, 15, 8, 15)
        ));

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setBackground(new Color(26, 115, 232));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchButton.setPreferredSize(new Dimension(100, 40));

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        // --- CENTER PANEL (Results Container) ---
        resultsContainer = new JPanel();
        resultsContainer.setLayout(new BoxLayout(resultsContainer, BoxLayout.Y_AXIS));
        resultsContainer.setBackground(Color.WHITE);
        // Force the container to align children to the left
        resultsContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Wrap container in a panel to constrain width
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(Color.WHITE);
        wrapperPanel.setBorder(new EmptyBorder(20, 40, 40, 40));
        wrapperPanel.add(resultsContainer, BorderLayout.WEST); // Anchors results to the left

        JScrollPane scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // --- ACTIONS ---
        ActionListener performSearch = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeSearch();
            }
        };

        searchButton.addActionListener(performSearch);
        searchField.addActionListener(performSearch);
    }

    private void executeSearch() {
        String query = searchField.getText().trim();
        resultsContainer.removeAll();

        if (query.isEmpty()) {
            addMessageCard("Please enter a valid search query.");
            refreshUI();
            return;
        }

        List<MainController.SearchResult> results = controller.search(query);

        if (results.isEmpty()) {
            addMessageCard("No results found for '" + query + "'. Try different keywords.");
        } else {
            // Add a small "x results found" header
            JLabel statsHeader = new JLabel("Found " + results.size() + " results");
            statsHeader.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            statsHeader.setForeground(new Color(112, 117, 122));
            statsHeader.setBorder(new EmptyBorder(0, 0, 20, 0));
            resultsContainer.add(statsHeader);

            int rank = 1;
            for (MainController.SearchResult result : results) {
                resultsContainer.add(createResultCard(result, rank));
                resultsContainer.add(Box.createRigidArea(new Dimension(0, 30))); // Large gap between results
                rank++;
            }
        }
        
        refreshUI();
    }

    private JPanel createResultCard(MainController.SearchResult result, int rank) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        // Remove the box border, just rely on spacing
        card.setBorder(null); 
        
        // Ensure strictly left-aligned
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 1. URL Label (Green, small)
        JLabel urlLabel = new JLabel(result.page.url);
        urlLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        urlLabel.setForeground(new Color(32, 33, 36));
        urlLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 2. Title Label (Blue, bold, slightly larger)
        // Using HTML to enable hover-like underline aesthetic and exact color
        String titleHtml = "<html><span style='color: #1a0bcc; font-size: 16px;'>" + result.page.title + "</span></html>";
        JLabel titleLabel = new JLabel(titleHtml);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        titleLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 3. Snippet (Page Content) - Using HTML for automatic word-wrap and max width
        String snippetHtml = "<html><div style='width: 600px; color: #4d5156; line-height: 1.4;'>" 
                           + result.page.content + "</div></html>";
        JLabel snippetLabel = new JLabel(snippetHtml);
        snippetLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        snippetLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 4. Engine Metadata (Matched keywords, score)
        String metaText = String.format("Rank: %d • Score: %.4f • Matched: %s", rank, result.score, result.matchedKeywords);
        JLabel metaLabel = new JLabel(metaText);
        metaLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        metaLabel.setForeground(new Color(150, 150, 150));
        metaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add components with specific rigid areas for perfect spacing
        card.add(urlLabel);
        card.add(Box.createRigidArea(new Dimension(0, 2)));
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 6)));
        card.add(snippetLabel);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(metaLabel);

        return card;
    }

    private void addMessageCard(String message) {
        JLabel msgLabel = new JLabel(message);
        msgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        msgLabel.setForeground(Color.DARK_GRAY);
        resultsContainer.add(msgLabel);
    }

    private void refreshUI() {
        resultsContainer.revalidate();
        resultsContainer.repaint();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new SearchGUI().setVisible(true));
    }
}