package problemSolving;

public class Main {
    
    public static void main(String[] args) {

        MathLibrary myMathLib = new MathLibrary();
        myMathLib.setOperand1("512");
        myMathLib.setOperand2("7");
        myMathLib.printArray(myMathLib.getOperand1());
        myMathLib.printArray(myMathLib.getOperand2());
        
        
        int[] sum = myMathLib.add(myMathLib.getOperand1(), myMathLib.getOperand2());
        int[] diff = myMathLib.sub(myMathLib.getOperand1(), myMathLib.getOperand2());
        int[] prod = myMathLib.multiply(myMathLib.getOperand1(), myMathLib.getOperand2());
        int[] div = myMathLib.division(myMathLib.getOperand1(), myMathLib.getOperand2());
        
        /*
        myMathLib.setOperand2();
        
        int[] sum = myMathLib.add(myMathLib.getOperand1(), myMathLib.getOperand2());
        int[] diff = myMathLib.sub(myMathLib.getOperand1(), myMathLib.getOperand2());
        int[] prod = myMathLib.multiply(myMathLib.getOperand1(), myMathLib.getOperand2());
        
        // challenge, div.. 100 digits after the decimal and stop.
        
        
        ------------> grand evolution...
        
        Number, 
        n1 = new Number("54"),
        n2 = new Number("45"), 
        n1.print()
        
        operation 
        n3 = n1.add(n2)
        n4 = n2.add(n1)
        
        n5 = n1.mult(n2)
        
        n3.equals(n4) // int -1, 0, 1
        
        n5.print("The product is")
        :> The prouct is 77.
        
        
        */
        
    }
}
