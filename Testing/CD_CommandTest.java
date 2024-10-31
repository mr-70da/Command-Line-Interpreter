package Testing;

import CLI.CD_Command;
import CLI.CLI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import CLI.InvalidPathException;

import static org.junit.jupiter.api.Assertions.*;

class CD_CommandTest {

    private File initialDir;

    @BeforeEach
    void setUp() {
        initialDir = CLI.getDirr();
    }

    @AfterEach
    void tearDown() {
        CLI.setDirr(initialDir);
    }

    @Test
    public void testInvalidDirectoryThrowsInvalidPathException() {
        CD_Command cdCommand = new CD_Command("nonexistent_directory");
        assertThrows(InvalidPathException.class, cdCommand::execute, "Expected InvalidPathException for nonexistent directory.");
    }


    @Test
    void testEmptyOperandDefaultsToCurrentDirectory() throws Exception {
        CD_Command cdCommand = new CD_Command();
        cdCommand.execute();
        assertEquals(initialDir.getCanonicalPath(), CLI.getDirr().getCanonicalPath(), "Should default to the current directory if operand is empty.");
    }
    @Test
    void testChangeToAbsolutePath() throws Exception {
        // Provide an existing absolute directory path for testing
        String absolutePath = System.getProperty("user.home"); // Example: home directory
        CD_Command cdCommand = new CD_Command(absolutePath);
        cdCommand.execute();
        assertEquals(absolutePath, CLI.getDirr().getCanonicalPath(), "Should change to the specified absolute path.");
    }

    @Test
    void testChangeToRelativePath() throws Exception {
        // Create a relative path assuming a folder named "testDir" exists in the current directory
        File testDir = new File(CLI.getDirr(), "testDir");
        testDir.mkdir();  // Create the directory for testing purposes

        CD_Command cdCommand = new CD_Command("testDir");
        cdCommand.execute();
        assertEquals(testDir.getCanonicalPath(), CLI.getDirr().getCanonicalPath(), "Should change to the specified relative path.");

        // Cleanup
        testDir.delete();
    }

    @Test
    void testStayInCurrentDirectoryIfPathIsFile() throws Exception {
        // Create a test file in the current directory
        File testFile = new File(CLI.getDirr(), "testFile.txt");
        testFile.createNewFile();

        CD_Command cdCommand = new CD_Command("testFile.txt");
        assertThrows(InvalidPathException.class, cdCommand::execute, "Expected InvalidPathException when trying to navigate to a file.");

        // Cleanup
        testFile.delete();
    }

    @Test
    void testEmptyOperandRemainsInCurrentDirectory() throws Exception {
        CLI.setDirr(new File(System.getProperty("user.home")));  // Set initial directory to home
        CD_Command cdCommand = new CD_Command();
        cdCommand.execute();
        assertEquals(System.getProperty("user.home"), CLI.getDirr().getCanonicalPath(), "Should remain in the current directory if operand is empty.");
    }

    @Test
    void testInvalidRelativePathThrowsException() {
        CD_Command cdCommand = new CD_Command("nonexistent_relative_directory");
        assertThrows(InvalidPathException.class, cdCommand::execute, "Expected InvalidPathException for nonexistent relative directory.");
    }
}
