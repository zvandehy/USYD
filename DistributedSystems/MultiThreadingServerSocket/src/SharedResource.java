import java.util.ArrayList;

public class SharedResource {
    ArrayList<Integer> shared;

    public SharedResource() {
        this.shared = new ArrayList<>();
    }

    public ArrayList<Integer> getShared() {
        return shared;
    }

    public void increment() {
        shared.add(shared.size() + 1);
    }
}
