package Testing;

import CLI.LS_Command;



import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LsTest {
   private String testDir = "testDir";
    private File testDirectory;
    private LS_Command lsCommand;

    @BeforeEach
    void setUp(){
        testDirectory = new File(testDir);
        testDirectory.mkdir();
        lsCommand = new LS_Command(testDirectory); 
    }

    @AfterEach
    void cleanUp() {
        deleteDirectory(testDirectory);
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
    void listAllFiles() throws Exception {
        File file1 = new File(testDir, "test1.txt");
        File file2 = new File(testDir, ".hidden.txt");
        file1.createNewFile();
        file2.createNewFile();
    
        lsCommand.setOption("a");
        lsCommand.execute();
    
        List<String> ouput = Arrays.asList(lsCommand.Output().trim().split("\n"));
        List<String> expected = Arrays.asList("test1.txt", ".hidden.txt");
    
        assertTrue(ouput.containsAll(expected), "Output does not match");
    }



    @Test
    void listFilesWithoutOptions() throws Exception {
    File file1 = new File(testDir, "test1.txt");
    File file2 = new File(testDir, ".hidden.txt");
    file1.createNewFile();
    file2.createNewFile();

    lsCommand.execute();

    List<String> ouput = Arrays.asList(lsCommand.Output().trim().split("\n"));
    List<String> expected = Arrays.asList("test1.txt"); 

    assertEquals(expected, ouput, "Output does not match");
}


    
    

}
