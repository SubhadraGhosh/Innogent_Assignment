class Fact {

    // Recursive factorial method (stored in Method Area)
    public static int fact(int n) {  
        // 'n' is a local variable → stored in Stack
        if (n == 0 || n == 1) 
            return 1; // return value stored in Stack

        return n * fact(n - 1); // recursive call → new stack frame created
    }

    public static void main(String args[]) {  
        int n = 5;               // local variable → Stack
        System.out.print(fact(n)); // method call → Stack frame created
    }
}
