package searchelements;
public class Sorts {
	public static int MIN_LENGTH_QUICKSORT = 4;
	public static <T extends Comparable<? super T>> void insertionSort(T[] array, int first, int last) {
		for (int i = first + 1; i <= last; i++) {
			T temp = array[i];
			int pos = i - 1;
			while (pos >= 0 && temp.compareTo(array[pos]) < 0) {
				array[pos + 1] = array[pos];
				pos--;
			}
			array[++pos] = temp;
		}
	}

	public static <T extends Comparable<? super T>> void mergeSort(T[] array, int first, int last) {
		int endFirst = (first + last) / 2;;
		if (last > first) {
			mergeSort(array, first, endFirst);
			mergeSort(array, endFirst + 1, last);
		}

		merge(array, first, endFirst, endFirst + 1, last);
	}

	private static <T extends Comparable<? super T>> void merge(T[] array, int first, int endFirst, int startLast, int last) {
		T[] temp = (T[]) new Comparable[last - first + 1];
		int i = 0, originalFirst = first, orginalLast = last;
		while (last >= startLast && endFirst >= first) {
			if (array[first].compareTo(array[startLast]) < 0) {
				temp[i] = array[first];
				first++;
			}
			else {
				temp[i] = array[startLast];
				startLast++;
			}
			i++;
		}

		while(endFirst >= first) {
			temp[i] = array[first];
			i++;
			first++;
		}

		while(last >= startLast) {
			temp[i] = array[startLast];
			startLast++;
			i++;
		}
		for (int j = originalFirst; j <= orginalLast; j++) {
			array[j] = temp[j - originalFirst];
		}
	}

	public static <T extends Comparable<? super T>> void quickSort(T[] arr, int low, int high) {
		if (high - low + 1 < MIN_LENGTH_QUICKSORT) {
			insertionSort(arr, low, high);
		}
		else {
			
			int split = partition(arr, low, high);
			quickSort(arr, low, split - 1);
			quickSort(arr, split + 1, high);
		}
	}

	private static <T extends Comparable<? super T>> int partition(T[] arr, int low, int high) {
		int mid = (low + high) / 2;
		sortFirstMiddleLast(arr, low, mid, high--);
		swap(arr, mid, high);
		T pivot = arr[high];
		int start = low;
		int last = high - 1;
		boolean done = false;
		while (!done) {
			while (arr[start].compareTo(pivot) < 0) {
				start++;
			}

			while (arr[last].compareTo(pivot) > 0) {
				last--;
			}

			if (start < last) {
				swap(arr, start, last);
				start++;
				last--;
			}
			else
				done = true;
		}
		swap(arr, start, high);
		return start;
	}

	private static <T extends Comparable<? super T>> void sortFirstMiddleLast(T[] arr, int first, int middle, int last) {
		T[] tempArr = (T[]) new Comparable[3];
		tempArr[0] = arr[first];
		tempArr[1] = arr[middle];
		tempArr[2] = arr[last];
		insertionSort(tempArr, 0, 2);
		arr[first] = tempArr[0];
		arr[middle] = tempArr[1];
		arr[last] = tempArr[2];
	}

	private static void swap(Object[] arr, int index1, int index2) {
		Object thing = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = thing;
	}
}