package graphics.core;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import static org.lwjgl.opengl.GL45C.*;


public class Shader {
    
    private static final Logger LOGGER = Logger.getLogger(Shader.class.getSimpleName());
    private static final HashMap<String, Integer> uniformLocationCache = new HashMap<>();
    private static final HashMap<String, Uniform> uniforms = new HashMap<>();
    
    private int rendererID;
    
    public Shader(String filepath) {
        List<String> source = readFile(filepath);
        Map<Integer, String> shaderSources = preProcess(source);
        compile(shaderSources);
        getUniforms();
        LOGGER.info("Shader: %s Compiled Successfully".formatted(filepath));
    }
    
    public void bind() {
        glUseProgram(rendererID);
    }
    
    public void unbind() {
        glUseProgram(0);
    }
    
    public void dispose() { 
        glDeleteProgram(rendererID);
    }
    
    /* Private Methods */
    private List<String> readFile(String filepath) {
        List<String> result = null;
        try {
             result = Files.readAllLines(Paths.get(filepath));
        } catch (IOException ex) {
            LOGGER.severe(ex.toString());
        }
        return result;
    }
    
    private Map<Integer, String> preProcess(final List<String> source) {
        HashMap<Integer, String> shaderSources = new HashMap<>();
        final String typeToken = "#type";
        String src = new String();
        int type = -1;
        boolean isFirst = true;
        for(String line: source) {
            if(line.contains(typeToken)) {
                if(!isFirst) shaderSources.put(type, src); 
                int pos = line.indexOf(typeToken);
                type = shaderTypeFromString(line.substring(pos + typeToken.length() + 1));
                isFirst = false;
                src = "";
            } else {
                src += line + "\n";
            }
        }
        shaderSources.put(type, src);
        return shaderSources;
    }
    
    private void compile(Map<Integer, String> shaderSources) {
        int program = glCreateProgram(), i = 0;
        int[] shaderID = new int[shaderSources.size()];
        for (Map.Entry<Integer, String> entry : shaderSources.entrySet()) {
            int shader = glCreateShader(entry.getKey());
            shaderID[i++] = shader;
            glShaderSource(shader, entry.getValue());
            glCompileShader(shader);
            if(glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
                String infoLog = glGetShaderInfoLog(shader);
                glDeleteShader(shader);
                LOGGER.severe("Shader compilation failure! \n  %s".formatted(infoLog));
                break;
            }
            glAttachShader(program, shader);
        }
        glLinkProgram(program);
        if(glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            String infoLog = glGetProgramInfoLog(program);
            LOGGER.severe("Shader Linking failure! \n  %s".formatted(infoLog));
            glDeleteProgram(program);
            for(int shader: shaderID) glDeleteShader(shader);
            return;
        }
        for(int shader: shaderID) glDetachShader(program, shader);
        this.rendererID = program;
    }

    private void getUniforms() {
        int[] count = new int[1];
        glGetProgramiv(rendererID, GL_ACTIVE_UNIFORMS, count);
        for (int i = 0; i < count[0]; i++) {
            int[] length = new int[1], size = new int[1], type = new int[1];
            ByteBuffer name = BufferUtils.createByteBuffer(Uniform.MAX_NAME_LENGTH);
            glGetActiveUniform(rendererID, i, length, size, type, name);
            String nameStr = StandardCharsets.UTF_8.decode(name).toString().substring(0, length[0]);
            int location = getUniformLocation(nameStr);
            Uniform uniform = new Uniform(location, size[0], type[0]);
            uniforms.put(nameStr, uniform);
        }
    }
    
    private static int shaderTypeFromString(final String type) {
        return switch(type) {
            case "vertex" -> GL_VERTEX_SHADER;
            case "fragment", "pixel" -> GL_FRAGMENT_SHADER;
            default -> -1;
        };
    }
    
    /* Uniforms */
    public void setInt(String name, int value){
        if(uniforms.containsKey(name)){
            Uniform uniform = uniforms.get(name);
            glUniform1i(uniform.location, value);
        }
        int location = getUniformLocation(name);
        glUniform1i(location, value);
    }

    public void setMatrix4f(String name, Matrix4f value){
        float[] matrixArray = new float[16];
        value.get(matrixArray);
        if(uniforms.containsKey(name)){
            Uniform uniform = uniforms.get(name);
            glUniformMatrix4fv(uniform.location, false, matrixArray);
            return;
        }
        int location = getUniformLocation(name);
        glUniformMatrix4fv(location, false, matrixArray);
    }

    private int getUniformLocation(String name) {
        if(!uniformLocationCache.containsKey(name)){
            int location = glGetUniformLocation(rendererID, name);
            if(location == -1) {
                LOGGER.severe("Uniform '" + name + "' doesn't exist");
            } else {
                uniformLocationCache.put(name, location);
            }
            return location;
        }
        return uniformLocationCache.get(name);
    }
}
