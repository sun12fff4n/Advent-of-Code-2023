package Day6;

import Utils.InputUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Day6 {
    private long part1(List<String> inputs){
        int[] time = Arrays.stream(inputs.get(0).split(":")[1].trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
        int[] distance = Arrays.stream(inputs.get(1).split(":")[1].trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
        long magin = 1;
        for (int i=0; i<time.length;i++) {
            long count = 0;
            for(int ms = 1; ms<=(time[i]/2); ms++){
                long boundary = findBoundry(time[i],distance[i]);
                count = time[i] - boundary*2 + 1;
            }
            magin *= count;
        }
        return magin;
    }

    private long part2(List<String> inputs){
        long time = Long.parseLong(inputs.get(0).split(":")[1].replaceAll("\\s",""));
        long distance = Long.parseLong(inputs.get(1).split(":")[1].replaceAll("\\s",""));
        long boundary = findBoundry(time,distance);
        return time - boundary*2 + 1;
    }

    private long findBoundry(long time, long distance){
        long t = time/2;
        long left = 0;
        long right = t;
        long min_hold_time = 0;
        while (left <= right) {
            long mid = left + (right - left) / 2;
            long currentDistance = mid * (time - mid);
            if (currentDistance == distance) {
                return mid+1;
            } else if (currentDistance < distance) {
                left = mid + 1;
                min_hold_time = left;
            } else {
                right = mid - 1;
            }

        }
        return min_hold_time;
    }



    public static void main(String[] args) {
        Day6 day6 = new Day6();
        List<String> inputs = InputUtils.inputFileToStringList(new File("src/Day6/input.txt"));
        System.out.println(day6.part1(inputs));
        System.out.println(day6.part2(inputs));
    }
}
