public class sortServiceTimes {//insertion sort
    public static void rebuildSortedServiceTimes(StudentList source, StudentList sortedView) {
        if (sortedView == null) {
            return;
        }

        sortedView.head = null;
        sortedView.tail = null;

        if (source == null || source.head == null) {
            return;
        }

        Node current = source.head;
        while (current != null) {
            insertIntoSortedView(sortedView, current);
            current = current.next;
        }
    }

    private static void insertIntoSortedView(StudentList sortedView, Node sourceNode) {
        Node newNode = new Node(sourceNode.studentNo, sourceNode.name, sourceNode.serviceType, sourceNode.estimatedServiceTime);

        if (sortedView.head == null) {
            sortedView.head = newNode;
            sortedView.tail = newNode;
            return;
        }

        if (newNode.estimatedServiceTime <= sortedView.head.estimatedServiceTime) {
            newNode.next = sortedView.head;
            sortedView.head = newNode;
            if (sortedView.tail == null) {
                sortedView.tail = newNode;
            }
            return;
        }

        Node current = sortedView.head;
        while (current.next != null && current.next.estimatedServiceTime < newNode.estimatedServiceTime) {
            current = current.next;
        }

        newNode.next = current.next;
        current.next = newNode;

        if (newNode.next == null) {
            sortedView.tail = newNode;
        }
    }
}
