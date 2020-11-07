package IntSortingMethods;

public class GrossSort extends Sort {

	void algorithm() {
		/* You may change any code within this method */
		linearPass(this.data);
		sort(this.data);
	}

	void linearPass(int[] arr) {
		float diffSum = 0;
		int count = 0;

		StdDevResult stdDevResult = calcStandardDeviation(arr);

		double min = stdDevResult.mean - stdDevResult.standardDeviation;
		double max = stdDevResult.mean + stdDevResult.standardDeviation;

		for (int i = 0; i < arr.length - 1; i++) {
			// check to make sure that the elements are within one standard deviation
			if (arr[i] < min || arr[i] > max || arr[i + 1] < min || arr[i + 1] > max) {
				continue;
			}

			int diff = arr[i + 1] - arr[i];
			diffSum += diff;
			count++;
		}

		float avgDiff = diffSum / count;

		if (avgDiff < 0) {
			arr = reverseArray(arr);
		}

		System.out.println("Min: " + min + ", Max: " + max);
		System.out.println("Average difference between array values: " + avgDiff);
	}

	int[] reverseArray(int[] arr) {
		int low = 0;
		int high = arr.length - 1;

		while (low < high) {
			int temp = arr[low];
			arr[low] = arr[high];
			arr[high] = temp;

			low++;
			high--;
		}

		return arr;
	}

	StdDevResult calcStandardDeviation(int[] arr) {
		double sum = 0.0;
		double standardDeviation = 0.0;
		int count = arr.length;

		for (int num : arr) {
			sum += num;
		}

		double mean = sum / count;

		for (int num : arr) {
			standardDeviation += Math.pow(num - mean, 2);
		}

		standardDeviation = Math.sqrt(standardDeviation / count);

		return new StdDevResult(standardDeviation, mean);
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

	static class StdDevResult {
		public double standardDeviation;
		public double mean;

		public StdDevResult(double standardDeviation, double mean) {
			this.standardDeviation = standardDeviation;
			this.mean = mean;
		}
	}
}
