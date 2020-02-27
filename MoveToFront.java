import java.util.LinkedList;

public class MoveToFront {
    private static final int CHAR_BIT_NUM = 8;
    private static final int EXTENDED_ASCII_SIZE = 256;
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        LinkedList<Character> asciiList = getAsciiList();
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int pos = asciiList.indexOf(c);
            if (pos == -1) throw new IllegalArgumentException();
            BinaryStdOut.write(pos, CHAR_BIT_NUM);

            // move c from current position to front
            asciiList.remove(pos);
            asciiList.addFirst(c);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        LinkedList<Character> asciiList = getAsciiList();
        while (!BinaryStdIn.isEmpty()) {
            int pos = (int) BinaryStdIn.readChar();
            if (pos < 0 || pos >= EXTENDED_ASCII_SIZE) throw new IllegalArgumentException();
            char c = asciiList.get(pos);
            BinaryStdOut.write(c);
        
            // move c from current position to front
            asciiList.remove(pos);
            asciiList.addFirst(c);
        }
        BinaryStdOut.close();
    }

    // create 256 extended ASCII charatcter list
    private static LinkedList<Character> getAsciiList() {
        LinkedList<Character> asciiList = new LinkedList<>();
        for (char c = 0; c < EXTENDED_ASCII_SIZE; c++) {
            asciiList.add(c);
        }
        return asciiList;
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
      if (args.length == 0) 
         throw new IllegalArgumentException();
      if (args[0].equals("-")) 
         encode();
      else if (args[0].equals("+")) 
         decode();
      else 
         throw new IllegalArgumentException();
    }

}
