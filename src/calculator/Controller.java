package calculator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 * Responsible for connecting View and Model, sets up interactive elements
 * of GUI, allows for user input and manages errors.
 * 
 * @author Bartosz Ciuækowski
 * @version 1.0
 */
public class Controller {
	private Model model = new Model();
	private String exprToCalc = "";
	private boolean afterEquals = false;
	
	@FXML
    private Button one;
	@FXML
    private Button two;
	@FXML
    private Button three;
	@FXML
    private Button four;
	@FXML
    private Button five;
	@FXML
    private Button six;
	@FXML
    private Button seven;
	@FXML
    private Button eight;
	@FXML
    private Button nine;
	@FXML
    private Button zero;
	@FXML
    private Button point;
	@FXML
    private Button equals;
	@FXML
    private Button plus;
	@FXML
    private Button minus;
	@FXML
    private Button mult;
	@FXML
    private Button div;
	@FXML
    private Button factorial;
	@FXML
    private Button sqrt;
	@FXML
    private Button square;
	@FXML
    private Button sign;
	@FXML
    private Button percent;
	@FXML
    private Button clear;
    @FXML
    private TextField display;
       
    /**
	 * Controller class simple constructor.
	 */
    public Controller() {
    	
    }
    
    /**
	 * Sets up all buttons, upon any action they perform specified action,
	 * allows user to communicate with application.
	 */
    public void setButtons() {
    	one.setOnAction(e->setDigit("1"));
    	two.setOnAction(e->setDigit("2"));
    	three.setOnAction(e->setDigit("3"));
    	four.setOnAction(e->setDigit("4"));
    	five.setOnAction(e->setDigit("5"));
    	six.setOnAction(e->setDigit("6"));
    	seven.setOnAction(e->setDigit("7"));
    	eight.setOnAction(e->setDigit("8"));
    	nine.setOnAction(e->setDigit("9"));
    	zero.setOnAction(e->setDigit("0"));
    	point.setOnAction(e->setPoint());
    	
    	equals.setOnAction(e->clickEquals());
    	plus.setOnAction(e->setOperator("+"));
    	minus.setOnAction(e->setOperator("-"));
    	mult.setOnAction(e->setOperator("*"));
    	div.setOnAction(e->setOperator("/"));
    	
    	factorial.setOnAction(e->clickFactorial());
    	sqrt.setOnAction(e->clickSqrt());
    	square.setOnAction(e->clickSquare());
    	sign.setOnAction(e->clickSign());
    	percent.setOnAction(e->clickPercent());
    	
    	clear.setOnAction(e->clickClear());
    }
    
    /**
	 * Puts digits onto the display, also prevents from creating numbers such as
	 * "000000" or "00000.5".
	 * 
	 * @param specified digit
	 */
    private void setDigit(String dig) {
    	if(isInfinity(display)) {
    		return;
    	}
    	if(afterEquals) {
    		display.setText("");
    		afterEquals = false;
    	}
    	
    	if(display.getText().contentEquals("0") || display.getText().length() > 21) {
    		return;
    	} else {
    		display.setText(display.getText() + dig);
    	}
    }
    
    /**
	 * Puts point sign onto the display also prevents from
	 * putting multiple signs of those in one number e.g. 1.21.344.
	 */
    private void setPoint() {
    	if(isInfinity(display)) {
    		return;
    	}
    	if(!display.getText().contains(".")) {
    		if(display.getText().equals("")) {
    			display.setText(display.getText() + "0.");
    		} else {
    			display.setText(display.getText() + ".");
    		}
    	}
    }
    
    /**
	 * Gets called after pressing + - * or / signs. Checks if given	expression is valid and
	 * adds it to the main expression which later is going to be calculated (exprToCalc).
	 * 
	 * @param specified operator (+,-,*,/)
	 */
    private void setOperator(String op) {
    	if(display.getText().isEmpty()) {
    		exprError("Empty");
    		return;
    	}
    	
    	if(!isPointAtEnd(display) && !isInfinity(display)) {
   
    		afterEquals = false;
    		
    		String tmp = display.getText();
    		String regEx = "^[0.]+$";
    		
    		if(!tmp.contains(".")) {
    			tmp = tmp + "d";
    		}
    		
    		if(tmp.matches(regEx)) {
    			tmp = "0";
    			display.setText("0");
    		}
    		
    		exprToCalc = exprToCalc + tmp + op;
    		isZeroDivision(exprToCalc);
    		display.setText("");
    	}
    }

