public class HW01_4111064232_1 extends ArrayData{
    private int[] A;
    private int max;
    private int min;

    public HW01_4111064232_1(int[] A){
        this.A = A;
        min = A[0];
        max = A[0];
        for(int num : A){
            if(num < min){
                min = num;
            }else if(num > max){
                max = num;
            }
        }
    }

    public int min(){
        return min;
    }

    public int max(){
        return max;
    }

    public static void main(String[] args){
        int[] A = {-100, 5, 2022, 45, 666, 90, 87, -55, 123, -88888};
        HW01_4111064232_1 test = new HW01_4111064232_1(A);
        System.out.println("Minimum value: " + test.min());
        System.out.println("Maximum value: " + test.max());
    }
}