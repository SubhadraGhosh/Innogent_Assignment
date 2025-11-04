import java.math.BigInteger;

class Fact {
    public static void main(String args[]) {
        int n = 5;           // local variable → Stack
        BigInteger fact = BigInteger.ONE;       // local variable → Stack

        // Loop variable 'i' is also stored in Stack
        for(int i = 2; i <= n; i++) {
            fact = fact.multiply(BigInteger.valueOf(i));      // intermediate calculation stored in Stack
        }

        System.out.print(fact); // result printed, fact is in Stack
    }
}
