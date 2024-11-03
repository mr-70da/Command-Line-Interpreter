package CLI;
import java.io.File;
import java.io.FileWriter;

public class Insertion_Command implements Command,WriterCommand,ReaderCommand {
    private String arg;
    private String output;
    private String input;
    private File currentDirectory;
    private static int numberOfCommands = 0;
    public Insertion_Command()
    {
        arg = new String("");
        this.input = "";
        this.output = "";
        currentDirectory = CLI.getDirr();
        Insertion_Command.numberOfCommands += 1;
    }
    public Insertion_Command(String ar)
    {
        this.arg = ar;
        this.input = "";
        this.output = "";
        currentDirectory = CLI.getDirr();
        Insertion_Command.numberOfCommands += 1;
    }
    public Insertion_Command(File directory)
    {
        this.currentDirectory = directory;
        this.output = "";
        this.input = "";
        this.arg = "";
        Insertion_Command.numberOfCommands += 1;
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
        if(Insertion_Command.numberOfCommands <= 1)
        {
            FileWriter newF = new FileWriter(directory);
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
        return Insertion_Command.numberOfCommands;
    }
    public static void ResentInstances()
    {
        Insertion_Command.numberOfCommands = 0;
    }
}
