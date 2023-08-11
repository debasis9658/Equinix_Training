package problemSolving;

import java.util.Scanner;
import java.util.Arrays;

public class Multiplication {
	
	
	
   public static void addAtBeg(int[] array, int num) {
		
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
   
   
   public static void preProcessing(int[][] array) {
	   
	    int row_size = array.length;
		int col_size = array[0].length;
		
		//Add appropriate amount of zeros at the end for each row
		
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
		
		
		//Converting all arrays to same format which may or may not start with zeros.
		
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
		
		for(int i = 0; i < row_size; i++) {
			for(int j = 0; j < col_size; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
   }
   
   
   
   
   
   
	public static int[] addArray(int[][] array) {
		
		int row_size = array.length;
		int col_size = array[0].length;
		
		// Pre-processing of array for addition
		preProcessing(array);
		
		//Adding arrays to get finalArray
		
		int[] finalAns = new int[15];
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
		
		
		return finalAns;
		
	}
	
	public static int[] subtractTwoArrays(int[] array1, int[] array2) {
		
		int size1 = array1.length;
		int size2 = array2.length;
		
		if(size2 < size1) {
			int[] arr2 = new int[size1];
			Arrays.fill(arr2, -1);
			for(int i = 0; i < size2; i++) {
				arr2[i] = array2[i];
			}
			int diff = size1 - size2;
			while(diff != 0) {
				addAtBeg(arr2, 0);
				diff--;
			}
			
			array2 = arr2;
		}
		
		int[] finalAns = new int[15];
		Arrays.fill(finalAns, -1);
		
		int num = 0, borrow = 0;
		
		for(int i = size1 - 1; i >= 0; i--) {
			num = array1[i] - array2[i] - borrow;
			
			if(num < 0) {
				num += 10;
				borrow = 1;
			}
			else {
				borrow = 0;
			}
			
			addAtBeg(finalAns, num);
		}
		
		return finalAns;
		
		
	}
	
	
	
	
	
	public static int multply(int[] firstNumber, int[] secondNumber) {
		int firstArraySize = firstNumber.length;
		int secondArraySize = secondNumber.length;
		
		int[][] multi_array = new int[secondArraySize][secondArraySize + firstArraySize];

		for (int[] row : multi_array) {
		    Arrays.fill(row, -1);
		}
		
		
		
		for(int i = secondArraySize - 1; i >= 0; i--) {
			int a = secondNumber[i];
			int mult = 0, carry = 0;
			
			for(int j = firstArraySize - 1; j >= 0; j--) {
				int b = firstNumber[j];
				
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
		
		int in_num = 0;
		System.out.print("Answer in array:");
		for(int i = 0; i < ans.length; i++) {
			if(ans[i] == -1) {
				break;
			}
			System.out.print(ans[i]);
			in_num = in_num * 10 + ans[i];
		}
		System.out.println();
		return in_num;
	}
	
	
	
    public static void main(String[] args) {
        System.out.println("Hello World");
        int n, m;
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter number of digits in first number:");
        n = scanner.nextInt();
        System.out.println("Enter number of digits in second number:");
        m = scanner.nextInt();
        
        int[] firstNumber = new int[n];
        int[] secondNumber = new int[m];
        
        System.out.println("Enter digits of first integer:");
        for(int i = 0; i < n; i++) {
        	firstNumber[i] = scanner.nextInt();
        }
        
        System.out.println("Enter digits of second integer:");
        for(int i = 0; i < m; i++) {
        	secondNumber[i] = scanner.nextInt();
        }
        
        System.out.println("Answer in number: " + multply(firstNumber, secondNumber));
        
        int ans[] = subtractTwoArrays(firstNumber, secondNumber);
        
        System.out.print("Result of subtraction is: ");
        
        for(int i = 0; i < ans.length; i++) {
        	if(ans[i] == -1) {
        		break;
        	}
        	 System.out.print(ans[i]);
        }
        
        scanner.close();
    }
}