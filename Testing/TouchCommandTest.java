package Testing;

import CLI.Touch_Command;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TouchCommandTest {
    private String testDir = "testDir";
    private File testDirectory;
    private Touch_Command touchCommand;

    @BeforeEach
    void setUp() {
        testDirectory = new File(testDir);
        testDirectory.mkdir();
        touchCommand = new Touch_Command(testDirectory);
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
    void createNewFile() throws Exception {
        String file = "test1.txt";
        touchCommand.appendOperand(file);
        touchCommand.execute();

        File createdFile = new File(testDirectory, file);
        assertTrue(createdFile.exists(), "File should be created");
    }

    @Test
    void create_multiple_empty_files() throws Exception {
        // Hardcoded file names
        String file1 = "file1.txt";
        String file2 = "file2.txt";
        String file3 = "file3.txt";
        

        touchCommand.appendOperand(file1);
        touchCommand.appendOperand(file2);
        touchCommand.appendOperand(file3);
        
        touchCommand.execute();

        File cfile1 = new File(testDirectory, file1);
        File cfile2 = new File(testDirectory, file2);
        File cfile3 = new File(testDirectory, file3);

        assertTrue(cfile1.exists());
        assertTrue(cfile1.length() == 0);

        assertTrue(cfile2.exists());
        assertTrue(cfile2.length() == 0);

        assertTrue(cfile3.exists());
        assertTrue(cfile3.length() == 0);
    }


}