import java.io.*;
import java.util.*;

class Account {
    String name;
    String surname;
    String accountNumber;
    double balance;
    ArrayList<String> transactions;

    public Account(String name, String surname, String accountNumber, double balance, ArrayList<String> transactions) {
        this.name = name;
        this.surname = surname;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactions = transactions;
    }

    // For creating new account
    public Account(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.accountNumber = generateAccountNumber();
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    private String generateAccountNumber() {
        long number = 1000000000L + (long)(Math.random() * 9000000000L);
        return String.valueOf(number);
    }

    // Convert account to a line for file storage
    public String toFileString() {
        String tran = String.join(";", transactions);
        return accountNumber + "," + name + "," + surname + "," + balance + "," + tran;
    }

    // Parse account from file line
    public static Account fromFileString(String line) {
        String[] parts = line.split(",", 5); // split into 5 parts max
        ArrayList<String> trans = new ArrayList<>();
        if(parts.length == 5 && !parts[4].isEmpty()) {
            trans.addAll(Arrays.asList(parts[4].split(";")));
        }
        return new Account(parts[1], parts[2], parts[0], Double.parseDouble(parts[3]), trans);
    }

    public void addTransaction(String transaction) {
        transactions.add(transaction);
    }

    public void showTransactions() {
        if(transactions.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println(" " + " "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+"SA Bank");
            System.out.println("--- Transaction History ---");
            for(String t : transactions) {
                System.out.println(t);
            }
            System.out.println("---------------------------\n");
        }
    }
}

public class BankSystem {
    static final String FILE_NAME = "accounts.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Account currentAccount = null;

        System.out.println("Welcome to SA Simple Banking!\n");

        // Create or login
        while(currentAccount == null) {
            try {
                System.out.println("Do you want to:");
                System.out.println("1. Create a New Account");
                System.out.println("2. Log In");
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 1) {
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter your surname: ");
                    String surname = scanner.nextLine();

                    currentAccount = new Account(name, surname);
                    saveAccount(currentAccount);

                    System.out.println("\nAccount created successfully!");
                    System.out.println("Your account number is: " + currentAccount.accountNumber);

                } else if (choice == 2) {
                    System.out.print("Enter your account number: ");
                    String accNumber = scanner.nextLine();

                    currentAccount = login(accNumber);

                    if(currentAccount == null) {
                        System.out.println("Account number not found! Try again.\n");
                    } else {
                        System.out.println("Login successful! Welcome " + currentAccount.name + " " + currentAccount.surname);
                    }

                } else {
                    System.out.println("Invalid choice! Try again.\n");
                }
            } catch(Exception e) {
                System.out.println("Error: Invalid input. Try again.\n");
            }
        }

        // Main menu
        int menuChoice = 0;
        while(menuChoice != 5) {
            try {
                System.out.println("\nWhat would you like to do?");
                System.out.println("1. Deposit");
                System.out.println("2. Withdraw");
                System.out.println("3. Check Balance");
                System.out.println("4. Show Transaction History");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                menuChoice = Integer.parseInt(scanner.nextLine());

                switch(menuChoice) {
                    case 1:
                        System.out.print("Enter amount to deposit: ");
                        double deposit = Double.parseDouble(scanner.nextLine());
                        currentAccount.balance += deposit;
                        currentAccount.addTransaction("Deposit R" + deposit);
                        updateAccount(currentAccount);
                        System.out.println("Deposited R" + deposit);
                        break;

                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdraw = Double.parseDouble(scanner.nextLine());
                        if(withdraw <= currentAccount.balance) {
                            currentAccount.balance -= withdraw;
                            currentAccount.addTransaction("Withdraw R" + withdraw);
                            updateAccount(currentAccount);
                            System.out.println("Withdrew R" + withdraw);
                        } else {
                            System.out.println("Insufficient balance! You only have R" + currentAccount.balance);
                        }
                        break;

                    case 3:
                        System.out.println("Current balance: R" + currentAccount.balance);
                        break;

                    case 4:
                        currentAccount.showTransactions();
                        break;

                    case 5:
                        System.out.println("Thank you for using SA Simple Banking!");
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }

            } catch(Exception e) {
                System.out.println("Error: Invalid input. Please enter numbers only.");
            }
        }
    }

    // Save new account to file (append)
    private static void saveAccount(Account account) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(account.toFileString());
            bw.newLine();
        } catch(IOException e) {
            System.out.println("Error saving account: " + e.getMessage());
        }
    }

    // Login by account number only
    private static Account login(String accNumber) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while((line = br.readLine()) != null) {
                Account acc = Account.fromFileString(line);
                if(acc.accountNumber.equals(accNumber)) {
                    return acc;
                }
            }
        } catch(IOException e) {
            System.out.println("Error reading accounts: " + e.getMessage());
        }
        return null; // not found
    }

    // Update account balance and transactions in file
    private static void updateAccount(Account account) {
        try {
            File file = new File(FILE_NAME);
            List<Account> accounts = new ArrayList<>();
            // Load all accounts
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while((line = br.readLine()) != null) {
                    Account acc = Account.fromFileString(line);
                    if(acc.accountNumber.equals(account.accountNumber)) {
                        acc.balance = account.balance;
                        acc.transactions = account.transactions;
                    }
                    accounts.add(acc);
                }
            }
            // Rewrite file with updated accounts
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for(Account acc : accounts) {
                    bw.write(acc.toFileString());
                    bw.newLine();
                }
            }
        } catch(IOException e) {
            System.out.println("Error updating account: " + e.getMessage());
        }
    }
}