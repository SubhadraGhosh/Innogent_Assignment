import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;

class Fact {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in); // Scanner object → Heap, reference in Stack

        try {
            System.out.print("Enter a number to find factorial: ");
            int n = sc.nextInt();            // local variable → Stack

            // Validation to prevent invalid or huge inputs
            if (n < 0) {
                throw new IllegalArgumentException("Factorial is not defined for negative numbers!");
            }
            if (n > 300000) {
                throw new IllegalArgumentException("Input too large! Please enter a number ≤ 300000.");
            }

            BigInteger fact = BigInteger.ONE;       // local variable → Stack

            // Loop variable 'i' is also stored in Stack
            for (int i = 2; i <= n; i++) {
                fact = fact.multiply(BigInteger.valueOf(i));      // intermediate calculation stored in Stack
            }

            System.out.print(fact); // result printed, fact is in Stack
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter an integer number.");
        }
        catch (IllegalArgumentException e) {
            System.out.println( e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Unexpected error: " + e);
        }
        finally {
            sc.close(); // good practice to release system resources
        }
    }
}
