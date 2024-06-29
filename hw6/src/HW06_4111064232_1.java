// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;
// import java.util.Arrays;

public class HW06_4111064232_1 extends SortIsAllYouNeed{
    // public static void main(String[] args) {
    //     Double[] arr = readDataFromFile("HW6_test_data/test_data_9.txt");

    //     // Sort the array
    //     HW06_4111064232_1 testClass = new HW06_4111064232_1();
    //     arr = testClass.sortWhat(arr);

    //     boolean result = compareWithAnswer(arr, "HW6_test_data/answer_9.txt");
    //     System.out.println("Arrays are equal: " + result);
    // }

    // // Method to read data from a file and return as an array of Doubles
    // private static Double[] readDataFromFile(String fileName) {
    //     Double[] arr = null;
    //     try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
    //         String line;
    //         int count = 0;
    //         while ((line = br.readLine()) != null) {
    //             count++;
    //         }
    //         arr = new Double[count];
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //         return null;
    //     }

    //     try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
    //         String line;
    //         int index = 0;
    //         while ((line = br.readLine()) != null) {
    //             arr[index++] = Double.parseDouble(line);
    //         }
    //         return arr;
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }


    // // Method to compare sorted array with the answer from a file
    // private static boolean compareWithAnswer(Double[] arr, String answerFileName) {
    //     Double[] answerArr = readDataFromFile(answerFileName);
    //     if (answerArr == null || arr.length != answerArr.length) {
    //         return false;
    //     }
    //     Arrays.sort(answerArr);
    //     return Arrays.equals(arr, answerArr);
    // }

    @Override
    public Double[] sortWhat(Double[] arr)
    {
        mergeSort(arr, 0, arr.length - 1);
        return arr;
    }

    public static void mergeSort(Double[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;

            // Create two threads to sort the left and right halves concurrently
            Thread leftThread = new Thread(() -> mergeSort(arr, l, m));
            Thread rightThread = new Thread(() -> mergeSort(arr, m + 1, r));

            leftThread.start();
            rightThread.start();

            try {
                leftThread.join();
                rightThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            merge(arr, l, m, r);
        }
    }

    public static void merge(Double[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        Double[] L = new Double[n1];
        Double[] R = new Double[n2];

        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
}