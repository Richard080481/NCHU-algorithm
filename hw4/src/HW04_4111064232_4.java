public class HW04_4111064232_4 extends LanguageModel
{
    public static class HashMap {
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

        public static class Entry {
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

    int frequency;
    String answer;
    HashMap hashMap;
    String target;
    String[] parts;
    int i;
    int currentFre;

    @Override
    public String nextPredictToken(String[] arrsStrings)
    {
        hashMap = new HashMap();
        frequency = 0;
        answer = "";
        target = arrsStrings[0];
        parts = arrsStrings[1].split(" ");
        for(i = 0; i < parts.length; i++)
        {
            // System.out.printf("i:%d target:\"%s\" part[i]:\"%s\"\n", i, target, parts[i]);
            if(target.equals(parts[i]))
            {
                // System.out.printf("hit!!%s\n", parts[i]);
                if(hashMap.containsKey(parts[i+1]))
                {
                    currentFre = hashMap.get(parts[i+1])+1;
                    hashMap.put(parts[i+1], currentFre);
                    if(currentFre > frequency)
                    {
                        frequency = currentFre;
                        answer = parts[i+1];
                    }
                }
                else
                {
                    hashMap.put(parts[i+1], 1);
                }
            }
        }
        return answer;
    }
}