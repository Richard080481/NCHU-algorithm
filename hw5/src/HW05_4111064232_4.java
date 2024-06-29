public class HW05_4111064232_4 extends WordChain
{
    public class HashMap {
        private static final int INITIAL_CAPACITY = 256;
        private static final double LOAD_FACTOR = 0.75;

        private int size;
        private int capacity;
        private Entry[] entries;

        public HashMap() {
            this.capacity = INITIAL_CAPACITY;
            this.entries = new Entry[capacity];
        }

        public boolean containsKey(String key) {
            int index = hash(key);
            for (Entry entry = entries[index]; entry != null; entry = entry.next) {
                if (entry.key.equals(key)) {
                    return true;
                }
            }
            return false;
        }

        public void put(String key, int value) {
            int index = hash(key);
            Entry entry = new Entry(key, value, null);
            entry.next = entries[index];
            entries[index] = entry;

            size++;
            if (size > capacity * LOAD_FACTOR) {
                resize();
            }
        }

        public int get(String key) {
            Entry entry = entries[hash(key)];
            while (entry != null) {
                if (entry.key.equals(key)) {
                    return entry.value;
                }
                entry = entry.next;
            }
            return -1; // Or whatever default value you choose
        }

        private int hash(String key) {
            return (key.hashCode() & 0x7fffffff) % capacity;
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

        public class Entry {
            String key;
            int value;
            Entry next;

            Entry(String key, int value, Entry next) {
                this.key = key;
                this.value = value;
                this.next = next;
            }
        }
    }

    public class ResizableArray {
        private Entry[] array;
        private int size;
        private int capacity;
        private int totalCount;

        public ResizableArray(int initialCapacity) {
            this.array = new Entry[initialCapacity];
            this.size = 0;
            this.capacity = initialCapacity;
            this.totalCount = 0;
        }

        public int findIndex(int key)
        {
            for(int i = 0; i < size; i++)
            {
                if(array[i].key == key)
                {
                    return i;
                }
            }
            return -1;
        }

        public void add(int key) {
            if (size == capacity) {
                resize();
            }
            int tempId = findIndex(key);
            if(tempId != -1)
            {
                array[tempId].count++;
            }
            else
            {
                Entry element = new Entry(key, 1);
                array[size++] = element;
            }
            totalCount++;
        }

        private void resize() {
            int newCapacity = capacity * 2;
            Entry[] newArray = new Entry[newCapacity];
            for (int i = 0; i < size; i++) {
                newArray[i] = array[i];
            }
            array = newArray;
            capacity = newCapacity;
        }

        public void printAll()
        {
            for (int i = 0; i < size; i++)
            {
                System.out.printf("%d %d, ", array[i].key, array[i].count);
            }
            System.out.println();
        }

        public int sumAllValue(){
            return totalCount;
        }

        public Entry get(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            return array[index];
        }

        public int size() {
            return size;
        }

        public int capacity() {
            return capacity;
        }

        private class Entry {
            int count;
            int key;

            Entry(int key, int count) {
                this.key = key;
                this.count = count;
            }
        }
    }
    int i, j, index, left, right, depth, col, btindex, neighborID;
    float neighbors, possiblity, best;
    String[] splitStrings, tokens;
    ResizableArray[] adjList;
    HashMap hashMap;
    Couplet[][] dp;
    @Override
    public String sequenceProbability(String[] target)
    {
        splitStrings = target[1].split(" ");
        adjList = new ResizableArray[splitStrings.length];
        hashMap = new HashMap();
        tokens = new String[splitStrings.length-1];
        index = 0;
        hashMap.put(splitStrings[0], index);
        tokens[index] = splitStrings[0];
        adjList[index] = new ResizableArray(splitStrings.length);
        left = index++;
        right = -1;
        for(i = 1; i < splitStrings.length; i++, left = right)
        {
            if(!hashMap.containsKey(splitStrings[i]))
            {
                hashMap.put(splitStrings[i], index);
                tokens[index] = splitStrings[i];
                adjList[index] = new ResizableArray(splitStrings.length);
                right = index++;
            }
            else
            {
                right = hashMap.get(splitStrings[i]);
            }
            adjList[left].add(right);
        }
        dp = new Couplet[4][index];
        for(i = 0; i < 4; i++)
        {
            dp[i] = new Couplet[index];
            for(j = 0; j < index; j++)
            {
                dp[i][j] = new Couplet();
            }
        }
        dp[0][hashMap.get(target[0])].possiblity = 1.0f;

        for(depth = 0; depth < 3; depth++)
        {
            for(col = 0; col < index; col++)
            {
                if(dp[depth][col].possiblity == 0) continue;
                neighbors = (float)(adjList[col].sumAllValue());
                for(i = 0; i < adjList[col].size(); i++)
                {
                    possiblity = dp[depth][col].possiblity * ((float)(adjList[col].get(i).count) / neighbors);
                    neighborID = adjList[col].get(i).key;
                    if(possiblity > dp[depth+1][neighborID].possiblity)
                    {
                        dp[depth+1][neighborID].possiblity = possiblity;
                        dp[depth+1][neighborID].previous = col;
                    }
                }
            }
        }
        best = 0.0f;
        btindex = 0;
        for(col = 0; col < index; col++)
        {
            if(dp[3][col].possiblity > best)
            {
                best = dp[3][col].possiblity;
                btindex = col;
            }
        }

        String answer = tokens[btindex];
        for(depth = 3; depth > 0; depth--)
        {
            btindex = dp[depth][btindex].previous;
            answer = tokens[btindex] + " " + answer;
        }
        return answer;
    }

    public static class Couplet{
        float possiblity;
        int previous;
        Couplet()
        {
            possiblity = 0;
            previous = 0;
        }
        Couplet(float possiblity, int previous){
            this.possiblity = possiblity;
            this.previous = previous;
        }
    }
}