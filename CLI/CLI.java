package CLI;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;


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
                else if (command.startsWith("rmdir")) {
                    removeDirectory(command);
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




    //mkdir
    private static void makeDirectory(String command){
        String parts[] = command.split(" ");
        if (parts.length<2) {
            System.out.println("Please specify a directory.");
            return;
        }
        String directoryPathTemp = command.substring(5).trim();
        List<String> folders = parseDirectoryPath(directoryPathTemp);

        for (String folder : folders) {
            _makeDirectory(folder);
        }

    }
    

   private static void _makeDirectory(String directoryPath){
       File directory  = new File(currentDirectory,directoryPath);
        if (directory.exists()) {
            System.err.println("Directory already exists: " + directory.getPath());
            return;
        }
        boolean created = directory.mkdir();
        if (created) {
            System.out.println("Directory created successfully: " + directory.getPath());
        } else {
            System.out.println("Failed to create the directory: " + directory.getPath());
        }        
        
    }




    //rmdir
    private static void removeDirectory(String command){
        String parts[] = command.split(" ");
        if (parts.length<2) {
            System.out.println("Please specify a directory.");
            return;
        }

        String directoryPathTemp = command.substring(5).trim();
        List<String> folders = parseDirectoryPath(directoryPathTemp);

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

    //parsing directory
    private static List<String> parseDirectoryPath(String directoryPathTemp){
        List<String> folders = new ArrayList<>();

        StringBuilder currentFolder = new StringBuilder();
        boolean  insideQuotes = false;
        char quote = '\0';

        for(int i = 0 ; i < directoryPathTemp.length(); i++){
            char current = directoryPathTemp.charAt(i);

            if ((current == '"' || current == '\'')&& (quote == '\0' || quote == current)) {
                insideQuotes = !insideQuotes;
                quote = insideQuotes? current : '\0' ;//0 for end //current if beginning

                if (!insideQuotes && currentFolder.length() > 0) { //ending quote
                    folders.add(currentFolder.toString());
                    currentFolder.setLength(0); //fady
                }
            }
            else if (current == ' ' && !insideQuotes){
                if (currentFolder.length() > 0) {
                    folders.add(currentFolder.toString());
                    currentFolder.setLength(0); //fady
                }
            }
            else{
                currentFolder.append(current);
            }
        }
        //last folder
        if (currentFolder.length() > 0) {
            folders.add(currentFolder.toString());
        }
        return folders;
    }


}

