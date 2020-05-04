package au.edu.sydney.soft3202.tutorials.week10.mvc;

import au.edu.sydney.soft3202.tutorials.week10.mvc.controller.CounterController;
import au.edu.sydney.soft3202.tutorials.week10.mvc.model.Counter;
import au.edu.sydney.soft3202.tutorials.week10.mvc.view.CounterView;

public class MainDriver {
    public static void main(String[] args) {
        Counter model  = new Counter();
        CounterView view = new CounterView(model);
        CounterController controller = new CounterController(model, view);

        view.launch();

        model.setCeiling(10); // Don't start it without a ceiling/floor to hit until you have threading...
        model.startCounting();
    }
}
