package CLI;

import java.io.File;
public class PWD_Command implements WriterCommand {
    
    private String output;
    private File currentDirr;
    public PWD_Command()
    {
        this.output = "";
        currentDirr = CLI.getDirr();
    }
    public String Output()
    {
        return this.output;
    }
    public void execute(String ...ar)
    {
        this.output = currentDirr.getAbsolutePath()+"\n";
    }

}
