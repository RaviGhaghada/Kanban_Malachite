package app;

public class DuplicateNameException extends Exception{
    public DuplicateNameException(String message){
        super(message);
    }
}