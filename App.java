package com.example.jdbc;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final EmployeeDAO dao = new EmployeeDAO();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Employee JDBC App ===");
        try {
            dao.createTableIfNotExists();
            boolean running = true;
            while (running) {
                printMenu();
                String choice = sc.nextLine().trim();
                switch (choice) {
                    case "1" -> add();
                    case "2" -> viewAll();
                    case "3" -> viewById();
                    case "4" -> update();
                    case "5" -> delete();
                    case "0" -> running = false;
                    default -> System.out.println("Invalid option.");
                }
            }
            System.out.println("Goodbye!");
        } catch (SQLException e) {
            System.err.println("DB error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printMenu() {
        System.out.println("\nChoose an option:");
        System.out.println("1) Add Employee");
        System.out.println("2) View All Employees");
        System.out.println("3) View Employee by ID");
        System.out.println("4) Update Employee");
        System.out.println("5) Delete Employee");
        System.out.println("0) Exit");
        System.out.print("> ");
    }

    private static void add() throws SQLException {
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Role: ");
        String role = sc.nextLine().trim();
        System.out.print("Salary: ");
        double salary = Double.parseDouble(sc.nextLine().trim());
        int id = dao.addEmployee(new Employee(name, role, salary));
        System.out.println(id > 0 ? "Inserted with ID: " + id : "Insert failed.");
    }

    private static void viewAll() throws SQLException {
        List<Employee> list = dao.getAllEmployees();
        if (list.isEmpty()) {
            System.out.println("No records.");
        } else {
            list.forEach(System.out::println);
        }
    }

    private static void viewById() throws SQLException {
        System.out.print("ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        Employee emp = dao.getEmployeeById(id);
        System.out.println(emp != null ? emp : "Not found.");
    }

    private static void update() throws SQLException {
        System.out.print("ID to update: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        System.out.print("New Name: ");
        String name = sc.nextLine().trim();
        System.out.print("New Role: ");
        String role = sc.nextLine().trim();
        System.out.print("New Salary: ");
        double salary = Double.parseDouble(sc.nextLine().trim());
        boolean ok = dao.updateEmployee(new Employee(id, name, role, salary));
        System.out.println(ok ? "Updated." : "Update failed.");
    }

    private static void delete() throws SQLException {
        System.out.print("ID to delete: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        boolean ok = dao.deleteEmployee(id);
        System.out.println(ok ? "Deleted." : "Delete failed.");
    }
}
