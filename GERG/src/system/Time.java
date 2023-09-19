package system;

/**
 * Class that holds time information
 * @author Jonathan Salisbury
 */

public final strictfp class Time implements Comparable<Time> {

    /* Constants */
    public static final Time ZERO = new Time(0);

    /* Variables */
    private final long microseconds;

    /* Static Methods */
    public static Time getSeconds(float seconds) {
        return new Time((long) (seconds * 1000000.0f));
    }

    public static Time getMilliseconds(long milliseconds) {
        return new Time(milliseconds * 1000);
    }

    public static Time getMicroseconds(long microseconds) {
        return new Time(microseconds);
    }

    public static Time add(Time a, Time b) {
        return new Time(a.microseconds + b.microseconds);
    }

    public static Time sub(Time a, Time b) {
        return new Time(a.microseconds - b.microseconds);
    }

    public static Time mul(Time a, float s) {
        return new Time((long) (s * (float) a.microseconds));
    }

    public static Time div(Time a, float s) {
        return new Time((long) ((float) a.microseconds / s));
    }

    public static float div(Time a, Time b) {
        return a.asSeconds() / b.asSeconds();
    }

    public static Time mod(Time a, Time b) {
        return new Time(a.microseconds % b.microseconds);
    }

    public static float ratio(Time a, Time b) {
        return (float) a.microseconds / (float) b.microseconds;
    }

    private Time(long microseconds) {
        this.microseconds = microseconds;
    }

    public float asSeconds() {
        return (float) microseconds / 1000000.0f;
    }

    public long asMilliseconds() {
        return microseconds / 1000;
    }

    public long asMicroseconds() {
        return microseconds;
    }

    @Override
    public int compareTo(Time t) {
        return ((Long) microseconds).compareTo(t.microseconds);
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Time && ((Time) o).microseconds == microseconds);
    }

    @Override
    public int hashCode() {
        return (int) (microseconds ^ (microseconds >>> 32));
    }

    @Override
    public String toString() {
        return "Time{" +
                "microseconds=" + microseconds +
                '}';
    }
}
