public class HW03_4111064232_4 extends DogeCoin{
    int buy;
    int profit;
    int i;

    @Override
    public int doge(int[] price)
    {
        buy = price[0];
        profit = 0;
        for (i = 1; i < price.length; i++) {
            if (price[i] < buy) {
                buy = price[i];
            } else if (price[i] - buy > profit) {
                profit = price[i] - buy;
            }
        }
        return profit;
    }

    public static void main(String[] args)
    {
        HW03_4111064232_4 test = new HW03_4111064232_4();
        int[] testInput = {7, 1, 3, 12, 0, 10};
        int[] testInput2 = {7, 6, 4, 3, 1};
        int[] testInput3 = {7, 1, 5, 3, 6, 4};
        System.out.printf("%d\n", test.doge(testInput));
        System.out.printf("%d\n", test.doge(testInput2));
        System.out.printf("%d\n", test.doge(testInput3));
    }
}
