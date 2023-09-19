package graphics.core;

import static org.lwjgl.opengl.GL11C.GL_FLOAT;
import static org.lwjgl.opengl.GL11C.GL_INT;
import static org.lwjgl.opengl.GL20C.*;

public class Uniform {

    public static final int MAX_NAME_LENGTH = 32;

    public int location,  size, type;
    public Class clazz;

    public Uniform(int location, int size, int type) {
        this.location = location;
        this.size = size;
        this.type = type;
    }

    public enum DataType {

        NONE,
        BOOL,
        INT, INT2, INT3, INT4,
        FLOAT, FLOAT2, FLOAT3, FLOAT4,
        MAT3, MAT4;

        public int size() {
            return switch(this) {
                case BOOL                           -> 1;
                case INT, FLOAT                     -> 4;
                case INT2, FLOAT2                   -> 4 * 2;
                case INT3, FLOAT3                   -> 4 * 3;
                case INT4, FLOAT4                   -> 4 * 4;
                case MAT3                           -> 4 * 3 * 3;
                case MAT4                           -> 4 * 4 * 4;
                default                             -> 0;
            };
            //Log.coreAssert(false, "Unknown ShaderDataType!");
        }

        public int getComponentCount() {
            return switch(this) {
                case BOOL, INT, FLOAT   -> 1;
                case INT2, FLOAT2       -> 2;
                case INT3, FLOAT3       -> 3;
                case INT4, FLOAT4       -> 4;
                case MAT3               -> 3 * 3;
                case MAT4               -> 4 * 4;
                default                 -> 0;
            };
        }

        public int toOpenGL() {
            return switch(this) {
                case BOOL                           -> GL_BOOL;
                case INT, INT2, INT3, INT4          -> GL_INT;
                case FLOAT, FLOAT2, FLOAT3, FLOAT4  -> GL_FLOAT;
                case MAT3, MAT4                     -> GL_FLOAT;
                default                             -> 0;
            };
            //Log.coreAssert(false, "Unknown ShaderDataType!");
        }

        public static Class openGLtoClass(int openGL) {
            return switch (openGL) {
                case GL_BOOL -> Boolean.class;
                case GL_SAMPLER_2D -> Integer.class;
                default -> null;
            };
        }
    }
}
