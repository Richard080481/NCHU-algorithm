public class HW06_4111064232_3 extends SortIsAllYouNeed{
    @Override
    public Double[] sortWhat(Double[] arr)
    {
        shuffleArray(arr);
        quickSort(arr, 0, arr.length - 1);
        return arr;
    }
    private static final int INSERTION_SORT_CUTOFF = 10;
    public static void quickSort(Double[] array, int low, int high) {
        if (high <= low) return;
        if (high - low + 1 <= INSERTION_SORT_CUTOFF) {
            insertionSort(array, low, high);
            return;
        }

        int partitionIndex = partition(array, low, high);
        quickSort(array, low, partitionIndex - 1);
        quickSort(array, partitionIndex + 1, high);
    }

    private static int partition(Double[] array, int low, int high) {
        Double pivot = array[low];
        int i = low;
        int j = high + 1;

        while (true) {
            while (array[++i] < pivot) {
                if (i == high) break;
            }

            while (pivot < array[--j]) {
                if (j == low) break;
            }

            if (i >= j) break;

            swap(array, i, j);
        }

        swap(array, low, j);
        return j;
    }

    private static void insertionSort(Double[] array, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            Double key = array[i];
            int j = i - 1;

            while (j >= low && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = key;
        }
    }

    private static void swap(Double[] array, int i, int j) {
        Double temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static void shuffleArray(Double[] array) {
        long seed = System.currentTimeMillis(); // Using current time as a seed
        final long a = 1664525;
        final long c = 1013904223;
        final long m = (long) Math.pow(2, 32);
        long rnd = seed;

        for (int i = array.length - 1; i > 0; i--) {
            rnd = (a * rnd + c) % m; // Linear Congruential Generator formula

            int index = (int) (rnd % (i + 1));

            // Swap the current element with the randomly chosen element
            Double temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}