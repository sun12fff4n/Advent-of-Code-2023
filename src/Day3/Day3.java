package Day3;

import Utils.InputUtils;

import java.io.File;
import java.util.*;

public class Day3 {
    private int part1(List<String> inputs){
        //surround input with ....
        int sum = 0;
        char[][] matrix = convertTo2DMatrix(inputs);
        for (int r = 1; r<matrix.length-1; r++){
            int c = 0;
            while (c<matrix[0].length){
                if(matrix[r][c] != '.'&& !isSymbol(matrix[r][c])){
                    int d = c;
                    boolean isPart = false;
                    while (isDigit(matrix[r][d])){
                        if(!isPart &&(isSymbol(matrix[r-1][d]) || isSymbol(matrix[r+1][d])||
                                isSymbol(matrix[r][d-1]) || isSymbol(matrix[r][d+1]) ||
                                isSymbol(matrix[r-1][d-1]) || isSymbol(matrix[r-1][d+1])||
                                isSymbol(matrix[r+1][d-1])||isSymbol(matrix[r+1][d+1]))){
                            isPart = true;
                        }
                        d++;
                    }
                    if(isPart) {
                        String number = "";
                        for (int n = c; n < d; n++) {
                            number += matrix[r][n];
                        }
                        c = d;
                        sum += Integer.parseInt(number);
                    }
                }
                c++;
            }
        }
        return sum;
    }

    private char[][] convertTo2DMatrix(List<String> inputs){
        char[][] matrix = new char[inputs.size()+2][inputs.get(1).length()+2];
        for(int i = -1; i<inputs.size()+1;i++){
            char[] splits = new char[inputs.get(1).length()+2];
            if (i != -1 && i != inputs.size()) {
                splits[0] = '.';
                splits[splits.length-1] = '.';
                for(int j = 0; j<inputs.get(i).length(); j++){
                    splits[j+1] = inputs.get(i).charAt(j);
                }
            } else {
                Arrays.fill(splits, '.');
            }
            matrix[i+1] = splits;
        }
        return matrix;
    }

    private boolean isDigit(char ch){
        return (9 >= (ch - '0')) && ((ch - '0') >= 0);
    }
    private boolean isSymbol(char ch){
        Set<Character> symbols = Set.of('&','*','#','%','/','$','-','+','@','=');
        return symbols.contains(ch);
    }

    private boolean isStar(char ch){
        return ch == '*';
    }

    private String findTwoDirectionNumber(char[] row, int c){
        String s = findLeftNumber(row,c-1) + row[c] + findRightNumber(row,c+1);

        return s;
    }

    private String findRightNumber(char[] row,int c){
        int d = c;
        String s ="";
        while (isDigit(row[d]) && d<row.length){
            s+= row[d];
            d++;
        }
        return s;
    }
    private String findLeftNumber(char[]row, int c){
        int d = c;
        StringBuilder sb = new StringBuilder();
        while (isDigit(row[d]) && d>0){
            sb.insert(0,row[d]);
            d--;
        }
        return sb.toString();
    }

    //check numbers around *
    private int part2(List<String> inputs){
        int gearRatio = 0;
        char[][] matrix = convertTo2DMatrix(inputs);
        for (int r = 1; r<matrix.length-1; r++){
            for (int c = 1; c<matrix.length-1; c++){
                if(isStar(matrix[r][c])){
                    ArrayList<Integer> gear = new ArrayList<>();
                    if(isDigit(matrix[r - 1][c])){
                        if(isDigit(matrix[r-1][c-1])&&isDigit(matrix[r-1][c+1])){
                            gear.add(Integer.parseInt(findTwoDirectionNumber(matrix[r-1],c )));
                        }else if(isDigit(matrix[r-1][c-1])&&!isDigit(matrix[r-1][c+1])){
                            gear.add(Integer.parseInt(findLeftNumber(matrix[r-1],c)));
                        }else if(!isDigit(matrix[r-1][c-1])&&isDigit(matrix[r-1][c+1])){
                            gear.add(Integer.parseInt(findRightNumber(matrix[r-1],c)));
                        }else{
                            gear.add(matrix[r-1][c]-'0');
                        }
                    }

                    if(isDigit(matrix[r-1][c-1])){
                        if(!isDigit(matrix[r - 1][c])){
                            gear.add(Integer.parseInt(findLeftNumber(matrix[r-1],c-1 )));
                        }
                    }
                    if(isDigit(matrix[r-1][c+1])){
                        if(!isDigit(matrix[r - 1][c])){
                            gear.add(Integer.parseInt(findRightNumber(matrix[r-1],c+1)));
                        }
                    }

                    if(isDigit(matrix[r+1][c])){
                        if(isDigit(matrix[r+1][c-1])&&isDigit(matrix[r+1][c+1])){
                            gear.add(Integer.parseInt(findTwoDirectionNumber(matrix[r+1],c )));
                        }else if(isDigit(matrix[r+1][c-1])&&!isDigit(matrix[r+1][c+1])){
                            gear.add(Integer.parseInt(findLeftNumber(matrix[r+1],c)));
                        }else if(!isDigit(matrix[r+1][c-1])&&isDigit(matrix[r+1][c+1])){
                            gear.add(Integer.parseInt(findRightNumber(matrix[r+1],c)));
                        }else{
                            gear.add(matrix[r+1][c]-'0');
                        }
                    }
                    if(isDigit(matrix[r][c-1])){
                        gear.add(Integer.parseInt(findLeftNumber(matrix[r],c-1 )));

                    }
                    if(isDigit(matrix[r][c+1])){
                        gear.add(Integer.parseInt(findRightNumber(matrix[r],c+1 )));
                    }
                    if(isDigit(matrix[r+1][c-1])){
                        if(!isDigit(matrix[r + 1][c])) {
                            gear.add(Integer.parseInt(findLeftNumber(matrix[r + 1], c - 1)));
                        }
                    }
                    if(isDigit(matrix[r+1][c+1])){
                        if(!isDigit(matrix[r + 1][c])) {
                            gear.add(Integer.parseInt(findRightNumber(matrix[r + 1], c + 1)));
                        }
                    }

                    if(gear.size() ==2){
                        gearRatio += gear.stream().reduce(1, (a, b) -> a * b);
                    }
                }
            }
        }

        return gearRatio;

    }



    public static void main(String[] args) {
        Day3 day3 = new Day3();
        List<String> inputs = InputUtils.inputFileToStringList(new File("src/Day3/input.txt"));
        System.out.println(day3.part1(inputs));
        System.out.println(day3.part2(inputs));
    }
}
