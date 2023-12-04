package Day4;

import Day2.Day2;
import Utils.InputUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day4 {
    private Integer part1(List<String> inputs){
        int total_points = 0;
        for(String input:inputs){
            String[] splits = input.split("[:|]");
            int count = countNumbers(splits);
            total_points += (int) Math.pow(2, count - 1);
        }
        return total_points;
    }
    private Integer part2(List<String> inputs) {
        int[] cards = new int[inputs.size()];
        Arrays.fill(cards, 1);
        for (int i = 0; i < inputs.size(); i++) {
            String[] splits = inputs.get(i).split("[:|]");
            int count = countNumbers(splits);
            for (int j = 0; j < count; j++) {
                cards[i + j + 1] += cards[i];
            }
        }
        return Arrays.stream(cards).sum();
    }

    private int countNumbers(String[] splits){
        Set<Integer> win_numbers = Arrays.stream(splits[1].strip().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());

        int[] numbers = Arrays.stream(splits[2].strip().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
        int count = 0;
        for (int num : numbers) {
            if (win_numbers.contains(num)) {
                count++;
            }
        }
        return count;
    }


    public static void main(String[] args) {
        Day4 day4 = new Day4();
        List<String> inputs = InputUtils.inputFileToStringList(new File("src/Day4/input.txt"));
        System.out.println(day4.part1(inputs));
        System.out.println(day4.part2(inputs));

    }
}
