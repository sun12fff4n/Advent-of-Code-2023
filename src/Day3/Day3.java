package Day3;

import Utils.InputUtils;

import java.io.File;
import java.util.*;

public class Day3 {
    private final Set<Character> SYMBOLS = Set.of('&','*','#','%','/','$','-','+','@','=');
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
                        if(!isPart && hasSymbolInNeighborhood(matrix,r,d)){
                            isPart = true;
                        }
                        d++;
                    }
                    if(isPart) {
                        String number = new String(Arrays.copyOfRange(matrix[r], c, d));
                        c = d;
                        sum += Integer.parseInt(number);
                    }
                }
                c++;
            }
        }
        return sum;
    }


    private char[][] convertTo2DMatrix(List<String> inputs) {
        char[][] matrix = new char[inputs.size() + 2][inputs.get(0).length() + 2];
        for (int i = -1; i < inputs.size() + 1; i++) {
            char[] splits = new char[inputs.get(0).length() + 2];
            if (i != -1 && i != inputs.size()) {
                Arrays.fill(splits, '.');
                System.arraycopy(inputs.get(i).toCharArray(), 0, splits, 1, inputs.get(i).length());
            } else {
                Arrays.fill(splits, '.');
            }
            matrix[i + 1] = splits;
        }
        return matrix;
    }
    private boolean hasSymbolInNeighborhood(char[][] matrix, int row, int column) {
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = column - 1; c <= column + 1; c++) {
                if (isSymbol(matrix[r][c])) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean isDigit(char ch){
        return Character.isDigit(ch);
    }
    private boolean isSymbol(char ch){

        return SYMBOLS.contains(ch);
    }

    private boolean isStar(char ch){
        return ch == '*';
    }

    private int findTwoDirectionNumber(char[] row, int c){
       String s = findLeftNumber(row,c-1) + row[c] + findRightNumber(row,c+1);
       return Integer.parseInt(s);
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
                    if(isDigit(matrix[r-1][c])) {
                        gatherAdjacentRowGearValues(matrix, r, c, gear, true);
                    }
                    if(isDigit(matrix[r+1][c])){
                        gatherAdjacentRowGearValues(matrix,r,c,gear,false);
                    }
                    processDigit(matrix,r-1,c-1,r-1,c,gear);
                    processDigit(matrix,r-1,c+1,r-1,c,gear);
                    processDigit(matrix,r,c-1,-1,-1,gear);
                    processDigit(matrix,r,c+1,-1,-1,gear);
                    processDigit(matrix,r+1,c-1,r+1,c,gear);
                    processDigit(matrix,r+1,c+1,r+1,c,gear);
                    if(gear.size() ==2){
                        gearRatio += gear.stream().reduce(1, (a, b) -> a * b);
                    }
                }
            }
        }
        return gearRatio;
    }
    private void processDigit(char[][] matrix, int r1, int c1, int r2, int c2,ArrayList<Integer> gear) {
        if (isDigit(matrix[r1][c1]) && (r2 == -1 || c2 == -1 || !isDigit(matrix[r2][c2]))) {
            gear.add(findTwoDirectionNumber(matrix[r1], c1));
        }
    }

    private void gatherAdjacentRowGearValues(char[][] matrix, int r, int c, List<Integer> gear, boolean up) {
        int adjacentRow = up? r-1:r+1;
        if (isDigit(matrix[adjacentRow][c - 1]) || isDigit(matrix[adjacentRow][c + 1])) {
            gear.add(findTwoDirectionNumber(matrix[adjacentRow], c));
        } else {
            gear.add(matrix[adjacentRow][c] - '0');
        }
    }

    public static void main(String[] args) {
        Day3 day3 = new Day3();
        List<String> inputs = InputUtils.inputFileToStringList(new File("src/Day3/input.txt"));
        System.out.println(day3.part1(inputs));
        System.out.println(day3.part2(inputs));
    }
}
