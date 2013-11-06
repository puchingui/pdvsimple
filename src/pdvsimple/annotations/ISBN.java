package pdvsimple.annotations;

import java.lang.annotation.*;
import org.hibernate.validator.*;
import pdvsimple.validators.*;

/**
 * 9.18 - 150 Crar tu propia anotacion de Hibernate Validator
 * @author Javier Paniza
 *
 */

@ValidatorClass(ISBNValidator.class)	//esta clase contiene la logica de validacion
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ISBN {	//una definicion de anotacion Java convencional
	
	boolean search() default true;		//para (des)activar la busqueda web al validar
	String message() default "ISBN no existe";	//mensaje si la validacion falla
	
}