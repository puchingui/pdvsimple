package pdvsimple.calculators;

import org.openxava.calculators.*;	//para usar ICalculator

import pdvsimple.util.*;			//para usar PdvsimplePreferences

@SuppressWarnings("serial")
public class PorcentajeItbisCalculator implements ICalculator {

	public Object calculate() throws Exception {
		return PdvsimplePreferences.getDefaultPorcentajeItbis();
	}
}
