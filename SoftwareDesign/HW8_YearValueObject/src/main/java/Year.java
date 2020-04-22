public class Year implements Comparable<Year> {

    private final int year;

    public Year(int year) {
        this.year = year;
    }

    public Year diff(int difference) {
        return new Year(this.year + difference);
    }

    public Year decrement() {
        return new Year(this.year - 1);
    }

    public Year increment() {
        return new Year(this.year + 1);
    }

    public int year() {
        return this.year;
    }

    @Override
    public String toString() {
        return Integer.toString(this.year);
    }

    public boolean equals(Object o) {
        return (o instanceof Year) && this.equals((Year) o);
    }

    public boolean equals(Year o) {
        return this.year == o.year;
    }

    @Override
    public int compareTo(Year o){
        return Integer.compare(this.year, o.year);
    }
}
