package problemSolving;

import java.util.Arrays;
import java.util.regex.Pattern;

public class Number { // Number class for arithmetic purpose of larger numbers
	private int[] operand;
	
	
	public Number(String numAsString) throws IllegalArgumentException {
		if(!setOperand(numAsString)) {
			//Throw an error
			throw new IllegalArgumentException("Sorry! Input doesn't follow required pattern. Your input may not be a whole number String");
		}
	}
	
	
	//Internal Helper methods
	
    /** Add an integer at the beginning of the array by shifting all 
     * the elements of array to right by one
     * @param array an integer array
     * @param num as integer
     */
	private static void addAtBeg(int[] array, int num) {
    	
		int size = array.length;
		int[] arr = array;
		
		int li = -1;
		for(int i = 0; i < size; i++) {
			if(arr[i] == -1) {
				li = i;
				break;
			}
		}
		
		while(li > 0) {
			arr[li] = arr[li - 1];
			li--;
		}
		arr[0] = num;
    }
	
    /** This function will take two arrays as input and after making it in standard format for
     * addition and subtraction.
     * 
     * @param op1 as an int array
     * @param op2 as an int array
     * @return a 2d array having standard format for two inputs 
     */
	private static int[][] make2dArray(int[] op1, int[] op2){
    	int size1 = op1.length;
    	int size2 = op2.length;
    	
    	if(size1 < size2) {
    		int[] temp = op2;
    		op2 = op1;
    		op1 = temp;
    	}
    	
    	int[] arr1 = op1, arr2;
    	
    	if(size2 < size1) {
			arr2 = new int[size1];
			Arrays.fill(arr2, -1);
			for(int i = 0; i < size2; i++) {
				arr2[i] = op2[i];
			}
			int diff = size1 - size2;
			while(diff != 0) {
				addAtBeg(arr2, 0);
				diff--;
			}
			
		}
    	else {
    		arr2 = op2;
    	}
    	
    	int[][] ans = new int[2][];
    	ans[0] = arr1;
    	ans[1] = arr2;
    	
    	return ans;
    }
	
    /** Pre-processing of arrays to bring it to a standard size by putting zeros at the beginning 
     * or ending accordingly.
     * @param A 2d array containing arrays which needed to be added
     * (2 in case of adding and it can be more in case of multiplication)
     */
    private static void preProcessing(int[][] array) {
    	int row_size = array.length;
		int col_size = array[0].length;
		
		int zero_cnt = row_size - 1;
		for(int i = 0; i < row_size; i++) {
			int cnt = zero_cnt;
			if(cnt == 0) {
				break;
			}
			for(int j = 0; j < col_size && cnt > 0; j++) {
				if(array[i][j] == -1) {
					array[i][j] = 0;
					cnt --;
				}
			}
			zero_cnt --;
		}
		
		for(int i = 0; i < row_size; i++) {
			int idx = -1;
			for(int j = 0; j < col_size; j++) {
				if(array[i][j] == -1) {
					idx = j;
					break;
				}
			}
			
			if(idx >= 0) {
				int to_shift = col_size - idx;
				int ele = idx - 1;
				
				for(int k = col_size - 1; k >= to_shift && ele >= 0; k--){
					array[i][k] = array[i][ele];
					ele--;
				}
				
				for(int k = 0; k < to_shift; k++) {
					array[i][k] = 0;
				}
				
			}
			
		}
    }	
	
