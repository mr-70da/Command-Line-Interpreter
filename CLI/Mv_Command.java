package CLI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Mv_Command implements Command {
    private List<String> operands;
    
    static File currentDirectory = new File(System.getProperty("user.dir"));
    public Mv_Command() {
        this.operands = new ArrayList<>();
    }

    public Mv_Command(String oper) {
        this.operands = new ArrayList<>();
        this.operands.add(oper);
    }

    public void appendOperand(String oper){
        this.operands.add(oper);
    }

    public void execute() throws Exception{
        moveFileOrDirectory();
    }
    
    void moveFileOrDirectory() {
        List<String> parts = operands;
        
        if (parts == null ||parts.size() < 2) {
            System.out.println("Please provide both source and destination paths.");
            return;
        }

        
        List<Path> sourcePaths = new ArrayList<>();
        for (int i = 0; i < parts.size() - 1; i++) {
            sourcePaths.add(Paths.get(currentDirectory.getAbsolutePath(), parts.get(i)));
        }
        
        Path destinationPath = Paths.get(currentDirectory.getAbsolutePath(), parts.get(parts.size() - 1));
        
        // If multiple sources and destination is not a directory, it's an error
        if (sourcePaths.size() > 1 && !Files.isDirectory(destinationPath)) {
            System.err.println("Error: Destination must be a directory when moving multiple files.");
            return;
        }
        
        for (Path sourcePath : sourcePaths) {

            if (!Files.exists(sourcePath)) {
                System.err.println("Source file/directory does not exist: " + sourcePath);
                continue;
            }

            if (Files.isDirectory(sourcePath) && !Files.isDirectory(destinationPath)) {
                System.err.println("Error: Cannot move a directory to a file location: " + destinationPath);
                continue;
            }
            
            Path actualDestination = (Files.isDirectory(destinationPath) ? destinationPath.resolve(sourcePath.getFileName()) : destinationPath);
            
            try {
                if (Files.exists(actualDestination) && !Files.isWritable(actualDestination)) {
                    System.err.println("Warning: Destination file is not writable: " + actualDestination);
                }

                // Perform the move operation
                Files.move(sourcePath, actualDestination, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Moved/Rename " + sourcePath + " to " + actualDestination);
            } catch (IOException e) {
                System.err.println("Failed to move " + sourcePath + " to " + actualDestination + ": " + e.getMessage());
            }
        }
    }

}
