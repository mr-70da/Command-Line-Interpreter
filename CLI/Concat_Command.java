package CLI;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Scanner;
import java.nio.file.Files;

public class Concat_Command implements Command,WriterCommand {
    private List<String> args;
    private String output;
    private File currentDirectory;
    public Concat_Command()
    {
        this.args = new ArrayList<>();
        this.output = new String("");
        currentDirectory = CLI.getDirr();
    }
    public Concat_Command(String operr)
    {
        this.args = new ArrayList<>();
        this.args.add(operr);
        this.output = new String("");
        currentDirectory = CLI.getDirr();
    }
    public Concat_Command(File dirr)
    {
        this.args = new ArrayList<>();
        this.output = new String("");
        currentDirectory = dirr;
    }
    public void execute(String ...ar) throws Exception
    {
        if(ar.length != 0 && !ar[0].isEmpty())
        {
            this.output = "";
            File directory = new File(currentDirectory,ar[0]);
            if (directory.exists() && directory.isFile()) {
                output += Files.readString(directory.toPath());
            }
            else{
                throw new InvalidPathException("File doesn't exist!\n");
            }
            return;
        }
        if(args.isEmpty())
        {
            Scanner cin = new Scanner(System.in);
            try
            {
                String in =  cin.nextLine();
                while(!in.equals("^D"))
                {
                    output = in;
                    System.out.println(output);
                    in = cin.nextLine();
                }
            }
            catch(Exception ex)
            {

                if(ar.length != 0)
                    this.output = ar[0]; 
            }
            // finally
            // {
            //     //cin.close();
            // }
            return;
        }
        else
        {
            for(String arg: args)
            {
                if(arg.isEmpty())
                {
                    // Scanner cin = new Scanner(System.in);
                    // String in =  cin.nextLine();
                    // while(!in.equals("^D"))
                    // {
                    //     output = in;
                    //     System.out.println(output);
                    //     in = cin.nextLine();
                    // }
                    // cin.close();
                    // output="";
                    // break;
                    continue;
                }
                File directory = new File(currentDirectory,arg);
                if (directory.exists() && directory.isFile()) {
                    output += Files.readString(directory.toPath());
                }
                else{
                    this.output = "";
                    throw new InvalidPathException("File doesn't exist!\n");
                }
            }
        }
    }
    public void appendOperand(String op)
    {
        this.args.add(op);
    }
    public String Output()
    {
        return this.output;
    }
    
}
