public class StudentList {
    Node head;
    Node tail; // kept so adding to the end is O(1) instead of O(n)

    // Add a new student record to the end of the list
    void addStudent(String studentNo, String name, String serviceType, int estimatedServiceTime) {
        Node newNode = new Node(studentNo, name, serviceType, estimatedServiceTime);
        if (head == null) {
            head = newNode;
            tail = newNode;
            return;
        }
        tail.next = newNode;
        tail = newNode;
    }

    // Look up a record by student number, return the Node if found, otherwise null
    Node lookup(String studentNo) {
        Node current = head;
        while (current != null) {
            if (current.studentNo.equals(studentNo)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    // Convenience wrapper: look up and print the result directly
    void lookupAndDisplay(String studentNo) {
        Node result = lookup(studentNo);
        if (result == null) {
            System.out.println("No student found with Student No. " + studentNo);
        } else {
            System.out.println("Found -> Student No: " + result.studentNo
                    + " | Name: " + result.name
                    + " | Service Type: " + result.serviceType
                    + " | Time: " + result.estimatedServiceTime + " min");
        }
    }

    // Remove a student record by student number
    boolean removeStudent(String studentNo) {
        if (head == null) return false;

        if (head.studentNo.equals(studentNo)) {
            head = head.next;
            if (head == null) {
                tail = null;
            }
            return true;
        }

        Node current = head;
        while (current.next != null && !current.next.studentNo.equals(studentNo)) {
            current = current.next;
        }

        if (current.next != null) {
            if (current.next == tail) {
                tail = current;
            }
            current.next = current.next.next;
            return true;
        }

        return false;
    }

    void displayAll() {
        if (head == null) {
            System.out.println("No student records stored.");
            return;
        }
        Node current = head;
        System.out.println("-------------------------------------------------------------------------------");
        System.out.printf("%-15s %-12s %-15s %-10s%n", "Student No.", "Name", "Service Type", "Time (min)");
        System.out.println("-------------------------------------------------------------------------------");
        while (current != null) {
            System.out.printf("%-15s %-12s %-15s %-10d%n",
                    current.studentNo, current.name, current.serviceType, current.estimatedServiceTime);
            current = current.next;
        }
        System.out.println("-------------------------------------------------------------------------------");
    }
}
