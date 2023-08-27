import java.util.*;

class User {
    private String username;
    private String password;
    private String accountNumber;
    private double balance;

    private String contact;
    private String address;
    private double ideposit; //initial deposit amount
    private List<String> transactionHistory;

    public User(String username, String password,String contact,String address,double ideposit) {
        this.username = username;
        this.password = password;

        this.contact = contact;
        this.address = address;
        this.ideposit=ideposit;
        this.accountNumber = generateAccountNumber();
        this.balance = 0.0;
        balance+=ideposit;
        this.transactionHistory = new ArrayList<>();
    }

    private String generateAccountNumber() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    public String getUsername() {
        return username;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposit: " + amount);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrawal: " + amount);
        } else {
            transactionHistory.add("Failed Withdrawal: Insufficient balance");
        }
    }

    public void transfer(User recipient, double amount) {
        if (amount <= balance) {
            balance -= amount;
            recipient.balance += amount;
            transactionHistory.add("Transfer to " + recipient.getAccountNumber() + ": " + amount);
            recipient.transactionHistory.add("Transfer from " + getAccountNumber() + ": " + amount);
        } else {
            transactionHistory.add("Failed Transfer: Insufficient balance");
        }
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History for Account Number " + accountNumber + ":");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    public void displayDetails() {
        System.out.println("Name : "+getUsername());
        System.out.println("Contact Number : "+getContact());
        System.out.println("Address : "+getAddress());
        System.out.println("Account Number : "+getAccountNumber());
        System.out.println("Balance : "+getBalance());
       
        }
    }


public class BankingInformationSystem {
    private static Map<String, User> users = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to the Banking System!");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter contact number: ");
        String contact = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter initial deposit amount: ");
        Double depositi = scanner.nextDouble();
        System.out.print("Current Balance: "+depositi);

        if (!users.containsKey(username)) {
            User newUser = new User(username, password,contact,address,depositi);
            users.put(username, newUser);
            System.out.println("User registered successfully with account number: " + newUser.getAccountNumber());
        } else {
            System.out.println("Username already exists. Choose a different username.");
        }
    }

    public static void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            performBankingOperations(user);
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    public static void performBankingOperations(User user) {
        while (true) {
            System.out.println("\nWelcome, " + user.getUsername() + "!");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. View Transaction History");
            System.out.println("5. Display User details");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    user.deposit(depositAmount);
                    System.out.println("Deposit successful. Current balance: " + user.getBalance());
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawalAmount = scanner.nextDouble();
                    user.withdraw(withdrawalAmount);
                    System.out.println("Withdrawal successful. Current balance: " + user.getBalance());
                    break;
                case 3:
                    System.out.print("Enter recipient's username: ");
                    String recipientUsername = scanner.nextLine();
                    User recipient = users.get(recipientUsername);
                    if (recipient != null) {
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        user.transfer(recipient, transferAmount);
                        System.out.println("Transfer successful. Current balance: " + user.getBalance());
                    } else {
                        System.out.println("Recipient not found.");
                    }
                    break;
                case 4:
                    user.printTransactionHistory();
                    break;
                case 5:
                    user.displayDetails();
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}