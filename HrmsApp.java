import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Employee {
    String name;
    String employeeId;
    String designation;
    String department;
    String contactInformation;

    public Employee(String name, String employeeId, String designation, String department, String contactInformation) {
        this.name = name;
        this.employeeId = employeeId;
        this.designation = designation;
        this.department = department;
        this.contactInformation = contactInformation;
    }
}

class HRMS {
    private Map<String, Employee> employeeMap;
    private Map<String, String> attendanceMap;
    private ArrayList<String> leaveRequests;

    public HRMS() {
        employeeMap = new HashMap<>();
        attendanceMap = new HashMap<>();
        leaveRequests = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employeeMap.put(employee.employeeId, employee);
        System.out.println("Employee added successfully.");
    }

    public void viewEmployee(String employeeId) {
        if (employeeMap.containsKey(employeeId)) {
            Employee employee = employeeMap.get(employeeId);
            System.out.println("Employee Details:\n" + employee.name + " | " + employee.employeeId + " | " +
                    employee.designation + " | " + employee.department + " | " + employee.contactInformation);
        } else {
            System.out.println("Employee not found.");
        }
    }

    public void updateEmployee(String employeeId, Employee updatedEmployee) {
        if (employeeMap.containsKey(employeeId)) {
            employeeMap.put(employeeId, updatedEmployee);
            System.out.println("Employee updated successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    public void deleteEmployee(String employeeId) {
        if (employeeMap.containsKey(employeeId)) {
            employeeMap.remove(employeeId);
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    public void markAttendance(String employeeId, String status, String date) {
        String key = employeeId + "-" + date;
        attendanceMap.put(key, status);
        System.out.println("Attendance marked successfully.");
    }

    public void viewAttendance(String employeeId, String date) {
        String key = employeeId + "-" + date;
        if (attendanceMap.containsKey(key)) {
            System.out.println("Attendance for " + date + ": " + attendanceMap.get(key));
        } else {
            System.out.println("Attendance not found for the specified date.");
        }
    }

    public void requestLeave(String employeeId, String leaveDetails) {
        leaveRequests.add(leaveDetails);
        System.out.println("Leave request submitted successfully.");
    }

    public void viewLeaveRequest(int index) {
        if (index >= 0 && index < leaveRequests.size()) {
            System.out.println("Leave Request:\n" + leaveRequests.get(index));
        } else {
            System.out.println("Leave request not found.");
        }
    }
}

public class HrmsApp {
    public static void main(String[] args) {
        HRMS hrms = new HRMS();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("==== HRMS Console App ====");
            System.out.println("1. Add Employee\n2. View Employee\n3. Update Employee\n4. Delete Employee");
            System.out.println("5. Mark Attendance\n6. View Attendance\n7. Request Leave\n8. View Leave Request");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.println("\nEnter Employee Details:");
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Employee ID: ");
                    String employeeId = scanner.nextLine();
                    System.out.print("Designation: ");
                    String designation = scanner.nextLine();
                    System.out.print("Department: ");
                    String department = scanner.nextLine();
                    System.out.print("Contact Information: ");
                    String contactInformation = scanner.nextLine();

                    Employee employee = new Employee(name, employeeId, designation, department, contactInformation);
                    hrms.addEmployee(employee);
                    break;

                case 2:
                    System.out.print("\nEnter Employee ID to view details: ");
                    String viewEmployeeId = scanner.nextLine();
                    hrms.viewEmployee(viewEmployeeId);
                    break;

                case 3:
                    System.out.print("\nEnter Employee ID to update details: ");
                    String updateEmployeeId = scanner.nextLine();
                    System.out.println("\nEnter Updated Employee Details:");
                    System.out.print("Name: ");
                    String updatedName = scanner.nextLine();
                    System.out.print("Designation: ");
                    String updatedDesignation = scanner.nextLine();
                    System.out.print("Department: ");
                    String updatedDepartment = scanner.nextLine();
                    System.out.print("Contact Information: ");
                    String updatedContactInformation = scanner.nextLine();

                    Employee updatedEmployee = new Employee(updatedName, updateEmployeeId, updatedDesignation,
                            updatedDepartment, updatedContactInformation);
                    hrms.updateEmployee(updateEmployeeId, updatedEmployee);
                    break;

                case 4:
                    System.out.print("\nEnter Employee ID to delete: ");
                    String deleteEmployeeId = scanner.nextLine();
                    hrms.deleteEmployee(deleteEmployeeId);
                    break;

                case 5:
                    System.out.print("\nEnter Employee ID for attendance: ");
                    String attendanceEmployeeId = scanner.nextLine();
                    System.out.print("Enter Date for attendance (YYYY-MM-DD): ");
                    String attendanceDate = scanner.nextLine();
                    System.out.print("Enter Attendance Status (Present/Absent/Leave): ");
                    String attendanceStatus = scanner.nextLine();

                    hrms.markAttendance(attendanceEmployeeId, attendanceStatus, attendanceDate);
                    break;

                case 6:
                    System.out.print("\nEnter Employee ID for attendance: ");
                    String viewAttendanceEmployeeId = scanner.nextLine();
                    System.out.print("Enter Date for attendance (YYYY-MM-DD): ");
                    String viewAttendanceDate = scanner.nextLine();

                    hrms.viewAttendance(viewAttendanceEmployeeId, viewAttendanceDate);
                    break;

                case 7:
                    System.out.print("\nEnter Employee ID for leave request: ");
                    String leaveEmployeeId = scanner.nextLine();
                    System.out.print("Enter Leave Details: ");
                    String leaveDetails = scanner.nextLine();

                    hrms.requestLeave(leaveEmployeeId, leaveDetails);
                    break;

                case 8:
                    System.out.print("\nEnter Leave Request Index to view: ");
                    int leaveRequestIndex = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    hrms.viewLeaveRequest(leaveRequestIndex);
                    break;

                case 9:
                    System.out.println("Exiting HRMS Console App. Goodbye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
