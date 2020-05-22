import java.util.HashMap;
import java.util.Map;

public class LetterInventory implements Comparable<LetterInventory> {
    private static final int MAX = 26;
    private static final Map<String, int[]> ALL_COUNTS = new HashMap();
    private static int INSTANCE_COUNT = 0;
    private int[] counts;

    public static int getInstanceCount() {
        return INSTANCE_COUNT;
    }

    public static void resetInstanceCount() {
        INSTANCE_COUNT = 0;
    }

    public static void clearCache() {
        ALL_COUNTS.clear();
    }

    public LetterInventory(LetterInventory other) {
        this.counts = other.counts;
    }

    public LetterInventory(String s) {
        if (s == null) {
            throw new NullPointerException("constructor's parameter cannot be null");
        } else {
            if (ALL_COUNTS.containsKey(s)) {
                this.counts = (int[])ALL_COUNTS.get(s);
            } else {
                ++INSTANCE_COUNT;
                this.counts = new int[27];
                s = s.toLowerCase();

                for(int i = 0; i < s.length(); ++i) {
                    char ch = s.charAt(i);
                    if ('a' <= ch && ch <= 'z') {
                        ++this.counts[ch - 97];
                        ++this.counts[26];
                    }
                }

                ALL_COUNTS.put(s, this.counts);
            }

        }
    }

    public void add(LetterInventory other) {
        this.counts = this.arrayCopy(this.counts);

        for(int i = 0; i < 26; ++i) {
            this.counts[i] += other.counts[i];
        }

        this.counts[26] += other.size();
    }

    public void add(String s) {
        this.add(new LetterInventory(s));
    }

    public int compareTo(LetterInventory other) {
        return this.toString().compareTo(other.toString());
    }

    public boolean contains(LetterInventory other) {
        for(int i = 26; i >= 0; --i) {
            if (this.counts[i] < other.counts[i]) {
                return false;
            }
        }

        return true;
    }

    public boolean contains(String s) {
        return this.contains(new LetterInventory(s));
    }

    public boolean equals(Object o) {
        if (o instanceof LetterInventory) {
            LetterInventory other = (LetterInventory)o;
            return this.compareTo(other) == 0;
        } else {
            return false;
        }
    }

    public int hashCode() {
        int code = 0;
        int multiplier = 37;

        for(int i = 0; i < 26; ++i) {
            code += this.counts[i] * multiplier * (97 + i);
            multiplier += 58;
        }

        return code;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public int size() {
        return this.counts[26];
    }

    public void subtract(LetterInventory other) {
        this.counts = this.arrayCopy(this.counts);

        for(int i = 0; i < 26; ++i) {
            this.counts[i] -= other.counts[i];
            this.checkCount(this.counts[i], (char)(97 + i), other);
        }

        this.counts[26] -= other.size();
    }

    public void subtract(String s) {
        this.subtract(new LetterInventory(s));
    }

    public String toString() {
        StringBuilder result = new StringBuilder(27);
        result.append("[");

        for(int i = 0; i < 26; ++i) {
            for(int j = this.counts[i]; j > 0; --j) {
                result.append((char)(97 + i));
            }
        }

        result.append("]");
        return result.toString();
    }

    private int[] arrayCopy(int[] a) {
        int[] a2 = new int[a.length];
        System.arraycopy(a, 0, a2, 0, a.length);
        ++INSTANCE_COUNT;
        return a2;
    }

    private void checkCount(int count, char c, Object other) {
        if (count < 0) {
            throw new IllegalArgumentException("\"" + this + "\" does not contain enough '" + c + "' to subtract \"" + other + "\"");
        }
    }
}
