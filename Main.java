public class Main {

    private static final Queue waitingQueue = new Queue();
    private static final StudentList studentRecords = new StudentList();
    private static final StudentList sortedStudents = new StudentList();
    private static final DailyStatistics dailyStats = new DailyStatistics();

    static {
    studentRecords.addStudent("221045678", "Maria", "Registration", 12);
    studentRecords.addStudent("222034512", "Tomas", "Student Card", 5);
    studentRecords.addStudent("223041876", "Ndapewa", "Fees", 8);
    studentRecords.addStudent("221067341", "Simon", "Documents", 4);

    // Every record added above counts toward the day's stats too.
    dailyStats.recordAdded(12);
    dailyStats.recordAdded(5);
    dailyStats.recordAdded(8);
    dailyStats.recordAdded(4);
    refreshSortedServiceTimes();
}
    public static String readLine() {
        StringBuilder sb = new StringBuilder();
        try {
            int c = System.in.read();
            if (c == -1) {
                return null;
            }
            while (c != -1 && c != '\n') {
                if (c != '\r') {
                    sb.append((char) c);
                }
                c = System.in.read();
            }
        } catch (Exception e) {
            return null;
        }
        return sb.toString();
    }

    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = readLine();
            if (line == null) {          
                System.out.println("\nNo more input. Exiting...");
                System.exit(0);
            }
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    public static String readString(String prompt) {
        System.out.print(prompt);
        String line = readLine();
        if (line == null) {
            System.out.println("\nNo more input. Exiting...");
            System.exit(0);
        }
        return line.trim();
    }

    public static int readIntOptional(String prompt, int currentValue) {
        while (true) {
            String input = readString(prompt);
            if (input.isEmpty()) {
                return currentValue;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number, or leave it blank to keep the current value.");
            }
        }
    }

    public static String readStringOptional(String prompt, String currentValue) {
        String input = readString(prompt);
        return input.isEmpty() ? currentValue : input;
    }

    //Program entry point

    public static void main(String[] args) {
        int selection;

        do {
            selection = menu();
            menuSelection(selection);
        } while (selection != 12);

        System.out.println("Exiting program...");
    }

    public static int menu() {
        System.out.println("========================================");
        System.out.println("          CAMPUS SERVICE CENTRE");
        System.out.println("========================================");
        System.out.println("Select an option from the menu below:");
        System.out.println("1. Add student to waiting queue");
        System.out.println("2. Serve next student");
        System.out.println("3. View waiting queue");
        System.out.println("4. Add student service record");
        System.out.println("5. Edit student service record");
        System.out.println("6. Display student service records");
        System.out.println("7. Search for student record");
        System.out.println("8. Remove student record");
        System.out.println("9. Display daily statistics");
        System.out.println("10. Sort service times");
        System.out.println("11. Run sorting experiment");
        System.out.println("12. Exit program");
        System.out.println("========================================");

        return readInt("\nEnter your selection: ");
    }

    public static void menuSelection(int selection) {
        switch (selection) {
            case 1:
                addStudentToQueue(); //enqueue
                break;
            case 2:
                serveNextStudent(); //peak then dequeue
                break;
            case 3:
                viewWaitingQueue(); //display queue
                break;
            case 4:
                addStudentServiceRecord(); //add record of student to array
                break;
            case 5:
                editStudenServiceRecord(); //edit student record in array
                break;
            case 6:
                displayStudentServiceRecords(); //display all student records
                break;
            case 7:
                searchForStudentRecord(); //search for student record
                break;
            case 8:
                removeStudentRecord(); //remove student record
                break;
            case 9:
                displayDailyStatistics(); //display daily statistics
                break;
            case 10:
                sortServiceTimes(); //sort service times
                break;
            case 11:
                runSortingExperiment(); //run sorting experiment
                break;
            case 12:
                break; // handled by the loop in main
            default:
                System.out.println("Invalid selection. Please choose 1-12.\n");
                break;
        }
    }

       //classes for each menu option
    public static void addStudentToQueue() {
        int studentId = readInt("Enter student ID to add to queue: ");
        waitingQueue.enqueue(studentId);
        System.out.println("Student " + studentId + " added to the waiting queue .");
    }

    public static void serveNextStudent() {
        if (waitingQueue.isEmpty()) {
            System.out.println("No students in the waiting queue.");
            return;
        }

        int servedStudent = waitingQueue.dequeue();
        System.out.println("Served student: " + servedStudent);
    }

    public static void viewWaitingQueue() {
        waitingQueue.display();
    }

    public static void addStudentServiceRecord(){
        String studentNo = readString("Enter Student No.: ");
        if (studentRecords.lookup(studentNo) != null) {
            System.out.println("A service record already exists for Student No. " + studentNo + ".");
            return;
        }

        String name = readString("Enter Name: ");
        String serviceType = readString("Enter Service Type: ");
        int estimatedServiceTime = readInt("Enter Estimated Service Time (min): ");

        studentRecords.addStudent(studentNo, name, serviceType, estimatedServiceTime);

        // The moment the record is added, it counts toward daily statistics - O(1).
        dailyStats.recordAdded(estimatedServiceTime);
        refreshSortedServiceTimes();

        System.out.println("Student service record added.");
    }

    public static void editStudenServiceRecord(){
        String studentNo = readString("Enter Student No. to edit: ");
        Node record = studentRecords.lookup(studentNo);

        if (record == null) {
            System.out.println("No service record found for Student No. " + studentNo + ".");
            return;
        }

        int oldTime = record.estimatedServiceTime;

        System.out.println("Leave a field blank and press Enter to keep its current value.");
        record.name = readStringOptional("Enter Name (" + record.name + "): ", record.name);
        record.serviceType = readStringOptional("Enter Service Type (" + record.serviceType + "): ", record.serviceType);
        record.estimatedServiceTime = readIntOptional(
                "Enter Estimated Service Time (" + record.estimatedServiceTime + " min): ",
                record.estimatedServiceTime);

        // Keep daily statistics in sync with the edited value.
        dailyStats.recordEdited(oldTime, record.estimatedServiceTime, studentRecords);
        refreshSortedServiceTimes();

        System.out.println("Service record for Student No. " + studentNo + " updated.");
    }

    public static void displayStudentServiceRecords(){
        studentRecords.displayAll();
    }

    public static void searchForStudentRecord(){
        String studentNo = readString("Enter Student No. to search for: ");
        studentRecords.lookupAndDisplay(studentNo);
    }

    public static void removeStudentRecord(){
        String studentNo = readString("Enter Student No. to remove: ");

        // Capture the record's data before it's removed, so daily statistics
        // can be adjusted to match.
        Node record = studentRecords.lookup(studentNo);
        if (record == null) {
            System.out.println("No service record found for Student No. " + studentNo + ".");
            return;
        }
        int removedTime = record.estimatedServiceTime;

        studentRecords.removeStudent(studentNo);
        dailyStats.recordRemoved(removedTime, studentRecords);
        refreshSortedServiceTimes();

        System.out.println("Service record for Student No. " + studentNo + " removed.");
    }

    public static void displayDailyStatistics(){
        // No re-scanning here - just prints the running totals kept up to
        // date by recordAdded/recordEdited/recordRemoved.
        dailyStats.displaySummary();
    }

    public static void refreshSortedServiceTimes() {
        sortServiceTimes.rebuildSortedServiceTimes(studentRecords, sortedStudents);
    }

    public static void sortServiceTimes() {
        refreshSortedServiceTimes();
        sortedStudents.displayAll();
    }

    public static void runSortingExperiment() {
        sortingExperiment.runExperiment();
    }
}
