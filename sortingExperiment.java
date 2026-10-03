public class sortingExperiment {

    private static long comparisons = 0;

    private static class SimpleRandom {

        private long seed;

        /** Seeds itself from the current time, so runs differ each time. */
        SimpleRandom() {
            this.seed = System.nanoTime();
        }

        /** Returns a pseudo-random, non-negative int in the range [0, bound). */
        int nextInt(int bound) {
            seed = (seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
            int bits = (int) (seed >>> 17); // keep the higher-quality upper bits
            if (bits < 0) {
                bits = -bits;
            }
            return bits % bound;
        }
    }


    /** Selection Sort: repeatedly find the smallest remaining value and move it into place. */
    private static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                comparisons++;
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            swap(arr, i, minIndex);
        }
    }

    /** Insertion Sort: build up a sorted section one element at a time. */
    private static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0) {
                comparisons++;
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = key;
        }
    }

    /** Merge Sort: split the array in half, sort each half, then merge them back together. */
    private static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    /** Merges two already-sorted halves, arr[left..mid] and arr[mid+1..right], into one sorted run. */
    private static void merge(int[] arr, int left, int mid, int right) {
        int leftSize = mid - left + 1;
        int rightSize = right - mid;

        int[] leftHalf = new int[leftSize];
        int[] rightHalf = new int[rightSize];

        for (int i = 0; i < leftSize; i++) {
            leftHalf[i] = arr[left + i];
        }
        for (int j = 0; j < rightSize; j++) {
            rightHalf[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;
        while (i < leftSize && j < rightSize) {
            comparisons++;
            if (leftHalf[i] <= rightHalf[j]) {
                arr[k++] = leftHalf[i++];
            } else {
                arr[k++] = rightHalf[j++];
            }
        }
        while (i < leftSize) {
            arr[k++] = leftHalf[i++];
        }
        while (j < rightSize) {
            arr[k++] = rightHalf[j++];
        }
    }

    /** Quick Sort: pick a pivot, partition around it, then sort each side. */
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    /** Partitions arr[low..high] around the last element, used as the pivot. */
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int boundary = low - 1;

        for (int j = low; j < high; j++) {
            comparisons++;
            if (arr[j] < pivot) {
                boundary++;
                swap(arr, boundary, j);
            }
        }
        swap(arr, boundary + 1, high);
        return boundary + 1;
    }

    /** Small helper so the sorts above don't repeat the same three lines everywhere. */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    /** Builds an array of the given size, filled with random values from 1 to 10,000. */
    private static int[] randomArray(int size) {
        SimpleRandom rand = new SimpleRandom();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1 + rand.nextInt(10000);
        }
        return arr;
    }

    /** Builds an array that's already sorted except for 5 randomly swapped neighbouring pairs. */
    private static int[] almostSortedArray(int size) {
        int[] arr = randomArray(size);
        mergeSort(arr, 0, arr.length - 1);

        SimpleRandom rand = new SimpleRandom();
        for (int s = 0; s < 5; s++) {
            int i = rand.nextInt(size - 1);
            swap(arr, i, i + 1);
        }
        return arr;
    }

    /** Makes an independent copy of an array, so each algorithm gets its own untouched data. */
    private static int[] copyOf(int[] source) {
        int[] copy = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            copy[i] = source[i];
        }
        return copy;
    }


    /** Runs all four algorithms on identical copies of the same data and prints one row per algorithm. */
    private static void runAll(int[] original, int size) {
        int[] forSelection = copyOf(original);
        int[] forInsertion = copyOf(original);
        int[] forMerge = copyOf(original);
        int[] forQuick = copyOf(original);

        time("Selection Sort", size, () -> selectionSort(forSelection));
        time("Insertion Sort", size, () -> insertionSort(forInsertion));
        time("Merge Sort", size, () -> mergeSort(forMerge, 0, forMerge.length - 1));
        time("Quick Sort", size, () -> quickSort(forQuick, 0, forQuick.length - 1));
    }

    /** Times a single sort, resets the comparison counter, and prints a formatted result row. */
    private static void time(String label, int size, Runnable sortCall) {
        comparisons = 0;
        long start = System.nanoTime();
        sortCall.run();
        long elapsed = System.nanoTime() - start;
        System.out.printf("%-15s %-10d %-15d %-15d%n", label, size, comparisons, elapsed);
    }

    private static void printHeader() {
        System.out.printf("%-15s %-10s %-15s %-15s%n", "Algorithm", "Size", "Comparisons", "Time (ns)");
    }

    // Public entry point other classes (like Main) can call directly.
    public static void runExperiment() {
        int[] sizes = {20, 50, 100, 500};

        System.out.println("=========== RANDOM ARRAY EXPERIMENT ===========");
        printHeader();
        for (int size : sizes) {
            runAll(randomArray(size), size);
        }

        System.out.println("\n===== ALMOST-SORTED 100-ELEMENT ARRAY =====");
        printHeader();
        runAll(almostSortedArray(100), 100);
    }

    // Lets this class still be run standalone for testing, if you ever need to.
    public static void main(String[] args) {
        runExperiment();
    }
}