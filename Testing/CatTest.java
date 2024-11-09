package Testing;

import CLI.Concat_Command;
import CLI.InsertAppend_Command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class CatTest {
    private String testDir = "testDir";
    private File testDirectory;
    private Concat_Command catCommand;

    @BeforeEach
    void setUp() {
        testDirectory = new File(testDir);
        testDirectory.mkdir();
        catCommand = new Concat_Command(testDirectory); // Use custom directory for testing
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
    void MatchingContent() throws Exception
    {
        File file = new File(testDir,"test.txt");
        Files.writeString(file.toPath(),"Content of test");
        catCommand.appendOperand("test.txt");
        catCommand.execute();
        assertEquals("Content of test",catCommand.Output(), "Content is misrepresented");
    }
    @Test
    void NoContent() throws Exception
    {
        File file = new File(testDir,"test.txt");
        //file.createNewFile();
        Files.writeString(file.toPath(),"");
        catCommand.appendOperand("test.txt");
        catCommand.execute();
        assertTrue(file.exists(),"Failed to create File.");
        assertEquals("",catCommand.Output(), "Content is misrepresented");
    }
    @Test
    void MultipleFiles() throws Exception
    {
        File file = new File(testDir,"test.txt");
        File file2 = new File(testDir,"test2.txt");
        File file3 = new File(testDir,"test3.txt");
        //file.createNewFile();
        Files.writeString(file.toPath(),"Content 1\n");
        Files.writeString(file2.toPath(),"Content 2");
        Files.writeString(file3.toPath(),"Content 3");
        catCommand.appendOperand("test.txt");
        catCommand.appendOperand("test2.txt");
        catCommand.appendOperand("test3.txt");
        catCommand.execute();
        assertEquals("Content 1\nContent 2Content 3",catCommand.Output(), "Content is misrepresented");
    }

    @Test
    void Cat_append() throws Exception
    {
        File file = new File(testDir,"test.txt");
        File file2 = new File(testDir,"test2.txt");
        Files.writeString(file.toPath(),"Content 1\n");
        Files.writeString(file2.toPath(),"Content 2");
        InsertAppend_Command iAcomm = new InsertAppend_Command(testDirectory);
        catCommand.appendOperand(file.getName());
        catCommand.execute();
        iAcomm.appendOperand(file2.getName());
        iAcomm.execute(catCommand.Output());
        catCommand.execute(file2.getName());
        assertEquals("Content 2Content 1\n", catCommand.Output());
    }
}