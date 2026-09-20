public class Main {

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
       
    }
    public static void serveNextStudent() {

    }
    public static void viewWaitingQueue(){

    }
    public static void addStudentServiceRecord(){

    }
    public static void editStudenServiceRecord(){

    }
    public static void displayStudentServiceRecords(){

    }
    public static void searchForStudentRecord(){

    }
    public static void removeStudentRecord(){

    }
    public static void displayDailyStatistics(){

    }
    public static void sortServiceTimes(){

    }
    public static void runSortingExperiment(){

    }
}