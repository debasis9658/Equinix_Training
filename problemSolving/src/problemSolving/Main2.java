package problemSolving;

public class Main2 {
	public static void main(String args[]) {
		Number n1 = new Number("5788398");
		Number n2 = new Number("26787");
	
		Number n3 = n1.add(n2);
		Number n4 = n1.sub(n2);
		Number n5 = n1.mult(n2);
		String demo = n1.division(n2);
		
		System.out.println(demo);
		
	}
}
