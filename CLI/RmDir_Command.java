package CLI;
import java.util.Set;

import java.util.HashSet;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RmDir_Command implements OptionedCommand {
    private List<String> operands;

    private static final Set<String> Options = new HashSet<>(Set.of("v"));
    private String Option;


    public RmDir_Command() {
        this.operands = new ArrayList<>();
        this.Option = new String("");
    }

    public RmDir_Command(String oper) {
        this.operands = new ArrayList<>();
        this.operands.add(oper);
        this.Option = new String("");
    }

    public void appendOperand(String oper){
        this.operands.add(oper);
    }

    public void execute() throws Exception{
        removeDirectory();
    }


    void removeDirectory(){
        List<String> folders = operands;

        for (String folder : folders) {
            _removeDirectory(folder);
        }

    }
    private void _removeDirectory(String directoryPath){
        File currentDirectory = new File(System.getProperty("user.dir"));
        File directory  = new File(currentDirectory,directoryPath);

        if (!directory.exists()) {
            System.err.println("Directory does not exist: " + directory.getPath());
            return;
        }

        if (directory.isDirectory() && directory.list().length > 0) {
            System.err.println("Directory is not empty.");
            return;
        }

        boolean isDeleted = directory.delete();
        if (isDeleted && Option.equals("v")) {
            System.out.println("Directory deleted successfully: " + directory.getPath());
        }
        else if (!isDeleted){
            System.out.println("Failed to delete the directory: " + directory.getPath());
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
