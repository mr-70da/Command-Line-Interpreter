package CLI;

public class FailedToCreate extends Exception {
    public FailedToCreate(String errString)
    {
        super(errString);
    }
}
