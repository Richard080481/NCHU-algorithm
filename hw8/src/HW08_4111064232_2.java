public class HW08_4111064232_2 extends LLK{
    @Override
    public boolean checkLLK(int[][] array)
    {
        int n = array.length;
        if (n < 3)
        {
            return false;
        }
        for (int i = 0; i < n; i++)
        {
            MyHashSet processedSlopes = new MyHashSet();

            for (int j = i+1; j < n; j++)
            {
                if (i != j)
                {
                    long dx = array[j][0] - array[i][0];
                    long dy = array[j][1] - array[i][1];

                    if (dx == 0 && dy == 0)
                    {
                        return true;
                    }

                    long gcd = gcd(dy, dx);

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

    private long gcd(long a, long b)
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