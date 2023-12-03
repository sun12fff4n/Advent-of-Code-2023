package Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputUtils {
    public static List<Integer> inputFileToIntList(File file) {
        List<Integer> inputs = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                inputs.add(scanner.nextInt());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputs;
    }

    public static List<String> inputFileToStringList(File file) {
        List<String> inputs = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                inputs.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputs;
    }


}
