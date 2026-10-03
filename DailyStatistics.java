public class DailyStatistics {
    private int totalStudents;
    private int totalTime;
    private int highest;
    private int lowest;
    private int longServices; // count of services taking longer than 10 minutes

    public DailyStatistics() {
        totalStudents = 0;
        totalTime = 0;
        highest = 0;
        lowest = 0;
        longServices = 0;
    }

    // Call this the MOMENT a student record is added - O(1), no scanning.
    public void recordAdded(int estimatedServiceTime) {
        if (totalStudents == 0) {
            highest = estimatedServiceTime;
            lowest = estimatedServiceTime;
        } else {
            if (estimatedServiceTime > highest) highest = estimatedServiceTime;
            if (estimatedServiceTime < lowest) lowest = estimatedServiceTime;
        }
        totalStudents++;
        totalTime += estimatedServiceTime;
        if (estimatedServiceTime > 10) longServices++;
    }

    // Call this right after a record is removed from studentRecords.
    // Only rescans the list in the rare case the removed value WAS the
    // current highest or lowest - everything else stays O(1).
    public void recordRemoved(int estimatedServiceTime, StudentList list) {
        if (totalStudents == 0) return; // safety guard

        totalStudents--;
        totalTime -= estimatedServiceTime;
        if (estimatedServiceTime > 10) longServices--;

        if (totalStudents == 0) {
            highest = 0;
            lowest = 0;
            return;
        }

        if (estimatedServiceTime == highest || estimatedServiceTime == lowest) {
            recalculateMinMax(list);
        }
    }

    // Call this right after a record's estimated service time changes.
    // Same idea: only rescans if the OLD value was the highest/lowest.
    public void recordEdited(int oldTime, int newTime, StudentList list) {
        if (oldTime == newTime) return; // nothing changed

        totalTime += (newTime - oldTime);

        if (oldTime > 10 && newTime <= 10) longServices--;
        if (oldTime <= 10 && newTime > 10) longServices++;

        if (newTime > highest) highest = newTime;
        if (newTime < lowest) lowest = newTime;

        if (oldTime == highest || oldTime == lowest) {
            recalculateMinMax(list);
        }
    }

    // Only called in the rare edge case above - walks the list once to
    // find the true current highest and lowest.
    private void recalculateMinMax(StudentList list) {
        Node current = list.head;
        if (current == null) {
            highest = 0;
            lowest = 0;
            return;
        }
        int newHighest = current.estimatedServiceTime;
        int newLowest = current.estimatedServiceTime;
        current = current.next;
        while (current != null) {
            if (current.estimatedServiceTime > newHighest) newHighest = current.estimatedServiceTime;
            if (current.estimatedServiceTime < newLowest) newLowest = current.estimatedServiceTime;
            current = current.next;
        }
        highest = newHighest;
        lowest = newLowest;
    }

    // O(1) - just prints numbers that were already kept up to date.
    public void displaySummary() {
        System.out.println("---- Daily Statistics ----");
        if (totalStudents == 0) {
            System.out.println("There are no student records yet, so there are no statistics to show.");
            return;
        }
        double average = (double) totalTime / totalStudents;
        System.out.println("Total students served: " + totalStudents);
        System.out.println("Total service time: " + totalTime + " minutes");
        System.out.printf("Average service time: %.2f minutes%n", average);
        System.out.println("Highest service time: " + highest + " minutes");
        System.out.println("Lowest service time: " + lowest + " minutes");
        System.out.println("Services longer than 10 minutes: " + longServices);
    }
}
