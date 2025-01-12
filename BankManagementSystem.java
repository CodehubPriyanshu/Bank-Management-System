package personalProject;
import java.util.*;
import java.util.regex.*;
import javax.swing.JFrame;

// Class representing a Bank Account
class Account {
    private String accountNumber;
    private String accountHolderName;
    private double checkingBalance;
    private double savingsBalance;
    private String password;
    private String email;
    private String address;
    private String phoneNum;

    public Account(String accountNumber, String accountHolderName, double checkingBalance, double savingsBalance, String password, String email, String address, String phoneNum) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.checkingBalance = checkingBalance;
        this.savingsBalance = savingsBalance;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public String getAccountDetails() {
    	System.out.println("\n");
        return "<----ACCOUNT DETAILS---->\n" +
                "Account Number: " + accountNumber + "\n" +
                "Account Holder: " + accountHolderName + "\n" +
                "Checking Balance: " + checkingBalance + "$\n" +
                "Savings Balance: " + savingsBalance + "$\n" +
                "Email: " + email + "\n" +
                "Address: " + address + "\n" +
                "Phone Number: " + phoneNum + "\n";
    }

    public void deposit(double amount, String accountType) {
        if (amount > 0) {
            if (accountType.equalsIgnoreCase("checking")) {
                checkingBalance += amount;
                System.out.println("Deposited: " + amount + "$ to Checking\nNew Checking Balance: " + checkingBalance + "$\n");
            } else if (accountType.equalsIgnoreCase("savings")) {
                savingsBalance += amount;
                System.out.println("Deposited: " + amount + "$ to Savings\nNew Savings Balance: " + savingsBalance + "$\n");
            } else {
                System.out.println("Invalid account type. Please choose Checking or Savings.");
            }
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public boolean withdraw(double amount, String accountType) {
        if (amount > 0) {
            if (accountType.equalsIgnoreCase("checking") && checkingBalance >= amount) {
                checkingBalance -= amount;
                System.out.println("Withdrawn: " + amount + "$ from Checking\nNew Checking Balance: " + checkingBalance + "$\n");
                return true;
            } else if (accountType.equalsIgnoreCase("savings") && savingsBalance >= amount) {
                savingsBalance -= amount;
                System.out.println("Withdrawn: " + amount + "$ from Savings\nNew Savings Balance: " + savingsBalance + "$\n");
                return true;
            } else {
                System.out.println("Invalid withdrawal amount or insufficient funds in " + accountType + ".");
                return false;
            }
        } else {
            System.out.println("Invalid withdrawal amount.");
            return false;
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

	public Object getAccountNumber() {
		return accountNumber;
	}
	
	public String getUserName() {
        return accountHolderName;
    }
	
	public void setName( String userName ) {
        this.accountHolderName = userName ;
    }
	
	public void setPassword(String password) {
        this.password = password ;
    }
    public void setEmail(String email) {
        this.email = email ;
    }
    public void setAdress(String address) {
        this.address = address ;
    }

	public void setPhoneNumber(String phoneNum) {
		this.phoneNum = phoneNum ; 	
	}
}

// Class to manage a list of accounts
class BankSystem {
    private List<Account> accounts;

    public BankSystem() {
        accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
        System.out.println("\nAccount added successfully: \n" + account.getAccountDetails());
    }

    public Account login(String email, String password) {
        for (Account account : accounts) {
            if (account.getEmail().equals(email) && account.getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }

    public boolean accountIsAvailable(String email) {
        for (Account account : accounts) {
            if (account.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
    
    public Account findAccountByNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}

public class BankManagementSystem {
    public static void main(String[] args) {
        BankSystem bankSystem = new BankSystem();
        Scanner scanner = new Scanner(System.in);
        String option ;
        Integer  option5 = null ; 

        do {
            System.out.println("\nWelcome to the Bank Management System!");
            System.out.println("--------------------------------------");
            System.out.println("1. Create an Account");
            System.out.println("2. Log in to your account");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            option = scanner.nextLine();

            switch (option) {
                case "1":
                    String email;
                    do {
                        System.out.print("Enter your email: ");
                        email = scanner.nextLine();
                        if (!isValidEmail(email)) {
                            System.out.println("Invalid email format. Please enter a valid email (e.g., @gmail.com, @uwindsor.ca).\n");
                            continue;
                        }
                        if (bankSystem.accountIsAvailable(email)) {
                            System.out.println("Account is already available!\n");
                            continue;
                        }
                        break;
                    } while (true);

                    System.out.print("Enter Account Number: ");
                    String accountNumber;
                    while (true) {
                        accountNumber = scanner.nextLine();
                        if (isValidAccountNumber(accountNumber)) {
                            break;
                        } else {
                            System.out.println("Invalid account number. Please enter a 10-digit number.");
                        }
                    }
                    System.out.print("Enter Account Holder Name: ");
                    String accountHolderName = scanner.nextLine();
                    System.out.print("Enter Initial Checking Balance: ");
                    double checkingBalance = getDepositWithdrawAmount();
                    System.out.print("Enter Initial Savings Balance: ");
                    double savingsBalance = getDepositWithdrawAmount();

                    String password = getPassword();
                    String address = getAddress();
                    String phoneNum = getPhoneNumber();

                    bankSystem.addAccount(new Account(accountNumber, accountHolderName, checkingBalance, savingsBalance, password, email, address, phoneNum));
                    break;

                case "2":
                    System.out.print("Enter Email: ");
                    String loginEmail = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String loginPassword = scanner.nextLine();
                    Account loggedInAccount = bankSystem.login(loginEmail, loginPassword);

                    if (loggedInAccount != null) {
                        System.out.println("\nLogin successful.\n Welcome " + loggedInAccount.getUserName());
                        String userOption; 
                        do {
                            System.out.println("\nAvailable Features:");
                            System.out.println("1. View Account Details");
                            System.out.println("2. Deposit Money");
                            System.out.println("3. Withdraw Money");
                            System.out.println("4. Transfer Money");
                            System.out.println("5. Access User Account");
                            System.out.println("6. Logout");
                            System.out.print("Choose an option: ");
                            userOption = scanner.nextLine();

                            switch (userOption) {
                                case "1":
                                    System.out.println(loggedInAccount.getAccountDetails());
                                    break;
                                case "2":
                                    System.out.print("Enter Amount to Deposit: ");
                                    double depositAmount = getDepositWithdrawAmount() ; 
                                    System.out.print("Deposit to (Checking/Savings): ");
                                    String depositAccountType;
                                    while (true) {
                                        depositAccountType = scanner.nextLine();
                                        if (isValidAccountType(depositAccountType)) {
                                            break;
                                        } else {
                                            System.out.println("Invalid account type. Please enter 'Checking' or 'Savings'.");
                                        }
                                    }
                                    loggedInAccount.deposit(depositAmount, depositAccountType);
                                    break;
                                case "3":
                                    System.out.print("Enter Amount to Withdraw: ");
                                    double withdrawAmount = getDepositWithdrawAmount() ; 
                                    System.out.print("Withdraw from (Checking/Savings): ");
                                    String withdrawAccountType;
                                    while (true) {
                                        withdrawAccountType = scanner.nextLine();
                                        if (isValidAccountType(withdrawAccountType)) {
                                            break;
                                        } else {
                                            System.out.println("Invalid account type. Please enter 'Checking' or 'Savings'.");
                                        }
                                    }
                                    loggedInAccount.withdraw(withdrawAmount, withdrawAccountType);
                                    break;
                                case "4":
                                	String sourceAccountType;
                                    while (true) {
                                        System.out.print("Transfer from which account (Checking/Savings): ");
                                        sourceAccountType = scanner.nextLine();
                                        if (isValidAccountType(sourceAccountType)) {
                                            break;
                                        } else {
                                            System.out.println("Invalid account type. Please enter 'Checking' or 'Savings'.");
                                        }
                                    }                                    
                                    System.out.print("Enter recipient's account number: ");
                                    String recipientAccountNumber = scanner.nextLine();
                                    Account recipientAccount = bankSystem.findAccountByNumber(recipientAccountNumber);

                                    if (recipientAccount == null) {
                                        System.out.println("Recipient account not found. Please check the account number.");
                                        break;
                                    }

                                    System.out.print("Enter amount to transfer: ");
                                    double transferAmount = getDepositWithdrawAmount();

                                    if (loggedInAccount.withdraw(transferAmount, sourceAccountType)) {
                                        recipientAccount.deposit(transferAmount, sourceAccountType);
                                        System.out.println("Transfer successful! Transferred " + transferAmount + "$ to account " + recipientAccountNumber);
                                    } else {
                                        System.out.println("Insufficient funds. Transfer failed.");
                                    }
                                    break;                    
                                case "5":                           
                                	do {
                                        System.out.println("\nAccount Information Section. Here you can manage your Account.");
                                        System.out.println("---------------------------------------------------------------");
                                        System.out.println("1. Change Account's Name.");
                                        System.out.println("2. Change Account's Address.");
                                        System.out.println("3. Change Account's Password.");
                                        System.out.println("4. Change Account's Phone Number.");
                                        System.out.println("5. Exit.");
                                        Scanner scanner5 = new Scanner(System.in);
                                        String optionTemp = scanner.nextLine();
                                        if (!isNumeric(optionTemp)) {
                                            System.out.println("Please enter a valid option! Pick from 1 to 9.");
                                            continue;
                                        }
                                        option5 = Integer.valueOf(optionTemp);
                                        switch (option5) {
                                            case 1:
                                                String newUserName = getUserName(); // getting the new userName
                                                loggedInAccount.setName(newUserName);
                                                System.out.println("User Name Has been changed to " + newUserName + ".");
                                                break;
                                            case 2:
                                                String newAddress = getAddress();
                                                loggedInAccount.setAdress(newAddress);
                                                System.out.println("Address Has been changed to " + newAddress + ".");
                                                break;
                                            case 3:
                                                String newPassword = getPassword();
                                                loggedInAccount.setPassword(newPassword);
                                                System.out.println("Password Has been changed to " + newPassword + ".");
                                                break;
                                            case 4:
                                                String newPhoneNumber = getPhoneNumber();
                                                loggedInAccount.setPhoneNumber(newPhoneNumber);
                                                System.out.println("Phone Number Has been changed to " + newPhoneNumber + ".");
                                                break;                                      
                                            case 5:
                                                System.out.println("Exiting...\n");
                                                break;                                           
                                            default:
                                                System.out.println("Invalid Input. Please try again.");
                                        }
                                    } while (option5 != 5);
                                	break;
                                case "6":
                                    System.out.println("Logging out...");
                                    break;	
                                default:
                                    System.out.println("Invalid option. Please try again.");
                            }
                        } while (!userOption.equals("6"));
                    } else {
                        System.out.println("Invalid email or password.");
                    }
                    break;

                case "3":
                    System.out.println("Exiting the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (!option.equals("3"));
    }

	public static String getPhoneNumber() {
        do {
            System.out.printf("\nPlease enter your phone number in one of the following formats:%n");
            System.out.printf("1. (123) 456-7890%n");
            System.out.printf("2. 123-456-7890%n");
            System.out.printf("3. 123.456.7890%n");
            System.out.printf("4. 1234567890%n");
            System.out.print("Phone number: ");
            Scanner scanner = new Scanner(System.in);
            String phoneNum = scanner.nextLine();
            if (!isValidPhoneNumber(phoneNum)) {
                System.out.println("Invalid Phone Number! Please enter numbers only.");
                continue;
            } else {
                return phoneNum;
            }
        } while (true);
    }

    public static String getAddress() {
        Scanner scanner = new Scanner(System.in);
        String address;
        String addressPattern = "^\\d{1,5} [A-Za-z]+(?: [A-Za-z]+)*, [A-Za-z]+(?: [A-Za-z]+)*$";
        Pattern pattern = Pattern.compile(addressPattern);

        while (true) {
            System.out.print("Enter your address (e.g., 1406 StreetName, CityName): ");
            address = scanner.nextLine();
            Matcher matcher = pattern.matcher(address);
            if (matcher.matches()) {
                return address;
            } else {
                System.out.println("Invalid address format. Please use the format: 1406 StreetName, CityName.");
            }
        }
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-\\.]+@((gmail\\.com)|(uwindsor\\.ca))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^(\\(\\d{3}\\) \\d{3}-\\d{4}|\\d{3}-\\d{3}-\\d{4}|\\d{10}|\\d{3}\\.\\d{3}\\.\\d{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
    
    public static boolean isValidAccountNumber(String accountNumber) {
        return accountNumber.matches("\\d{10}");
    }
    
    public static boolean isValidAccountType(String accountType) {
        return accountType.equalsIgnoreCase("checking") || accountType.equalsIgnoreCase("savings");
    }
    
    public static double getDepositWithdrawAmount() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String amount = scanner.nextLine();
            try {
                // Convert the input to a double
                double amountToAdd = Double.parseDouble(amount);
                // Check if the amount is positive
                if (amountToAdd < 0) {
                    System.out.println("Invalid input! Enter a positive value.");
                    continue;
                }
                // Return the valid amount
                return amountToAdd;
            } catch (NumberFormatException e) {
                // Handle invalid number format
                System.out.println("Invalid input. Please enter a valid decimal number.");
            }
        }
    }
    
    public static String getPassword() {
        Scanner scanner = new Scanner(System.in);
        String password;
        while (true) {
            System.out.print("Enter your password: ");
            password = scanner.nextLine();
            if (isValidPassword(password)) {
                break;
            } else {
                System.out.println("Password must be at least 8 characters long, include at least one uppercase letter, one lowercase letter, one digit, and one special character.");
            }
        }
        return password;
    }

    public static boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordPattern);
    }
    
    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }// end of isNumeric Method
    
    public static String getUserName() {
        do{
            System.out.print("Enter Your User Name:"); // getting the name
            Scanner scanner = new Scanner(System.in) ;
            String userName =  scanner.nextLine() ;
            if ( isNumeric(userName)) {
                System.out.println("Invalid Name!");
                continue;
            }else {return userName;}
        }while( true );
    }

}
