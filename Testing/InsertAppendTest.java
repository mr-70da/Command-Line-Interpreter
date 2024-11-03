package Testing;

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
        InsertAppend_Command.ResentInstances();
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
        insertAppendCommand.Input("testing content");
        insertAppendCommand.execute();
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
        insertAppendCommand.Input("New Content");
        insertAppendCommand.execute();
        String s = Files.readString(file.toPath());
        assertEquals(s,"Test Number 2New Content","Content not Concatenated Properly!");
    }
}
