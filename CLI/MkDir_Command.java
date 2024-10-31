package CLI;
import java.util.Set;

import java.util.HashSet;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MkDir_Command implements OptionedCommand {
    private List<String> operands;

    private static final Set<String> Options = new HashSet<>(Set.of("v"));
    private String Option;

    private File currentDirectory; //For testing

    public MkDir_Command() {
        this.operands = new ArrayList<>();
        this.Option = new String("");
        this.currentDirectory = CLI.getDirr();
    }

    public MkDir_Command(String oper) {
        this.operands = new ArrayList<>();
        this.operands.add(oper);
        this.Option = new String("");
        this.currentDirectory = CLI.getDirr();

    }

    public MkDir_Command(File currentDirectory) { // constructor for testing
        this.operands = new ArrayList<>();
        this.Option = "";
        this.currentDirectory = currentDirectory;
    }
    
    public void appendOperand(String oper){
        this.operands.add(oper);
    }

    public void execute() throws Exception{
        makeDirectory();
    }

    void makeDirectory() throws Exception{
        if(this.operands.isEmpty())
        {
            throw new FailedToCreate("Empty Directory Name.\n");
        }
        List<String> folders = operands;
        for (String folder : folders) {
            _makeDirectory(folder);
        }

    }


    private void _makeDirectory(String directoryPath) throws Exception{
    File directory  = new File(currentDirectory,directoryPath);
        if (directory.exists()) {
            // System.err.println("Directory already exists: " + directory.getPath());
            // return;
            throw new DirectoryExistsException("Directory already exists: " + directory.getPath());
        }
        boolean created = directory.mkdir();
        if (created && Option.equals("v")) {
            System.out.println("Directory created successfully: " + directory.getPath());
        } else if (!created) {
            System.out.println("Failed to create the directory: " + directory.getPath());
        }        
        
    }

    public void setOption(String opt) throws InvalidOptionException
    {
        if(Options.contains(opt))
        {
            this.Option = opt;
        }
        else
        {
            throw new InvalidOptionException("ls: Ivalid Option     Options: '-v'");
        }
    }


}