    /**
	 * Gets called after clicking equals button, checks whether current expression can be
	 * calculated and if so - calls calculate method form Model class.
	 */
    private void clickEquals() {
    	if(display.getText().isEmpty()) {
    		exprError("Empty");
    		return;
    	}
    	
    	if(!isPointAtEnd(display) && !isInfinity(display) && !exprToCalc.isEmpty()) {
    		String tmp = display.getText();
    		System.out.println(exprToCalc.concat(tmp));
    		
    		if(isZeroDivision(exprToCalc.concat(tmp))) {
    			return;
    		}
    		
    		if(!tmp.contains(".")) {
    			tmp = tmp + "d";
    		}
    		
    		tmp = model.calculate(exprToCalc + tmp);
    		
    		if(tmp.substring(tmp.length() - 2).equals(".0")) {
    			tmp = tmp.substring(0, tmp.length() - 2);
    		}
    		
    		display.setText(tmp);
    		exprToCalc = "";
    		
    		afterEquals = true;
    	}
    }
    
    /**
	 * Gets called after clicking factorial sign, checks if given number is valid for
	 * calculating a factorial (positive integers only) and if it's not too large.
	 * Then calls different method which creates a valid factorial expression
	 * and then calculates it by calling calculate method from Model class.
	 */
    private void clickFactorial() {
    	if(display.getText().isEmpty()) {
    		exprError("Empty");
    		return;
    	}
    	
    	if(!isPointAtEnd(display) && !isInfinity(display)) {
    		String tmp = display.getText();
    		
    		if(tmp.contains("-") || tmp.contains(".")) {
    			exprError("FactorialNumber");
        		return;
    		}
    		
    		int i = Integer.parseUnsignedInt(tmp);
    		if(i > 170) {
        		exprError("FactorialNumber");
        		return;
        	}
    		
    		tmp = factorialExpr(tmp);
    		
    		tmp = model.calculate(tmp);
    		if(tmp.substring(tmp.length() - 2).equals(".0")) {
    			tmp = tmp.substring(0, tmp.length() - 2);
    		}
    		
    		display.setText(tmp);
    		afterEquals = true;
    	}
    }
    
    /**
	 * Creates a valid factorial expression out of a given number
	 * e.g. changes 5 into 1*5*4*3*2.
	 * 
	 * @param number to be parsed
	 * @return valid expression
	 */
    private String factorialExpr(String number) {
    	String outcome = "";
    	number = number.replaceFirst("[.]0*$", "");
    	
    	int i = Integer.parseUnsignedInt(number);
    	
    	outcome = "1d";
    	for(; i > 1; --i) {
    		outcome += "*" + i + "d";
    	}
    	return outcome;
    }
    
    /**
	 * Gets called after clicking sqrt button, checks if the number is valid for calculation
	 * and calls calculate method from Model class.
	 */
    private void clickSqrt() {
    	if(display.getText().isEmpty()) {
    		exprError("Empty");
    		return;
    	}
    	
    	if(!isPointAtEnd(display) && !isInfinity(display)) {
    		String tmp = model.calculate("Math.sqrt(" +  display.getText() + ")");
    		
    		if(tmp.substring(tmp.length() - 2).equals(".0")) {
    			tmp = tmp.substring(0, tmp.length() - 2);
    		}
    		display.setText(tmp);
    		afterEquals = true;
    	}
    }

