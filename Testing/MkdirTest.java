package Testing;

import CLI.MkDir_Command;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MkdirTest {
    private File testDirectory;
    private MkDir_Command mkdirCommand;

    @BeforeEach
    void setUp() {
        testDirectory = new File("testDir");
        testDirectory.mkdir();
        mkdirCommand = new MkDir_Command(testDirectory); // Use custom directory for testing
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
    void makeOneDirectorySuccess() throws Exception {
        mkdirCommand.appendOperand("newDir");
        mkdirCommand.execute();
        File newDir = new File(testDirectory, "newDir");
        assertTrue(newDir.exists(), "Directory should exist after creation");
    }

    @Test
    void makeDirectoryInvalidPath() throws Exception {
        mkdirCommand.appendOperand("invalidDir*");
        mkdirCommand.execute();
        File invalidDir = new File(testDirectory, "invalidDir*");
        assertFalse(invalidDir.exists(), "Directory should not be created for invalid path");
    }

    @Test
    void makeMultipleDirectories() throws Exception {
        mkdirCommand.appendOperand("dirOne");
        mkdirCommand.appendOperand("dirTwo");
        mkdirCommand.appendOperand("dirThree");
        mkdirCommand.execute();

        File dirOne = new File(testDirectory, "dirOne");
        File dirTwo = new File(testDirectory, "dirTwo");
        File dirThree = new File(testDirectory, "dirThree");

        assertTrue(dirOne.exists() && dirTwo.exists() && dirThree.exists(), "All directories should be created");
    }

    @Test
    void makeDirectoryWithVerboseOption() throws Exception {
        mkdirCommand.appendOperand("verboseDir");
        mkdirCommand.setOption("v"); // Set verbose option
        mkdirCommand.execute();

        File verboseDir = new File(testDirectory, "verboseDir");
        assertTrue(verboseDir.exists(), "Directory should be created and verbose message should be printed");
    }

    @Test
    void makeNestedDirectoriesFails() throws Exception {
        mkdirCommand.appendOperand("dir/one");
        mkdirCommand.execute();

        File dir = new File(testDirectory, "dir");
        File one = new File(testDirectory, "dir/one");

        assertFalse(dir.exists() && one.exists(), "Nested directories should not be created with single command execution");
    }
}
