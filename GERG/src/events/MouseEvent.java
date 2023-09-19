package events;

/**
 * Abstract class that represent a Mouse event.
 * @author Jonathan Salisbury
 */
public abstract class MouseEvent extends Event {

    /**
     * Abstract class that represent a MouseButton event.
     * @author Jonathan Salisbury
     */
    public static abstract class MouseButtonEvent extends MouseEvent {

        protected int button;

        /**
         * Default constructor
         * @param button indentifier of the button that caused the event
         */
        public MouseButtonEvent(int button) {
            this.button = button;
        }

        /**
         * Method that returns the identifier of the button that caused the event
         * @return
         */
        public int getButton() {
            return this.button;
        }

        /**
         * Abstract class that represent a MouseButton pressing event.
         * @author Jonathan Salisbury
         */
        public static class MouseButtonPressedEvent extends MouseButtonEvent {

            /**
             * Default constructor
             * @param button indentifier of the button that caused the event
             */
            public MouseButtonPressedEvent(int button) {
                super(button);
            }

            /**
             * Interface for callbacks
             */
            public static interface MouseButtonPressedCallback {
                public void call(MouseButtonPressedEvent event);
            }
        }

        /**
         * Abstract class that represent a MouseButton releasing event.
         * @author Jonathan Salisbury
         */
        public static class MouseButtonReleasedEvent extends MouseButtonEvent {

            /**
             * Default constructor
             * @param button indentifier of the button that caused the event
             */
            public MouseButtonReleasedEvent(int button) {
                super(button);
            }

            /**
             * Interface for callbacks
             */
            public static interface MouseButtonReleasedCallback {
                public void call(MouseButtonReleasedEvent event);
            }
        }
    }

    /**
     * Abstract class that represent a Mouse scrolling event.
     * @author Jonathan Salisbury
     */
    public static class MouseScrolledEvent extends MouseEvent {

        private float xOffset, yOffset;

        /**
         * Default constructor
         * @param xOffset amount of movement in the xAxis
         * @param yOffset amount of movement in the yAxis
         */
        public MouseScrolledEvent(float xOffset, float yOffset) {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
        }

        /**
         * Method that returns the offset of the xAxis
         * @return amount of movement in the xAxis
         */
        public float getXOffset() {
            return this.xOffset;
        }

        /**
         * Method that returns the offset of the yAxis
         * @return amount of movement in the yAxis
         */
        public float getYOffset() {
            return this.yOffset;
        }

        /**
         * Interface for callbacks
         */
        public static interface MouseScrolledCallback {
            public void call(MouseScrolledEvent event);
        }
    }

    /**
     * Abstract class that represent a Mouse moving event.
     * @author Jonathan Salisbury
     */
    public static class MouseMovedEvent extends MouseEvent {

        private float xPos, yPos;

        /**
         * Default constructor
         * @param xPos new xAxis position
         * @param yPos new yAxis position
         */
        public MouseMovedEvent(float xPos, float yPos) {
            this.xPos = xPos;
            this.yPos = yPos;
        }

        /**
         * Method that returns the new position of the mouse
         * @return Float rray contiaing the new coordinates of the mouse
         */
        public float[] getPos() {
            return new float[] {xPos, yPos};
        }

        /**
         * Method for obtaining the xAxis position
         * @return new xAxis position
         */
        public float getX() {
            return this.xPos;
        }

        /**
         * Method for obtaining the yAxis position
         * @return new yAxis position
         */
        public float getY() {
            return this.yPos;
        }

        /**
         * Interface for callbacks
         */
        public static interface MouseMovedCallback {
            public void call(MouseMovedEvent event);
        }
    }
}
