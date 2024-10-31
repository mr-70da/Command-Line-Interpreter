package Testing;

import CLI.MkDir_Command;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;


class MkdirTest {
    private String testDir = "testDir";
    private File testDirectory;
    private MkDir_Command mkdirCommand;

    @BeforeEach
    void setUp() {
        testDirectory = new File(testDir);
        testDirectory.mkdir();
        mkdirCommand = new MkDir_Command(testDirectory); // Use custom directory for testing
    }

    @AfterEach
    void cleanUp() {
        deleteDirectory(testDirectory);
    }

    private void deleteDirectory(File file) {
        File[] files = file.listFiles();
        if (file != null) {
            for (File f : files) {
                deleteDirectory(f);
            }
        }
        file.delete();
    }

    @Test
    void make_one_directory_success_operation() throws Exception{
        mkdirCommand.appendOperand("newDir");
        mkdirCommand.execute();

        File newDir = new File(testDirectory, "newDir");
        assertTrue(newDir.exists(), "Directory should exist after creation");
    }

    @Test
    void make_directory_invalid_path() throws Exception{
        mkdirCommand.appendOperand("invalidDir*");
        mkdirCommand.execute();

        File invalidDir = new File(testDirectory, "invalidDir*");
        assertFalse(invalidDir.exists(), "Directory should not be created for invalid path");
    }

    @Test
    void make_multiple_directories() throws Exception {
        mkdirCommand.appendOperand("dirOne");
        mkdirCommand.appendOperand("dirTwo");
        mkdirCommand.appendOperand("dirThree");
        mkdirCommand.execute();

        File dirOne = new File(testDirectory, "dirOne");
        File dirTwo = new File(testDirectory, "dirTwo");
        File dirThree = new File(testDirectory, "dirThree");

        boolean isCreated = dirOne.exists() && dirTwo.exists() && dirThree.exists();
        assertTrue(isCreated, "Three directories should be created");
    }


    @Test
    void make_nested_directories_in_right_order() throws Exception {
        mkdirCommand.appendOperand("dir");
        mkdirCommand.appendOperand("dir/one");
        mkdirCommand.appendOperand("dir/one/two");
        mkdirCommand.execute();

        File dir = new File(testDirectory, "dir");
        File one = new File(testDirectory, "dir/one");
        File two = new File(testDirectory, "dir/one/two");
        boolean isCreated = dir.exists() && one.exists() && two.exists();
        assertTrue(isCreated, "Directory two should exist inside one and one should exist inside dir");
    }

    @Test
    void make_nested_directories_at_once()  throws Exception{
        mkdirCommand.appendOperand("dir/one");
        mkdirCommand.execute();

        File dir = new File(testDirectory, "dir");
        File one = new File(testDirectory, "dir/one");

        assertFalse(dir.exists() && one.exists(), "Invalid argument cannot create nested directories at once; no directory should be created");
    }
}
