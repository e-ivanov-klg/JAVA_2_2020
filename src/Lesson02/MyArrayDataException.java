package Lesson02;

public class MyArrayDataException extends NumberFormatException {
    int lineNumber;
    int columnNumber;

    MyArrayDataException (int line, int column){
        lineNumber = line;
        columnNumber = column;
    }

    public String toString () {
        return "Элемент массива: [" + String.valueOf(lineNumber) + "," + String.valueOf(columnNumber) + "].\nНе может быть преобразован в число !";
    }
}
