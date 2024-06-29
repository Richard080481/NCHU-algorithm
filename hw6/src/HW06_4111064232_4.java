import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class HW06_4111064232_3 extends SortIsAllYouNeed{
    public static void main(String[] args) {
        Double[] arr1 = readDataFromFile("HW6_test_data/test_data_1.txt");
        Double[] arr2 = readDataFromFile("HW6_test_data/test_data_2.txt");
        Double[] arr3 = readDataFromFile("HW6_test_data/test_data_3.txt");
        Double[] arr4 = readDataFromFile("HW6_test_data/test_data_4.txt");
        Double[] arr5 = readDataFromFile("HW6_test_data/test_data_5.txt");
        Double[] arr6 = readDataFromFile("HW6_test_data/test_data_6.txt");
        Double[] arr7 = readDataFromFile("HW6_test_data/test_data_7.txt");
        Double[] arr8 = readDataFromFile("HW6_test_data/test_data_8.txt");
        Double[] arr9 = readDataFromFile("HW6_test_data/test_data_9.txt");

        // Sort the array
        HW06_4111064232_3 testClass = new HW06_4111064232_3();
        arr1 = testClass.sortWhat(arr1);
        arr2 = testClass.sortWhat(arr2);
        arr3 = testClass.sortWhat(arr3);
        arr4 = testClass.sortWhat(arr4);
        arr5 = testClass.sortWhat(arr5);
        arr6 = testClass.sortWhat(arr6);
        arr7 = testClass.sortWhat(arr7);
        arr8 = testClass.sortWhat(arr8);
        arr9 = testClass.sortWhat(arr9);

        boolean result1 = compareWithAnswer(arr1, "HW6_test_data/answer_1.txt");
        boolean result2 = compareWithAnswer(arr2, "HW6_test_data/answer_2.txt");
        boolean result3 = compareWithAnswer(arr3, "HW6_test_data/answer_3.txt");
        boolean result4 = compareWithAnswer(arr4, "HW6_test_data/answer_4.txt");
        boolean result5 = compareWithAnswer(arr5, "HW6_test_data/answer_5.txt");
        boolean result6 = compareWithAnswer(arr6, "HW6_test_data/answer_6.txt");
        boolean result7 = compareWithAnswer(arr7, "HW6_test_data/answer_7.txt");
        boolean result8 = compareWithAnswer(arr8, "HW6_test_data/answer_8.txt");
        boolean result9 = compareWithAnswer(arr9, "HW6_test_data/answer_9.txt");
        System.out.println("result1 are equal: " + result1);
        System.out.println("result2 are equal: " + result2);
        System.out.println("result3 are equal: " + result3);
        System.out.println("result4 are equal: " + result4);
        System.out.println("result5 are equal: " + result5);
        System.out.println("result6 are equal: " + result6);
        System.out.println("result7 are equal: " + result7);
        System.out.println("result8 are equal: " + result8);
        System.out.println("result9 are equal: " + result9);
    }

    // Method to read data from a file and return as an array of Doubles
    private static Double[] readDataFromFile(String fileName) {
        Double[] arr = null;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                count++;
            }
            arr = new Double[count];
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                arr[index++] = Double.parseDouble(line);
            }
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to compare sorted array with the answer from a file
    private static boolean compareWithAnswer(Double[] arr, String answerFileName) {
        Double[] answerArr = readDataFromFile(answerFileName);
        if (answerArr == null || arr.length != answerArr.length) {
            return false;
        }
        Arrays.sort(answerArr);
        return Arrays.equals(arr, answerArr);
    }

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

    private static Double[] generateRandomArray(int size) {
        Double[] array = new Double[size];
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextDouble() * 100; // java.util.Random double between 0 and 100
        }
        return array;
    }

    private static void shuffleArray(Double[] array) {
        java.util.Random rnd = new java.util.Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Double temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}