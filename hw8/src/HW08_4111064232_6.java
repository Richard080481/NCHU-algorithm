import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HW08_4111064232_1 extends LLK{
    public static void main(String[] args)
    {
        HW08_4111064232_1 test = new HW08_4111064232_1();
        int[][] input1 = {{11, 3}, {1, 0}, {-9, -3}};
        int[][] input2 = readDataFromFile("hw8_public_data_1.txt");
        int[][] input3 = readDataFromFile("hw8_public_data_2.txt");
        int[][] input4 = {{1, 3}, {1, 0}, {1, 2}};
        int[][] input5 = {{3, 1}, {0, 1}, {2, 1}};
        int[][] input6 = {{1, 1}, {2, 1}, {3, 2}};
        int[][] input7 = {{0, 0}, {1, 0}, {0, 1}, {1, 1}};
        int[][] input8 = {{1, 1}, {1, 1}, {2, 1}, {3, 2}};
        System.out.printf("%b\n", test.checkLLK(input1) == true);
        System.out.printf("%b\n", test.checkLLK(input2) == true);
        System.out.printf("%b\n", test.checkLLK(input3) == false);
        System.out.printf("%b\n", test.checkLLK(input4) == true);
        System.out.printf("%b\n", test.checkLLK(input5) == true);
        System.out.printf("%b\n", test.checkLLK(input6) == false);
        System.out.printf("%b\n", test.checkLLK(input7) == false);
        System.out.printf("%b\n", test.checkLLK(input8) == true);
    }

    private static int[][] readDataFromFile(String fileName)
    {
        List<int[]> tempList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                if (line.equalsIgnoreCase("true") || line.equalsIgnoreCase("false"))
                {
                    break;
                }
                line = line.replaceAll("[{}]", "");
                String[] pairs = line.split(",\\s*");
                for (int i = 0; i < pairs.length; i += 2)
                {
                    int[] row = new int[2];
                    row[0] = Integer.parseInt(pairs[i]);
                    row[1] = Integer.parseInt(pairs[i + 1]);
                    tempList.add(row);
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

        int[][] arr = new int[tempList.size()][2];
        for (int i = 0; i < tempList.size(); i++) {
            arr[i] = tempList.get(i);
        }

        return arr;
    }

    @Override
    public boolean checkLLK(int[][] array)
    {
        int n = array.length;
        if (n < 3)
        {
            return false;
        }
        for (int i = n-1; i >= 0; i--)
        {
            MyHashSet processedSlopes = new MyHashSet();

            for (int j = i-1; j >= 0; j--)
            {
                if (i != j)
                {
                    int dx = array[j][0] - array[i][0];
                    int dy = array[j][1] - array[i][1];

                    if (dx == 0 && dy == 0)
                    {
                        return true;
                    }

                    int gcd = gcd(dy, dx);

                    dx /= gcd;
                    dy /= gcd;

                    if (dx < 0)
                    {
                        dx = -dx;
                        dy = -dy;
                    } else if (dx == 0)
                    {
                        dy = Math.abs(dy);
                    }

                    String slopeKey = dy + "/" + dx;
                    if (!processedSlopes.contains(slopeKey))
                    {
                        processedSlopes.add(slopeKey);
                    }
                    else
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int gcd(int a, int b)
    {
        if (b == 0)
        {
            return Math.abs(a);
        }
        return gcd(b, a % b);
    }

    class MyHashSet
    {
        private class Entry
        {
            String key;
            Entry next;

            Entry(String key)
            {
                this.key = key;
                this.next = null;
            }
        }

        private final int SIZE = 1000;
        private Entry[] table;

        public MyHashSet()
        {
            table = new Entry[SIZE];
        }

        private int getIndex(String key)
        {
            return Math.abs(key.hashCode()) % SIZE;
        }

        public void add(String key)
        {
            int index = getIndex(key);
            Entry newEntry = new Entry(key);
            if (table[index] == null)
            {
                table[index] = newEntry;
            } else
            {
                Entry current = table[index];
                while (current != null)
                {
                    if (current.key.equals(key))
                    {
                        return;
                    }
                    if (current.next == null)
                    {
                        current.next = newEntry;
                        return;
                    }
                    current = current.next;
                }
            }
        }

        public boolean contains(String key)
        {
            int index = getIndex(key);
            Entry current = table[index];
            while (current != null)
            {
                if (current.key.equals(key))
                {
                    return true;
                }
                current = current.next;
            }
            return false;
        }
    }
}