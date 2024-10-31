package CLI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RmDir_Command implements Command {
    private List<String> operands;
    
    static File currentDirectory = new File(System.getProperty("user.dir"));
    public RmDir_Command() {
        this.operands = new ArrayList<>();
    }

    public RmDir_Command(String oper) {
        this.operands = new ArrayList<>();
        this.operands.add(oper);
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
    private static void _removeDirectory(String directoryPath){
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
        if (isDeleted) {
            System.out.println("Directory deleted successfully: " + directory.getPath());
        }
        else{
            System.out.println("Failed to delete the directory: " + directory.getPath());
        }
    }

}
