import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LCSGui extends JFrame {
    private JTextField inputField1, inputField2;
    private JTextArea resultArea, table1Area, table2Area;

    public LCSGui() {
        setTitle("Longest Common Subsequence Finder");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));

        inputPanel.add(new JLabel("String 1:"));
        inputField1 = new JTextField();
        inputPanel.add(inputField1);

        inputPanel.add(new JLabel("String 2:"));
        inputField2 = new JTextField();
        inputPanel.add(inputField2);

        JButton calculateButton = new JButton("Calculate LCS");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateLCS();
            }
        });
        inputPanel.add(calculateButton);

        add(inputPanel, BorderLayout.NORTH);

        JPanel resultPanel = new JPanel(new GridLayout(1, 3));

        table1Area = new JTextArea();
        table1Area.setEditable(false);
        JScrollPane table1ScrollPane = new JScrollPane(table1Area);
        resultPanel.add(table1ScrollPane);

        table2Area = new JTextArea();
        table2Area.setEditable(false);
        JScrollPane table2ScrollPane = new JScrollPane(table2Area);
        resultPanel.add(table2ScrollPane);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);
        resultPanel.add(resultScrollPane);

        add(resultPanel, BorderLayout.CENTER);
    }

    private void calculateLCS() {
        String str1 = inputField1.getText();
        String str2 = inputField2.getText();

        int[][] table1 = new int[str1.length() + 1][str2.length() + 1];
        int[][] table2 = new int[str1.length() + 1][str2.length() + 1];

        // Call the LCS algorithm function and populate table1 and table2
        int length = computeLCS(str1, str2, table1, table2);

        // Display tables in JTextAreas
        displayTable(table1Area, table1);
        displayTable(table2Area, table2);

        // Construct and display the LCS result
        String lcs = constructLCS(str1, table2);
        resultArea.setText("Length of LCS: " + length + "\nLongest Common Subsequence: " + lcs);
    }

    private int computeLCS(String str1, String str2, int[][] table1, int[][] table2) {
        // Implement your LCS algorithm using dynamic programming here
        // Populate table1 and table2
        int m = str1.length();
        int n = str2.length();

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0) {
                    table1[i][j] = 0;
                } else if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    table1[i][j] = table1[i - 1][j - 1] + 1;
                    table2[i][j] = 1; // Diagonal arrow
                } else {
                    if (table1[i - 1][j] >= table1[i][j - 1]) {
                        table1[i][j] = table1[i - 1][j];
                        table2[i][j] = 2; // Up arrow
                    } else {
                        table1[i][j] = table1[i][j - 1];
                        table2[i][j] = 3; // Left arrow
                    }
                }
            }
        }

        return table1[m][n];
    }

    private void displayTable(JTextArea textArea, int[][] table) {
        textArea.setText("");
        for (int[] row : table) {
            for (int value : row) {
                textArea.append(value + "\t");
            }
            textArea.append("\n");
        }
    }

    private String constructLCS(String str, int[][] table) {
        StringBuilder lcs = new StringBuilder();
        int i = table.length - 1;
        int j = table[0].length - 1;

        while (i > 0 && j > 0) {
            if (table[i][j] == 1) {
                lcs.insert(0, str.charAt(i - 1));
                i--;
                j--;
            } else if (table[i][j] == 2) {
                i--;
            } else {
                j--;
            }
        }

        return lcs.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LCSGui().setVisible(true);
            }
        });
    }
}