    /** Internal function for addition of 2 as well as multiple number of arrays
     * @param 2d integer array 
     * @return a array after sum of arrays
     */
    private static int[] addArray(int[][] array) {
		
		int row_size = array.length;
		int col_size = array[0].length;
		
		// Pre-processing of array for addition
		preProcessing(array);
		
		//Adding arrays to get finalArray	
		
		int[] finalAns = new int[100];
		Arrays.fill(finalAns, -1);
		
		int carry = 0;
		for(int i = (col_size - 1); i >= 0; i--){
	        int sum = carry;
	        for(int j = 0; j < row_size; j++){
	            sum += array[j][i];
	        }
	        if(sum >= 10){
	        	addAtBeg(finalAns, sum % 10);
	        	carry = sum / 10;
	        }
	        else{
	        	addAtBeg(finalAns, sum);
	            carry = 0;
	        }
	    }
		if(carry > 0) {
			addAtBeg(finalAns, carry);
		}
		
		
		return finalAns;
		
	}	
	
    /** This is a helper function to help print the operand in a human-readable
     * fashion.
     * @param an integer array to be printed to the screen.
     */
    private static void printArray(int arrayToPrint[]) {
        
        System.out.print(" --> ");
        for (int i = 0; i < arrayToPrint.length; i++) {
        	if(arrayToPrint[i] == -1) {
        		System.out.print(".");
        		continue;
        	}
            System.out.print(arrayToPrint[i]);
        }
        
        System.out.println(" <-- ");
    }
    
    /** Internal function for resizing the array by removing -1
     * @param res which is an int array 
     * @return an integer array
     */
    private static int[] resize(int[] res) {
    	int size = 1;
    	for(int i = 0; i < res.length; i++) {
    		if(res[i] == -1) {
    			size = i;
    			break;
    		}
    	}
    	
    	int[] result = new int[size];
    	for(int i = 0; i < size; i++) {
    		result[i] = res[i];
    	}
    	return result;
    }
    
    /** Private helper function for addition operation
     * @param op1 as integer array operand1 
     * @param op2 as integer array operand2
     * @return an array signifying addition
     */
	private static int[] addHelper(int[] op1, int[] op2) {
		int[][] matrix = make2dArray(op1, op2);
    	int[] res = addArray(matrix);
    	
    	int[] result = resize(res);
    	System.out.print("After addition the result is: ");
    	printArray(result);
    	return result;
	}
	
	/** Private helper function for subtraction operation
     * @param op1 as integer array operand1 
     * @param op2 as integer array operand2
     * @return an array signifying subtraction
     */
	private static int[] subHelper(int[] op1, int[] op2) {
    	int[][] matrix = make2dArray(op1, op2);
    	
    	int[] firstNum = matrix[0];
    	int[] secondNum = matrix[1];
    	
    	int[] finalAns = new int[100];
    	Arrays.fill(finalAns, -1);
    	
		int num = 0, borrow = 0;
		
		for(int i = firstNum.length - 1; i >= 0; i--) {
			num = firstNum[i] - secondNum[i] - borrow;
			
			if(num < 0) {
				num += 10;
				borrow = 1;
			}
			else {
				borrow = 0;
			}
			
			addAtBeg(finalAns, num);
		}
		finalAns = resize(finalAns);
		System.out.print("After subtraction the result is: ");
		printArray(finalAns);
		return finalAns;		
	}
	
	/** Private helper function for multiplication operation
     * @param op1 as integer array operand1 
     * @param op2 as integer array operand2
     * @return an array signifying multiplication
     */
	private static int[] multHelper(int[] op1, int[] op2) {
    	int firstArraySize = op1.length;
		int secondArraySize = op2.length;
		
		int[][] multi_array = new int[secondArraySize][secondArraySize + firstArraySize];
		for (int[] row : multi_array) {
		    Arrays.fill(row, -1);
		}
		
		for(int i = secondArraySize - 1; i >= 0; i--) {
			int a = op2[i];
			int mult = 0, carry = 0;
			
			for(int j = firstArraySize - 1; j >= 0; j--) {
				int b = op1[j];
				
				if(a * b + carry >= 10){
	                mult = (a * b + carry) % 10;
	                carry = (a * b + carry) / 10;
	                addAtBeg(multi_array[i], mult);
	            }
				else {
					addAtBeg(multi_array[i], a * b + carry);
	                carry = 0;
				}
			}
			
			if(carry > 0){
				addAtBeg(multi_array[i], carry);
	        }
			
		}
		
		int[] ans = addArray(multi_array);
		ans = resize(ans);
		System.out.print("After multiplication the result is: ");
		printArray(ans);
		return ans;		
	}
	
