import Controllers.CalculatorController;
import Views.CalculatorView;
import models.CalculatorModel;

public class CalculatorApp {
    public static void main(String[] args){
        CalculatorModel model = new CalculatorModel();
        CalculatorView view = new CalculatorView();
        new CalculatorController(model, view);

        view.setVisible(true);
    }
}
