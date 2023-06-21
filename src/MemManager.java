import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for the memPool functionality.
 *
 * @author Ethan Neece
 * @version 12-11-2021
 */
public class MemManager
{

    private ArrayList<ArrayList<Integer>> freeList;

    private byte[] memPool;

    /**
     * Creates an empty memManager object.
     *
     * @param size is the size of the memPool.
     */
    public MemManager(int size)
    {
        this.memPool = new byte[size];

        int freeListSize = (int) log(2, size);
        this.freeList = new ArrayList<>();

        for (int i = 0; i < freeListSize + 1; i++)
        {
            freeList.add(new ArrayList<>());
        }

        freeList.get(freeListSize).add(0);
    }

    /**
     * inserts input into memPool.
     *
     * @param input is the thing being put into memPool.
     * @return Handle where it was inserted.
     */
    public Handle insert(String input)
    {
        byte[] space = input.getBytes();
        int size = space.length;


        int openBlock = findBlock(size);
        while (openBlock == -1)
        {
            expandMemory();
            openBlock = findBlock(size);
        }

        int blockSize = getBlockSize(size);

        int startIndex = freeList.get(openBlock).get(0);

        while (blockSize < Math.pow(2, openBlock))
        {
            freeList.get((int) log(2, blockSize)).add(startIndex + blockSize);

            blockSize = blockSize * 2;
        }

        for (int i = 0; i < size; i++)
        {
            memPool[freeList.get(openBlock).get(0) + i] = space[i];
        }
        Handle rtn = new Handle(freeList.get(openBlock).get(0), size);
        freeList.get(openBlock).remove(0);

        return rtn;
    }

    private void expandMemory()
    {
        byte[] newMemPool = new byte[memPool.length * 2];
//        boolean check = false;

        for (int i = 0; i < memPool.length; i++)
        {
            newMemPool[i] = memPool[i];
        }
//        for (int j = 0; j < freeList.size(); j++)
//        {
//            if (freeList.get(j).size() > 0)
//            {
//                check = true;
//                break;
//            }
//        }
//        if (!check)
//        {
//            System.out.println("No free blocks are available.");
//        }
        freeList.add(new ArrayList<>());
        freeList.get(freeList.size() - 2).add(memPool.length);
        reMerge(freeList.size() - 2);

        memPool = newMemPool;
        System.out.println(
                "Memory pool expanded to be " + memPool.length + " bytes.");
    }

    private int findBlock(int size)
    {
        int blockSize = getBlockSize(size);
        int spot = (int) log(2, blockSize);
        for (int i = spot; i < freeList.size(); i++)
        {
            if (freeList.get(i).size() > 0)
            {
                return i;
            }
        }

        return -1;
    }

    private int getBlockSize(int size)
    {
        int i = 1;
        while (i < Integer.MAX_VALUE)
        {
            if (i >= size)
            {
                break;
            }

            i *= 2;
        }

        return i;
    }

    /**
     * Removes handle from memPool.
     *
     * @param theHandle is the thing being removed.
     */
    public void remove(Handle theHandle)
    {

        int index = (int) Math.ceil(log(2, theHandle.getLength()));

        if (index < 0)
        {
            index = 0;
        }

        freeList.get(index).add(theHandle.getIndex());
        Collections.sort(freeList.get(index));

        reMerge(index);
    }

    /**
     * @param location is the place being remerged.
     */
    private void reMerge(int location)
    {
        boolean merged = true;
        while (merged && location != freeList.size() - 1)
        {
            merged = false;
            Collections.sort(freeList.get(location));
            for (int i = freeList.get(location).size() - 1; i > 0; i--)
            {
                if (freeList.get(location).get(i) - (int) Math.pow(2,
                        location) ==
                        freeList.get(location).get(i - 1))
                {
                    freeList.get(location + 1)
                            .add(freeList.get(location).get(i - 1));
                    freeList.get(location).remove(i);
                    freeList.get(location).remove(i - 1);
                    i--;
                    merged = true;
                }
            }
            location++;
        }
    }

    /**
     * gets bytes that contain word for the record.
     *
     * @param space     bytes for the word.
     * @param theHandle handle for the bytes.
     * @return number of bytes read.
     */
    public int get(byte[] space, Handle theHandle)
    {
        int bytes = 0;
        for (int i = 0; i < theHandle.getLength(); i++)
        {
            space[i] = memPool[theHandle.getIndex() + i];
            bytes++;
        }

        return bytes;
    }

    /**
     * Dumps a representation of the freeList.
     */
    public void dump()
    {
        for (int i = 0; i < freeList.size(); i++)
        {
            boolean check = false;
            if (freeList.get(i).size() > 0)
            {
                System.out.print((int) Math.pow(2, i) + ": ");
            }
            for (int k = 0; k < freeList.get(i).size(); k++)
            {
                check = true;
                if (k == freeList.get(i).size() - 1)
                {
                    System.out.print(freeList.get(i).get(k));
                }
                else
                {
                    System.out.print(freeList.get(i).get(k) + " ");
                }
            }
            if (check)
            {
                System.out.println();
            }
        }

    }

    private double log(int base, int x)
    {
        return Math.log(x) / Math.log(base);
    }
}