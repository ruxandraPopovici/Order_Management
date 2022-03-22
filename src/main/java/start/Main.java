package start;

import model.Model;
import presentation.Controller;
import presentation.View;

public class Main {
    public static void main(String[] args)
    {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(view, model);
        view.setVisible(true);
    }
}
