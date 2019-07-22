package Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UtilsLoadconfig {
private static final String configPath= "./Config/config.properties";


public static String getConfig(String key) throws IOException {
	//initialize
	Properties prop = new Properties();
	InputStream input;
	input = new FileInputStream(configPath);
	
	//load a properties file
	prop.load(input);
	
	//return 
	return prop.getProperty(key);
}
} 
