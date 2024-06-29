public class HW02_4111064232_1 extends FourSum{
    public static class Stopwatch {
        private long startTime;
        private boolean running;

        public void start() {
            if (!running) {
                startTime = System.nanoTime();
                running = true;
            }
        }

        public long stop() {
            if (running) {
                running = false;
                return System.nanoTime() - startTime;
            }
            return 0;
        }
    }

    private void mergeSort(int[] nums, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSort(nums, low, mid);
            mergeSort(nums, mid + 1, high);
            merge(nums, low, mid, high);
        }
    }

    private void merge(int[] nums, int low, int mid, int high) {
        int leftLength = mid - low + 1;
        int rightLength = high - mid;

        int[] leftArray = new int[leftLength];
        int[] rightArray = new int[rightLength];

        for (int i = 0; i < leftLength; i++) {
            leftArray[i] = nums[low + i];
        }

        for (int j = 0; j < rightLength; j++) {
            rightArray[j] = nums[mid + 1 + j];
        }

        int i = 0, j = 0, k = low;

        while (i < leftLength && j < rightLength) {
            if (leftArray[i] <= rightArray[j]) {
                nums[k++] = leftArray[i++];
            } else {
                nums[k++] = rightArray[j++];
            }
        }

        while (i < leftLength) {
            nums[k++] = leftArray[i++];
        }

        while (j < rightLength) {
            nums[k++] = rightArray[j++];
        }
    }

    class HashMap {
        private static final int INITIAL_CAPACITY = 256;
        private static final double LOAD_FACTOR = 0.75;

        private int size;
        private int capacity;
        private Entry[] entries;

        public HashMap() {
            this.capacity = INITIAL_CAPACITY;
            this.entries = new Entry[capacity];
        }

        public boolean containsKey(int key){
            int index = hash(key);
            for (Entry entry = entries[index]; entry != null; entry = entry.next) {
                if (entry.key == key) {
                    return true;
                }
            }
            return false;
        }

        public void put(int key, int value1, int value2) {
            if (value1 >= value2) {
                // Swap value1 and value2 to ensure value1 < value2
                int temp = value1;
                value1 = value2;
                value2 = temp;
            }

            int index = hash(key);

            Entry entry = new Entry(key, value1, value2, null);
            // System.out.printf("%d %d %d\n", key, value1, value2);
            entry.next = entries[index];
            entries[index] = entry;

            // for(Entry it = entry; it != null; it = it.next){
            //     System.out.printf("key:%d hash:%d value1:%d value2:%d ", it.key, hash(it.key), it.value1, it.value2);
            // }
            // System.out.printf("\n");

            size++;
            if (size > capacity * LOAD_FACTOR) {
                resize();
            }
        }

        public int hash(int key){
            return (key % capacity + capacity) % capacity;
        }

        public Entry get(int key){
            return entries[hash(key)];
        }

        private void resize() {
            capacity = capacity * 2;
            Entry[] newEntries = new Entry[capacity];

            for (Entry entry : entries) {
                while (entry != null) {
                    int index = hash(entry.key);
                    Entry next = entry.next;
                    entry.next = newEntries[index];
                    newEntries[index] = entry;
                    entry = next;
                }
            }

            entries = newEntries;
        }
    }

    public static class Entry {
        int key;
        int value1;
        int value2;
        Entry next;
        Entry(int key, int value1, int value2, Entry next){
            this.key = key;
            this.value1 = value1;
            this.value2 = value2;
            this.next = next;
        }
    }

    @Override
    public int F_sum(int[] input_array){
        answer = 0;
        HashMap map = new HashMap();
        // mergeSort(input_array, 0, input_array.length-1);
        for(int i = 0; i < input_array.length-1; i++){
            for(int j = i+1; j < input_array.length; j++){
                int sum = input_array[i] + input_array[j];
                // System.out.printf("%d %d sum:%d\n", i, j, sum);
                map.put(sum, i, j);
            }
        }

        for(int i = 0; i < input_array.length-1; i++){
            for(int j = i+1; j < input_array.length; j++){
                int sum = input_array[i] + input_array[j];
                for(Entry it = map.get(-sum); it != null; it = it.next){
                    // System.out.printf("key:%d sum:%d\n", it.key, sum);
                    if(it.key != -sum){
                        // System.out.printf("Continue..\n");
                        continue;
                    }

                    int x = it.value1;
                    int y = it.value2;
                    // System.out.printf("%d %d %d %d\n%d %d %d %d\n", i, j, x, y,
                    // input_array[i], input_array[j], input_array[x], input_array[y]);
                    if(i==x || i==y || j==x || j==y) continue;
                    else if((j < x) && (x < y)){
                        answer++;
                    }
                }
            }
        }

        return answer;
    }


    int answer;
    int[] input_array;

    public static void main(String[] args){
        HW02_4111064232_1 test = new HW02_4111064232_1();
        int[] test_input = {-1, 1, 1, 2, -4, 4, 8, -3};
        int[] test_input2 = {-1, -1, -1, 1, 1, 1};
        int[] test_input3 = new int[160];
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        System.out.println(test.F_sum(test_input) + ", " + stopwatch.stop() / 1e6);
        stopwatch.start();
        System.out.println(test.F_sum(test_input2) + ", " + stopwatch.stop() / 1e6);
        float totaltime = 0;
        for(int i = 0; i < 100; i++){
            stopwatch.start();
            test.F_sum(test_input3);
            totaltime += stopwatch.stop() / 1e6;
        }
        System.out.println("Total:" + totaltime / 100.0 + "\n");
    }
}