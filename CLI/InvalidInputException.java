package CLI;

public class InvalidInputException extends Exception
{
    public InvalidInputException(String errString)
    {
        super(errString);
    }   
}
