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

	/* Spawn a new thread to run the sorting algorithm on the provided array: */
	/* TODO: The main thread should be included in the thread pool, and shouldn't just be waiting around here */
	void algorithm() {
		SortThread sorter = new SortThread(data, 0, data.length);
		sorter.start();
		try {
			sorter.join();
		} catch(InterruptedException e) {
			System.err.println("ERROR: Sorting thread was interrupted unexpectedly");
			System.exit(1);
		}
	}

	void linearPass(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			int diff = arr[i + 1] - arr[i];

		}
	}

	public String getAuthor() {
		return "crg222 and brr322";
	}
}

