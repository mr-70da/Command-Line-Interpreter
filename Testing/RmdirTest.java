package Testing;

import CLI.MkDir_Command;
import CLI.RmDir_Command;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class RmdirTest {
    private String testDir = "testDir";
    private File testDirectory;
    private RmDir_Command rmdirCommand;
    private MkDir_Command mkdirCommand;
    private final ByteArrayOutputStream outputAtConsole = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        testDirectory = new File(testDir);
        testDirectory.mkdir();
        rmdirCommand = new RmDir_Command(testDirectory); // Use custom directory for testing
        mkdirCommand = new MkDir_Command(testDirectory); // Set up MkDir command for directory creation

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
    void remove_single_directory_success() throws Exception {
        // Making directories
        mkdirCommand.appendOperand("dirToDelete");
        mkdirCommand.execute();

        File dirToDelete = new File(testDirectory, "dirToDelete");

        // Removing directories
        rmdirCommand.appendOperand("dirToDelete");
        rmdirCommand.execute();

        assertFalse(dirToDelete.exists(), "The directory should be deleted");
    }

    @Test
    void remove_directory_invalid_path() throws Exception {
        String expectedMessage = "Directory does not exist: " + new File(testDirectory, "invalidDir").getPath();

        rmdirCommand.appendOperand("invalidDir");
        rmdirCommand.execute();
        String actualMessage = outputAtConsole.toString().trim();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void remove_multiple_directories() throws Exception {
        // Making directories
        mkdirCommand.appendOperand("dirOne");
        mkdirCommand.appendOperand("dirTwo");
        mkdirCommand.appendOperand("dirThree");
        mkdirCommand.execute();
        
        File dirOne = new File(testDirectory, "dirOne");
        File dirTwo = new File(testDirectory, "dirTwo");
        File dirThree = new File(testDirectory, "dirThree");

        // Removing directories
        rmdirCommand.appendOperand("dirOne");
        rmdirCommand.appendOperand("dirTwo");
        rmdirCommand.appendOperand("dirThree");
        rmdirCommand.execute();

        boolean allDeleted = !dirOne.exists() && !dirTwo.exists() && !dirThree.exists();
        assertTrue(allDeleted, "All directories should be deleted");
    }

    @Test
    void remove_directory_with_spaces_using_double_quotes() throws Exception {
        // Making directories
        mkdirCommand.appendOperand("\"Dir Name\"");
        mkdirCommand.execute();

        File dirWithSpaces = new File(testDirectory, "Dir Name");

        // Removing directories
        rmdirCommand.appendOperand("\"Dir Name\"");
        rmdirCommand.execute();

        assertFalse(dirWithSpaces.exists(), "The directory with spaces should be deleted");
    }

    @Test
    void remove_directory_with_spaces_using_single_quotes() throws Exception {
        // Making directories
        mkdirCommand.appendOperand("'Dir Name'");
        mkdirCommand.execute();

        File dirWithSpaces = new File(testDirectory, "Dir Name");

        // Removing directories
        rmdirCommand.appendOperand("'Dir Name'");
        rmdirCommand.execute();

        assertFalse(dirWithSpaces.exists(), "The directory with spaces should be deleted");
    }

    @Test
    void remove_non_empty_directory_fails() throws Exception {
        // Making directories
        mkdirCommand.appendOperand("nonEmptyDir");
        mkdirCommand.appendOperand("nonEmptyDir/subDir");
        mkdirCommand.execute();

        File nonEmptyDir = new File(testDirectory, "nonEmptyDir");

        // Removing directories
        rmdirCommand.appendOperand("nonEmptyDir");
        rmdirCommand.execute();

        assertTrue(nonEmptyDir.exists(), "Non-empty directory should not be deleted");
    }

    @Test
    void remove_nested_directories_in_order() throws Exception {
        // Making directories
        mkdirCommand.appendOperand("One");
        mkdirCommand.appendOperand("One/Two");
        mkdirCommand.execute();

        File one = new File(testDirectory, "One");
        File two = new File(testDirectory, "One/Two");

        // Removing directories
        rmdirCommand.appendOperand("One/Two");
        rmdirCommand.execute();
        rmdirCommand.appendOperand("One");
        rmdirCommand.execute();

        assertFalse(two.exists(), "Child directory should be deleted first");
        assertFalse(one.exists(), "Parent directory should be deleted after child directory");
    }
}
