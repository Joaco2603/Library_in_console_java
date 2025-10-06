package cr.ac.ucenfotec.ui;

import java.util.Scanner;

import cr.ac.ucenfotec.bl.entities.UserEntity;

public class UI {
    private final Scanner scanner = new Scanner(System.in);
    private final java.io.PrintStream out = System.out;

    public void displayAccess(){
        out.println("=== Welcome to the Library System ===");
        out.println("1. Login");
        out.println("2. Register");
        out.print("Select an option: ");
    }

    public String[] getLoginCredentials(){
        out.println("=== Login ===");
        out.print("Email: ");
        scanner.nextLine(); // Limpiar buffer
        String email = scanner.nextLine();
        out.print("Password: ");
        String password = scanner.nextLine();
        return new String[]{email, password};
    }

    public void displayLoginSuccess(String username){
        out.println("Login successful! Welcome, " + username + "!");
    }

    public void displayLoginFailure(){
        out.println("Login failed. Invalid email or password.");
    }

    public String[] getRegisterData(){
        out.println("=== Register ===");
        scanner.nextLine(); // Limpiar buffer
        out.print("Choose a username: ");
        String username = scanner.nextLine();
        out.print("Choose a password: ");
        String password = scanner.nextLine();
        out.print("Enter your email: ");
        String email = scanner.nextLine();
        return new String[]{username, password, email};
    }

    public void displayRegisterSuccess(){
        out.println("Registration successful! You are now logged in.");
    }

    public void displayMenu() {
    out.println("=== Menú Principal de la Biblioteca ===");
    out.println("1. Reservar libro");
    out.println("2. Devolver libro");
    out.println("3. Buscar libro");
    out.println("4. Ver usuarios");
    out.println("5. Salir");
    out.print("Seleccione una opción: ");
    }

    public void displayResult(String text,String result) {
        out.println("=== Resultados de la búsqueda ===");

        if(result.isEmpty()){
            out.println("No se encontraron resultados para la búsqueda.");
            return;
        }

        out.println(text + ": " + result);
    }

    public void displayBookReservation(int option) {
        out.println("=== Reservar Libro ===");
        out.print("Ingrese el ID del libro que desea reservar: ");
    }

    public void displayBookReturn(int option) {
        out.println("=== Devolver Libro ===");
        out.print("Ingrese el ID del libro que desea devolver: ");
    }

    public void displayBookSearch(int option) {
        out.println("=== Buscar Libro ===");
        out.print("Ingrese el título o autor del libro que desea buscar: ");
    }

    public int readOption() {
        while (!scanner.hasNextInt()) {
            out.println("Please enter a valid number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public void displayExit(){
        out.println("Saliendo...");
    }

    public void displayError(){
        out.println("Opción no válida. Intente de nuevo.");
    }

    public Object displayUserInfo(UserEntity user) {
        out.println("User Information:");
        out.println("Username: " + user.getUsername());
        out.println("Email: " + user.getEmail());
        out.println("Role ID: " + user.getRole());
        return null;
    }
}
