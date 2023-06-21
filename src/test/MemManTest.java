import student.TestCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Test the main function (you should throw this away for your project and write
 * your own tests)
 *
 * @author ethann
 * @version 12-11-2021
 */
public class MemManTest extends TestCase
{

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp()
    {
        // Nothing Here
    }


    /**
     * Get code coverage of the class declaration.
     */
    public void testRInit() throws FileNotFoundException
    {
        MemMan manager = new MemMan();
        assertNotNull(manager);
        MemMan.main(new String[]{"32", "10", "test01.txt"});

        String output = systemOut().getHistory();
        String[] outputSplit = output.split("\n");

        Scanner reader = new Scanner(new File("test01Output.txt"));

        for (int i = 0; i < outputSplit.length; i++)
        {
            assertEquals(reader.nextLine(), outputSplit[i]);
        }
    }
}
