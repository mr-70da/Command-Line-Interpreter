package CLI;
import java.io.File;
import java.io.FileWriter;

public class InsertAppend_Command implements Command,WriterCommand,ReaderCommand {
    private String arg;
    private String output;
    private String input;
    private File currentDirectory;
    private static int numberOfCommands = 0;
    public InsertAppend_Command()
    {
        arg = new String("");
        this.input = "";
        this.output = "";
        currentDirectory = CLI.getDirr();
        InsertAppend_Command.numberOfCommands += 1;
    }
    public InsertAppend_Command(String ar)
    {
        this.arg = ar;
        this.input = "";
        this.output = "";
        currentDirectory = CLI.getDirr();
        InsertAppend_Command.numberOfCommands += 1;
    }
    public InsertAppend_Command(File dir)
    {
        this.arg = "";
        this.input = "";
        this.output = "";
        currentDirectory = dir;
        InsertAppend_Command.numberOfCommands += 1;
    }
    public void execute(String ...Args) throws Exception
    {
        if(this.arg.isEmpty())
        {
            throw new InvalidPathException("Empty File Name.\n");
        }
        File directory = new File(currentDirectory,arg);
        if(!(directory.exists() && directory.isFile()))
        {
            if(!directory.createNewFile())
            {
                throw new FailedToCreate("Failed To Create File.\n");
            }
        }
        if(InsertAppend_Command.numberOfCommands <= 1)
        {
            FileWriter newF = new FileWriter(directory,true);
            newF.write(input);
            newF.close();
        }
        this.output = input;
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
    public static int getInstances()
    {
        return InsertAppend_Command.numberOfCommands;
    }
    public static void ResentInstances()
    {
        InsertAppend_Command.numberOfCommands = 0;
    }
}
