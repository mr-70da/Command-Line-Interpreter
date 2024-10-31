package Testing;

import CLI.Mv_Command;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MvTest {
    private String testDir = "testDir";
    private File testDirectory;
    private Mv_Command mvCommand;
    private final ByteArrayOutputStream outputAtConsole = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        testDirectory = new File(testDir);
        testDirectory.mkdir();
        mvCommand = new Mv_Command(testDirectory); // Use custom directory for testing

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
    void move_single_file_success() throws Exception {
        // Create a file to move
        File fileToMove = new File(testDirectory, "fileToMove.txt");
        fileToMove.createNewFile();

        // Specify destination
        mvCommand.appendOperand("fileToMove.txt");
        mvCommand.appendOperand("newLocation.txt");
        mvCommand.execute();

        File movedFile = new File(testDirectory, "newLocation.txt");
        assertTrue(movedFile.exists(), "File should be moved successfully");
        assertFalse(fileToMove.exists(), "Original file should no longer exist");
    }

    @Test
    void move_file_invalid_source_path() throws Exception {
        String expectedMessage = "Source file/directory does not exist: " + new File(testDirectory, "invalidFile.txt").getAbsolutePath();

        mvCommand.appendOperand("invalidFile.txt");
        mvCommand.appendOperand("destinationFile.txt");
        mvCommand.execute();
        String actualMessage = outputAtConsole.toString().trim();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void move_multiple_files_to_directory() throws Exception {
        File fileOne = new File(testDirectory, "fileOne.txt");
        File fileTwo = new File(testDirectory, "fileTwo.txt");
        fileOne.createNewFile();
        fileTwo.createNewFile();

        
        File destDir = new File(testDirectory, "destDir");
        destDir.mkdir();

        // Move files
        mvCommand.appendOperand("fileOne.txt");
        mvCommand.appendOperand("fileTwo.txt");
        mvCommand.appendOperand("destDir");
        mvCommand.execute();

        assertTrue(new File(destDir, "fileOne.txt").exists(), "fileOne.txt should be moved to destDir");
        assertTrue(new File(destDir, "fileTwo.txt").exists(), "fileTwo.txt should be moved to destDir");
    }





}