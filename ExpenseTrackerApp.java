import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Expense {
    private String category;
    private String description;
    private double amount;

    public Expense(String category, String description, double amount) {
        this.category = category;
        this.description = description;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }
}

class ExpenseTracker {
    private Map<String, List<Expense>> expensesByCategory;

    public ExpenseTracker() {
        expensesByCategory = new HashMap<>();
    }

    public void addExpense(String category, String description, double amount) {
        Expense expense = new Expense(category, description, amount);
        if (expensesByCategory.containsKey(category)) {
            expensesByCategory.get(category).add(expense);
        } else {
            List<Expense> expenses = new ArrayList<>();
            expenses.add(expense);
            expensesByCategory.put(category, expenses);
        }
        System.out.println("Expense added successfully!");
    }

    public void viewExpensesByCategory(String category) {
        if (expensesByCategory.containsKey(category)) {
            List<Expense> expenses = expensesByCategory.get(category);
            System.out.println("Expenses in category " + category + ":");
            for (Expense expense : expenses) {
                System.out.println("Description: " + expense.getDescription() +
                                   ", Amount: " + expense.getAmount());
            }
        } else {
            System.out.println("No expenses found in category " + category);
        }
    }

    public void viewSpendingSummary() {
        System.out.println("Spending Summary:");
        for (String category : expensesByCategory.keySet()) {
            double totalAmount = 0;
            List<Expense> expenses = expensesByCategory.get(category);
            for (Expense expense : expenses) {
                totalAmount += expense.getAmount();
            }
            System.out.println("Category: " + category + ", Total Amount: " + totalAmount);
        }
    }
}

public class ExpenseTrackerApp {
    public static void main(String[] args) {
        ExpenseTracker expenseTracker = new ExpenseTracker();
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        while (!exit) {
            System.out.println("==== Expense Tracker ====");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses by Category");
            System.out.println("3. View Spending Summary");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline character
                    expenseTracker.addExpense(category, description, amount);
                    break;

                case 2:
                    System.out.print("Enter category: ");
                    category = scanner.nextLine();
                    expenseTracker.viewExpensesByCategory(category);
                    break;

                case 3:
                    expenseTracker.viewSpendingSummary();
                    break;

                case 4:
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }

        scanner.close();
    }
}
