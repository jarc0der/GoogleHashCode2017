package com.google.hashcode.utils;

import com.google.hashcode.entity.Cell;
import com.google.hashcode.entity.Ingredient;
import com.google.hashcode.entity.SliceInstruction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Formatter;
import java.util.List;

/**
 * @author Grigoriy Lyashenko (Grog).
 */
public class IoUtils {

    /**
     * Parses given input file to a 2d pizza cells array
     *
     * @param file input file
     * @return 2d array representing a pizza
     * @throws IOException parsing fail
     */
    public static Cell[][] parsePizza(String file) throws IOException {
        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader br = new BufferedReader(fileReader);
            //parse the first line
            String[] headerTokens = br.readLine().split(" ");
            int rowsCount = Integer.parseInt(headerTokens[0]);
            int columnsCount = Integer.parseInt(headerTokens[1]);
            //declare a pizza cells array
            Cell[][] ingredients = new Cell[rowsCount][columnsCount];
            int row = 0;
            String fileLine;
            while ((fileLine = br.readLine()) != null) {
                for (int column = 0; column < fileLine.length(); column++) {
                    Character literal = fileLine.charAt(column);
                    if (literal.toString().equals(Ingredient.TOMATO.toString())) {
                        ingredients[row][column] = new Cell(column, row, Ingredient.TOMATO);
                    } else if (literal.toString().equals(Ingredient.MUSHROOM.toString())) {
                        ingredients[row][column] = new Cell(column, row, Ingredient.MUSHROOM);
                    }
                }
                row++;
            }
            return ingredients;
        }
    }

    /**
     * Produces SliceInstructions based on given input data set
     *
     * @param file input data set
     * @return slice instructions
     * @throws IOException file reading error
     */
    public static SliceInstruction parseSliceInstructions(String file) throws IOException {
        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader br = new BufferedReader(fileReader);
            String[] headerTokens = br.readLine().split(" ");
            int minNumberOfIngredientPerSlice = Integer.parseInt(headerTokens[2]);
            int maxNumberOfCellsPerSlice = Integer.parseInt(headerTokens[3]);
            return new SliceInstruction(minNumberOfIngredientPerSlice, maxNumberOfCellsPerSlice);
        }
    }


    /**
     * Converts given pizza cells 2d array to human readable string representation
     *
     * @param ingredients given array
     * @return table like String representation
     */
    public static String convertToHumanReadableTable(Cell[][] ingredients) {
        StringBuilder output = new StringBuilder();
        for (Cell[] row : ingredients) {
            for (Cell cell : row) {
                output.append(cell).append(" ");
            }
            output.append("\n");
        }
        return output.toString();
    }


    /**
    * this method formats data for output to file
    * @see issue 1 description
    * @see task description
    *
    * @author github.com/VadimKlindukhov skype: kv_vadim
    * @param list — inner representation of pizza
    * @return long String that contains output data
    */
    public static String outputFormat(List<List<Cell>> list){
    	Comparator<Cell> cellComparator = (Cell c1, Cell c2) ->{
    		if(c1.x != c2.x){
    			return Integer.compare(c1.x, c2.x);
    		} else
    			return Integer.compare(c1.y, c2.y);
    	};
    	StringBuffer sb = new StringBuffer();
    	Formatter textFormatter = new Formatter(sb);
    	textFormatter.format("%d%n", list.size());
    	Cell min, max;
    	for(List<Cell> innerList : list){
    		min = innerList.stream().min(cellComparator).get();
    		max = innerList.stream().max(cellComparator).get();
    		textFormatter.format("%d %d %d %d%n", min.y, min.x, max.y, max.x);
    	}
    	textFormatter.close();
    	return sb.toString();
    }
}
