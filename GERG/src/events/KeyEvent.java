package events;

/**
 * Abstract class that represent a Key event.
 * @author Jonathan Salisbury
 */
public abstract class KeyEvent extends Event {

    protected int keyCode;

    /**
     * Default constructor
     * @param keyCode id of the key that caused the event.
     */
    protected KeyEvent(int keyCode) {
        this.keyCode = keyCode;
    }

    /**
     * Method for obtaining the id of the key that caused the event.
     * @return integer id of the key
     */
    public int getKeycode() {
        return this.keyCode;
    }

    /**
     * Abstract class that represent a Key Pressing event.
     * @author Jonathan Salisbury
     */
    public static class KeyPressedEvent extends KeyEvent {

        private final int repeatCount;

        /**
         * Default constructor
         * @param keyCode id of the key that caused the event.
         * @param repeatCount 0 for normal keypress, 1 for maintained keypress
         */
        public KeyPressedEvent(int keyCode, int repeatCount) {
            super(keyCode);
            this.repeatCount = repeatCount;
        }

        /**
         * Method for checking if its a repeated keypress
         * @return 0 for normal keypress, 1 for maintained keypress
         */
        public int getRepeatCount() {
            return this.repeatCount;
        }

        /**
         * Interface for callbacks
         */
        public static interface KeyPressedCallback {
            public void call(KeyPressedEvent event);
        }
    }

    /**
     * Abstract class that represent a Key Releasing event.
     * @author Jonathan Salisbury
     */
    public static class KeyReleasedEvent extends KeyEvent {

        /**
         * Default constructor
         * @param keyCode id of the key that caused the event.
         */
        public KeyReleasedEvent(int keyCode) {
            super(keyCode);
        }

        /**
         * Interface for callbacks
         */
        public static interface KeyReleasedCallback {
            public void call(KeyReleasedEvent event);
        }
    }
}
