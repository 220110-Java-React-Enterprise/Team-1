package ORMExceptions;

public class NoMatchingSQLTypeException extends Exception {
    //Base error message
    private static String baseErrorMessage = " has no matching SQL data types.";

    //No args constructor using super constructor on blank class name with base error message
    public NoMatchingSQLTypeException() {
        super("Given class " + baseErrorMessage);
    }

    //Constructor taking in className and concatenating with baseErrorMessage for errorMessage
    public NoMatchingSQLTypeException(String className) {
        super(className + baseErrorMessage);
    }

    //Getters/setters for base error message
    public static String getBaseErrorMessage() {
        return baseErrorMessage;
    }

    private static void setBaseErrorMessage(String baseErrorMessage) {
        NoMatchingSQLTypeException.baseErrorMessage = baseErrorMessage;
    }
}
