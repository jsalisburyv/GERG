package system;

/**
 * Class used for measuring time
 * @author Jonathan Salisbury
 */

public class Clock {

    private long startTime;

    /**
     * Default constructor that initializes the clock
     */
    public Clock() {
        startTime = System.nanoTime();
    }

    /**
     * Function that returns an instance of time that holds the elapsed time since init or restart.
     * @return
     */
    public Time getElapsedTime() {
        long deltaTime = System.nanoTime() - startTime;
        return Time.getMicroseconds(deltaTime / 1000L);
    }


    /**
     * Function that restarts the clock and returns the last deltatime.
     * @return Time instance containing elapsed time since init or last restart.
     */
    public Time restart() {
        Time deltaTime  = getElapsedTime();
        startTime = System.nanoTime();
        return deltaTime;
    }
}
