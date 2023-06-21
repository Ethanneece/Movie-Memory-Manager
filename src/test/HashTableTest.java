import student.TestCase;

/**
 * Testing class for HashTable.
 *
 * @author Ethan Neece
 * @version 12-11-2021
 */
public class HashTableTest extends TestCase
{

    private HashTable empty;
    private HashTable small;
    private HashTable big;

    private Record insert;

    /**
     * Sets up testing for hashTable.
     */
    public void setUp()
    {
        insert = new Record("insert", new Handle(0, 0));
        empty = new HashTable(10);

        small = new HashTable(10);
        for (int i = 0; i < 5; i++)
        {
            small.insert(i + "", new Record(i + "", new Handle(i, i)));
        }

        big = new HashTable(1000);
        for (int i = 0; i < 500; i++)
        {
            big.insert(i + "", new Record(i + "", new Handle(i, i)));
        }
    }

    /**
     * Test the insert() method in hashTable.
     */
    public void testInsert()
    {
        assertTrue(empty.insert("insert", insert));

        assertTrue(big.insert("", insert));

        assertTrue(small.insert("insert", insert));
    }

    /**
     * Test the remove() method in hashTable.
     */
    public void testRemove()
    {
        empty.insert("insert", insert);
        assertEquals(insert, empty.remove("insert"));

        assertNull(empty.remove(""));

        small.insert("insert", insert);
        assertEquals(insert, small.remove("insert"));
    }

    /**
     * Test the find() method in hashTable.
     */
    public void testFind()
    {
        assertNull(empty.find(""));

        small.insert("insert", insert);
        assertEquals(insert, small.find("insert"));
    }

    /**
     * test the dump() method in hashTable.
     */
    public void testDump()
    {
        empty.dump();
        assertEquals("Total records: 0\n", systemOut().getHistory());

        empty.insert("insert", insert);
        systemOut().clearHistory();
        empty.dump();
        assertEquals("|insert| 1\nTotal records: 1\n",
                systemOut().getHistory());
    }
}
