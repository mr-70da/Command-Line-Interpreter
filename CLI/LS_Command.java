package CLI;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.io.File;

public class LS_Command  implements OptionedCommand,WriterCommand {
    private static final Set<String> Options = new HashSet<>(Set.of("a","r"));
    private List<String> operands;
    private String Option;
    private File currentDirectory;
    private String output;
    public LS_Command()
    {
        this.operands = new ArrayList<>();
        this.Option = new String("");
        this.output = new String("");
        currentDirectory = CLI.getDirr();
    }
    public LS_Command(String operand)
    {
        this.operands = new ArrayList<>();
        this.operands.add(operand);
        this.Option = new String("");
        this.output = new String("");
        currentDirectory = CLI.getDirr();
    }
    public LS_Command(File dirr)
    {
        currentDirectory = dirr;
        this.operands = new ArrayList<>();
        this.Option = new String("");
        this.output = new String("");
    }
    public String Output()
    {
        return this.output;
    }
    public void execute(String ...ar) throws Exception
    {
       
        switch (Option) {
            case "a":
                if(operands.isEmpty())
                {
                    File directory;
                    directory = currentDirectory;
                    if (directory.isDirectory()){
                        File[] files = directory.listFiles();
                        if ( files != null ) {
                            for(File file : files){
                                    this.output += file.getName();
                                    this.output += "\n";
                            }
                        }    
                    }
                    else if(directory.isFile())
                    {
                        this.output += directory.getName();
                        this.output += "\n";
                    }
                }
                else
                {
                    for(String directoryPath : operands)
                    {
                        File directory;
                        if (directoryPath.isEmpty()) {
                            directory = currentDirectory;
                        }
                        else {
                            directoryPath = (currentDirectory.getAbsolutePath() + "/" +directoryPath);
                            directory = new File(directoryPath);
                        }
                        if (directory.isDirectory()){
                            File[] files = directory.listFiles();
                            if ( files != null ) {
                                for(File file : files){
                                    this.output += file.getName();
                                    this.output+='\n';
                                }
                            }  
                        }
                        else if(directory.isFile())
                        {
                            this.output += directory.getName();
                            this.output += "\n";
                        }
                        else {
                            throw new InvalidPathException("Not a Valid Path");
                        }
                        //this.output+="\n";
                    }
                }      
                break;
            case "r":
                if (operands.isEmpty()) {
                    File directory;
                    directory = currentDirectory;
                    if (directory.isDirectory()) {
                        File[] files = directory.listFiles();
                        if (files != null) {
                            Arrays.sort(files,Collections.reverseOrder());
                            for ( File file : files) {
                                if ( !file.isHidden()) {
                                    // System.out.println(file.getName());
                                    this.output += file.getName();
                                    this.output += "\n";
                                }
                            }
                        }
                    }
                }
                else {
                    for(String directoryPath : operands)
                    {
                        File directory;
                        directoryPath = (currentDirectory.getAbsolutePath() + "/" + directoryPath);
                        if (directoryPath.isEmpty()) {
                            directory = currentDirectory;
                        }
                        else {
                            directory = new File(directoryPath);
                        }
                        if (directory.isDirectory()) {
                            File[] files = directory.listFiles();
                            if (files != null) {
                                Arrays.sort(files,Collections.reverseOrder());
                                for ( File file : files) {
                                    if ( !file.isHidden()) {
                                        //System.out.println(file.getName());
                                        this.output += file.getName();
                                        this.output += "\n";
                                    }
                                }
                            }
                        }
                        else if(directory.isFile())
                        {
                            this.output += directory.getName();
                            this.output += "\n";
                        }
                        else {
                            throw new InvalidPathException("Not a Valid Path");
                        }
                    }
                }
                break;
            default:
                if(operands.isEmpty())
                {
                    File directory;
                    directory = currentDirectory;
                    if (directory.isDirectory()){
                        File[] files = directory.listFiles();
                        if ( files != null ) {
                            for(File file : files){
                                if ( !file.isHidden()) {
                                    this.output += file.getName();
                                    this.output += "\n";
                                }
                            }
                        } 
                    }
                }
                else
                {
                    for(String directoryPath : operands)
                    {
                        File directory;
                        if (directoryPath.isEmpty()) {
                            directory = currentDirectory;
                        }
                        else {
                            directoryPath = (currentDirectory.getAbsolutePath() + "/" +directoryPath);
                            directory = new File(directoryPath);
                        }
                        if (directory.isDirectory()){
                            File[] files = directory.listFiles();
                            if ( files != null ) {
                                for(File file : files){
                                    if ( !file.isHidden()) {
                                        this.output += file.getName();
                                        this.output+='\n';
                                    }
                                }
                            }  
                        }
                        else if(directory.isFile())
                        {
                            this.output += directory.getName();
                            this.output += "\n";
                        }
                        else {
                            throw new InvalidPathException("Not a Valid Path");
                        }
                        //this.output+="\n";
                    }
                }
                break;
        }
    }
    public void appendOperand(String op)
    {
        this.operands.add(op);
    }
    public void setOption(String opt) throws InvalidOptionException
    {
        if(Options.contains(opt))
        {
            this.Option = opt;
        }
        else
        {
            throw new InvalidOptionException("ls: Ivalid Option     Options: '-a' '-r'");
        }
    }
}
