import javax.swing.*;
import java.awt.*;

public class SortedListGUI extends JFrame {
    private final SortedList sortedList;
    private JTextField inputField;
    private JTextField searchField;
    private JTextArea logArea;

    public SortedListGUI() {
        sortedList = new SortedList();
        initComponents();
    }

    private void initComponents() {
        setTitle("Sorted List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(400, 400));

        // Input panel for adding strings
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputField = new JTextField();
        JButton addButton = new JButton("Add String");

        inputPanel.add(new JLabel("Add String: "), BorderLayout.WEST);
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");

        searchPanel.add(new JLabel("Search: "), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Combine input and search panels
        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        topPanel.add(inputPanel);
        topPanel.add(searchPanel);
        add(topPanel, BorderLayout.NORTH);

        // Log area for operations and list display
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        // Action listener for add button
        addButton.addActionListener(e -> addString());

        // Action listener for search button
        searchButton.addActionListener(e -> searchString());

        // Enter key support for input field
        inputField.addActionListener(e -> addString());

        // Enter key support for search field
        searchField.addActionListener(e -> searchString());

        pack();
        setLocationRelativeTo(null);
    }

    private void addString() {
        String input = inputField.getText().trim();
        if (!input.isEmpty()) {
            try {
                sortedList.add(input);
                logOperation(getListString());
                inputField.setText("");
                inputField.requestFocus();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Cannot add null or empty string",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a string to add",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchString() {
        String searchTerm = searchField.getText().trim();
        if (!searchTerm.isEmpty()) {
            SortedList results = sortedList.search(searchTerm);
            StringBuilder message = new StringBuilder("Search: \"" + searchTerm + "\"\n");
            if (results.size() > 0) {
                message.append("Found:\n");
                for (int i = 0; i < results.size(); i++) {
                    message.append(i).append(": ").append(results.get(i)).append("\n");
                }
            } else {
                message.append("Not found\n");
            }
            logOperation(message.toString());
            searchField.setText("");
            searchField.requestFocus();
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a search term",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logOperation(String operation) {
        logArea.setText("");
        logArea.append(operation + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength()); // Auto-scroll to bottom
    }

    private String getListString() {
        StringBuilder sb = new StringBuilder();
        if (sortedList.size() == 0) {
            sb.append("[empty]\n");
        } else {
            for (int i = 0; i < sortedList.size(); i++) {
                sb.append(i+1).append(": ").append(sortedList.get(i)).append("\n");
            }
        }
        return sb.toString();
    }
}