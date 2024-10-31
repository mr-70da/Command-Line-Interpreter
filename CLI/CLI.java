package CLI;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays; //
import java.util.Collections; //



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
                else if (command.startsWith("mv")) {
                    moveFileOrDirectory(command);
                }
                else if(command.startsWith("rm")){
                    removeFile(command);
                }
                else{
                    System.err.println("wrong command.");
                }

            }


        }


    }
    private static void changeDirectory(String command) {
        String parts[] = command.split(" ");
        if (parts.length<2) {
            System.err.println("Please specify a directory.");
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

    //mv
    private static void moveFileOrDirectory(String command) {
        List<String> parts = parseDirectoryPath(command.substring(2).trim());
        
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


    //rm
    private static void removeFile(String command){
        List<String> parts = parseDirectoryPath(command.substring(2).trim());
        
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
    //parsing directory
    private static List<String> parseDirectoryPath(String directoryPathTemp) {
        List<String> folders = new ArrayList<>();
        StringBuilder currentFolder = new StringBuilder();
        boolean inQuotes = false;
        boolean escaped = false;
        Character quoteType = '\0';
    
        for (int i = 0; i < directoryPathTemp.length(); i++) {
            char c = directoryPathTemp.charAt(i);
    
            if (escaped) {
                currentFolder.append(c);
                escaped = false;
                continue;
            }
    
            if (c == '\\') {
                escaped = true;
                continue;
            }
    
            if (c == '"' || c == '\'') {
                if (!inQuotes) {
                    inQuotes = true;
                    quoteType = c;
                }
                else if (c == quoteType) {
                    inQuotes = false;
                }
                else {
                    currentFolder.append(c);
                }
            } 
            else if (c == ' ' && !inQuotes) {
                if (currentFolder.length() > 0) {
                    folders.add(currentFolder.toString());
                    currentFolder.setLength(0);
                }
            } else {
                currentFolder.append(c);
            }
        }
    
        if (currentFolder.length() > 0) {
            folders.add(currentFolder.toString());
        }
    
        if (inQuotes) {
            System.err.println("Invalid argument: Unmatched quote");
            return null;
        }
    
        return folders;
    }

     public void ls(String directoryPath) { //
        File directory;
        if (directoryPath.isEmpty()) {
            directory = new File(".");
        } else {
            directory = new File(directoryPath);
        }
        
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            
            if (files != null && files.length == 0) {
                System.out.println("Directory is Empty");
            } else {
                for (File file : files) {
                    if (!file.isHidden()) {
                        System.out.println(file.getName());
                    }
                }
            }
        } else {
            System.out.println("Not a Valid Path");
        }
    }

    public void ls_a(String directoryPath){ //
        File directory;
        if (directoryPath.isEmpty()) {
            directory = new File(".");
        } else {
            directory = new File(directoryPath);
        }
        
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            
            if (files != null && files.length == 0) {
                System.out.println("Directory is Empty");
            } else {
                for (File file : files) {
                    System.out.println(file.getName());
                }
                
            }
                    
        } 
        else {
            System.out.println("Not a Valid Path");
        }
    }

      public void ls_r(String directoryPath){ //
        File directory;
        if (directoryPath.isEmpty()) {
            directory = new File(".");
        } else {
            directory = new File(directoryPath);
        }
        
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            
            if (files != null && files.length == 0) {
                System.out.println("Directory is Empty");
            } else {
                Arrays.sort(files,Collections.reverseOrder());
                for (File file : files) {
                    if (!file.isHidden()) {
                        System.out.println(file.getName());
                    }
                }
            }
        } else {
            System.out.println("Not a Valid Path");
        }
    }

    public void touch(String directoryPath){ //
        File file = new File(directoryPath);
        try {
            if (file.exists()) {
                file.setLastModified(System.currentTimeMillis());
                System.out.println("FILE TIMESTAMP UPDATED");
            }
            else{
                if (file.createNewFile()) {
                    System.out.println(file.getName());
                } else {
                    System.out.println("Failed to create file.");
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
 

}

