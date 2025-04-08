package models;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class CalculatorModel {
    private final Map<String, BiFunction<Double, Double, Double>> operations;

    public CalculatorModel(){
        operations = new HashMap<>();
        addDefaultOperations();
    }

    private void addDefaultOperations(){
        addOperation(Operators.ADD, Double::sum);
        addOperation(Operators.SUBTRACT, (a, b) -> {
            return a - b;
        });
        addOperation(Operators.MULTIPLY, (a, b) -> a * b);
        addOperation(Operators.DIVIDE, (a, b) -> {
            if (b != 0) return a / b;
            else throw new ArithmeticException("Cannot divide by zero");
        });
    }

    public void addOperation(String operator, BiFunction<Double, Double, Double> operation){
        operations.put(operator, operation);
    }

    public double calculate(String operator, double operand1, double operand2){
        if(operations.containsKey(operator)){
            return operations.get(operator).apply(operand1, operand2);
        } else {
            throw new IllegalArgumentException("Invalid operator");
        }
    }
}