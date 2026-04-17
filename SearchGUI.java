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
    private JPanel topPanel;
    private JPanel wrapperPanel;
    private JScrollPane scrollPane;
    private JLabel titleLabel;
    private JButton searchButton;
    private JButton themeButton;
    
    // State to track current theme
    private boolean isDarkMode = false;
    
    // Cache the last results so we can re-render them instantly when swapping themes
    private List<MainController.SearchResult> currentResults = null;

    public SearchGUI() {
        controller = new MainController();
        controller.loadData();

        setTitle("Context-Rank-Engine");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- TOP PANEL (Search Bar & Theme Toggle) ---
        topPanel = new JPanel(new BorderLayout(15, 10));
        
        // Left: Logo
        titleLabel = new JLabel("Engine");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));

        // Center: Search Box
        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        
        // Right: Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false); // Let topPanel background show through
        
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setFocusPainted(false);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchButton.setPreferredSize(new Dimension(100, 40));
        
        themeButton = new JButton("🌙 Dark");
        themeButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        themeButton.setFocusPainted(false);
        themeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        themeButton.setPreferredSize(new Dimension(100, 40));

        buttonPanel.add(searchButton);
        buttonPanel.add(themeButton);

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        // --- CENTER PANEL (Results Container) ---
        resultsContainer = new JPanel();
        resultsContainer.setLayout(new BoxLayout(resultsContainer, BoxLayout.Y_AXIS));
        resultsContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

        wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(new EmptyBorder(20, 40, 40, 40));
        wrapperPanel.add(resultsContainer, BorderLayout.WEST); 

        scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Apply initial Light Theme
        applyTheme();

        // --- ACTIONS ---
        ActionListener performSearch = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeSearch();
            }
        };

        searchButton.addActionListener(performSearch);
        searchField.addActionListener(performSearch);
        
        themeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isDarkMode = !isDarkMode;
                applyTheme();
                renderResults(); // Re-draw cards with new colors
            }
        });
    }

    private void applyTheme() {
        Color bg = isDarkMode ? new Color(32, 33, 36) : Color.WHITE;
        Color fg = isDarkMode ? new Color(232, 234, 237) : new Color(32, 33, 36);
        Color searchBoxBg = isDarkMode ? new Color(48, 49, 52) : Color.WHITE;
        Color borderCol = isDarkMode ? new Color(95, 99, 104) : new Color(223, 225, 229);
        
        // Base backgrounds
        getContentPane().setBackground(bg);
        topPanel.setBackground(bg);
        wrapperPanel.setBackground(bg);
        resultsContainer.setBackground(bg);
        scrollPane.getViewport().setBackground(bg); // Fixes the scroll area background

        // Borders
        topPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, borderCol),
                BorderFactory.createEmptyBorder(25, 40, 20, 40)
        ));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderCol, 1, true),
                new EmptyBorder(8, 15, 8, 15)
        ));

        // Text & Components
        titleLabel.setForeground(isDarkMode ? new Color(138, 180, 248) : new Color(66, 133, 244));
        searchField.setBackground(searchBoxBg);
        searchField.setForeground(fg);
        searchField.setCaretColor(fg); // Cursor color
        
        // Primary Button (Search)
        searchButton.setBackground(isDarkMode ? new Color(138, 180, 248) : new Color(26, 115, 232));
        searchButton.setForeground(isDarkMode ? new Color(32, 33, 36) : Color.WHITE);
        
        // Secondary Button (Theme Toggle)
        themeButton.setText(isDarkMode ? "☀️ Light" : "🌙 Dark");
        themeButton.setBackground(searchBoxBg);
        themeButton.setForeground(fg);
    }

    private void executeSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            currentResults = null;
            resultsContainer.removeAll();
            addMessageCard("Please enter a valid search query.");
            refreshUI();
            return;
        }

        currentResults = controller.search(query);
        renderResults();
    }

    private void renderResults() {
        resultsContainer.removeAll();

        if (currentResults == null) {
            refreshUI();
            return;
        }

        if (currentResults.isEmpty()) {
            addMessageCard("No results found. Try different keywords.");
        } else {
            // Header stats
            JLabel statsHeader = new JLabel("Found " + currentResults.size() + " results");
            statsHeader.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            statsHeader.setForeground(isDarkMode ? new Color(154, 160, 166) : new Color(112, 117, 122));
            statsHeader.setBorder(new EmptyBorder(0, 0, 20, 0));
            resultsContainer.add(statsHeader);

            int rank = 1;
            for (MainController.SearchResult result : currentResults) {
                resultsContainer.add(createResultCard(result, rank));
                resultsContainer.add(Box.createRigidArea(new Dimension(0, 30)));
                rank++;
            }
        }
        
        refreshUI();
    }

    private JPanel createResultCard(MainController.SearchResult result, int rank) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(isDarkMode ? new Color(32, 33, 36) : Color.WHITE);
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Theme-aware hex colors for HTML blocks
        String titleHex = isDarkMode ? "#8ab4f8" : "#1a0bcc";
        String snippetHex = isDarkMode ? "#bdc1c6" : "#4d5156";
        Color urlColor = isDarkMode ? new Color(129, 201, 149) : new Color(32, 33, 36);
        Color metaColor = isDarkMode ? new Color(154, 160, 166) : new Color(150, 150, 150);

        // 1. URL Label
        JLabel urlLabel = new JLabel(result.page.url);
        urlLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        urlLabel.setForeground(urlColor);
        urlLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 2. Title Label (HTML)
        String titleHtml = "<html><span style='color: " + titleHex + "; font-size: 16px;'>" + result.page.title + "</span></html>";
        JLabel titleLabel = new JLabel(titleHtml);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        titleLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 3. Snippet (HTML for wrapping)
        String snippetHtml = "<html><div style='width: 600px; color: " + snippetHex + "; line-height: 1.4;'>" 
                           + result.page.content + "</div></html>";
        JLabel snippetLabel = new JLabel(snippetHtml);
        snippetLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        snippetLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 4. Engine Metadata
        String metaText = String.format("Rank: %d • Score: %.4f • Matched: %s", rank, result.score, result.matchedKeywords);
        JLabel metaLabel = new JLabel(metaText);
        metaLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        metaLabel.setForeground(metaColor);
        metaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

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
        msgLabel.setForeground(isDarkMode ? new Color(232, 234, 237) : Color.DARK_GRAY);
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