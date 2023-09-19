package core.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that loads and stores the engine config from an external file.
 * @author Jonathan Salisbury
 */
public class Config {
    
    public static String DEFAULT_CONFIG_PATH;
    private static HashMap<String, String> registry = new HashMap<>();

    /**
     * Method that inits the module with the default config path.
     */
    public static void init() {
        loadFile(DEFAULT_CONFIG_PATH);
    }

    /**
     * Method to load a custom config file.
     * @param filepath filepath of the config file.
     */
    public static void loadFile(String filepath){
        Properties defaultProps = new Properties();
        try (FileInputStream in = new FileInputStream(DEFAULT_CONFIG_PATH)){
            defaultProps.load(in);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
        defaultProps.forEach((key, value) -> registry.put((String) key, (String) value));
    }

    /**
     * getter for the properties keys
     * @return Set of string containing the config keys.
     */
    public static Set<String> getKeys() {
        return registry.keySet();
    }

    /*
    public static <T> T getValue(String name, Class<T> returnType) {
        PropertyEditor editor = PropertyEditorManager.findEditor(returnType);
        editor.setAsText(registry.get(name));
        return (T) editor.getValue();
    } */

    /**
     * Method that returns a string value of a property.
     * @param name String name of the property to be obtained.
     * @return String value of the property
     */
    public static String getStringValue(String name) {
        return registry.get(name);
    }

    /**
     * Method that returns a int value of a property.
     * @param name String name of the property to be obtained.
     * @return Integer value of the property
     */
    public static int getIntValue(String name){
        return Integer.parseInt(registry.get(name));
    }

    /**
     * Method that returns a boolean value of a property.
     * @param name String name of the property to be obtained.
     * @return boolean value of the property
     */
    public static boolean getBoolValue(String name){
        return Boolean.parseBoolean(registry.get(name));
    }
}
