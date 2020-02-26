package Lesson02;

public class MyArraySizeException extends ArrayIndexOutOfBoundsException{
    int lineNumber;
    int columnNumber;

    MyArraySizeException (int line, int column) {
        lineNumber = line;
        columnNumber = column;
    }

    public String toString (){
        return "Размер массива: [" + String.valueOf(lineNumber) + "][" + String.valueOf(columnNumber) + "].\nДопустимый размер массива [4][4] !";
    }
}
