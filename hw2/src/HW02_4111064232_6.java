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
    private class Quadruple{
        public Quadruple(int x, int y, int z, int w){
            arr[0] = x;
            arr[1] = y;
            arr[2] = z;
            arr[3] = w;
            for(int i = 1; i < arr.length; ++i){
                int key = arr[i];
                int j = i - 1;
                while (j >= 0 && arr[j] > key) {
                    arr[j + 1] = arr[j--];
                }
                arr[j + 1] = key;
            }
        }

        public boolean equals(Quadruple other){
            return (other.arr[0] == arr[0]) &&
                   (other.arr[1] == arr[1]) &&
                   (other.arr[2] == arr[2]) &&
                   (other.arr[3] == arr[3]);
        }
        public int[] arr = new int[4];
    }
    private class Set{
        public Set(int numBucket){
            bucket = new Quadruple[numBucket*(numBucket-1)*(numBucket-2)*(numBucket-3)/24];
            size = 0;
        }

        public boolean contains(Quadruple target){
            for(int i = 0; i < size; i++){
                if(bucket[i].equals(target)){
                    return true;
                }
            }
            return false;
        }

        public boolean insert(Quadruple item){
            if(!contains(item)){
                bucket[size++] = item;
                return true;
            }
            return false;
        }

        private int size;
        private Quadruple[] bucket;
    }


    private void mergesort(int[] indices, int[] nums, int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergesort(indices, nums, low, mid);
            mergesort(indices, nums, mid + 1, high);
            merge(indices, nums, low, mid, high);
        }
    }

    private void merge(int[] indices, int[] nums, int low, int mid, int high) {
        int leftLength = mid - low + 1;
        int rightLength = high - mid;

        int[] leftIndices = new int[leftLength];
        int[] rightIndices = new int[rightLength];

        for (int i = 0; i < leftLength; i++) {
            leftIndices[i] = indices[low + i];
        }

        for (int j = 0; j < rightLength; j++) {
            rightIndices[j] = indices[mid + 1 + j];
        }

        int i = 0, j = 0, k = low;

        while (i < leftLength && j < rightLength) {
            if (nums[leftIndices[i]] <= nums[rightIndices[j]]) {
                indices[k++] = leftIndices[i++];
            } else {
                indices[k++] = rightIndices[j++];
            }
        }

        while (i < leftLength) {
            indices[k++] = leftIndices[i++];
        }

        while (j < rightLength) {
            indices[k++] = rightIndices[j++];
        }
    }


    public boolean isOverlap(int left, int right, int[][] id2pair){
        return((id2pair[left][0] == id2pair[right][0]) ||
               (id2pair[left][0] == id2pair[right][1]) ||
               (id2pair[left][1] == id2pair[right][0]) ||
               (id2pair[left][1] == id2pair[right][1]));
    }

    @Override
    public int F_sum(int[] input_array){
        int[] sum_array = new int[(input_array.length*(input_array.length-1))/2];
        int[] indices = new int[sum_array.length];
        int[][] id2pair = new int[sum_array.length][2];
        int counter = 0;
        int left = 0;
        int right = sum_array.length-1;
        int answer = 0;
        Set condidates = new Set(input_array.length);
        for(int i = 0; i < indices.length; i++){
            indices[i] = i;
        }
        for(int i = 0; i < input_array.length; i++){
            for(int j = i + 1; j < input_array.length; j++){
                sum_array[counter] = input_array[i] + input_array[j];
                id2pair[counter][0] = i;
                id2pair[counter][1] = j;
                // System.out.printf("counter:%d %d %d sumArr:%d\n", counter, i, j, sum_array[counter]);
                counter++;
            }
        }
        mergesort(indices, sum_array, 0, sum_array.length-1);
        // for(int i = 0; i < indices.length; i++){
        //     System.out.printf("%d %d\n", indices[i], sum_array[indices[i]]);
        // }
        // System.out.println();
        while(left < right){
            int left_id = indices[left];
            int right_id = indices[right];
            int target = sum_array[left_id] + sum_array[right_id];
            // System.out.printf("left_id:%d, right_id:%d, %d = %d + %d\n", left_id, right_id, target, sum_array[left_id], sum_array[right_id]);
            if(target == 0){
                boolean Overlap = isOverlap(left_id, right_id, id2pair);
                // System.out.printf("Overlap: %b, %d %d left:%d %d right:%d %d\n", Overlap, left_id, right_id, id2pair[left_id][0], id2pair[left_id][1], id2pair[right_id][0], id2pair[right_id][1]);
                if(!Overlap){
                    Quadruple item = new Quadruple(id2pair[left_id][0], id2pair[left_id][1], id2pair[right_id][0], id2pair[right_id][1]);
                    if(condidates.insert(item)){
                        // System.out.println(item);
                        answer++;
                    }
                }
                for(int lo = left; lo < right; lo++){
                    if(sum_array[indices[lo]] != sum_array[indices[left]]) break;
                    for(int hi = right; hi > lo; hi--){
                        if((sum_array[indices[hi]] != sum_array[indices[right]]) || isOverlap(indices[lo], indices[hi], id2pair)) break;
                        // System.out.printf("%d %d %d\n", answer, lo, hi);
                        Quadruple item2 = new Quadruple(id2pair[indices[lo]][0], id2pair[indices[lo]][1], id2pair[indices[hi]][0], id2pair[indices[hi]][1]);
                        if(condidates.insert(item2)){
                            answer++;
                        }
                    }
                }

                while((left < right) && (sum_array[indices[left]] == sum_array[indices[left+1]])) left++;
                while((left < right) && (sum_array[indices[right]] == sum_array[indices[right-1]])) right--;
                left++;
                right--;
            }else if(target < 0){
                left++;
            }else if(target > 0){
                right--;
            }
        }
        return answer;
    }

    int answer = 0;
    int[] input_array;

    public static void main(String[] args){
        HW02_4111064232_1 test = new HW02_4111064232_1();
        int[] test_input = {-1, 1, 1, 2, -4, 4, 8, -3};
        int[] test_input2 = {-1, -1, -1, 1, 1, 1};
        int[] test_input3 = new int[40];
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        System.out.println(test.F_sum(test_input) + ", " + stopwatch.stop() / 1e6);
        stopwatch.start();
        System.out.println(test.F_sum(test_input2) + ", " + stopwatch.stop() / 1e6);
        stopwatch.start();
        System.out.println(test.F_sum(test_input3) + ", " + stopwatch.stop() / 1e6);
    }
}