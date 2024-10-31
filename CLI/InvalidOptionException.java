package CLI;

public class InvalidOptionException extends Exception {

    public InvalidOptionException(String errString)
    {
        super(errString);
    }   
}
