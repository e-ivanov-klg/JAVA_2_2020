package Lesson02;

import java.util.Random;

public class ValidArrayConvert {
    public static int calcStringArray (String[][] array) throws MyArrayDataException {
        int arraySumm = 0;
        if (array.length != 4 || array[0].length != 4) {
            throw new MyArraySizeException(array.length, array[0].length);
        }
        for (int rowCounter = 0; rowCounter < array.length; rowCounter++){
            for (int colCounter = 0; colCounter < array[rowCounter].length; colCounter++){
                try {
                    System.out.print(array[rowCounter][colCounter] + " ");
                    arraySumm = arraySumm + Integer.parseInt(array[rowCounter][colCounter]);
                } catch (NumberFormatException exc) {
                    //exc.printStackTrace();
                    System.out.println();
                    throw new MyArrayDataException(rowCounter, colCounter);
                }
            }
            System.out.println();
        }
        return arraySumm;
    }
    public static void main(String[] args) {
        Random random = new Random();
        String[][] stringArray = new String[4][4];
        // Заполнение массива числами
        for (int rowCounter = 0; rowCounter < stringArray.length; rowCounter++){
            for (int colCounter = 0; colCounter < stringArray[rowCounter].length; colCounter++){
                stringArray[rowCounter][colCounter] = String.valueOf(random.nextInt(6));
            }
        }
        try {
            System.out.println(calcStringArray(stringArray));
        } catch (MyArraySizeException exc) {
            System.out.println(exc);
        } catch (MyArrayDataException exc){
            System.out.println(exc);
        }
    }
}
