import org.junit.AfterClass;
import org.junit.Before;
// import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class cliTest {
    private static cli linuxCommandTest;
    
    @Before
    public void setup() {
        linuxCommandTest = new cli();
    }
    
    @AfterClass
    public static void clean() {
        linuxCommandTest = null; 
    }
    
    @Test
    public void testLS_validDirectory(){
        //create a directory
        File temporaryFile = createTemporaryDirectory("testdir");
        try {
            File visibleFile = new File(temporaryFile,"visiblefile.txt");
            visibleFile.createNewFile();
            File hiddFile = new File(temporaryFile,".hiddFile.txt");
            hiddFile.createNewFile();
            
            String output = captureLsOutput(temporaryFile.getPath());
            
            assertTrue(output.contains("visiblefile.txt"));
            assertFalse(output.contains(".hiddfile.txt"));
            
        } catch (IOException e) {
            //
            fail("IOException occurred while creating files: " + e.getMessage());
        }
        finally {
            //delete the files
            File visibleFile = new File(temporaryFile, "visibleFile.txt");
            File hiddFile = new File(temporaryFile, ".hiddFile.txt");
            deleteFile(visibleFile);
            deleteFile(hiddFile);
            // Delete the directory itself
            deleteFile(temporaryFile);
        }
    }
    
    
    @Test
    public void testLS_invalidDirectory(){
        String output = captureLsOutput("INVALIDPATH");
        assertTrue(output.contains("Not a Valid Path")); 
    }
    
    
    @Test
    public void testLS_emptyDirectory(){
        File temporaryDirectory = createTemporaryDirectory("testdir");
        
        
        String output = captureLsOutput(temporaryDirectory.getPath());
        assertTrue(output.contains("Directory is Empty"));
        
        deleteFile(temporaryDirectory);
    }
    
    @Test
    public void testLS_A_validDirectory(){
        //create a directory
        File temporaryFile = createTemporaryDirectory("testdir");
        try {
            File visibleFile = new File(temporaryFile,"visiblefile.txt");
            visibleFile.createNewFile();
            File hiddFile = new File(temporaryFile,".hiddFile.txt");
            hiddFile.createNewFile();
            
            String output = captureLs_a_Output(temporaryFile.getPath());
            
            assertTrue(output.contains("visiblefile.txt"));
            assertTrue(output.contains(".hiddFile.txt"));
            
        } catch (IOException e) {
            //
            fail("IOException occurred while creating files: " + e.getMessage());
        }
        finally {
            //delete the files
            File visibleFile = new File(temporaryFile, "visibleFile.txt");
            File hiddFile = new File(temporaryFile, ".hiddFile.txt");
            deleteFile(visibleFile);
            deleteFile(hiddFile);
        }
    }
    
    
    
    
    @Test
    public void testLS_A_invalidDirectory(){
        String output = captureLs_a_Output("INVALIDPATH");
        assertTrue(output.contains("Not a Valid Path")); 
    }
    
    @Test
    public void testLS_A_emptyDirectory(){
        File temporaryDirectory = createTemporaryDirectory("testdir");
        
        String output = captureLsOutput(temporaryDirectory.getPath());
        assertTrue(output.contains("Directory is Empty"));
        
        deleteFile(temporaryDirectory);
    }
    
    @Test
    public void testLS_R_validDirectory(){
        //create a directory
        File temporaryFile = createTemporaryDirectory("testdir");
        try {
            File File1 = new File(temporaryFile,"file1.txt");
            File1.createNewFile();
            File File2 = new File(temporaryFile,"file2.txt");
            File2.createNewFile();
            File File3 = new File(temporaryFile,"file3.txt");
            File3.createNewFile();
            
            String output = captureLs_r_Output(temporaryFile.getPath());
            
            assertTrue(output.contains("file3.txt"));
            assertTrue(output.contains("file2.txt"));
            assertTrue(output.contains("file1.txt"));
            assertTrue(output.indexOf("file3.txt") < output.indexOf("file2.txt"));
            assertTrue(output.indexOf("file2.txt") < output.indexOf("file1.txt"));
        } catch (IOException e) {
            //
            fail("IOException occurred while creating files: " + e.getMessage());
        }
        finally {
            File File1 = new File(temporaryFile,"file1.txt");
            File File2 = new File(temporaryFile,"file2.txt");
            File File3 = new File(temporaryFile,"file3.txt");
            deleteFile(File3);
            deleteFile(File2);
            deleteFile(File1);
            deleteFile(temporaryFile);
        }
    }
    
    @Test
    public void testLS_R_invalidDirectory(){
        String output = captureLs_r_Output("INVALIDPATH");
        assertTrue(output.contains("Not a Valid Path")); 
    }
    
    @Test
    public void testLS_R_emptyDirectory(){
        File temporaryDirectory = createTemporaryDirectory("testdir");
        
        String output = captureLs_r_Output(temporaryDirectory.getPath());
        assertTrue(output.contains("Directory is Empty"));
        
        deleteFile(temporaryDirectory);
    }
    
    @Test
    public void testTouch(){
        File temporaryFile = new File("testFile.txt");
        deleteFile(temporaryFile);
        String output = captureTouchOutput("testFile.txt");
        assertTrue(output.contains("testFile.txt"));
        deleteFile(temporaryFile);
    }
    
    @Test
    public void testTouch_ExistingFile(){
        File temporaryFile = new File("testFile.txt");
        try {
            temporaryFile.createNewFile();
            String output = captureTouchOutput("testFile.txt");
            assertTrue(output.contains("FILE TIMESTAMP UPDATED"));
            
        } catch (IOException e) {
            fail("IOException occurred while creating files: " + e.getMessage());
        }
        finally{
            deleteFile(temporaryFile); 
        }
        
    }
    
    
    
    
    private String captureLsOutput(String directoryPath) {
        // Use a custom PrintStream to capture the output
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        
        // Call the ls method with the directory path
        linuxCommandTest.ls(directoryPath);
        
        // Reset System.out and return output
        System.setOut(System.out);
        return outputContent.toString().trim();
    }
    
    private String captureLs_a_Output(String directoryPath) {
        // Use a custom PrintStream to capture the output
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        
        // Call the ls method with the directory path
        linuxCommandTest.ls_a(directoryPath);
        
        // Reset System.out and return output
        System.setOut(System.out);
        return outputContent.toString().trim();
    }
    private String captureLs_r_Output(String directoryPath) {
        // Use a custom PrintStream to capture the output
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        
        // Call the ls method with the directory path
        linuxCommandTest.ls_r(directoryPath);
        
        // Reset System.out and return output
        System.setOut(System.out);
        return outputContent.toString().trim();
    }
    private String captureTouchOutput(String directoryPath){
        // Use a custom PrintStream to capture the output
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        
        // Call the ls method with the directory path
        linuxCommandTest.touch(directoryPath);
        
        // Reset System.out and return output
        System.setOut(System.out);
        return outputContent.toString().trim();
    }    
    private File createTemporaryDirectory(String dirName){
        File temporaryDir = new File(dirName);
        temporaryDir.mkdir();
        return temporaryDir;
    }
    private void deleteFile(File file){
        if (file.exists()) file.delete(); 
    }
    // private File createTemporaryFile(String filename){
        //     File temporaryFile = new File(filename);
        //     try {
            //         temporaryFile.createNewFile();
            //     } 
            //     catch (IOException e) {
    //         fail("IO EXCEPTION OCCURED WHILE CREATING THE FILE "+e.getMessage());
    //     }
    //     return temporaryFile;
    // }
    
}

