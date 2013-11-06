package pdvsimple.validators;

import org.apache.commons.logging.*;
import org.hibernate.validator.*;
import org.openxava.util.*;

import pdvsimple.annotations.*;

import com.gargoylesoftware.htmlunit.*;		//para usar HtmlUnit
import com.gargoylesoftware.htmlunit.html.*;	//para usar HtmlUnit

public class ISBNValidator implements Validator<ISBN> {

	private static Log log = LogFactory.getLog(ISBNValidator.class);
	private static org.apache.commons.validator.ISBNValidator 
		validator =		//de commons validator
			new org.apache.commons.validator.ISBNValidator();
	private boolean search;		//almacena la opcion search
	
	public void initialize(ISBN isbn) {
		this.search = isbn.search();
	}

	public boolean isValid(Object value) {
		if (Is.empty(value)) return true;
		if (!validator.isValid(value.toString())) return false;
		return search ? isbnExists(value) : true;	//aqui hacemos la llamada REST
	}
	
	private boolean isbnExists(Object isbn) {
		try {
			WebClient client = new WebClient();
			//llamamos a bookfinder4u con la URL para buscar por ISBN
			HtmlPage page = (HtmlPage) client.getPage(
				"http://www.bookfinder4u.com/" +
				"IsbnSearch.aspx?isbn=" +
				isbn + "&mode=direct");
			log.warn(page.asText());
			return page.asText()		//comprueba si la pagina resultante contiene el ISBN buscado
					.indexOf("ISBN: " + isbn) >= 0;
					
		}
		catch (Exception ex) {
			log.warn("Imposible conectarse con Bookfinder4u para validar el ISBN. Validacion fallida", ex);
			return false;	//si hay algun error asumimos que la validacion ha fallado
		}
	}

}
