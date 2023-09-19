package graphics.enums;

import static org.lwjgl.opengl.GL45C.*;

public enum BufferUsage {
    STREAM,
    DYNAMIC,
    STATIC;

    public int toGLEnum() {
        return switch (this) {
            case STREAM -> GL_STREAM_DRAW;
            case DYNAMIC -> GL_DYNAMIC_DRAW;
            case STATIC -> GL_STATIC_DRAW;
        };
    }


}
