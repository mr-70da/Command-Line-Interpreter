package CLI;

public class TooManyArgsException extends Exception {

    public TooManyArgsException(String errString)
    {
        super(errString);
    }
    
}
