package IntSortingMethods;

public class SelectionSort extends Sort {

    void algorithm() {
    	/* You may change any code within this method */
        sort(this.data);
    }

    /* You may define any new methods you want and may change this method */
    void sort(int[] arr) {
        int n = arr.length;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n - 1; i++) {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i + 1; j < n; j++)
                if (arr[j] < arr[min_idx])
                    min_idx = j;

            // Swap the found minimum element with the first
            // element
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
            
        }
    }

    public String getAuthor() {
    	/* You MUST change the following line of code if you want credit.*/
        return "abc123 [and xyz456]";
    }

}

