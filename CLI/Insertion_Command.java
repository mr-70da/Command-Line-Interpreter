package CLI;
import java.io.File;
import java.io.FileWriter;

public class Insertion_Command implements Command,WriterCommand,ReaderCommand {
    private String arg;
    private String output;
    private String input;
    private File currentDirectory;
    public Insertion_Command()
    {
        arg = new String("");
        this.input = "";
        this.output = "";
        currentDirectory = CLI.getDirr();
    }
    public Insertion_Command(String ar)
    {
        this.arg = ar;
        this.input = "";
        this.output = "";
        currentDirectory = CLI.getDirr();
    }
    public void execute(String ...ar) throws Exception
    {
        if(arg.isEmpty())
        {
            throw new InvalidPathException("Empty File Name.\n");
        }
        File directory = new File(currentDirectory,arg);
        if(directory.exists() && directory.isFile())
        {
            directory.delete();
        }
        if(!directory.createNewFile())
        {
            throw new FailedToCreate("Failed To Create File.\n");
        }
        FileWriter newF = new FileWriter(directory);
        newF.write(input);
        this.output = input;
        newF.close();
    }
    public void appendOperand(String oper)
    {
        if(this.arg.isEmpty())
            this.arg = oper;
    }
    public String Output()
    {
        return this.output;
    }
    public void Input(String in)
    {
        this.input = in;
    }

}
