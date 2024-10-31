package CLI;

public class FailedToCreateFile extends Exception {
    public FailedToCreateFile(String errString)
    {
        super(errString);
    }
}
