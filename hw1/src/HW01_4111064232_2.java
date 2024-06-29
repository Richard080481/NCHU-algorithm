public class HW01_4111064232_2 extends ArrayData{
    public static final int numThread = 2;
    private int[] A;
    private int[] m_min = new int[numThread];
    private int[] m_max = new int[numThread];
    private MyWorker[] myWorkers;

    class MyWorker extends Thread{
        private int lo;
        private int hi;
        private int tid;

        public void setup(int lo, int hi, int tid){
            this.lo = lo;
            this.hi = hi;
            this.tid = tid;
        }

        // [lo, hi)
        public void run(){
            int t_min = A[lo];
            int t_max = A[lo];
            for(int i = lo + 1; i < hi; i++){
                if(A[i] < t_min){
                    t_min = A[i];
                }else if(A[i] > t_max){
                    t_max = A[i];
                }
            }

            m_min[tid] = t_min;
            m_max[tid] = t_max;
        }
    }

    public HW01_4111064232_2(int[] A){
        this.A = A;
        myWorkers = new MyWorker[numThread];

        for(int i = 0; i < numThread; i++){
            myWorkers[i] = new MyWorker();
            myWorkers[i].setup(A.length / numThread * i, A.length / numThread * (i+1), i);
        }

        for(int i = 0; i < numThread; i++){
            myWorkers[i].start();
        }

        try{
            for(int i = 0; i < numThread; i++){
                myWorkers[i].join();
            }
            for(int i = 1; i < numThread; i++){
                if(m_min[i] < m_min[0]){
                    m_min[0] = m_min[i];
                }
                if(m_max[i] > m_max[0]){
                    m_max[0] = m_max[i];
                }
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public int min(){
        return m_min[0];
    }

    public int max(){
        return m_max[0];
    }

    public static void main(String[] args){
        int[] A = {-100, 5, 2022, 45, 666, 90, 87, -55, 123, -88888};
        HW01_4111064232_2 test = new HW01_4111064232_2(A);
        System.out.println("Minimum value: " + test.min());
        System.out.println("Maximum value: " + test.max());
    }
}