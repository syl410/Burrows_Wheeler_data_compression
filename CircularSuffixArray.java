import java.util.Arrays;

public class CircularSuffixArray {
	private static final int RADIX = 256;
	private static String strIn;
    private final int sLen; // input strIn length

	// rank of sorted suffix array
	// index is ranking
	// int value is index original suffix array
    private final int[] rank;
	// rank of last sort of d digits
	// array index is the index of original SuffixArr
	private int[] lastRank;

	private int[] SuffixArr;

	private int d; // number of digits sorted

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException();
		this.strIn = s;
        sLen = strIn.length();
		
        rank = new int[sLen];
		lastRank = new int[sLen];

		SuffixArr = new int[sLen];
        for (int i = 0; i < sLen; i++) {
			SuffixArr[i] = i;
        }
		
		// sort SuffixArr using key-indexed couting sort
		keyIndexedCounting();
		// Manber - Myers MSD
		mmMSD();
    }

    // length of input str strIn
    public int length() {
        return sLen;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= sLen) throw new IllegalArgumentException();
        return SuffixArr[i];
    }

	// use key-indexed counting to sort SuffixArr
	private void keyIndexedCounting() {
		int[] count = new int[RADIX + 1];

		// count frequencies
		for (int i = 0; i < sLen; i++)
			count[strIn.charAt(i) + 1]++;
		// compute cumulates
		for (int r = 0; r < RADIX; r++)
			count[r + 1] += count[r];
		// move items
		for (int i = 0; i < sLen; i++)
			rank[count[strIn.charAt(i)]++] = SuffixArr[i]; // rank is used as aux array to save memory
		// copy back
		for (int i = 0; i < sLen; i++)
			SuffixArr[i] = rank[i];

		int ranking = 0;
		for (int r = 0; r < RADIX; r++) {
			int max = count[r];
			for (int i = ranking; i < max; i++) {
				rank[i] = ranking;
				lastRank[SuffixArr[i]] = ranking;
			}
			ranking = max;
		}
	}

	// Manber - Myers MSD
	private void mmMSD() {
		// log2(N) loops
		// d will be double each loop
		for (d = 1; d < sLen; d *= 2) {
			int i = 0;
			while (i < sLen) {
				int lo = i;
				int hi = lastSameRanking(i);
				i = hi + 1;
				if (lo == hi) continue;
				// sort SuffixArr in part of [lo, hi]
				qSort3Way(lo, hi);

				// update rank, lastRank
				updateRank(lo, hi);
			}
		}
	}

	// update rank, lastRank
	private void updateRank(int lo, int hi) {
		int ranking = lo;
		int i = lo;
		
		while (i <= hi) {
			ranking = i;
			rank[i] = ranking;
			int i_dRank = dRank(SuffixArr[i]);
			while (i + 1 <= hi && i_dRank == dRank(SuffixArr[i + 1])) {
				i++;
				rank[i] = ranking;
			}
			i++;
		}

		// update partial rank, lastRank
		for (int j = lo; j <= hi; j++) {
			lastRank[SuffixArr[j]] = rank[j];
		}
	}

	// find last index with same ranking
	private int lastSameRanking(int i) {
		while (i + 1 < sLen && rank[i] == rank[i + 1]) {
			i++;
		}
		return i;
	}


	// rank of index + d
	private int dRank(int id) {
		int dId = id + d; // index of id + d
		if (dId >= sLen) dId -= sLen;
		return lastRank[dId];
	}

	private void swap(int a, int b) {
		int tmp = SuffixArr[a];
		SuffixArr[a] = SuffixArr[b];
		SuffixArr[b] = tmp;
	}

	// 3-WAY QUICKSORT
	private void qSort3Way(int lo, int hi) {
		// cut off at two - use simple compare and swap
		if (hi - lo <= 1) {
			if (hi - lo == 1) {
				int lo_dRank = dRank(SuffixArr[lo]);
				int hi_dRank = dRank(SuffixArr[hi]);
				if (lo_dRank > hi_dRank)
					swap(lo, hi);
			}
			return;
		}

		int l = lo;
		int r = hi;
		// int p = SuffixArr[lo]; // pivot
		int p_dRank = dRank(SuffixArr[lo]); // rank of pivot + d
		int i = lo + 1;
		while (i <= r) {
			int i_dRank = dRank(SuffixArr[i]);
			int cmp = i_dRank - p_dRank;
			if (cmp < 0) { // less
				swap(l++, i++);
			} else if (cmp > 0) { // more
				swap(i, r--);
			} else { // equal
				i++;
			}
		}

		qSort3Way(lo, l - 1);
		qSort3Way(r + 1, hi);
	}
}
