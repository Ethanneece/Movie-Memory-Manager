import student.TestCase;

/**
 * Class used to test MenManager.
 *
 * @author Ethan Neece
 * @version 12-11-2021
 */
public class MenManagerTest extends TestCase
{
    private MemManager manager;

    /**
     * sets up testing for memManager.
     */
    public void setUp()
    {
        manager = new MemManager(64);
    }

    /**
     * test the insert() method in MemManager.
     */
    public void testInsert()
    {
        String insert = "aaaa";
        Handle correct = new Handle(0, 4);
        Handle handle = manager.insert(insert);

        assertEquals(correct, handle);

        correct = new Handle(4, 4);
        handle = manager.insert(insert);

        assertEquals(handle, correct);

        String bigInsert = "                                                  ";
        manager.insert(bigInsert);
        manager.insert(bigInsert);
        assertNotSame(systemOut().getHistory().length(), 0);
    }

    /**
     * test the remove() method.
     */
    public void testRemove()
    {
        String insert = "aaaa";
        Handle handle = manager.insert(insert);
        manager.remove(handle);
        manager.dump();

        assertEquals("64: 0\n", systemOut().getHistory());
    }

    /**
     * test the get() method in MemManager.
     */
    public void testGet()
    {
        String insert = "aaaa";
        Handle handle = manager.insert(insert);
        byte[] rtn = new byte[insert.length()];
        int num = manager.get(rtn, handle);
        assertEquals(4, num);
        assertEquals(new String(rtn), insert);
    }

    /**
     * Test the dump() method in the MemManager.
     */
    public void testDump()
    {
        manager.dump();
        assertEquals("64: 0\n", systemOut().getHistory());

        manager.insert("                 ");
        systemOut().clearHistory();
        manager.dump();
        assertEquals("32: 32\n", systemOut().getHistory());
    }
}
