import student.TestCase;

// -------------------------------------------------------------------------

/**
 *
 * @author ethann
 * @version 12-11-2021
 */
public class HashTest extends TestCase
{

    /**
     * Sets up the tests that follow.
     */
    public void setUp()
    {
        // Nothing Here
    }


    /**
     * Test the hash function
     */
    public void testh()
    {

        Hash myHash = new Hash();
        assertEquals(myHash.h("aaaabbbb", 101), 75);
        assertEquals(myHash.h("aaaabbb", 101), 1640219587 % 101);
    }
}
