package Testing;

import CLI.Concat_Command;
import CLI.InsertAppend_Command;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class InsertAppendTest {
    private String testDir = "testDir";
    private File testDirectory;
    private InsertAppend_Command insertAppendCommand;

    @BeforeEach
    void setUp() {
        testDirectory = new File(testDir);
        testDirectory.mkdir();
        insertAppendCommand = new InsertAppend_Command(testDirectory); // Use custom directory for testing
    }
    @AfterEach
    void cleanUp() {
        deleteDirectory(testDirectory);
        InsertAppend_Command.ResetInstances();
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
    void SuccessfulInsertion() throws Exception
    {
        insertAppendCommand.appendOperand("test.txt");
        insertAppendCommand.execute("testing content");
        File file = new File(testDir,"test.txt");
        String s = Files.readString(file.toPath());
        assertTrue(file.exists(),"File is not created.\n");
        assertEquals(s,"testing content","Content mismatch!");
    }
    @Test
    void ConcatenatedToExistingFile() throws Exception
    {
        File file = new File(testDir,"test.txt");
        Files.writeString(file.toPath(),"Test Number 2");
        insertAppendCommand.appendOperand("test.txt");
        insertAppendCommand.execute("New Content");
        String s = Files.readString(file.toPath());
        assertEquals(s,"Test Number 2New Content","Content not Concatenated Properly!");
    }
    @Test
    void Cat_append() throws Exception
    {
        File file = new File(testDir,"test.txt");
        File file2 = new File(testDir,"test2.txt");
        Files.writeString(file.toPath(),"Content 1\n");
        Files.writeString(file2.toPath(),"Content 2");
        Concat_Command catCommand = new Concat_Command(testDirectory);
        catCommand.appendOperand(file2.getName());
        catCommand.execute();
        insertAppendCommand.appendOperand(file.getName());
        insertAppendCommand.execute(catCommand.Output());
        catCommand.execute(file.getName());
        assertEquals("Content 1\nContent 2", catCommand.Output());
    }
}
