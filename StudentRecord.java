
class Node {
    String studentNo;
    String name;
    String serviceType;
    int estimatedServiceTime; // in minutes
    Node next;

    Node(String studentNo, String name, String serviceType, int estimatedServiceTime) {
        this.studentNo = studentNo;
        this.name = name;
        this.serviceType = serviceType;
        this.estimatedServiceTime = estimatedServiceTime;
        this.next = null;
    }
}

public class StudentRecord {

    // Reads one line of input from the keyboard, byte by byte.
    // Built manually so no java.util.Scanner / java.io.BufferedReader import is needed.
    static String readLine() throws Exception {
        StringBuilder input = new StringBuilder();
        int ch = System.in.read();
        while (ch != -1 && ch != '\n') {
            if (ch != '\r') { // ignore carriage return from Windows-style line endings
                input.append((char) ch);
            }
            ch = System.in.read();
        }
        return input.toString();
    }

    static void printMenu() {
        System.out.println("\n================ Student Information Menu ================");
        System.out.println("1. Insertion (add a student)");
        System.out.println("2. Traversal/Display (list all students)");
        System.out.println("3. Search (look up a student)");
        System.out.println("4. Deletion (remove a student)");
        System.out.println("5. Cancel");
        System.out.println("============================================================");
        System.out.print("Enter your choice: ");
    }

    public static void main(String[] args) {
        StudentList students = new StudentList();

        // ---- Hardcoded students from the problem statement ----
        students.addStudent("221045678", "Maria", "Registration", 12);
        students.addStudent("222034512", "Tomas", "Student Card", 5);
        students.addStudent("223041876", "Ndapewa", "Fees", 8);
        students.addStudent("221067341", "Simon", "Documents", 4);

        System.out.println("Student information system started with the following records already stored:");
        students.displayAll();

        boolean running = true;
        while (running) {
            try {
                printMenu();
                int choice = Integer.parseInt(readLine().trim());

                switch (choice) {
                    case 1: { // Insertion
                        System.out.print("Enter Student No.: ");
                        String studentNo = readLine().trim();
                        System.out.print("Enter Name: ");
                        String name = readLine().trim();
                        System.out.print("Enter Service Type: ");
                        String serviceType = readLine().trim();
                        System.out.print("Enter Estimated Service Time (min): ");
                        int time = Integer.parseInt(readLine().trim());

                        students.addStudent(studentNo, name, serviceType, time);
                        System.out.println("Student record added.");
                        break;
                    }
                    case 2: { // Traversal/Display
                        students.displayAll();
                        break;
                    }
                    case 3: { // Search
                        System.out.print("Enter Student No. to search for: ");
                        String studentNo = readLine().trim();
                        students.lookupAndDisplay(studentNo);
                        break;
                    }
                    case 4: { // Deletion
                        System.out.print("Enter Student No. to delete: ");
                        String studentNo = readLine().trim();
                        boolean removed = students.removeStudent(studentNo);
                        System.out.println(removed
                                ? "Student record removed."
                                : "No student found with that Student No.");
                        break;
                    }
                    case 5: { // Cancel
                        running = false;
                        System.out.println("Cancelled. Goodbye!");
                        break;
                    }
                    default: {
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }
}