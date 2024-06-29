public class HW06_4111064232_2 extends SortIsAllYouNeed{

    @Override
    public Double[] sortWhat(Double[] arr)
    {
        sort(arr);
        return arr;
    }

    private static final int MIN_MERGE = 32;

    public static void sort(Double[] array) {
        int minRun = minRunLength(array.length);

        // Sort individual subarrays of size minRun
        for (int i = 0; i < array.length; i += minRun) {
            insertionSort(array, i, Math.min(i + minRun, array.length));
        }

        // Merge subarrays to produce sorted array
        for (int size = minRun; size < array.length; size = 2 * size) {
            for (int left = 0; left < array.length; left += 2 * size) {
                int mid = left + size;
                int right = Math.min(left + 2 * size, array.length);
                if (mid < right) {
                    merge(array, left, mid, right);
                }
            }
        }
    }

    private static void insertionSort(Double[] arr, int left, int right) {
        for (int i = left + 1; i < right; i++) {
            Double key = arr[i];
            int j = i - 1;
            while (j >= left && arr[j].compareTo(key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    private static void merge(Double[] arr, int left, int mid, int right) {
        int len1 = mid - left;
        int len2 = right - mid;

        Double[] leftArr = new Double[len1];
        Double[] rightArr = new Double[len2];

        // Copy elements to left array
        for (int i = 0; i < len1; i++) {
            leftArr[i] = arr[left + i];
        }

        // Copy elements to right array
        for (int i = 0; i < len2; i++) {
            rightArr[i] = arr[mid + i];
        }

        int i = 0, j = 0, k = left;

        while (i < len1 && j < len2) {
            if (leftArr[i].compareTo(rightArr[j]) <= 0) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }

        while (i < len1) {
            arr[k++] = leftArr[i++];
        }

        while (j < len2) {
            arr[k++] = rightArr[j++];
        }
    }

    private static int minRunLength(int n) {
        int r = 0;
        while (n >= MIN_MERGE) {
            r |= (n & 1);
            n >>= 1;
        }
        return n + r;
    }
}