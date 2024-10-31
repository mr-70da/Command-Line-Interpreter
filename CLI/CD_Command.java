package CLI;
import java.io.File;

public class CD_Command implements Command {
    private String operand;
    public CD_Command() {
        this.operand = "";
    }
    public CD_Command(String oper) {
        this.operand = oper;
    }
    public void execute(String ...ar) throws Exception
    {
        if(this.operand.isEmpty())
        {
            CLI.setDirr(new File(System.getProperty("user.dir")));
            return;
        }
        File currentDirectory = CLI.getDirr();
        File newDirectory = new File(this.operand);
        if (newDirectory.isAbsolute()) {
            if (newDirectory.isDirectory()) {
                currentDirectory = newDirectory;
            } else {
                // System.out.println("Not a directory: " + this.operand);
                throw new InvalidPathException("Not a Valid Path");
            }
        } else {
            File relativeDir = new File(currentDirectory, this.operand);
            if (relativeDir.isDirectory()) {
                currentDirectory = relativeDir;
            } else {
                throw new InvalidPathException("Not a Valid Path");
            }
        }
        CLI.setDirr(currentDirectory);
        
    }
    public void appendOperand(String op)
    {
        this.operand = (op);
    }
    public boolean emptyOperand()
    {
        return this.operand.isEmpty();
    }
    
}
