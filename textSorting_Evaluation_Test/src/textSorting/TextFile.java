package textSorting;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.*;
import java.io.*;

public class TextFile {
	
	//private file which needs to be accessed on each operation.
	private File file;
	//Internal arrayList of Strings for operations
	private ArrayList<String> stringArray;   
	
	/** Constructor which creates instance of TextFile class object
	 * 
	 */
	public TextFile(String fileName) {
		file = new File(fileName);
		stringArray =  new ArrayList<String>();
		return;
	}
	
	/** Private method checks if the argument is valid in terms of our requirement or not.
	 * @param str as any String input
	 * @return a boolean value if we succeed in validating the input
	 * @throws IllegalArgumentException if input is not of required format
	 */
	private static boolean isValid(String str) throws IllegalArgumentException {
		String regex = "^[^\\s]*$"; // This regex matches strings that do not contain any whitespace characters.

        if (Pattern.matches(regex, str)) {
            return true;
        }
        return false;
	}
	
	/** Taking a string and pushing it to our text file for operation purpose
	 * @param input as String
	 * @return a boolean value indicating success of this operation
	 */
	public boolean create(String input) {
		if(isValid(input)) {
			stringArray.add(input);
			Collections.sort(stringArray);
			writeToFile();
			return true;
		}
		else {
			throw new IllegalArgumentException("Sorry! Input doesn't follow required pattern. Your input might have space character");
		}
		// Why can't I add return false here?
	}
	
	/** Public method to read all the elements inside the arrayList directly without accessing the file
	 * 
	 */
	public void readArray() {
		System.out.println("Elements are: ");
		for(int i = 0; i < this.stringArray.size(); i++) {
			System.out.print(stringArray.get(i) + " ");
		}
		System.out.println();
	}
	
	/** Internal method to write all the string elements in the arrayList to the File
	 * 
	 */
	private void writeToFile() {
		
		
		try {
		      FileWriter myWriter = new FileWriter(this.file);
		      for(int i = 0; i < this.stringArray.size(); i++) {
		    	  myWriter.write(stringArray.get(i) + " ");
		      }
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
		return;
	}

	/** Updating a particular string with another keeping the objective of library intact
	 * @param str1 as String to be updated
	 * @param str2 as String is new String
	 * @return boolean value indicating success of this method
	 * @throws IllegalArgumentException if taken argument is not in required format
	 */
	public boolean update(String str1, String str2) throws IllegalArgumentException {
		if(!isValid(str1)) {
			throw new IllegalArgumentException("Sorry! Input doesn't follow required pattern. Your input might have space character");
		}
		int position = stringArray.indexOf(str1);
		if(position == -1) {
			throw new IllegalArgumentException("Sorry string to be updated is not present in File");
		}
		else {
			stringArray.remove(position);
		}
		boolean temp = create(str2);
		if(temp == false) {
			return false;
		}
//		writeToFile();
		return true;
		
	}
	
	/** Deletion of a particular string keeping the objective of library intact
	 * @param str as String
	 * @return boolean value indicating success of this method
	 * @throws IllegalArgumentException if taken argument is not in required format
	 */
	public boolean delete(String str) throws IllegalArgumentException  {
		int position = stringArray.indexOf(str);
		if(position == -1) {
			throw new IllegalArgumentException("Sorry string to be deleted is not present in File");
		}
		stringArray.remove(position);
		writeToFile();
		return true;
	}
	
	/** Method used for reading file
	 * 
	 */
	public void readFile() {
		
		try (FileInputStream inputStream = new FileInputStream(this.file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            System.out.println("Output stream from file " + this.file + " are: ");
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                String content = new String(buffer, 0, bytesRead);
                System.out.println(content); // Print the content of the buffer
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
	
	
	public static void main(String args[]) {
		TextFile t1 = new TextFile("dictionary2.txt");
		t1.create("abc");
		t1.readArray();
		t1.readFile();
		t1.create("acd");
		t1.readArray();
		t1.readFile();
		t1.create("aabc");
		t1.readArray();
		t1.readFile();
		t1.update("abc",  "zxy");
		t1.readArray();
		t1.readFile();
		t1.create("leets");
		t1.create("aaa");
		t1.readFile();
		t1.delete("leets");
		t1.readFile();
		
		TextFile t2 = new TextFile("dictionary3.txt");
		t2.create("demo1");
		t2.create("demo2");
		
		t1.readFile();
		t2.readFile();
	}
	
}



