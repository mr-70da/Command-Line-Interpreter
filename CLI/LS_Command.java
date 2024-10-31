package CLI;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class LS_Command  implements OptionedCommand,WriterCommand {
    private static final Set<String> Options = new HashSet<>(Set.of("a","r"));
    private List<String> operands;
    private String Option;
    private String output;
    public LS_Command()
    {
        //super(comm);
        this.operands = new ArrayList<>();
        this.Option = new String("");
        this.output = new String("");
    }
    public LS_Command(String operand)
    {
        //super(comm);
        this.operands = new ArrayList<>();
        this.operands.add(operand);
        this.Option = new String("");
        this.output = new String("");
    }
    public String Output()
    {
        return this.output;
    }
    public void execute() throws Exception
    {
        switch (Option) {
            case "a":
                if(operands.isEmpty())
                {
                    File directory;
                    directory = new File(".");
                    if (directory.isDirectory()){
                        File[] files = directory.listFiles();
                        if ( files != null ) {
                            for(File file : files){
                                //if ( !file.isHidden()) {
                                    this.output += file.getName();
                                    this.output += "\n";
                                    //System.out.println(file.getName());
                                //}
                            }
                        }
                        // else {
                        //     System.out.println("Directory is Empty");
                        // }    
                    }
                }
                else
                {
                    for(String directoryPath : operands)
                    {
                        File directory;
                        if (directoryPath.isEmpty()) {
                            directory = new File(".");
                        }
                        else {
                            directory = new File(directoryPath);
                        }
                        if (directory.isDirectory()){
                            File[] files = directory.listFiles();
                            if ( files != null ) {
                                for(File file : files){
                                    //if ( !file.isHidden()) {
                                        this.output += file.getName();
                                        this.output+='\n';
                                        //System.out.println(file.getName());
                                // }
                                }
                            }
                            // else {
                            //     System.out.println("Directory is Empty");
                            // }    
                        }
                        else {
                            throw new InvalidPathException("Not a Valid Path");
                            // System.out.println("Not a Valid Path");
                        }
                        this.output+="\n";
                    }
                }      
                break;
            case "r":

                break;
            default:
                if(operands.isEmpty())
                {
                    File directory;
                    directory = new File(".");
                    if (directory.isDirectory()){
                        File[] files = directory.listFiles();
                        if ( files != null ) {
                            for(File file : files){
                                if ( !file.isHidden()) {
                                    this.output += file.getName();
                                    this.output += "\n";
                                    //System.out.println(file.getName());
                                }
                            }
                        }
                        // else {
                        //     System.out.println("Directory is Empty");
                        // }    
                    }
                }
                else
                {
                    for(String directoryPath : operands)
                    {
                        File directory;
                        if (directoryPath.isEmpty()) {
                            directory = new File(".");
                        }
                        else {
                            directory = new File(directoryPath);
                        }
                        if (directory.isDirectory()){
                            File[] files = directory.listFiles();
                            if ( files != null ) {
                                for(File file : files){
                                    if ( !file.isHidden()) {
                                        this.output += file.getName();
                                        this.output+='\n';
                                        //System.out.println(file.getName());
                                    }
                                }
                            }
                            // else {
                            //     System.out.println("Directory is Empty");
                            // }    
                        }
                        else {
                            throw new InvalidPathException("Not a Valid Path");
                            // System.out.println("Not a Valid Path");
                        }
                        this.output+="\n";
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
