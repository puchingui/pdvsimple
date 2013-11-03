package pdvsimple.util;

import java.io.*;
import java.math.*;
import java.util.*;

import org.apache.commons.logging.*;
import org.openxava.util.*;

// 8.17 - 127
public class PdvsimplePreferences {

	private final static String FILE_PROPERTIES = "pdvsimple.properties";
	private static Log log = LogFactory.getLog(PdvsimplePreferences.class);
	private static Properties properties;	//almacenamo las propiedades aqui
	
	private static Properties getProperties() {
		if(properties == null) {	//usamos inicializacion vaga
			PropertiesReader reader = 	//PropertiesReader es de OpenXava
					new PropertiesReader(PdvsimplePreferences.class,
							FILE_PROPERTIES);
			try {
				properties = reader.get();
			}
			catch (IOException ex) {
				//para leer un mensaje i18n
				log.error(XavaResources.getString("properties_file_error", FILE_PROPERTIES), ex);
				properties = new Properties();
			}
		}
		return properties;
	}
	
	/***
	 * El unico metodo publico
	 * @return
	 */
	public static BigDecimal getDefaultPorcentajeItbis() {
		return new BigDecimal(getProperties().getProperty(
				"defaultPorcentajeItbis"));
	}
}
