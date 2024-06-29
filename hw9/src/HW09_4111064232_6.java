import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HW09_4111064232_6 extends BuyPhone{
    public static void main(String[] args)
    {
        HW09_4111064232_6 test = new HW09_4111064232_6();
        int[][] input1 = {{0,0}};
        int[][] file1Data = null;
        try
        {
            file1Data = readDocumentToArray(args[0]);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        int[][] answer = test.bestPhone(file1Data);
        System.out.printf("{");
        System.out.printf("{%d,%d}", answer[0][0], answer[0][1]);
        for (int i = 1; i < answer.length; i++)
        {
            System.out.printf(",{%d,%d}", answer[i][0], answer[i][1]);
        }
        System.out.printf("}\n");
    }

    public static int[][] readDocumentToArray(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        List<int[]> dataList = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim().substring(2, line.length() - 2); // Remove curly braces
            String[] parts = line.split("},\\s*\\{");
            for (String part : parts) {
                String[] coordinates = part.split(",");
                int[] point = new int[2];
                point[0] = Integer.parseInt(coordinates[0]);
                point[1] = Integer.parseInt(coordinates[1]);
                dataList.add(point);
            }
        }

        reader.close();

        // Convert list to array
        int[][] data = new int[dataList.size()][2];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i);
        }

        return data;
    }

    @Override
    public int[][] bestPhone(int[][] phones)
    {
        int[] indices = indirectSort(phones);
        DoublyLinkedList list = new DoublyLinkedList();
        for (int i = 0; i < indices.length; i++)
        {
            int index = indices[i];
            list.addNode(index);
        }
        for (Node node = list.head.next; node != null; node = node.next)
        {
            while (node.prev != null && phones[node.data][1] >= phones[node.prev.data][1])
            {
                list.removeNode(node.prev);
            }
        }
        int i = 0;
        int[] indices2 = new int[list.getSize()];
        for (Node node = list.head; node != null; node = node.next, i++)
        {
            // System.out.printf("%d %d %d\n", phones[node.data][0], phones[node.data][1], node.data);
            indices2[i] = node.data;
        }
        timSort(indices2);
        int[][] answer = new int[list.getSize()][];
        for(i = 0; i < list.size; i++)
        {
            // System.out.printf("%d %d %d\n", phones[indices2[i]][0], phones[indices2[i]][1], indices2[i]);
            answer[i] = phones[indices2[i]];
        }
        return answer;
    }

    public static int[] indirectSort(int[][] array) {
        int n = array.length;
        int[] indices = new int[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }

        // Perform Timsort
        timSort(array, indices, n);

        return indices;
    }

    private static final int MIN_MERGE = 32;

    public static void timSort(int[] array) {
        int n = array.length;
        int minRun = minRunLength(MIN_MERGE);

        for (int i = 0; i < n; i += minRun) {
            insertionSort(array, i, Math.min((i + MIN_MERGE - 1), (n - 1)));
        }

        for (int size = minRun; size < n; size = 2 * size) {
            for (int left = 0; left < n; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1), (n - 1));

                if (mid < right) {
                    merge(array, left, mid, right);
                }
            }
        }
    }

    private static void insertionSort(int[] array, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int temp = array[i];
            int j = i - 1;

            while (j >= left && array[j] > temp) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = temp;
        }
    }

    private static void merge(int[] array, int left, int mid, int right) {
        int len1 = mid - left + 1, len2 = right - mid;
        int[] leftArray = new int[len1];
        int[] rightArray = new int[len2];

        System.arraycopy(array, left, leftArray, 0, len1);
        System.arraycopy(array, mid + 1, rightArray, 0, len2);

        int i = 0, j = 0, k = left;

        while (i < len1 && j < len2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
            }
        }

        while (i < len1) {
            array[k++] = leftArray[i++];
        }

        while (j < len2) {
            array[k++] = rightArray[j++];
        }
    }

    public static void timSort(int[][] array, int[] indices, int n) {
        int minRun = minRunLength(MIN_MERGE);

        for (int i = 0; i < n; i += minRun) {
            insertionSort(array, indices, i, Math.min((i + MIN_MERGE - 1), (n - 1)));
        }

        for (int size = minRun; size < n; size = 2 * size) {
            for (int left = 0; left < n; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1), (n - 1));

                if (mid < right) {
                    merge(array, indices, left, mid, right);
                }
            }
        }
    }

    private static void insertionSort(int[][] array, int[] indices, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int temp = indices[i];
            int j = i - 1;

            while (j >= left && compare(array, indices[j], temp) > 0) {
                indices[j + 1] = indices[j];
                j--;
            }
            indices[j + 1] = temp;
        }
    }

    private static void merge(int[][] array, int[] indices, int left, int mid, int right) {
        int len1 = mid - left + 1, len2 = right - mid;
        int[] leftArray = new int[len1];
        int[] rightArray = new int[len2];

        System.arraycopy(indices, left, leftArray, 0, len1);
        System.arraycopy(indices, mid + 1, rightArray, 0, len2);

        int i = 0, j = 0, k = left;

        while (i < len1 && j < len2) {
            if (compare(array, leftArray[i], rightArray[j]) <= 0) {
                indices[k++] = leftArray[i++];
            } else {
                indices[k++] = rightArray[j++];
            }
        }

        while (i < len1) {
            indices[k++] = leftArray[i++];
        }

        while (j < len2) {
            indices[k++] = rightArray[j++];
        }
    }

    private static int compare(int[][] array, int index1, int index2) {
        if (array[index1][0] != array[index2][0]) {
            return Integer.compare(array[index1][0], array[index2][0]);
        } else {
            return Integer.compare(array[index1][1], array[index2][1]);
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

    public class Node {
        int data;
        Node prev;
        Node next;

        public Node(int data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    public class DoublyLinkedList {
        private Node head;
        private Node tail;
        private int size;

        public DoublyLinkedList() {
            this.head = null;
            this.tail = null;
            this.size = 0;
        }

        public void addNode(int data) {
            Node newNode = new Node(data);
            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
            size++;
        }

        public boolean removeNode(Node node)
        {
            if (node.prev == null)
            {
                head = node.next;
            }
            else
            {
                node.prev.next = node.next;
            }

            if (node.next == null)
            {
                tail = node.prev;
            }
            else
            {
                node.next.prev = node.prev;
            }
            size--;
            return true;
        }

        public boolean removeNode(int data) {
            Node current = head;
            while (current != null) {
                if (current.data == data) {
                    if (current == head) {
                        head = current.next;
                        if (head != null) {
                            head.prev = null;
                        }
                    } else if (current == tail) {
                        tail = current.prev;
                        if (tail != null) {
                            tail.next = null;
                        }
                    } else {
                        current.prev.next = current.next;
                        current.next.prev = current.prev;
                    }
                    size--;
                    return true;
                }
                current = current.next;
            }
            return false;
        }

        public int getSize() {
            return size;
        }
    }
}