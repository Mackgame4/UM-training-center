import java.util.Scanner;

import Classes.Terminal;
import Classes.Utilizador;

public class Main {
    public static void main(String[] args) {
        System.out.println(Terminal.ANSI_CLEAR); // Clear system console
        // Show login page
        showLogin();
    }

    public static void showLogin() {
        // Show title
        System.out.println(Terminal.ANSI_RED + "-".repeat(32) + Terminal.ANSI_RESET);
        System.out.println(Terminal.ANSI_RED + "Welcome to " + Terminal.ANSI_BLUE + "CENTRO FIT" + Terminal.ANSI_RED + " login page" + Terminal.ANSI_RESET);
        System.out.println(Terminal.ANSI_RED + "-".repeat(32) + Terminal.ANSI_RESET);
        // Ask for login or register
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. " + Terminal.ANSI_YELLOW + "Login" + Terminal.ANSI_RESET);
        System.out.println("2. " + Terminal.ANSI_YELLOW + "Registar" + Terminal.ANSI_RESET);
        System.out.println("0. " + Terminal.ANSI_YELLOW + "Sair" + Terminal.ANSI_RESET);
        System.out.print(Terminal.ANSI_YELLOW + "Escolha uma opção: " + Terminal.ANSI_RESET);
        int option = scanner.nextInt();
        scanner.nextLine();
        // Check the option
        if (option == 0) {
            System.exit(0);
        } else if (option == 1) {
            login(scanner);
        } else if (option == 2) {
            register(scanner);
        } else {
            System.out.println(Terminal.ANSI_RED + "Invalid option" + Terminal.ANSI_RESET);
        }
        // Close the scanner
        scanner.close();
    }

    public static void login(Scanner scanner) {
        System.out.print(Terminal.ANSI_YELLOW + "Enter your email: " + Terminal.ANSI_RESET);
        String email = scanner.nextLine();
        System.out.print(Terminal.ANSI_YELLOW + "Enter your password: " + Terminal.ANSI_RESET);
        String password = scanner.nextLine();
        System.out.print(Terminal.ANSI_CLEAR); // Clean system console
        // Check if the email and password are correct
        GestaoUtilizadores gestaoUtilizadores = new GestaoUtilizadores();
        gestaoUtilizadores.loadUtilizadores();
        if (gestaoUtilizadores.doesUserExist(email, password)) {
            //System.out.println(Terminal.ANSI_GREEN + "Login successful!" + Terminal.ANSI_RESET);
            Utilizador user = gestaoUtilizadores.getUtilizadorByEmail(email);
            Menu.showMenu(user); // Show the menu
        } else {
            System.out.println(Terminal.ANSI_RED + "Invalid email or password." + Terminal.ANSI_RESET);
            showLogin();
        }
    }

    public static void register(Scanner scanner) {
        GestaoUtilizadores gestaoUtilizadores = new GestaoUtilizadores();
        gestaoUtilizadores.loadUtilizadores();
        gestaoUtilizadores.createUtilizador(scanner);
        gestaoUtilizadores.saveUtilizadores(); // we cound save the users after creating a new user (but better not?)
        showLogin();
    }
}
