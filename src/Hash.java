/**
 * Clas containing sfold hash function.
 *
 * @author ethann
 * @version 12-11-2021
 */

public class Hash
{

    /**
     * Create a new Hash object.
     */
    public Hash()
    {
        // Nothing here yet
    }


    /**
     * Compute the hash function. Uses the "sfold" method from the OpenDSA
     * module on hash functions
     *
     * @param s The string that we are hashing
     * @param m The size of the hash table
     * @return The home slot for that string
     */
    public int h(String s, int m)
    {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++)
        {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++)
            {
                sum += c[k] * mult;
                mult *= 256;
            }
        }

        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++)
        {
            sum += c[k] * mult;
            mult *= 256;
        }

        return (int) (Math.abs(sum) % m);
    }
}
