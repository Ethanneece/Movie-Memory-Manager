/**
 * {Project Description Here}
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The class containing the main method.
 *
 * @author ethann
 * @version 12-11-2021
 */
public class MemMan
{

    /**
     * @param args Command line parameters
     */
    public static void main(String[] args) throws FileNotFoundException
    {

        if (args.length != 3)
        {
            System.out.println("Invoke with commands MenMan: " +
                    "<initial-memory-size> <initial-hash-size> " +
                    "<commands-file>");

            return;
        }

        int memSize = Integer.parseInt(args[0]);
        int hashSize = Integer.parseInt(args[1]);
        File inputFile = new File(args[2]);

        MovieManager movieManager = new MovieManager(memSize, hashSize);

        Scanner input = new Scanner(inputFile);

        while (input.hasNextLine())
        {
            String readLine = input.nextLine().trim();
            String[] commands = readLine.split("\\s+");

            if (commands[0].equals("add"))
            {
                movieManager.add(commands);
            }
            else if (commands[0].equals("delete"))
            {
                movieManager.delete(commands);
            }
            else if (commands[0].equals("update") && commands[1].equals("add"))
            {
                movieManager.updateAdd(commands);
            }
            else if (commands[0].equals("update"))
            {
                movieManager.updateDelete(commands);
            }
            else if (commands[0].equals("print") &&
                    commands[1].equals("hashtable"))
            {
                movieManager.printHashTable();
            }
            else if (commands[0].equals("print"))
            {
                movieManager.printBlocks();
            }
        }
    }
}
