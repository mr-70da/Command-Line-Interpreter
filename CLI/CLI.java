package CLI;

import java.io.File;

import java.util.*;

public class CLI {
    private static File currentDirectory = new File(System.getProperty("user.dir"));
    private static void CLI_interface()
    {
        System.out.println("pw: Display your current location path.");
        System.out.println("cd: Display the current directory in the specified drive.");
        System.out.println("ls: Display files and directories in your current location in alphabetical order.");
        System.out.println("ls –a: Display all files and directories in your current location including that are hidden.");
        System.out.println("ls –r: Display files and directories in your current location that need permission to read the file.");
        System.out.println("mkdir: Create a new folder or directory.");
        System.out.println("rmdir: Remove empty directories. It ensures that the directories are empty before deleting them.");
        System.out.println("touch: Create a file without any content");
        System.out.println("mv: Move files or rename files");
        System.out.println("rm: Remove files and directories");
        System.out.println("cat: Concatenate files and print to stdout.");
        System.out.println(">: Add output of the fortune command to a file, creating it if is doesn’t already exist and overwriting it if it does.");
        System.out.println(">>: Content will be appended to the file if it exists and used to create a new file if it doesn’t.");
        System.out.println("|: Pass the output of the command on the left side of the pipe to the command on the right side of the pipe");
        System.out.println("exit: To end the terminal session");
    }
    public static File getDirr()
    {
        return currentDirectory;
    }
    public static void setDirr(File newDirr)
    {
        currentDirectory = newDirr;
    }
    public void Run()
    {
        System.out.print(currentDirectory+"> ");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        while (!command.equals("exit")) {
            if(command.equals("help"))
            {
                CLI.CLI_interface();
                command = scanner.nextLine();
            }
            else
            {
                List<Executable> ParsedInstructions = new ArrayList<>();
                String outt = new String("");
                String printed = new String("");
                try{
                    ParsedInstructions = CommandProcessor.CommandParser(command);
                    for(Executable comm : ParsedInstructions)
                    {
                        try{
                            if(comm instanceof WriterCommand && comm instanceof ReaderCommand)
                            {
                                ((ReaderCommand)comm).Input(outt);
                                comm.execute();
                                outt = ((WriterCommand)comm).Output();
                                printed = "";
                            }
                            else if(comm instanceof WriterCommand)
                            {
                                comm.execute();
                                outt += ((WriterCommand)comm).Output();
                                printed = outt;
                            }
                            else
                            {
                                comm.execute();
                                outt = "";
                                printed = "";
                            }
                        }
                        catch(Exception ex){
                            System.out.println(printed);
                            System.err.println(ex.getMessage());        
                            outt = "";
                            printed = "";
                        }
                    }
                }
                catch(Exception e)
                {
                    System.err.println(e.getMessage());
                }
                finally{
                    if(!printed.isEmpty())
                    {
                        System.out.println(printed);
                        outt = "";
                        printed = "";
                    }
                    System.out.print(currentDirectory+"> ");
                    command = scanner.nextLine();
                }

            }
        }
        scanner.close();
    }
