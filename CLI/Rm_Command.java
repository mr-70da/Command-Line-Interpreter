package CLI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Rm_Command implements Command {
    private List<String> operands;
    
    static File currentDirectory = new File(System.getProperty("user.dir"));
    public Rm_Command() {
        this.operands = new ArrayList<>();
    }

    public Rm_Command(String oper) {
        this.operands = new ArrayList<>();
        this.operands.add(oper);
    }

    public void appendOperand(String oper){
        this.operands.add(oper);
    }

    public void execute() throws Exception{
        removeFile();
    }

    void removeFile(){
        List<String> parts = operands;
        
        if (parts == null ||parts.size() < 1) {
            System.out.println("Please provide at least one file to remove.");
            return;
        }

        for(String part : parts) {
            Path filePath = Paths.get(currentDirectory.getAbsolutePath(), part);

            if (!Files.exists(filePath)) {
                System.err.println("File does not exist: " + filePath);
                continue;
            }

            if (Files.isDirectory(filePath)){
                System.err.println("Error: cannot remove directory with rm. Use rmdir to remove directories");
                continue;
            }

            try{
                Files.delete(filePath);
                System.out.println("Removed file: " + filePath);
            }
            catch (IOException e){
                System.err.println("Failed to remove file " + filePath + ": " + e.getMessage());
            }
        }
    }
}
