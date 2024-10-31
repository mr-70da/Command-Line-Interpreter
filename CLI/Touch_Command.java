package CLI;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

public class Touch_Command implements Command {
    private List<String> operands;
    private File currentDirectory; //For testing
    public Touch_Command()
    {
        this.operands = new ArrayList<>();
        currentDirectory = CLI.getDirr();
    }
    public Touch_Command(String oper)
    {
        this.operands = new ArrayList<>();
        this.operands.add(oper);
        currentDirectory = CLI.getDirr();
    }
    public Touch_Command(File dirr)
    {
        this.operands = new ArrayList<>();
        currentDirectory = dirr;
    }
    public void execute() throws Exception
    {
        if(operands.isEmpty())
        {
            throw new FailedToCreateFile("Empty File Name.\n");
        }
        for(String args : operands)
        {
            if(args.isEmpty())
            {
                throw new FailedToCreateFile("Empty File Name.\n");
            }
            File file = new File(currentDirectory,args);
            if (file.exists()) {
                file.setLastModified(System.currentTimeMillis());
                //System.out.println("FILE TIMESTAMP UPDATED: "+ file.getName());
            }
            else{
                if (file.createNewFile()) {
                    //System.out.println(file.getName());
                } else {
                    //System.out.println("Failed to create file.");
                    throw new FailedToCreateFile("Failed to create file.\n");
                }
            }
        }
    }
    public void appendOperand(String oper)
    {
        this.operands.add(oper);
    }
    
}
