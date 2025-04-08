package Controllers;

import Views.CalculatorView;
import models.CalculatorModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorController implements ActionListener {
    private final CalculatorModel model;
    private final CalculatorView view;

    private double firstOperand;
    private String operator;
    private boolean isOperatorPressed;

    public CalculatorController(CalculatorModel model, CalculatorView view) {
        this.model = model;
        this.view = view;
        this.view.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Handle number input
        if (command.matches("\\d")) {
            // Clear display after operator is pressed
            if (isOperatorPressed) {
                view.updateDisplay("");
                isOperatorPressed = false;
            }
            view.updateDisplay(view.getDisplay() + command);
        }
        // Handle decimal point input
        else if (command.equals(".")) {
            String currentDisplay = view.getDisplay();

            // Check if the display already contains a decimal point
            if (!currentDisplay.contains(".")) {
                // If it's empty, start with "0."
                if (currentDisplay.equals("") || isOperatorPressed) {
                    view.updateDisplay("0.");
                    isOperatorPressed = false;
                } else {
                    // Append the decimal point if a number is already there
                    view.updateDisplay(currentDisplay + ".");
                }
            }
        }
        // Handle arithmetic operators
        else if (command.matches("[+\\-*/]")) {
            try {
                firstOperand = Double.parseDouble(view.getDisplay());
            } catch (NumberFormatException ex) {
                firstOperand = 0; // Handle potential empty display
            }
            operator = command;
            isOperatorPressed = true; // Flag that operator is pressed

            // Update the expression label to show the first operand and operator
            view.updateExpressionLabel(firstOperand + " " + operator);
        }
        // Handle backspace button
        else if (command.equals("⌫")) {
            String currentDisplay = view.getDisplay();
            if (currentDisplay.length() > 0) {
                view.updateDisplay(currentDisplay.substring(0, currentDisplay.length() - 1));
            }
        }
        // handle equal button
        else if (command.equals("=")) {
            double secondOperand = Double.parseDouble(view.getDisplay());
            try {
                double result = model.calculate(operator, firstOperand, secondOperand);
                // Remove unnecessary decimals if the result is an integer
                String resultStr = (result == (int) result) ? String.valueOf((int) result) : String.valueOf(result);

                view.updateDisplay(resultStr);

                // Update the expression label to show the full calculation
                view.updateExpressionLabel(firstOperand + " " + operator + " " + secondOperand + " =");
            } catch (ArithmeticException ex) {
                view.updateDisplay(ex.getMessage());
            }
        }
        // Handle clear button
        else if (command.equals("C")) {
            view.updateDisplay("");
            view.updateExpressionLabel("");
            firstOperand = 0;
            operator = "";
            isOperatorPressed = false;
        }
    }
}
