package Testing;

import CLI.Insertion_Command;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class InsertTest {
    private String testDir = "testDir";
    private File testDirectory;
    private Insertion_Command insertCommand;

    @BeforeEach
    void setUp() {
        testDirectory = new File(testDir);
        testDirectory.mkdir();
        insertCommand = new Insertion_Command(testDirectory); // Use custom directory for testing
    }
    @AfterEach
    void cleanUp() {
        deleteDirectory(testDirectory);
        Insertion_Command.ResentInstances();
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
        insertCommand.appendOperand("test.txt");
        insertCommand.Input("testing content");
        insertCommand.execute();
        File file = new File(testDir,"test.txt");
        String s = Files.readString(file.toPath());
        assertTrue(file.exists(),"File is not created.\n");
        assertEquals(s,"testing content","Content mismatch!");
    }
    @Test
    void ModifiedExistingFile() throws Exception
    {
        File file = new File(testDir,"test.txt");
        Files.writeString(file.toPath(),"Test Number 2");
        insertCommand.appendOperand("test.txt");
        insertCommand.Input("New Content");
        insertCommand.execute();
        String s = Files.readString(file.toPath());
        assertEquals(s,"New Content","Content not modified!");
    }
}
