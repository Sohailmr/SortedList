import java.util.Arrays;

public class SortedList {
    private String[] strings;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public SortedList() {
        strings = new String[DEFAULT_CAPACITY];
        size = 0;
    }

    public void add(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Cannot add null string");
        }

        if (size == strings.length) {
            strings = Arrays.copyOf(strings, strings.length * 2);
        }

        int insertIndex = findInsertIndex(str);
        for (int i = size - 1; i >= insertIndex; i--) {
            strings[i + 1] = strings[i];
        }

        strings[insertIndex] = str;
        size++;
    }

    public String get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return strings[index];
    }

    public int size() {
        return size;
    }

    public SortedList search(String str) {
        SortedList result = new SortedList();
        if (str == null) {
            return result; // Return empty list for null
        }

        // Find any match using binary search
        int low = 0;
        int high = size - 1;
        int foundIndex = -1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int cmp = strings[mid].compareTo(str);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                foundIndex = mid; // Found a match
                break;
            }
        }

        if (foundIndex == -1) {
            // No match found, return empty list
            return result;
        }

        // Found at least one match, collect all duplicates
        int start = foundIndex;
        int end = foundIndex;

        // Look left for duplicates
        while (start > 0 && strings[start - 1].equals(str)) {
            start--;
        }

        // Look right for duplicates
        while (end < size - 1 && strings[end + 1].equals(str)) {
            end++;
        }

        // Add all matching strings to the result list
        for (int i = start; i <= end; i++) {
            result.add(strings[i]);
        }

        return result;
    }

    private int findInsertIndex(String str) {
        int low = 0;
        int high = size - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int cmp = strings[mid].compareTo(str);

            if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }
}
