package IntSortingMethods;

public class GrossSort extends Sort {

	void algorithm() {
		sort(0, data.length);
	}

	void linearPass(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			int diff = arr[i + 1] - arr[i];

		}
	}

	/* Perform the partitioning step of QuickSort: */
	public void sort(int start, int len) {
		// Handle the base case:
		if(len - start < 2) {
			return;
		}

		// TODO: Select an optimal pivot point:
		int pivot = start;

		// Perform the necessary swap operations:
		for(int i = start; i < len; ++i) {
			if(data[i] < data[pivot]) {
				int tmp = data[pivot];
				data[pivot] = data[i];
				data[i] = data[pivot + 1];
				data[pivot + 1] = tmp;
				++pivot;
			}
		}

		sort(start, pivot);
		sort(pivot + 1, len);
	}

	public String getAuthor() {
		return "crg222 and brr322";
	}

}

