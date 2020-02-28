package calculator;

import jdk.jshell.*;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 * Responsible for doing the calculations
 * 
 * @author Bartosz Ciuækowski
 * @version 1.0
 */
public class Model {
	
	/**
	 * Model class simple constructor
	 */
	public Model() {

	}
	
	/**
	 * Does all calculations
	 * 
	 * @param String type expression which gets calculated using JShell
	 * 		  e.g. "2+3/9" or "0.5/2*100+7-12"
	 * @return value of calculated expression
	 */
	public String calculate(String arg) {
		JShell jshell = JShell.create();
		try (jshell) {
			List<SnippetEvent> events = jshell.eval(arg);	//eval a String type expression e.g. "9/2+1.5"
			for (SnippetEvent e : events) {
				if (e.causeSnippet() == null) {
					switch (e.status()) {
						case VALID:
							if (e.value() != null) {
								System.out.printf("%s = %s\n", arg, e.value());
								arg = e.value();	
							}
							break;
						default:
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error Dialog");
							alert.setHeaderText("Uknown Calculation ERROR");
							break;
					} 
				} 
			} 
		}
		return arg;
	}


	
	
	
	
	
	
	
	/*
	final static double ROOT_PRECISION = 0.000000001;
	
	public Model() {
    	System.out.println("8");

	}
	
	public double addition(double a, double b) {
		return a + b;
	}
	
	public double substraction(double a, double b) {
		return a - b;
	}
	
	public double multiplication(double a, double b) {
		return a * b;
	}
	
	public double division(double a, double b) {
		return a / b;
	}
	
	static public double power(double base, double n) {
		double result = 1.0;
		
		for (int i = 0; i < n; i++) {
			result *= base;
		}
		
		return result;
	}

	static public double root(double base, double n) {
		double result = Math.pow(Math.E, Math.log(base)/n);	
		
		// a way to get rid of precision errors e.g. sqrt(4)==1.99999... 
		if (Math.ceil(result) - result < ROOT_PRECISION) {
			result = Math.ceil(result);
		}
		
		return result;	
	}

	static public double factorial(double a) { 
		double result = 1;	//might use BigInteger class instead of long/double type
		
		for (double i = a; i > 0; i--, a--) {
			result *= a;
		}
		
		return result;
	}
	
	public double percent(double a, double b) {
		// TODO
		return 0;
	}
	
	public double sign(double a) {
		return -a;
	}
	*/
}
