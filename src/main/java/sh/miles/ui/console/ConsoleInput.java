package sh.miles.ui.console;

import java.util.Scanner;

public class ConsoleInput {

    private final Scanner scanner;

    public ConsoleInput(final Scanner scanner) {
        this.scanner = scanner;
    }

    public String getString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int getInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for a integer required input please try again");
            return getInt(prompt);
        }
    }

    public double getDouble(String prompt) {
        System.out.print(prompt);
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for a double required input please try again");
            return getDouble(prompt);
        }
    }

    public boolean getBoolean(String prompt) {
        System.out.print(prompt);
        try {
            return Boolean.parseBoolean(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for a boolean required input please try again");
            return getBoolean(prompt);
        }
    }

    public String getOption(String prompt, String... options) {
        System.out.println(prompt);
        System.out.println("========================================");
        for (String option : options) {
            System.out.println(option);
        }
        System.out.println("========================================");
        String input = scanner.nextLine();
        for (String option : options) {
            if (option.equals(input)) {
                return input;
            }
        }
        System.out.println("Invalid input for a option required input please try again");
        return getOption(prompt, options);
    }

    /**
     * Assigns a number starting at 1 to each option and returns the number of the
     * option selected
     * minus 1
     * 
     * @param prompt  The prompt to display
     * @param options The options to display
     * @return The number of the option selected
     */
    public int getNumberOption(String prompt, String... options) {

        System.out.println(prompt);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        int input = getInt("Please select an option: ");
        if (input > 0 && input <= options.length) {
            return input - 1;
        }

        return getNumberOption(prompt, options);
    }

    public void mimicClear() {
        for(int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    public void waitForInput(String prompt) {
        System.out.print(prompt);
        scanner.nextLine();
    }

    public Scanner getScanner() {
        return scanner;
    }

}
