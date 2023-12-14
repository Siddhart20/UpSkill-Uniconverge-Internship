import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class User {
    private String name;
    private String address;
    private String contactInfo;
    private String accountNumber;
    private double balance;
    private List<String> transactionHistory;

    public User(String name, String address, String contactInfo, double initialDeposit) {
        this.name = name;
        this.address = address;
        this.contactInfo = contactInfo;
        this.accountNumber = generateAccountNumber();
        this.balance = initialDeposit;
        this.transactionHistory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposit: +" + amount);
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add("Withdraw: -" + amount);
            return true;
        }
        return false;
    }

    public void transfer(User receiver, double amount) {
        if (withdraw(amount)) {
            receiver.deposit(amount);
            transactionHistory.add("Transfer to " + receiver.getAccountNumber() + ": -" + amount);
            receiver.transactionHistory.add("Transfer from " + getAccountNumber() + ": +" + amount);
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction History for Account " + accountNumber);
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    private String generateAccountNumber() {
        int accountNumber = (int) (Math.random() * 10000);
        return String.format("%04d", accountNumber);
    }
}

class Bank {
    private List<User> users;

    public Bank() {
        this.users = new ArrayList<>();
    }

    public void registerUser(String name, String address, String contactInfo, double initialDeposit) {
        User user = new User(name, address, contactInfo, initialDeposit);
        users.add(user);
        System.out.println("Registration successful! Your account number is: " + user.getAccountNumber());
    }

    public User login(String accountNumber) {
        for (User user : users) {
            if (user.getAccountNumber().equals(accountNumber)) {
                return user;
            }
        }
        return null;
    }
}

public class BankingSystemPrototype {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        User currentUser = null;

        while (!exit) {
            System.out.println("==== Banking Information System ====");
            System.out.println("1. Register User");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter your address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter your contact information: ");
                    String contactInfo = scanner.nextLine();
                    System.out.print("Enter initial deposit amount: ");
                    double initialDeposit = scanner.nextDouble();
                    bank.registerUser(name, address, contactInfo, initialDeposit);
                    break;

                case 2:
                    System.out.print("Enter your account number: ");
                    String accountNumber = scanner.nextLine();
                    currentUser = bank.login(accountNumber);
                    if (currentUser != null) {
                        System.out.println("Welcome, " + currentUser.getName() + "!");
                        performBankingOperations(currentUser, scanner, bank);
                    } else {
                        System.out.println("Invalid account number!");
                    }
                    break;

                case 3:
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }

        scanner.close();
    }

    public static void performBankingOperations(User user, Scanner scanner, Bank bank) {
        boolean logout = false;

        while (!logout) {
            System.out.println("\n==== Banking Operations ====");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Fund Transfer");
            System.out.println("4. Account Statement");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter the amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    user.deposit(depositAmount);
                    System.out.println("Deposit successful! Current balance: " + user.getBalance());
                    break;

                case 2:
                    System.out.print("Enter the amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    if (user.withdraw(withdrawAmount)) {
                        System.out.println("Withdrawal successful! Current balance: " + user.getBalance());
                    } else {
                        System.out.println("Insufficient funds!");
                    }
                    break;

                case 3:
                    System.out.print("Enter the account number of the receiver: ");
                    String receiverAccountNumber = scanner.nextLine();
                    User receiver = bank.login(receiverAccountNumber);
                    if (receiver != null) {
                        System.out.print("Enter the amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        user.transfer(receiver, transferAmount);
                    } else {
                        System.out.println("Invalid receiver account number!");
                    }
                    break;

                case 4:
                    user.displayTransactionHistory();
                    break;

                case 5:
                    logout = true;
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}