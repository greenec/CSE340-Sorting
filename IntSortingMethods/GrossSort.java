package IntSortingMethods;

public class GrossSort extends Sort {
	/* Define a class which can serve as our thread runner: */
	private class SortThread extends Thread {
		private int data[];
		private int start;
		private int len;

		/* Define a constructor to set all of the internal fields: */
		public SortThread(int data[], int start, int len) {
			this.data = data;
			this.start = start;
			this.len = len;
		}

		/* Perform the partitioning step of QuickSort: */
		@Override public void run() {
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

			/* Spawn a child thread to process 50% of the remaining work: */
			/* TODO: Don't spawn a thread if that thread's base-case is met */
			SortThread t1 = new SortThread(data, start, pivot);
			t1.start();

			/* Recursively process the next section: */
			start = pivot + 1;
			run();

			/* Attempt to join the child thread: */
			try {
				t1.join();
			} catch(InterruptedException e) {
				System.err.println("ERROR: Sorting thread was interrupted unexpectedly");
				System.exit(1);
			}
		}
	}

	void linearPass(int[] arr) {
		float diffSum = 0;
		int count = 0;

		StdDevResult stdDevResult = calcStandardDeviation(arr);

		double min = stdDevResult.mean - stdDevResult.standardDeviation;
		double max = stdDevResult.mean + stdDevResult.standardDeviation;

		for (int i = 0; i < arr.length - 1; i++) {
			// skip over outliers in the data set when analyzing the array
			if (arr[i] < min || arr[i] > max || arr[i + 1] < min || arr[i + 1] > max) {
				continue;
			}

			int diff = arr[i + 1] - arr[i];
			diffSum += diff;
			count++;
		}

		// calculate metrics
		float avgDiff = diffSum / count;

		if (avgDiff < 0) {
			arr = reverseArray(arr);
		}

		// System.out.println("Min: " + min + ", Max: " + max);
		// System.out.println("Average difference between array values: " + avgDiff);
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

	/* Spawn a new thread to run the sorting algorithm on the provided array: */
	/* TODO: The main thread should be included in the thread pool, and shouldn't just be waiting around here */
	void algorithm() {
		linearPass(this.data);
		SortThread sorter = new SortThread(data, 0, data.length);
		sorter.start();
		try {
			sorter.join();
		} catch(InterruptedException e) {
			System.err.println("ERROR: Sorting thread was interrupted unexpectedly");
			System.exit(1);
		}

		Runtime runtime = Runtime.getRuntime();

		// runtime.gc();

		long memory = runtime.totalMemory() - runtime.freeMemory();
		double memoryMB = memory / 1024.0 / 1024.0;
		System.out.println("Used memory: " + memoryMB + " MB");
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

