package CLI;

import java.io.File;

public class Pipe_Command implements WriterCommand,ReaderCommand{
    private String output;
    public Pipe_Command()
    {
        this.output = "";
    }
    public Pipe_Command(File dirr)
    {
        this.output = "";
    }
    public String Output()
    {
        return this.output;
    }
    public void execute(String ...ar)
    {
        if(ar.length != 0)
            this.output = ar[0];
    }
    public void Input(String in)
    {
        
    }
}
