package problemSolving;

import java.util.regex.Pattern;

public class Number { // Number class for arithmetic purpose of larger numbers
	private int[] operand;
	
	public Number(String numAsString) throws IllegalArgumentException {
		if(!setOperand(numAsString)) {
			//Throw an error
			throw new IllegalArgumentException("Sorry! Input doesn't follow required pattern. Your input may not be a whole number String");
		}
	}
	
	/** internal method validates if string which is given as input follows pattern of a number or not
	 * @param str as string representing operand
	 * @return true if it follows the pattern else returns false
	 */
	private boolean isValid(String str) {
		Pattern pattern = Pattern.compile("[0-9]+");
        return pattern.matcher(str).matches();
	}
	
	/** This is a setter function which can be accessed publicly and sets the operand array
	 * @param str is a String
	 * @return true if operand is set successfully or returns false
	 */
	public boolean setOperand(String str) {
		if(isValid(str)) {
			operand = convertStringToArray(str);
			return true;
		}
		return false;
	}
	
	/** This is an internal method which converts String to array for new operations purpose
	 * @param Str is an String representing operand
	 * @return an array which represents operand
	 */
	private int[] convertStringToArray(String str) {
		int size = str.length();
		int result[] = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = Character.getNumericValue(str.charAt(i));
        }
        
        return result;
	}
	
	/** This is an internal method which converts array to String for new object instance purpose
	 * @param arr is an integer array representing operand
	 * @return a String which represents operand
	 */
	private String convertArrayToString(int[] arr) {
		if (arr == null || arr.length == 0) {
	        return "";
	    }
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == -1) {
				sb.append('.');
				continue;
			}
			sb.append(arr[i]);
		}
		return sb.toString();
	}
	/** This function is a getter function for operand where each array element is an digit.
	 * @return an integer array
	 */
	public int[] getOperand() {
		return this.operand;
	}
	
	/** this function is to print the number/operand
	 * 
	 */
	public void printNumber() {
		Number num = this;
		for(int i = 0; i < num.operand.length; i++) {
			System.out.print(num.operand[i]);
		}
		System.out.println();
	}
	
	/** This function gives addition of two Number class objects
	 * @param numToBeOperated is a Number class object which is going to be added to operand
	 * @return an Number class object
	 */
	public Number add(Number numToBeOperated) {
		int[] externalOperand = numToBeOperated.operand;
		MathLibrary helperLib = new MathLibrary();
		int[] array = helperLib.add(operand, externalOperand);
		String result = convertArrayToString(array);
		
		Number finalAns = new Number(result);
		return finalAns;
	}
	
	/** This function gives subtraction of two Number class objects
	 * @param numToBeOperated is a Number class object which is going to be subtracted from operand
	 * @return an Number class object
	 */
	public Number sub(Number numToBeOperated) {
		int[] externalOperand = numToBeOperated.operand;
		MathLibrary helperLib = new MathLibrary();
		int[] array = helperLib.sub(operand, externalOperand);
		String result = convertArrayToString(array);
		
		Number finalAns = new Number(result);
		return finalAns;
	}
	
	/** This function gives multiplication of two Number class objects
	 * @param numToBeOperated is a Number class object which is going to be multiplied to operand
	 * @return an Number class object
	 */
	public Number mult(Number numToBeOperated) {
		int[] externalOperand = numToBeOperated.operand;
		MathLibrary helperLib = new MathLibrary();
		int[] array = helperLib.multiply(operand, externalOperand);
		String result = convertArrayToString(array);
		
		Number finalAns = new Number(result);
		return finalAns;
	}
	
	/** This function prints division of two Number class objects
	 * @param numToBeOperated is a Number class object which is going to be divisor to operand
	 * @return A string after which is after division
	 */
	public String division(Number numToBeOperated) {
		int[] externalOperand = numToBeOperated.operand;
		MathLibrary helperLib = new MathLibrary();
		int[] array = helperLib.division(operand, externalOperand);
		String result = convertArrayToString(array);
		return result;
	}
	
	/** Public function to check if the number taken in parameter is greater, less or equals to operand of this object
	 * @param numToBeOperated is a Number type object
	 * @return integers who can be [-1, 0, 1] -> -1 signifying operand is smaller, +1 signifies it is greater and 0 signifies they are same.
	 */
	public int equals(Number numToBeOperated) {
		int[] externalOperand = numToBeOperated.operand;
		
		if(this.operand.length < externalOperand.length) {
			return -1;
		}
		else if(this.operand.length > externalOperand.length) {
			return 1;
		}
		else {
			int i = 0;
			
			while(i < this.operand.length) {
				if(this.operand[i] > externalOperand[i]) {
					return 1;
				}
				else if(this.operand[i] < externalOperand[i]) {
					return -1;
				}
				else {
					i++;
				}
			}
		}
		return 0;
		
	}
}
