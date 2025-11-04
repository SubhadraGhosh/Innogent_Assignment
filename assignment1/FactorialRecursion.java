
import java.math.BigInteger;
class Fact {

    // Recursive factorial method (stored in Method Area)
    public static BigInteger  fact(int n) {
        // 'n' is a local variable → stored in Stack
        if (n == 0 || n == 1) 
            return BigInteger.ONE;// return value stored in Stack

        return BigInteger.valueOf(n).multiply(fact(n - 1)); // recursive call → new stack frame created
    }

    public static void main(String args[]) {  
        int n = 1000;               // local variable → Stack
        System.out.print(fact(n)); // method call → Stack frame created
    }
}
