package IntSortingMethods;

public class GrossSort extends Sort {

	void algorithm() {
		/* You may change any code within this method */
		sort(this.data);
	}

	void linearPass(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			int diff = arr[i + 1] - arr[i];

		}
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

			// Swap the found minimum element with the first element
			int temp = arr[min_idx];
			arr[min_idx] = arr[i];
			arr[i] = temp;

		}
	}

	public String getAuthor() {
		return "crg222 and brr322";
	}

}

