package pdvsimple.validators;

import org.hibernate.validator.*;
import org.openxava.util.*;

import pdvsimple.annotations.*;

public class ISBNValidator implements Validator<ISBN> {

	private static org.apache.commons.validator.ISBNValidator 
		validator =		//de commons validator
			new org.apache.commons.validator.ISBNValidator();
	
	public void initialize(ISBN isbn) {
	}

	public boolean isValid(Object value) {	//contiene la logica de validacion
		if (Is.empty(value)) return true;
		return validator.isValid(value.toString());	//usa commons validator
	}
}