    /**
	 * Gets called after clicking square button, checks if the number is valid for calculation
	 * and calls calculate method from Model class.
	 */
    private void clickSquare() {
    	if(display.getText().isEmpty()) {
    		exprError("Empty");
    		return;
    	}
    	
    	if(!isPointAtEnd(display) && !isInfinity(display)) {
    		String tmp = display.getText();
    		if(!tmp.contains(".")) {
    			tmp = tmp + "d";
    			tmp = model.calculate(tmp + "*" + tmp);
    			
    			if(!tmp.substring(tmp.length() - 2).equals(".0")) {
    				display.setText(tmp);
    			} else {
    				display.setText(tmp.substring(0, tmp.length() - 2));
    			}
    		} else {
    			tmp = model.calculate(tmp + "*" + tmp);
    			if(tmp.substring(tmp.length() - 2).equals(".0")) {
        			tmp = tmp.substring(0, tmp.length() - 2);
        		}
    			display.setText(tmp);
    		}
    		afterEquals = true;
    	}
    }
    
    /**
	 * Gets called after clicking sign button, checks if the number is valid for calculation
	 * and changes its sign.
	 */
    private void clickSign() {
    	if(display.getText().isEmpty()) {
    		exprError("Empty");
    		return;
    	}
    	
    	if(!isInfinity(display)) {
    		String tmp = display.getText();
    		String regEx = "^[0.]+$";
    		
    		if(tmp.matches(regEx)) {
    			display.setText("");
    		}
    		
    		if(!display.getText().equals("0")) {
    			if(display.getText(0, 1).equals("-")) {
    				display.setText(display.getText(1, display.getText().length()));
    			} else {
    				display.setText("-" + display.getText());
    			}
    		}
    	}
    }
    
    /**
	 * TODO
	 */
    private void clickPercent() {
    	//TODO
    }

    /**
	 * Gets called after clicking AC button, clears the display and
	 * makes main expression empty.
	 */
    private void clickClear() {
    	display.setText("");
    	exprToCalc = "";
    }
    
    /**
	 * Shows alert windows when specified error or warning situation occurs,
	 * which inform users about the cause of it.
	 * 
	 * @param expression that decides which error/warning should be shown
	 */
    private void exprError(String expr) {
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error");
    	
    	if(expr.equals("ZeroDivision")) {
    		alert.setHeaderText("Dividing by 0 is forbidden");
    	} else if(expr.equals("PointAtEnd")) {
    		alert.setHeaderText("Number cannot end with point sign");
    	} else if(expr.equals("OperatorAtEnd")) {
    		alert.setHeaderText("Expression cannot end with an operator");
    	} else if(expr.contentEquals("FactorialNumber")) {
    		alert.setHeaderText("Desired number is too big to be calculated or negative/not integer");
    	} else if(expr.contentEquals("Empty")) {
    		alert.setHeaderText("Number needed before any operation");
    	} else if(expr.equals("ReachedInfinity")) {
    		Alert warning = new Alert(AlertType.WARNING);
    		warning.setHeaderText("Infinity has been reached!");
    		warning.showAndWait();
    		return;
    	} else {
    		alert.setHeaderText("Unknown error occured");
    	}
    		
    	alert.showAndWait();
    	
    }
    
    /**
	 * Checks if user is not trying to divide by 0.
	 * 
	 * @param expr is an expression to be checked
	 * @return false if there is no error
	 * 		   true if there is an error
	 */
    private boolean isZeroDivision(String expr) {
    	if(exprToCalc.length() < 3) return false;
    	
    	String regEx = ".*\\/0([^.]|$|\\.(0{4,}.*|0{1,4}([^0-9]|$))).*";
    		if(expr.matches(regEx)) {
    			exprError("ZeroDivision");
    			display.setText("");
    			exprToCalc = "";
    			return true;
    		}
    	return false;
    }
    
    /**
	 * Checks if current number doesn't end with a point sign.
	 * 
	 * @param display is given to get its text
	 * @return false if there is no error
	 * 		   true if there is an error
	 */
    private boolean isPointAtEnd(TextField display) {
    	if(display.getText().substring(display.getText().length() - 1).equals(".")) {
    		exprError("PointAtEnd");
    		return true;
    	}
    	return false;
    }
    
    /**
	 * Checks if user got to infinity
	 * 
	 * @param display is given to get its text
	 * @return false if there is no error
	 * 		   true if there is an error
	 */
    private boolean isInfinity(TextField display) {
    	if(display.getText().contains("Infinity")) {
    		exprError("ReachedInfinity");
    		
    		exprToCalc = "";
    		return true;
    	}
    	return false;
    }
    
}
