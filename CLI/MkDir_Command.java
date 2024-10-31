package CLI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MkDir_Command implements Command {
    private List<String> operands;
    
    static File currentDirectory = new File(System.getProperty("user.dir"));
    public MkDir_Command() {
        this.operands = new ArrayList<>();
    }

    public MkDir_Command(String oper) {
        this.operands = new ArrayList<>();
        this.operands.add(oper);
    }

    public void appendOperand(String oper){
        this.operands.add(oper);
    }

    public void execute() throws Exception{
        makeDirectory();
    }

    void makeDirectory(){
        List<String> folders = operands;
        for (String folder : folders) {
            _makeDirectory(folder);
        }

    }


    private static void _makeDirectory(String directoryPath){
    File directory  = new File(currentDirectory,directoryPath);
        if (directory.exists()) {
            System.err.println("Directory already exists: " + directory.getPath());
            return;
        }
        boolean created = directory.mkdir();
        if (created) {
            System.out.println("Directory created successfully: " + directory.getPath());
        } else {
            System.out.println("Failed to create the directory: " + directory.getPath());
        }        
        
    }


}
