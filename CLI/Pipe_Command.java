package CLI;

public class Pipe_Command implements WriterCommand,ReaderCommand{
    private String output;
    private String input;
    public Pipe_Command()
    {
        this.output = "";
        this.input = "";
    }
    public String Output()
    {
        return this.output;
    }
    public void Input(String in)
    {
        this.input = in;
    }
    public void execute(String ...ar)
    {
        if(ar.length != 0)
            this.output = ar[0];
        this.input = "";
    }
}
