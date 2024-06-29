public class HW02_4111064232_4 extends FourSum{
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

    @Override
    public int F_sum(int[] nums)
    {
        answer = 0;
        int[] targetArr = new int[100000];
        // mergeSort(nums, 0, nums.length-1);

        for(int cur = 2; cur < nums.length; cur++)
        {
            for(int i = 0; i < cur-1; i++)
            {
                int sum = nums[cur-1] + nums[i];
                targetArr[sum + 50000]++;
            }

            for(int j = cur + 1; j < nums.length; j++)
            {
                int target = nums[cur] + nums[j];
                answer += targetArr[-target+50000];
            }
        }
        return answer;
    }

    int answer;

    public static void main(String[] args){
        HW02_4111064232_4 test = new HW02_4111064232_4();
        int[] test_input = {-1, 1, 1, 2, -4, 4, 8, -3};
        int[] test_input2 = {-1, -1, -1, 1, 1, 1};
        // Random random = new Random(4111);
        // int[] test_input3 = new int[1000];
        // for (int i = 0; i < test_input3.length; i++) {
        //     test_input3[i] = random.nextInt(2001) - 1000; // Generate random integers
        // }
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        System.out.println(test.F_sum(test_input));
        stopwatch.start();
        System.out.println(test.F_sum(test_input2));
    }
}