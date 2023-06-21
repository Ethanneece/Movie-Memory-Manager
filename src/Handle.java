/**
 * Class used to hold data from memPool insertion.
 *
 * @author Ethan Neece
 * @version 12-11-2021
 */
public class Handle
{
    private int index;
    private int length;

    /**
     * Creates a class of Handle.
     *
     * @param index  is where the handle starts.
     * @param length is how long the handle is.
     */
    public Handle(int index, int length)
    {
        this.index = index;
        this.length = length;
    }

    /**
     * @return the index of the handle.
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * @return the length of the handle.
     */
    public int getLength()
    {
        return length;
    }

    /**
     * test equality between two objects.
     *
     * @param o object being tested to.
     * @return true if they are equal, false otherwise
     */
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (o == null)
        {
            return false;
        }

        if (o.getClass() == this.getClass())
        {
            Handle handle = (Handle) o;
            return handle.index == this.index && handle.length == this.length;
        }

        return false;
    }
}
