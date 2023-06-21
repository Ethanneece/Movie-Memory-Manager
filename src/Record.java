/**
 * Class used to hold records.
 *
 * @author Ethan Neece
 * @version 12-11-2021
 */
public class Record
{
    private String name;
    private Handle handle;

    /**
     * Class used to create Record object.
     *
     * @param name   is the name.
     * @param handle is the handle.
     */
    public Record(String name, Handle handle)
    {
        this.name = name;
        this.handle = handle;
    }

    /**
     * @return is the name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the handle.
     */
    public Handle getHandle()
    {
        return handle;
    }

    /**
     * @param handler is being updated.
     */
    public void updateHandle(Handle handler)
    {
        this.handle = handler;
    }

    /**
     * Test equality.
     *
     * @param o is the object being tested.
     * @return true if equal false, otherwise.
     */
    public boolean equals(Object o)
    {
        if (o == this)
        {
            return true;
        }

        if (o == null)
        {
            return false;
        }

        if (o.getClass() == this.getClass())
        {
            Record record = (Record) o;
            return record.name.equals(this.name);
        }

        return false;
    }
}
