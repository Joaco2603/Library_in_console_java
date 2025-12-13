package cr.ac.ucenfotec.ui;

import java.util.Scanner;

public class UI {
    private final Scanner scanner;

    public UI() {
        this.scanner = new Scanner(System.in);
    }

    public void displayAccess() {
        System.out.println("\n=== Sistema de Biblioteca ===");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrarse");
        System.out.print("Seleccione una opción: ");
    }

    public void displayAdminMenu() {
        System.out.println("\n=== Menú Administrador ===");
        System.out.println("1. Agregar libro");
        System.out.println("2. Eliminar libro");
        System.out.println("3. Ver todos los libros");
        System.out.println("4. Ver usuarios");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public void displayUserMenu() {
        System.out.println("\n=== Menú Usuario ===");
        System.out.println("1. Reservar libro");
        System.out.println("2. Devolver libro");
        System.out.println("3. Buscar libro");
        System.out.println("4. Ver usuarios");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public int readOption() {
        try {
            int option = Integer.parseInt(scanner.nextLine());
            return option;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
