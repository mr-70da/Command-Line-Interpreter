package CLI;
import java.util.Set;

import java.util.HashSet;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Rm_Command implements OptionedCommand {
    private List<String> operands;

    private static final Set<String> Options = new HashSet<>(Set.of("v"));
    private String Option;

    public Rm_Command() {
        this.operands = new ArrayList<>();
        this.Option = new String("");
    }

    public Rm_Command(String oper) {
        this.operands = new ArrayList<>();
        this.operands.add(oper);
        this.Option = new String("");
    }

    public void appendOperand(String oper){
        this.operands.add(oper);
    }

    public void execute(String ...ar) throws Exception{
        removeFile();
    }

    void removeFile(){
        List<String> parts = operands;
        
        if (parts == null ||parts.size() < 1) {
            System.out.println("Please provide at least one file to remove.");
            return;
        }
        File currentDirectory = CLI.getDirr();
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
                if(Option.equals("v"))
                    System.out.println("Removed file: " + filePath);
            }
            catch (IOException e){
                System.err.println("Failed to remove file " + filePath + ": " + e.getMessage());
            }
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
