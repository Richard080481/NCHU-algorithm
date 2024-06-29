public class HW02_4111064232_2 extends FourSum{
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

    int[] targetArr = new int[100000];
    int answer;
    int cur, i, j, target, sum;

    @Override
    public int F_sum(int[] nums)
    {
        answer = 0;
        // mergeSort(nums, 0, nums.length-1);

        for(cur = 2; cur < nums.length; cur++)
        {
            for(i = 0; i < cur-1; i++)
            {
                sum = nums[cur-1] + nums[i];
                targetArr[sum + 50000]++;
            }

            for(j = cur + 1; j < nums.length; j++)
            {
                target = nums[cur] + nums[j];
                answer += targetArr[-target+50000];
            }
        }
        return answer;
    }


    public static void main(String[] args){
        HW02_4111064232_2 test = new HW02_4111064232_2();
        int[] test_input = {-1, 1, 1, 2, -4, 4, 8, -3};
        int[] test_input2 = {-1, -1, -1, 1, 1, 1};
        int[] test_input3 = new int[400];
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        System.out.println(test.F_sum(test_input));
        stopwatch.start();
        System.out.println(test.F_sum(test_input2));
        stopwatch.start();
        System.out.println(test.F_sum(test_input3));
    }
}