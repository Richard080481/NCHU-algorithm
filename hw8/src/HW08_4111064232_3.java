public class HW08_4111064232_3 extends LLK{
    @Override
    public boolean checkLLK(int[][] array)
    {
        if(array.length <= 2)
        {
            return true;
        }
        double deltaX = 0.0, deltaY = 0.0;
        int index = 1;
        for (; index < array.length; index++) {
            if (array[index][0] != array[0][0] || array[index][1] != array[0][1]) {
                deltaX = (double)array[index][0] - array[0][0];
                deltaY = (double)array[index][1] - array[0][1];
                break;
            }
        }
        for (; index < array.length; index++)
        {
            double deltaX2 = (double)array[index][0] - array[0][0];
            double deltaY2 = (double)array[index][1] - array[0][1];
            if (deltaX * deltaY2 != deltaY * deltaX2) {
                return false;
            }
        }
        return true;
    }
}