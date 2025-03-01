import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


abstract class LZ78Compression {
    public abstract void compress(String inputFilePath, String outputFilePath) throws IOException;
    public abstract void decompress(String inputFilePath, String outputFilePath) throws IOException;
    public abstract int bitsNeeded(int number);
    public abstract String toBinary(int number, int bits);
    public abstract String BinarizeTag(int pos, int bits, char nextChar);
}


class LZ78 extends LZ78Compression {

    public int bitsNeeded(int number) {

        if (number == 0) {
            return 1; // Base case for 0
        }

        return (int) (Math.floor(Math.log(number) / Math.log(2))) + 1; // int(floor(log(number) / log(2) + 1))
    }

    // integer to a binary string of a specified length
    public String toBinary(int number, int bits) {

        String binaryString = Integer.toBinaryString(number);
        int length = binaryString.length();

        if (length < bits) {
            // Pad with leading zeros
            binaryString = String.format("%" + bits + "s", binaryString).replace(' ', '0');
        }

        return binaryString;
    }

    // Method to convert a tag into a binary string
    public String BinarizeTag(int pos, int bits, char nextChar) {
        // the position will be encoded in certain number of bits
        String binaryPos = toBinary(pos, bits);

        // the nextChar will be encoded in exactly 8 bits
        int asciiCode = (int) nextChar;
        String binaryChar = toBinary(asciiCode, 8);

        // return the string concatenation of binary representation of pos and nextChar
        return binaryPos + binaryChar;
    }


    public void compress(String inputFilePath, String outputFilePath) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get(inputFilePath)));
        if (text.isEmpty()) {
            System.out.println("Input file is empty. No compression needed.");
            return;
        }

        Map<String, Integer> dictionary = new HashMap<>();
        int dictSize = 0;
        StringBuilder binaryString = new StringBuilder();

        String current = "";
        for (char c : text.toCharArray()) {
            String combined = current + c;
            if (dictionary.containsKey(combined)) {
                current = combined;
            } else {
                int pos = dictionary.getOrDefault(current, 0);
                binaryString.append(BinarizeTag(pos, bitsNeeded(dictSize), c));
                dictionary.put(combined, ++dictSize);
                current = "";
            }
        }

        if (!current.isEmpty()) {
            int pos = dictionary.getOrDefault(current, 0);
            binaryString.append(BinarizeTag(pos, bitsNeeded(dictSize), '\0'));
        }

        // Pad the binary string to make its length a multiple of 8
        int padding = (8 - (binaryString.length() % 8)) % 8;
        for (int i = 0; i < padding; i++) {
            binaryString.append('0');
        }

        // Write the binary string to a binary file
        byte[] byteArray = new byte[binaryString.length() / 8];
        for (int i = 0; i < byteArray.length; i++) {
            String byteString = binaryString.substring(8 * i, 8 * (i + 1));
            byteArray[i] = (byte) Integer.parseInt(byteString, 2);
        }

        try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
            fos.write(byteArray);
            System.out.println("Binary data written to file.");
        }
    }


    public void decompress(String inputFilePath, String outputFilePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(inputFilePath)) {
            byte[] byteArray = fis.readAllBytes();
            if (byteArray.length == 0) {
                System.out.println("Input file is empty. No decompression needed.");
                return;
            }

            StringBuilder binaryString = new StringBuilder();
            for (byte b : byteArray) {
                String byteString = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
                binaryString.append(byteString);
            }

            List<String> dictionary = new ArrayList<>();
            dictionary.add(""); // Initialize with an empty string

            StringBuilder text = new StringBuilder();
            int i = 0;
            while (i < binaryString.length()) {
                int bits = bitsNeeded(dictionary.size() - 1);
                if (i + bits + 8 > binaryString.length()) {
                    break; // Handle padding at the end
                }

                String posBinary = binaryString.substring(i, i + bits);
                int pos = Integer.parseInt(posBinary, 2);
                i += bits;

                String charBinary = binaryString.substring(i, i + 8);
                char nextChar = (char) Integer.parseInt(charBinary, 2);
                i += 8;

                String entry = dictionary.get(pos) + nextChar;
                text.append(entry);
                dictionary.add(entry);
            }

            // Write the decompressed text to a text file
            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                fos.write(text.toString().getBytes());
                System.out.println("Decompressed text written to file.");
            }
        }
    }
}