package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CalculatorView extends JFrame {
    private final JTextField display;
    private final JLabel expressionLabel;
    private JButton[] numberButtons;
    private JButton additionButton, subtractButton, multiplyButton, divideButton;
    private JButton clearButton, equalButton, percentButton, backspaceButton;
    private JButton negateButton, decimalButton;

    public CalculatorView() {
        setTitle("Calculator");
        setBackground(new Color(240,240,240));
        setSize(335, 500); // Adjusted size for layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display
        expressionLabel = new JLabel(" ");  // Empty space initially
        expressionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        expressionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));  // Smaller font for the expression
        expressionLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));

        // TextField for main result
        display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        display.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        display.setPreferredSize(new Dimension(325,140));
        add(display, BorderLayout.NORTH);

        // Create a panel to hold both expressionLabel and display
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(expressionLabel, BorderLayout.NORTH); // Add expression label above the main display
        displayPanel.add(display, BorderLayout.SOUTH); // Main result display

        // Add the displayPanel to the frame
        add(displayPanel, BorderLayout.NORTH);

        // Button panel with GridLayout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 3, 3)); // 5 rows, 4 columns, spacing 2px
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 4, 4, 4)); // Margins around the buttons

        initializeButtons(buttonPanel);

        // Add the button panel to the bottom
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void initializeButtons(JPanel buttonPanel) {
        Font buttonFont = new Font("Segoe UI", Font.PLAIN, 20);

        // Create buttons
        percentButton = createButton("%", buttonFont, new Color(248,248,248));
        clearButton = createButton("C", buttonFont, new Color(248,248,248));
        backspaceButton = createButton("⌫", buttonFont, new Color(248,248,248));
        divideButton = createButton("/", buttonFont, new Color(248,248,248));

        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = createButton(String.valueOf(i), buttonFont, new Color(251,252,253));
        }

        multiplyButton = createButton("*", buttonFont, new Color(248,248,248));
        subtractButton = createButton("-", buttonFont, new Color(248,248,248));
        additionButton = createButton("+", buttonFont, new Color(248,248,248));

        negateButton = createButton("±", buttonFont, new Color(248,248,248));
        decimalButton = createButton(".", buttonFont, new Color(251,252,253));
        equalButton = createButton("=", buttonFont, new Color(0, 104, 192));

        // Add buttons to panel in GridLayout order
        buttonPanel.add(percentButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backspaceButton);
        buttonPanel.add(divideButton);

        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(multiplyButton);

        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(subtractButton);

        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(additionButton);

        buttonPanel.add(negateButton);
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(decimalButton);
        buttonPanel.add(equalButton);
    }

    private JButton createButton(String text, Font font, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setPreferredSize(new Dimension(70, 50));

        // Remove the button border and focus outline
        button.setBorder(BorderFactory.createEmptyBorder()); // Remove border
        button.setFocusPainted(false); // Remove focus border when clicked

        // Set font color only for the equal button
        if (text.equals("=")) {
            button.setForeground(Color.WHITE); // Set font color to white for equal button
        }
        return button;
    }

    private String formatNumber(double number) {
        // If it's an integer, return it without decimal places
        if (number == (int) number) {
            return String.valueOf((int) number);
        } else {
            // Otherwise, return it with decimal places
            return String.valueOf(number);
        }
    }

    // Add a method to update the expression label
    public void updateExpressionLabel(String expression) {
        try {
            // Parse and format each part of the expression
            String[] parts = expression.split(" ");
            StringBuilder formattedExpression = new StringBuilder();

            for (String part : parts) {
                try {
                    // Try to format as a number
                    double number = Double.parseDouble(part);
                    formattedExpression.append(formatNumber(number)).append(" ");
                } catch (NumberFormatException e) {
                    // If it's not a number (like operator), append as is
                    formattedExpression.append(part).append(" ");
                }
            }

            expressionLabel.setText(formattedExpression.toString().trim()); // Set the formatted expression
        } catch (Exception e) {
            expressionLabel.setText(expression); // Fallback to original expression in case of error
        }
    }

    public void updateDisplay(String value) {
        if (value.endsWith(".")) {
            // If the input ends with a decimal point, display it as is
            display.setText(value);
        } else {
            try {
                double number = Double.parseDouble(value);
                display.setText(formatNumber(number)); // Use formatted number
            } catch (NumberFormatException e) {
                display.setText(value); // Display as is if it's not a valid number
            }
        }
    }

    public String getDisplay() {
        return display.getText();
    }

    public void addActionListener(ActionListener actionListener) {
        for (JButton button : numberButtons) {
            button.addActionListener(actionListener);
        }
        additionButton.addActionListener(actionListener);
        subtractButton.addActionListener(actionListener);
        multiplyButton.addActionListener(actionListener);
        divideButton.addActionListener(actionListener);
        equalButton.addActionListener(actionListener);
        clearButton.addActionListener(actionListener);
        percentButton.addActionListener(actionListener);
        negateButton.addActionListener(actionListener);
        decimalButton.addActionListener(actionListener);
        backspaceButton.addActionListener(actionListener);
    }
}
