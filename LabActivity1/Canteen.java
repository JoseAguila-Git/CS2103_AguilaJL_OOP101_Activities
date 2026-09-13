import java.util.Scanner;

public class Canteen {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Menu data
        String[] itemNames = {"Burger", "Pizza", "Pasta", "Sandwich", "Milk Tea"};
        double[] itemPrices = {80.00, 120.00, 100.00, 70.00, 90.00};
        int menuSize = itemNames.length;

        // Running totals for the whole transaction
        int totalQuantityPurchased = 0;
        double totalAmountBeforeDeductions = 0.0;
        double totalDeduction = 0.0;
        double finalAmountToPay = 0.0;

        displayMenu(itemNames, itemPrices);

        char orderAgain = 'Y';

        while (orderAgain == 'Y' || orderAgain == 'y') {

            System.out.print("Enter item number: ");
            int itemNumber = readInt(scanner);

            System.out.print("Enter quantity: ");
            int quantity = readInt(scanner);

            boolean validItem = (itemNumber >= 1 && itemNumber <= menuSize);
            boolean validQuantity = (quantity >= 1 && quantity <= 10);

            if (!validItem || !validQuantity) {
                // Invalid order: skip straight to the next attempt, no student
                // prompt and no purchase computation for this entry
                System.out.println();
                System.out.println("Invalid order! Please enter a valid item and quantity.");
                System.out.println();
            } else {
                System.out.print("Are you a student? (Y/N): ");
                char studentAnswer = readChar(scanner);
                boolean isStudent = (studentAnswer == 'Y' || studentAnswer == 'y');

                double price = itemPrices[itemNumber - 1];
                double subtotal = price * quantity;

                double discountRate;
                if (isStudent && subtotal >= 500.0) {
                    discountRate = 0.15;
                } else if (subtotal >= 500.0) {
                    discountRate = 0.05;
                } else if (isStudent) {
                    discountRate = 0.10;
                } else {
                    discountRate = 0.0;
                }

                double discount = subtotal * discountRate;
                double orderTotal = subtotal - discount;

                // Update running totals
                totalQuantityPurchased += quantity;
                totalAmountBeforeDeductions += subtotal;
                totalDeduction += discount;
                finalAmountToPay += orderTotal;

                System.out.println();
                System.out.printf("Subtotal: $%.2f%n", subtotal);
                System.out.printf("Discount: $%.2f%n", discount);
                System.out.printf("Order total: $%.2f%n", orderTotal);
                System.out.println();
            }

            System.out.print("Do you want to order again? (Y/N): ");
            orderAgain = readChar(scanner);
            System.out.println();
        }

        // Final summary
        System.out.println("===== ORDER SUMMARY =====");
        System.out.println("Total quantity of items purchased: " + totalQuantityPurchased);
        System.out.printf("Total amount before deductions: $%.2f%n", totalAmountBeforeDeductions);
        System.out.printf("Total deduction: $%.2f%n", totalDeduction);
        System.out.printf("Final amount to pay: $%.2f%n", finalAmountToPay);
        System.out.println("Thank you for ordering!");

        scanner.close();
    }

    // Prints the canteen menu
    private static void displayMenu(String[] itemNames, double[] itemPrices) {
        System.out.println("=====  M E N U  =====");
        for (int i = 0; i < itemNames.length; i++) {
            System.out.printf("%d. %-10s - $%.2f%n", i + 1, itemNames[i], itemPrices[i]);
        }
        System.out.println();
    }

    // Reads an integer safely; non-numeric input is treated as invalid (-1)
    private static int readInt(Scanner scanner) {
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Reads the first character of a line (Y/N answers); blank input treated as 'N'
    private static char readChar(Scanner scanner) {
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return 'N';
        }
        return input.charAt(0);
    }
}