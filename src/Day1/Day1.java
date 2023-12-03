package Day1;

import Utils.InputUtils;

import java.io.File;
import java.util.*;

public class Day1 {
    private int calibration1(List<String> line){
        int[] arr = new int[line.size()];
        int idx =0;
        for(String l:line){
            List<Integer> numbers = new ArrayList<>();
            for(int i = 0; i<l.length(); i++){
                if(l.charAt(i) >= '0' && '9' >= l.charAt(i)){
                    numbers.add(l.charAt(i)-'0');
                }
            }
            arr[idx] = numbers.isEmpty()? 0 : 10*numbers.get(0) + numbers.get(numbers.size()-1);
            idx ++;
        }
        return Arrays.stream(arr).sum();
    }
    private int calibration2 (List<String> line){
        Map<String, Integer> string2Number = Map.of(
                "one",1,
                "two", 2,
                "three", 3,
                "four", 4,
                "five", 5,
                "six",6,
                "seven", 7,
                "eight",8,
                "nine", 9,
                "zero", 0);
        int sum = 0;
        for(String l : line){
            int[] firstnlast = new int[2];
            StringBuilder first = new StringBuilder();
            StringBuilder last = new StringBuilder();
            boolean findfirst = false;
            boolean findlast = false;
            int head = 0;
            int tail = l.length()-1;
            while (head < l.length() && !(findfirst && findlast)){
                first.append(l.charAt(head));
                last.insert(0,l.charAt(tail));
                if(!findfirst) {
                    if(l.charAt(head) >= '0' && '9' >= l.charAt(head)){
                        firstnlast[0] = l.charAt(head) - '0';
                        findfirst = true;
                    }else {
                        for (String key : string2Number.keySet()) {
                            if (first.toString().contains(key)) {
                                firstnlast[0] = string2Number.get(key);
                                findfirst = true;
                            }
                        }
                    }
                }
                if(!findlast) {
                    if(l.charAt(tail) >= '0' && '9'>=l.charAt(tail)){
                        firstnlast[1] = l.charAt(tail) - '0';
                        findlast = true;
                    } else {
                        for (String key : string2Number.keySet()) {
                            if (last.toString().contains(key)) {
                                firstnlast[1] = string2Number.get(key);
                                findlast = true;
                            }
                        }
                    }
                }
                head++;
                tail--;
            }
            sum += firstnlast[0]*10 + firstnlast[1];
        }
        return sum;
    }

    //another method, got idea from reddit, slower than v1 ^^
    private int part2_v2(List<String> lines){
        List<String> list = new ArrayList<>();
        Map<String, String> string2Number = Map.of(
                "one","one1one",
                "two", "two2two",
                "three", "three3three",
                "four", "four4four",
                "five", "five5five",
                "six","six6six",
                "seven", "seven7seven",
                "eight","eight8eight",
                "nine", "nine9nine",
                "zero", "zero0zero");
        for(String l: lines) {
            for (Map.Entry<String, String> entry : string2Number.entrySet()) {
                l = l.replaceAll(entry.getKey(), entry.getValue());
            }
            list.add(l);
        }
        return calibration1(list);
    }

    public static void main(String[] args) {
        Day1 day1 = new Day1();
        String path = "src/Day1/input.txt";
        List<String> inputs = InputUtils.inputFileToStringList(new File(path));
        System.out.println(day1.calibration1(inputs));
        System.out.println(day1.calibration2(inputs));
    }

}
