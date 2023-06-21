/**
 * Class that does a hashtable for string, record.
 *
 * @author Ethan Neece
 * @version 12-11-2021
 */
public class HashTable
{
    private Record[] table;
    private int size;
    private Hash hash;
    private Record tombstone;

    /**
     * Creates an empty object of HashTable.
     *
     * @param tableSize is the initial size of the table.
     */
    public HashTable(int tableSize)
    {
        table = new Record[tableSize];
        hash = new Hash();
        size = 0;
        this.tombstone = new Record("TOMBSTONE_RECORD_DOES_NOT_EXIST",
                new Handle(-1, -1));
    }

    /**
     * Inserts value into hashtable.
     *
     * @param key   is the thing that is used to find the record and determine
     *              placement.
     * @param value is the thing being inserted into
     * @return true if inserted, false otherwise.
     */
    public boolean insert(String key, Record value)
    {
        if (size + 1 > table.length / 2)
        {
            rebuildHashTable();
            System.out.println(
                    "Name hash table size doubled to " + table.length +
                            " slots.");
        }

        int hashCode = hash.h(key, table.length);
        int index = hashCode;
        int powerCounter = 1;

        while (table[index] != null &&
                table[index] != tombstone &&
                !table[index].getName().equals(value.getName()))
        {
            index = (hashCode +
                    (int) Math.pow(powerCounter++, 2) ) % table.length;
        }

        if (table[index] == null || table[index] == tombstone)
        {
            table[index] = value;
            size++;
            return true;
        }

        return false;
    }

    private void rebuildHashTable()
    {
        Record[] newRecords = new Record[table.length * 2];

        for (int i = 0; i < table.length; i++)
        {
            if (table[i] != null && table[i] != tombstone)
            {
                int hashCode =
                        hash.h(table[i].getName(), newRecords.length);
                int index = hashCode;

                int powerCounter = 1;
                while (newRecords[index] != null)
                {
                    index = (hashCode + (int) Math.pow(powerCounter++, 2)) %
                            newRecords.length;
                }

                newRecords[index] = table[i];
            }
        }

        table = newRecords;
    }

    /**
     * Removes a record from the HashTable.
     *
     * @param name is the thing being used as a key.
     * @return true if removed, false otherwise.
     */
    public Record remove(String name)
    {
        int hashCode = hash.h(name, table.length);
        int index = hashCode;

        int powerCounter = 1;
        while (table[index] != null &&
                table[index] != tombstone &&
                !table[index].getName().equals(name))
        {
            index = (hashCode +
                    (int) Math.pow(powerCounter++, 2)) % table.length;
        }

        if (table[hashCode] == null)
        {
            return null;
        }

        Record rtn = table[index];
        table[index] = tombstone;
        size--;
        return rtn;
    }

    /**
     * finds record if it exist in the hashtable.
     *
     * @param name is the key of the record.
     * @return The record if found, false otherwise.
     */
    public Record find(String name)
    {
        int hashCode = hash.h(name, table.length);
        int index = hashCode;

        int powerCounter = 1;
        while (table[index] != null || table[index] == tombstone)
        {
            if (table[index] != null &&
                    table[index].getName().equals(name))
            {
                break;
            }
            index = (hashCode +
                    (int) Math.pow(powerCounter++, 2)) % table.length;
        }

        return table[index];
    }

    /**
     * Produces a dump of the hashtable.
     */
    public void dump()
    {
        for (int i = 0; i < table.length; i++)
        {
            if (table[i] != null && table[i] != tombstone)
            {
                System.out.println("|" + table[i].getName() + "| " + i);
            }
        }
        System.out.println("Total records: " + size);
    }
}
