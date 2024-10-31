package CLI;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Scanner;

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
    public void execute() throws Exception
    {
        if(args.isEmpty())
        {
            Scanner cin = new Scanner(System.in);
            String in =  cin.nextLine();
            while(!in.equals("^D"))
            {
                output = in;
                System.out.println(output);
                in = cin.nextLine();
            }
            cin.close();
            return;
        }
        else
        {
            for(String arg: args)
            {
                if(arg.isEmpty())
                {
                    Scanner cin = new Scanner(System.in);
                    String in =  cin.nextLine();
                    while(!in.equals("^D"))
                    {
                        output = in;
                        System.out.println(output);
                        in = cin.nextLine();
                    }
                    cin.close();
                    output="";
                    break;
                }
                File directory = new File(currentDirectory,arg);
                if (directory.exists()) {
                    Scanner read = new Scanner(directory);
                    while(read.hasNextLine())
                    {
                        output += read.nextLine();
                        output +="\n";
                    }
                    read.close();
                    //System.out.println("FILE TIMESTAMP UPDATED: "+ file.getName());
                }
                else{
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
