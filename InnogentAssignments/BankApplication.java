//  Interface for Account Operations 
interface AccountOperations {
    void deposit(double amount);
    void withdraw(double amount);
    void displayAccountDetails();
}

//  Transaction Class (Encapsulation) 
class Transaction {
    private String type;      // e.g., DEPOSIT, WITHDRAW, OPEN
    private double amount;    // Transaction amount

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

//  Abstract Class (Abstraction + Base for Inheritance) 
abstract class BankAccount implements AccountOperations {
    private String accountNumber;
    private String accountHolder;
    protected double balance;
    private Transaction[] transactions;
    private int transactionCount;

    public BankAccount(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.transactions = new Transaction[100]; // Max 100 transactions
        this.transactionCount = 0;
        recordTransaction("OPEN", initialBalance);
    }

    // Record every transaction
    protected void recordTransaction(String type, double amount) {
        if (transactionCount < transactions.length) {
            transactions[transactionCount++] = new Transaction(type, amount);
        }
    }

    // Display all transactions
    public void displayTransactions() {
        System.out.println("Transactions:");
        for (int i = 0; i < transactionCount; i++) {
            Transaction t = transactions[i];
            System.out.println("  " + t.getType() + " | " + t.getAmount());
        }
    }

    // Common display method
    @Override
    public void displayAccountDetails() {
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Account Number: " + accountNumber);
        System.out.printf("Current Balance: %.2f%n", balance);
        displayTransactions();
        System.out.println("--------------------------------");
    }

    // Abstract withdraw - behavior varies by account type
    @Override
    public abstract void withdraw(double amount);

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            recordTransaction("DEPOSIT", amount);
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }
}

// SavingsAccount (Inheritance + Polymorphism) 
class SavingsAccount extends BankAccount {
    private static final double MIN_BALANCE = 500.0;

    public SavingsAccount(String accountNumber, String accountHolder, double initialBalance) {
        super(accountNumber, accountHolder, initialBalance);
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && balance - amount >= MIN_BALANCE) {
            balance -= amount;
            recordTransaction("WITHDRAW", -amount);
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Error: Withdrawal would breach minimum balance of " + MIN_BALANCE);
        }
    }
}

//  CurrentAccount (Inheritance + Polymorphism) 
class CurrentAccount extends BankAccount {
    private static final double OVERDRAFT_LIMIT = -10000.0;

    public CurrentAccount(String accountNumber, String accountHolder, double initialBalance) {
        super(accountNumber, accountHolder, initialBalance);
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && (balance - amount) >= OVERDRAFT_LIMIT) {
            balance -= amount;
            recordTransaction("WITHDRAW", -amount);
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Error: Overdraft limit exceeded");
        }
    }
}

//  TransactionService (Dependency Inversion Principle) 
class TransactionService {
    private final AccountOperations account;

    public TransactionService(AccountOperations account) {
        this.account = account;
    }

    public void performDeposit(double amount) {
        account.deposit(amount);
    }

    public void performWithdraw(double amount) {
        account.withdraw(amount);
    }

    public void showDetails() {
        account.displayAccountDetails();
    }
}

//  Main Application 
public class BankApplication {
    public static void main(String[] args) {
        // Creating accounts
        AccountOperations savings = new SavingsAccount("S001", "Subhadra", 2000.0);
        AccountOperations current = new CurrentAccount("C001", "Ghosh", 5000.0);

        // Services (Dependency Inversion applied)
        TransactionService savingsService = new TransactionService(savings);
        TransactionService currentService = new TransactionService(current);

        // Savings Account Operations
        System.out.println(" Savings Account ");
        savingsService.performDeposit(1000.0);
        savingsService.performWithdraw(1200.0);
        savingsService.performWithdraw(1800.0); // This should fail
        savingsService.showDetails();

        // Current Account Operations
        System.out.println(" Current Account ");
        currentService.performWithdraw(14000.0);
        currentService.showDetails();
    }
}
