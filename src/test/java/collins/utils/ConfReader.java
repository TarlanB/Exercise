package collins.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfReader {


    static Properties properties = new Properties();

    static {

        try {
            FileInputStream file = new FileInputStream("conf.properties");

            properties.load(file);

            file.close();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static String getProperty(String key) {
        return properties.getProperty(key);

    }


}
