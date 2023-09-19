package graphics.enums;

import static org.lwjgl.opengl.GL45C.*;

public enum PrimitiveType {
    /* 0D */
    POINTS,

    /* 1D */
    LINES,
    LINE_STRIP,

    /* 2D */
    TRIANGLES,
    TRIANGLE_STRIP,
    TRIANGLE_FAN,
    QUADS;

   public int toGLEnum() {
       return switch (this) {
           case POINTS -> GL_POINTS;
           case LINES -> GL_LINES;
           case LINE_STRIP -> GL_LINE_STRIP;
           case TRIANGLES -> GL_TRIANGLES;
           case TRIANGLE_STRIP -> GL_TRIANGLE_STRIP;
           case TRIANGLE_FAN -> GL_TRIANGLE_FAN;
           case QUADS -> GL_QUADS;
       };
   }
}
