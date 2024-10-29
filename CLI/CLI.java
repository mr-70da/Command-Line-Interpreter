package CLI;

import java.io.File;
import java.util.Scanner;

public class CLI {
    private static File currentDirectory = new File(System.getProperty("user.dir"));
    public static void main(String[] args){
        int errorCnt=0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to command line interpreter type 'exist' to quit or type 'help' to show available commands' menu.");
        boolean isExist = false;
        while(!isExist){
            System.out.print(currentDirectory.getAbsolutePath()+"> ");
            String command = scanner.nextLine();
            if(command.equals("exit")){
                isExist = true;
            }else if (command.equals("help")){
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

            }else{
                String s[] = command.split(" ");
                if(command.equals("pwd")){
                    System.out.print(currentDirectory.getAbsolutePath());
                }else if(command.startsWith("cd")){
                    changeDirectory(command);
                }
                else if(command.startsWith("mkdir")){
                    makeDirectory(command);
                }
                else{
                    System.out.println("wrong command.");
                    errorCnt++;
                    if(errorCnt==3){
                        System.out.println("dah enta ghaby ebn metnaka.");
                    }else if(errorCnt==4){
                        System.out.println("laa kosomk ba2a.");
                    }else if(errorCnt==5){
                        System.out.println("yabnl el sharmota estkhadem 'help' talma gahel keda");
                    }

                }

            }


        }


    }
    private static void changeDirectory(String command) {
        String parts[] = command.split(" ");
        if (parts.length<2) {
            System.out.println("Please specify a directory.");
            return;
        }
        String directoryPath = command.substring(3).trim();

        File newDirectory = new File(directoryPath);
        if (newDirectory.isAbsolute()) {
            if (newDirectory.isDirectory()) {
                currentDirectory = newDirectory;
            } else {
                System.out.println("Not a directory: " + directoryPath);
            }
        } else {
            File relativeDir = new File(currentDirectory, directoryPath);
            if (relativeDir.isDirectory()) {
                currentDirectory = relativeDir;
            } else {
                System.out.println("Not a directory: " + directoryPath);
            }
        }
    }


    private static void makeDirectory(String command){
        String parts[] = command.split(" ");
        if (parts.length<2) {
            System.out.println("Please specify a directory.");
            return;
        }
        String directoryPath = command.substring(5).trim();

        String[] pathParts = directoryPath.split("/");

        File current = currentDirectory;

        for (int i = 0; i < pathParts.length; i++) {

            File subDirectory = new File(current, pathParts[i]);

            if (i == pathParts.length - 1) { // new file to be created
                if (subDirectory.exists()) {
                    System.out.println("Directory already exists: " + subDirectory.getPath());
                }
                else{
                    boolean isCreated = subDirectory.mkdir();
                    if (isCreated) {
                        System.out.println("Directory created successfully: " + subDirectory.getPath());
                    }
                    else{
                        System.out.println("Failed to create directory: " + subDirectory.getPath());
                    }
                }
            }
            else{
                if (!subDirectory.exists() || !subDirectory.isDirectory()) {
                    System.out.println("No such file or directory: " + subDirectory.getPath());
                    return;
                }       
            }
            current = subDirectory;
            }
        
        
        
    }

}

