package Testing;

import CLI.LS_Command;



import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.lang.Exception;


import static org.junit.jupiter.api.Assertions.*;

public class LsTest {
   private String testDir = "testDir";
    private File testDirectory;
    private LS_Command lsCommand;
    private final ByteArrayOutputStream outputAtConsole = new ByteArrayOutputStream();

    @BeforeEach
    void setUp(){
        testDirectory = new File(testDir);
        testDirectory.mkdir();
        lsCommand = new LS_Command(testDirectory); 
        System.setErr(new PrintStream(outputAtConsole));
    }

    @AfterEach
    void cleanUp() {
        deleteDirectory(testDirectory);
        outputAtConsole.reset();
    }

    private void deleteDirectory(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                deleteDirectory(f);
            }
        }
        file.delete();
    }


    @Test
    void ls() throws Exception {
    File file1 = new File(testDir, "test1.txt");
    File file2 = new File(testDir, ".hidden.txt");
    file1.createNewFile();
    file2.createNewFile();

    lsCommand.execute();

    String output = lsCommand.Output();
    
    // Assertions
    assertTrue(output.contains("test1.txt"), "Output should contain test1.txt");
    assertFalse(output.contains(".hidden.txt"), "Output should not contain .hiddFile.txt");

}

@Test
public void testLS_A() throws Exception {
    // Create files for testing
    File visibleFile = new File(testDirectory, "visiblefile.txt");
    File hiddenFile = new File(testDirectory, ".hiddFile.txt");
    visibleFile.createNewFile();
    hiddenFile.createNewFile();
    
    // Set the option and execute the command
    lsCommand.setOption("a");
    lsCommand.execute();
    
    // Capture the output directly from LS_Command's Output method
    String output = lsCommand.Output();
    
    // Assertions
    assertTrue(output.contains("visiblefile.txt"), "Output should contain visiblefile.txt");
    assertTrue(output.contains(".hiddFile.txt"), "Output should contain .hiddFile.txt");
}


@Test
public void testLS_R()throws Exception{
    
   

        File File1 = new File(testDirectory,"file1.txt");
        File1.createNewFile();
        File File2 = new File(testDirectory,"file2.txt");
        File2.createNewFile();
        File File3 = new File(testDirectory,"file3.txt");
        File3.createNewFile();
        lsCommand.setOption("r");
        lsCommand.execute();
        String output = lsCommand.Output();
        
        assertTrue(output.contains("file3.txt"));
        assertTrue(output.contains("file2.txt"));
        assertTrue(output.contains("file1.txt"));
        assertTrue(output.indexOf("file3.txt") < output.indexOf("file2.txt"));
        assertTrue(output.indexOf("file2.txt") < output.indexOf("file1.txt"));
        
    }
    @Test
    public void testLS_InvalidPath() throws Exception {
        // Set an invalid directory path as an operand
        lsCommand.appendOperand("nonExistentDir");
        
        try {
            lsCommand.execute();
            fail("Expected an exception to be thrown for an invalid path");
        } 
        catch (Exception e) {
            assertTrue(e.getMessage().contains("Not a Valid Path"), "Exception should indicate invalid path");
        }
    }
    @Test
    public void testLS_A_InvalidPath()  throws Exception {
        // Set an invalid directory path as an operand
        lsCommand.appendOperand("nonExistentDir");
        lsCommand.setOption("a");

        try {
            lsCommand.execute();
            fail("Expected an exception to be thrown for an invalid path");
        } 
        catch (Exception e) {
            assertTrue(e.getMessage().contains("Not a Valid Path"), "Exception should indicate invalid path");
        }
    }
    
    
    @Test
    public void testLS_R_InvalidPath()  throws Exception {
        // Set an invalid directory path as an operand
        lsCommand.appendOperand("nonExistentDir");
        lsCommand.setOption("r");
    
        try {
            lsCommand.execute();
            fail("Expected an exception to be thrown for an invalid path");
        } 
        catch (Exception e) {
            assertTrue(e.getMessage().contains("Not a Valid Path"), "Exception should indicate invalid path");
        }
    }
        
    
    @Test
    public void testLS_EmptyDirectory() throws Exception {
        // Ensure the directory is empty
        for (File file : testDirectory.listFiles()) {
            file.delete();
        }
        
        lsCommand.execute();
        
        String output = lsCommand.Output();
        assertTrue(output.isEmpty());
    }

    @Test
    public void testLS_a_EmptyDirectory() throws Exception {
        // Ensure the directory is empty
        for (File file : testDirectory.listFiles()) {
            file.delete();
        }
        lsCommand.setOption("a");
        lsCommand.execute();
        
        String output = lsCommand.Output();
        assertTrue(output.isEmpty());
    }
    
    @Test
    public void testLS_r_EmptyDirectory() throws Exception {
        // Ensure the directory is empty
        for (File file : testDirectory.listFiles()) {
            file.delete();
        }
        lsCommand.setOption("r");
        lsCommand.execute();
        
        String output = lsCommand.Output();
        assertTrue(output.isEmpty());
    }
    
     

}


 
    

    
    


