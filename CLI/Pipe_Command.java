package CLI;

import java.io.File;

public class Pipe_Command implements WriterCommand,ReaderCommand{
    private String output;
    private String input;
    private File directory;
    public Pipe_Command()
    {
        this.output = "";
        this.input = "";
        this.directory = CLI.getDirr();
    }
    public Pipe_Command(File dirr)
    {
        this.output = "";
        this.input = "";
        this.directory = dirr;
    }
    public String Output()
    {
        return this.output;
    }
    public void Input(String in)
    {
        this.input = in;
    }
    public String getInput()
    {
        return this.input;
    }
    public void execute(String ...ar)
    {
        if(ar.length != 0)
            this.output = ar[0];
        this.input = "";
    }
}
