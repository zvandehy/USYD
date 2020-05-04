package au.edu.sydney.soft3202.tutorials.week10.mvc.controller;

import au.edu.sydney.soft3202.tutorials.week10.mvc.model.Counter;
import au.edu.sydney.soft3202.tutorials.week10.mvc.view.CounterView;

public class CounterController {
    private Counter model;
    private CounterView view;

    public CounterController(Counter model, CounterView view) {
        this.model = model;
        this.view = view;
    }
}
