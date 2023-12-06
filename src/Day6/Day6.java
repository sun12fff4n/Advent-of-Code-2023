package Day6;

import Utils.InputUtils;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6 {
    private int part1(List<String> inputs){
        int[] time = Arrays.stream(inputs.get(0).split(":")[1].trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
        int[] distance = Arrays.stream(inputs.get(1).split(":")[1].trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
        int magin = 1;
        for (int i=0; i<time.length;i++) {
            int count = 0;
            for(int ms = 1; ms<(time[i]-1); ms++){
                if(ms*(time[i]-ms) > distance[i]){
                    count = time[i] - ms*2 +1;
                    break;
                }
            }
            magin *= count;
        }
        return magin;
    }

    private int part2(List<String> inputs){
        int time = Integer.parseInt(inputs.get(0).split(":")[1].replaceAll("\\s",""));
        long distance = Long.parseLong(inputs.get(1).split(":")[1].replaceAll("\\s",""));
        for(int ms = 1; ms<(time-1); ms++) {
            if ((long) ms * (time - ms) > distance) {
                return time - ms*2 + 1;
            }
        }
        return 0;
    }


    public static void main(String[] args) {
        Day6 day6 = new Day6();
        List<String> inputs = InputUtils.inputFileToStringList(new File("src/Day6/input.txt"));

        System.out.println(day6.part1(inputs));
        System.out.println(day6.part2(inputs));

    }
}
