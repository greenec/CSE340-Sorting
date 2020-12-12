package IntSortingMethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.Random;


public class GrossSort extends Sort {
	// static analysis
	// private int cpuThreads;

	// statistical analysis from linear pass
	// private int maxArrayValue;
	// private int minArrayValue;
	// private float avgArrayValue;
	// private float avgDiff;
	private int numPositive = 0;
	private int numNegative = 0;
	private int numZero = 0;

	// identifying sorted sub-arrays
	// private int nmiSize;
	// private int[] naturalMergeIndices;

	/* Define a class which can serve as our thread runner: */
	private class Sorter implements Runnable {
		// Manage concurrent execution:
		private final ExecutorService pool;
		private final Phaser ph;

		// Store the application state:
		private int data[];
		private int start;
		private int len;

		/* Define a constructor to set all of the internal fields: */
		public Sorter(ExecutorService pool, Phaser ph, int data[], int start, int len) {
			this.pool = pool;
			this.ph = ph;
			this.data = data;
			this.start = start;
			this.len = len;

			ph.register();
		}

		/* Perform the partitioning step of QuickSort: */
		@Override public void run() {
			// Handle the base case:
			if(len - start < 2) {
				// Arrive at the base-case state & return:
				ph.arrive();
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

			// Handle the single-threaded case:
			if(this.pool == null) {
				// Store local variables temporarily:
				int start = this.start;
				int len = this.len;

				// Do the first half:
				this.len = pivot;
				run();

				// Prepare for the second half of the array:
				this.len = len;
			} else {
					// Register a task to process the first half of the array:
					// TODO: Don't do this if that thread's base-case is met
					pool.submit(new Sorter(this.pool, this.ph, data, start, pivot));
			}

			// Recursively process the second half of the array:
			start = pivot + 1;
			run();
		}
	}

	/*
	void staticAnalysis() {
		this.cpuThreads = Runtime.getRuntime().availableProcessors();
	}
	*/

	void linearPass(int[] arr) {
		/*
		// used to find the average difference between consecutive array elements
		float diffSum = 0;
		int avgDiffCount = 0;

		// used to calculate the average array value
		// int sum = 0;

		// used to find naturally sorted sub-arrays
		this.nmiSize = 0;
		this.naturalMergeIndices = new int[arr.length];

		StdDevResult stdDevResult = calcStandardDeviation(arr);

		double min = stdDevResult.mean - stdDevResult.standardDeviation;
		double max = stdDevResult.mean + stdDevResult.standardDeviation;
		*/

		for (int i = 0; i < arr.length; i++) {
			/*
			if (arr[i] > this.maxArrayValue) {
				this.maxArrayValue = arr[i];
			}
			else if (arr[i] < this.minArrayValue) {
				this.minArrayValue = arr[i];
			}

			sum += arr[i];
			*/

			if (arr[i] > 0) {
				this.numPositive++;
			}
			else if (arr[i] < 0) {
				this.numNegative++;
			}
			else if (arr[i] == 0) {
				this.numZero++;
			}

			/*
			if (i != arr.length - 1) {
				// record the ending index of the naturally sorted sub-array
				if (arr[i] > arr[i + 1]) {
					naturalMergeIndices[nmiSize] = i;
					nmiSize++;
				}

				// skip over outliers in the data set when doing the "average difference" computation
				if (arr[i] < min || arr[i] > max || arr[i + 1] < min || arr[i + 1] > max) {
					continue;
				}

				diffSum += arr[i + 1] - arr[i];
				avgDiffCount++;
			}
			*/
		}

		/*
		naturalMergeIndices[nmiSize] = arr.length - 1;
		nmiSize++;

		// calculate metrics
		this.avgDiff = diffSum / avgDiffCount;
		this.avgArrayValue = (float)sum / arr.length;

		if (avgDiff < 0) {
			arr = reverseArray(arr);
		}
		*/

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

	/*
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
 	*/

	List<int[]> splitPositiveNegative(int[] arr) {

		List<int[]> out = new ArrayList<>(2);

		int[] pos = new int[this.numPositive + this.numZero];
		int[] neg = new int[this.numNegative];

		int posIdx = 0;
		int negIdx = 0;

		for (int value : arr) {
			if (value >= 0) {
				pos[posIdx++] = value;
			} else {
				neg[negIdx++] = value * -1;
			}
		}

		out.add(0, pos);
		out.add(1, neg);

		return out;
	}

	void insertionSort(ArrayList<Integer> arr) {
		int n = arr.size();
		for (int i = 1; i < n; i++) {
			int current = arr.get(i);
			int j = i - 1;

			while ((j >= 0) && (arr.get(j) > current)) {
				arr.set(j + 1, arr.get(j));
				j--;
			}
			arr.set(j + 1, current);
		}
	}

	void bucketSort(int[] arr)  {
		int len = arr.length;

		if (len == 0)
			return;

		ArrayList<ArrayList<Integer>> buckets = new ArrayList<>(len);

		int maxVal = 0;
		for (int i = 0; i < len; i++) {
			if (arr[i] > maxVal) {
				maxVal = arr[i];
			}

			buckets.add(i, new ArrayList<>());
		}

		for (int value : arr) {
			int idx = (int)((long)value * (len - 1) / maxVal);
			buckets.get(idx).add(value);
		}

		for (int i = 0; i < len; i++) {
			insertionSort(buckets.get(i));
		}

		int idx = 0;
		for (List<Integer> bucket : buckets) {
			for (int value : bucket) {
				arr[idx++] = value;
			}
		}
	}

	void bucketSortPosNeg(int[] arr) {
		List<int[]> splitResult = splitPositiveNegative(arr);

		int[] pos = splitResult.get(0);
		int[] neg = splitResult.get(1);

		bucketSort(pos);
		bucketSort(neg);

		// merge the positive and negative arrays
		int idx = 0;
		for (int i = neg.length - 1; i >= 0; i--) {
			arr[idx++] = neg[i] * -1;
		}
		for (int value : pos) {
			arr[idx++] = value;
		}
	}

	/* Spawn a new thread to run the sorting algorithm on the provided array: */
	/* TODO: The main thread should be included in the thread pool, and shouldn't just be waiting around here */
	public void algorithm() {
		// staticAnalysis();
		linearPass(this.data);

		Random rnd = new Random(2);

		if (rnd.nextInt() == 0) {
			Phaser ph = new Phaser(1);
			// TODO: Figure out how to choose between a fixed-sized thread pool & a work-stealing pool:
			//ExecutorService pool = Executors.newFixedThreadPool(3);
			ExecutorService pool = Executors.newWorkStealingPool();
			Sorter sorter = new Sorter(pool, ph, data, 0, data.length);
			sorter.run();

			// Shutdown the pool:
			ph.arriveAndAwaitAdvance();
			pool.shutdownNow();
		}
		else {
			bucketSortPosNeg(this.data);
		}

		// Memory Usage
		// Runtime runtime = Runtime.getRuntime();
		// long memory = runtime.totalMemory() - runtime.freeMemory();
		// double memoryMB = memory / 1024.0 / 1024.0;
		// System.out.println("Used memory: " + memoryMB + " MB");
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