	/** Private helper function for division operation
     * @param op1 as integer array operand1 
     * @param op2 as integer array operand2
     * @return an array signifying division
     */
	private static int[] divHelper(int[] op1, int[] op2) {
    	int firstArraySize = op1.length;
		int secondArraySize = op2.length;
		
		int divisor = 0;
		for(int i = 0; i < secondArraySize; i++) {
			divisor = divisor * 10 + op2[i];
		}
		
		int remainder = 0;
		int[] finalAns_whole = new int[100];
		int[] finalAns_decimal = new int[100];
		Arrays.fill(finalAns_whole, -1);
		Arrays.fill(finalAns_decimal, -1);
		
		int counter1 = 0;
		for(int i = 0; i < firstArraySize; i++) {
			int num = remainder * 10 + op1[i];
			
			finalAns_whole[i] = num / divisor;
			remainder = num % divisor;
			counter1++;
		}
		
		int k = 0, num = 0, counter2 = 0;
		while(remainder != 0 && k < 100) {
			num = remainder * 10;
			finalAns_decimal[k] = num / divisor;
			remainder = num % divisor;
			k++;
			counter2++;
		}
		
		int[] result = new int[counter1 + counter2 + 1];
		
		
		for(int i = 0; i < counter1; i++) {
			result[i] = finalAns_whole[i];
		}
		result[counter1] = -1;
		for(int i = 1; i <= counter2; i++) {
			result[counter1 + i] = finalAns_decimal[i - 1]; 
		}
		
		System.out.print("The division result of two operands is: ");
		printArray(result);
		return result;		
	}
	
	
	
	//Requirement methods
	
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
		int[] externalOperand = numToBeOperated.getOperand();
		int [] array = addHelper(this.operand, externalOperand);
		String result = convertArrayToString(array);
		
		Number finalAns = new Number(result);
		return finalAns;
	}
	
	/** This function gives subtraction of two Number class objects
	 * @param numToBeOperated is a Number class object which is going to be subtracted from operand
	 * @return an Number class object
	 */
	public Number sub(Number numToBeOperated) {
		int[] externalOperand = numToBeOperated.getOperand();
		int[] array = subHelper(this.operand, externalOperand);
		String result = convertArrayToString(array);
		
		Number finalAns = new Number(result);
		return finalAns;
	}
	
	/** This function gives multiplication of two Number class objects
	 * @param numToBeOperated is a Number class object which is going to be multiplied to operand
	 * @return an Number class object
	 */
	public Number mult(Number numToBeOperated) {
		int[] externalOperand = numToBeOperated.getOperand();
		int[] array = multHelper(this.operand, externalOperand);
		String result = convertArrayToString(array);
		
		Number finalAns = new Number(result);
		return finalAns;
	}
	
	/** This function prints division of two Number class objects
	 * @param numToBeOperated is a Number class object which is going to be divisor to operand
	 * @return A string after which is after division
	 */
	public String division(Number numToBeOperated) {
		int[] externalOperand = numToBeOperated.getOperand();
		int[] array = divHelper(this.operand, externalOperand);
		String result = convertArrayToString(array);
		return result;
	}
	
	/** Public function to check if the number taken in parameter is greater, less or equals to operand of this object
	 * @param numToBeOperated is a Number type object
	 * @return integers who can be [-1, 0, 1] -> -1 signifying operand is smaller, +1 signifies it is greater and 0 signifies they are same.
	 */
	public int equals(Number numToBeOperated) {
		int[] externalOperand = numToBeOperated.getOperand();
		
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
