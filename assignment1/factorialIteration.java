class Fact {
    public static void main(String args[]) {
        int n = 5;           // local variable → Stack
        int fact = 1;        // local variable → Stack

        // Loop variable 'i' is also stored in Stack
        for(int i = 1; i <= n; i++) {
            fact *= i;       // intermediate calculation stored in Stack
        }

        System.out.print(fact); // result printed, fact is in Stack
    }
}
