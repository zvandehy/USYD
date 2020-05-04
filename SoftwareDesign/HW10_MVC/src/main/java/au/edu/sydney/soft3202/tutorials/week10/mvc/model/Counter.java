package au.edu.sydney.soft3202.tutorials.week10.mvc.model;

public class Counter {
    private int count = 0;
    private boolean counting = false;
    private Integer ceiling = null;
    private Integer floor = null;
    private boolean reverse = false;

    public void startCounting() {
        counting = true;
        while (counting && checkLimits()) {
            try {
                Thread.sleep(1000);
                count = reverse ? count - 1 : count + 1;

                // You should replace this print with something observable so the View can handle it
                System.err.println(count);
            } catch (InterruptedException ignored) {}
        }
    }

    public void stopCounting() {
        counting = false;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public void setCeiling(Integer ceiling) {
        this.ceiling = ceiling;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public int getCount() {
        return count;
    }

    public void resetCount() {
        count = 0;
    }

    private boolean checkLimits() {
        if (null != ceiling && count >= ceiling) {
            return false;
        }
        if (null != floor && count <= floor) {
            return false;
        }

        return true;
    }
}
