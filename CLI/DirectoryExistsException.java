package CLI;

public class DirectoryExistsException extends Exception{
    public DirectoryExistsException(String errSrString)
    {
        super(errSrString);
    }
}
