package Testing;

import CLI.Rm_Command;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class RmTest {
    private String testDir = "testDir";
    private File testDirectory;
    private Rm_Command rmCommand;
    private final ByteArrayOutputStream outputAtConsole = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        testDirectory = new File(testDir);
        testDirectory.mkdir();
        rmCommand = new Rm_Command(testDirectory); // Use custom directory for testing

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
    void remove_single_file_success() throws Exception{
        File fileToRemove = new File (testDirectory, "fileToRemove.txt");
        fileToRemove.createNewFile();

        rmCommand.appendOperand("fileToRemove.txt");
        rmCommand.execute();

        assertFalse(fileToRemove.exists(), "File shoudl be removed successfully");
    }

    @Test
    void remove_file_invalid_path() throws Exception{
        String excpecMessage = "File does not exist: " + new File(testDirectory, "nonexistant.txt").getAbsolutePath();

        rmCommand.appendOperand("nonexistant.txt");
        rmCommand.execute();

        String actualMessage = outputAtConsole.toString().trim();

        assertEquals(excpecMessage, actualMessage);
    }

    @Test
    void remove_multiple_files() throws Exception{
        File fileOne = new File(testDirectory, "fileOne.txt");
        File fileTwo = new File(testDirectory, "fileTwo.txt");
        fileOne.createNewFile();
        fileTwo.createNewFile();

        rmCommand.appendOperand("fileOne.txt");
        rmCommand.appendOperand("fileTwo.txt");
        rmCommand.execute();

        assertFalse(fileOne.exists(), "fileOne.txt should be removed");
        assertFalse(fileTwo.exists(), "fileTwo.txt should be removed");
    }

    @Test
    void remove_directory_with_rm_fails() throws Exception {
        File dirToRemove = new File(testDirectory, "dirToRemove");
        dirToRemove.mkdir();

        
        rmCommand.appendOperand("dirToRemove");
        rmCommand.execute();

        String expectedMessage = "Error: cannot remove directory with rm. Use rmdir to remove directories";
        String actualMessage = outputAtConsole.toString().trim();

        assertTrue(dirToRemove.exists(), "Directory should not be removed");
        assertEquals(expectedMessage, actualMessage);
    }


}