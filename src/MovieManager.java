/**
 * Clased used for managing request made from
 * input file.
 *
 * @author Ethan Neece
 * @version 12-11-2021
 */
public class MovieManager
{
    private MemManager memPool;
    private HashTable hashTable;

    /**
     * Creates an empty Movie Manager class.
     *
     * @param memSize  is the size of the memManager.
     * @param hashSize is the size of the hashTable.
     */
    public MovieManager(int memSize, int hashSize)
    {
        memPool = new MemManager(memSize);
        hashTable = new HashTable(hashSize);
    }

    /**
     * adds command to memPool and hashTable if it does not already
     * exist in HashTable.
     *
     * @param command is the full line
     *                that this command runs.
     */
    public void add(String[] command)
    {
        String name = reCombineName(command, 1);
        if (hashTable.find(name) != null)
        {
            System.out.println("|" + name + "| duplicates a record already " +
                    "in the Name database.");
            return;
        }
        Handle handle = memPool.insert(name);
        hashTable.insert(name, new Record(name, handle));
        System.out.println(
                "|" + name + "| has been added to the Name " + "database" +
                        ".");
    }

    /**
     * deletes command in memPool and hashTable if it exist.
     *
     * @param command is the command being deleted.
     */
    public void delete(String[] command)
    {
        String name = reCombineName(command, 1);
        Record removed = hashTable.remove(name);

        if (removed != null)
        {
            memPool.remove(removed.getHandle());
            System.out.println("|" + name + "| has been deleted from the Name" +
                    " database.");
        }
        else
        {
            System.out.println("|" + name + "| not deleted because it does " +
                    "not exist in the Name database.");
        }
    }

    /**
     * Updates and adds a new field and value onto a record currently in the
     * table and memPool.
     *
     * @param command is the field nad value to be added.
     */
    public void updateAdd(String[] command)
    {
        String word = reCombineName(command, 2);
        String[] split = word.trim().split("<SEP>");
        String name = split[0].trim();
        String field = split[1].trim();
        String value = split[2].trim();
        Record record = hashTable.find(name);
        if (record == null)
        {
            System.out.println("|" + name + "| not updated because it does " +
                    "not exist in the Name database.");
            return;
        }

        byte[] currentWord = new byte[record.getHandle().getLength()];
        memPool.get(currentWord, record.getHandle());
        String memPoolWord = new String(currentWord);

        String[] split2 = memPoolWord.split("<SEP>");

        String rebuild = name + "<SEP>";
        for (int i = 1; i < split2.length - 1; i += 2)
        {
            if (!split2[i].contains(field))
            {
                rebuild += split2[i] + "<SEP>" + split2[i + 1] + "<SEP>";
            }
        }

        rebuild += field + "<SEP>" + value;

        memPool.remove(record.getHandle());
        Handle newHandle = memPool.insert(rebuild);
        record.updateHandle(newHandle);
        System.out.println("Updated Record: |" + rebuild + "|");
    }

    /**
     * Deletes a field from mempool if the record exist and the record contains
     * the field.
     *
     * @param command is the command being deleted.
     */
    public void updateDelete(String[] command)
    {
        String word = reCombineName(command, 2);
        String[] split = word.trim().split("<SEP>");
        String name = split[0].trim();
        String field = split[1].trim();

        Record record = hashTable.find(name);

        if (record == null)
        {
            System.out.println("|" + name + "| not updated because it does " +
                    "not exist in the Name database.");
            return;
        }

        byte[] currentWord = new byte[record.getHandle().getLength()];
        memPool.get(currentWord, record.getHandle());
        String memPoolWord = new String(currentWord);

        if (!memPoolWord.contains("<SEP>" + field + "<SEP>"))
        {
            System.out.println(
                    "|" + name + "| not updated because the field " + "|" +
                            field + "| does not exist");
            return;
        }

        String[] split2 = memPoolWord.split("<SEP>");

        String rebuild = name;

        for (int i = 1; i < split2.length - 1; i += 2)
        {
            if (!split2[i].contains(field))
            {
                rebuild += "<SEP>" + split2[i] + "<SEP>" + split2[i + 1];
            }
        }

        memPool.remove(record.getHandle());
        Handle newHandle = memPool.insert(rebuild);
        record.updateHandle(newHandle);
        System.out.println("Updated Record: |" + rebuild + "|");
    }

    /**
     * Prints out the HashTable.
     */
    public void printHashTable()
    {
        hashTable.dump();
    }

    /**
     * Prints out the memPool.
     */
    public void printBlocks()
    {
        memPool.dump();
    }

    private String reCombineName(String[] name, int start)
    {
        String rtn = "";
        for (int i = start; i < name.length - 1; i++)
        {
            rtn += name[i] + " ";
        }

        rtn += name[name.length - 1];

        return rtn;
    }
}
