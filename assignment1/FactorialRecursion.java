import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;

class Fact {

    // Recursive factorial method (stored in Method Area)


    public static BigInteger fact(int n) {
        // 'n' is a local variable → stored in Stack
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers!");
        }
        if (n > 300000) {
            // validation to prevent StackOverflowError or excessive computation
            throw new IllegalArgumentException("Input too large! Please enter a number ≤ 100000.");
        }

        BigInteger result = BigInteger.ONE; // local variable → Stack
        // Using iteration to avoid deep recursion → prevents StackOverflowError
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        // return value stored in Stack
        return result;
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter a number to find factorial: ");
            int n = sc.nextInt();   // local variable → Stack

            System.out.print(fact(n)); // method call → Stack frame created

        } catch (InputMismatchException e) {
            System.out.println(" Invalid input! Please enter an integer number.");
        } catch (IllegalArgumentException e) {
            System.out.println( e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e);
        } finally {
            sc.close(); // good practice to release system resources
        }
    }
}
