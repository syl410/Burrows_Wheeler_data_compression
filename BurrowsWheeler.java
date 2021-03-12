public class BurrowsWheeler {
    private static final int RADIX = 256; 

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output 
    public static void transform() {
        // check if input is empty
        if (BinaryStdIn.isEmpty()) return;

        // read string from input
        String inputStr = BinaryStdIn.readString();
        // length of inputStr
        int sLen = inputStr.length();
        int first = 0;

        CircularSuffixArray csArr = new CircularSuffixArray(inputStr);

        // StringBuilder of last column string of sorted suffixes
        StringBuilder lastColSB = new StringBuilder();

        // iterate sorted suffixed
        for (int i = 0; i < sLen; i++) {
            // find row of index(i) = 0
            int prevIdx = csArr.index(i) - 1;
            // csArr.index(i) == 0
            if (prevIdx == -1) { 
                first = i;
                prevIdx += sLen;
            }
            // calculate index in original sufffixes for row i
            // int prevIdx = (csArr.index(i) - 1 + sLen) % sLen;
            
            lastColSB.append(inputStr.charAt(prevIdx));
        }

        String outputStr = lastColSB.toString();

        // write output
        BinaryStdOut.write(first);
        BinaryStdOut.write(outputStr);
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String inputStr = BinaryStdIn.readString();
        int strLen = inputStr.length();
        
        int[] count = new int[RADIX + 1];
        char[] sortedArr = new char[strLen];
        int[] next = new int[strLen];

        for (int i = 0; i < strLen; i++) {
            char c = inputStr.charAt(i);
            count[(int) c + 1]++;
        }
        for (int i = 0; i < RADIX; i++) {
            count[i + 1] += count[i];
        }
        for (int i = 0; i < strLen; i++) {
            char c = inputStr.charAt(i);
            // c in frist col is at row count[(int)c]
            // c in last col is at row i
            // at row count[(int)c], last col is previous element of c
            next[count[(int) c]] = i;
            sortedArr[count[(int) c]] = c;
            count[(int) c]++;
        }

        int row = first;
        for (int i = 0; i < strLen; i++) {
            BinaryStdOut.write(sortedArr[row]);
            row = next[row];
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args == null || args.length == 0) 
            throw new IllegalArgumentException();
        if (args[0].equals("-")) 
            transform();
        else if (args[0].equals("+")) 
            inverseTransform();
        else 
            throw new IllegalArgumentException();
    }

}
