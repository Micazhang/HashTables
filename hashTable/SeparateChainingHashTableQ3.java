package hashTable;

import java.util.LinkedList;
import java.util.List;

// SeparateChaining Hash table class
//
// CONSTRUCTION: an approximate initial size or default of 101
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// void makeEmpty( )      --> Remove all items

/**
 * Separate chaining table implementation of hash tables.
 * Note that all "matching" is based on the equals method.
 * @author Mark Allen Weiss
 */
public class SeparateChainingHashTableQ3<AnyType>
{
    /**
     * Construct the hash table.
     */
    public SeparateChainingHashTableQ3( )
    {
        this( DEFAULT_TABLE_SIZE );
    }

    /**
     * Construct the hash table.
     * @param size approximate table size.
     */
    public SeparateChainingHashTableQ3( int size )
    {
        theLists = new LinkedList[ nextPrime( size ) ];
        for( int i = 0; i < theLists.length; i++ )
            theLists[ i ] = new LinkedList<>( );
    }

    /**
     * Insert into the hash table. If the item is
     * already present, then do nothing.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        List<AnyType> whichList = theLists[ myhash( x ) ];
        if( !whichList.contains( x ) )
        {
            whichList.add( x );

                // Rehash; see Section 5.5
            if( ++currentSize > theLists.length )
                rehash( );
        }
    }

    /**
     * Remove from the hash table.
     * @param x the item to remove.
     */
    public void remove( AnyType x )
    {
        List<AnyType> whichList = theLists[ myhash( x ) ];
        if( whichList.contains( x ) )
    {
        whichList.remove( x );
            currentSize--;
    }
    }

    /**
     * Find an item in the hash table.
     * @param x the item to search for.
     * @return true if x isnot found.
     */
    public boolean contains( AnyType x )
    {
        List<AnyType> whichList = theLists[ myhash( x ) ];
        return whichList.contains( x );
    }

    /**
     * Make the hash table logically empty.
     */
    public void makeEmpty( )
    {
        for( int i = 0; i < theLists.length; i++ )
            theLists[ i ].clear( );
        currentSize = 0;    
    }

    /**
     * A hash routine for String objects.
     * @param key the String to hash.
     * @param tableSize the size of the hash table.
     * @return the hash value.
     */
    public static int hash( String key, int tableSize )
    {
        int hashVal = 0;

        for( int i = 0; i < key.length( ); i++ )
            hashVal = 37 * hashVal + key.charAt( i );

        hashVal %= tableSize;
        if( hashVal < 0 )
            hashVal += tableSize;

        return hashVal;
    }

    private void rehash( )
    {
        List<AnyType> [ ]  oldLists = theLists;

            // Create new double-sized, empty table
        theLists = new List[ nextPrime( 2 * theLists.length ) ];
        for( int j = 0; j < theLists.length; j++ )
            theLists[ j ] = new LinkedList<>( );

            // Copy table over
        currentSize = 0;
        for( List<AnyType> list : oldLists )
            for( AnyType item : list )
                insert( item );
    }

    private int myhash( AnyType x )
    {
        int hashVal = x.hashCode( );

        hashVal %= theLists.length;
        if( hashVal < 0 )
            hashVal += theLists.length;

        return hashVal;
    }
    
    private static final int DEFAULT_TABLE_SIZE = 101;

        /** The array of Lists. */
    private List<AnyType> [ ] theLists; 
    private int currentSize;

    /**
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
    private static int nextPrime( int n )
    {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime( int n )
    {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }

  //Q1:creates n random strings and inserts them in a hash table, and compute the average time for each insertion
    private static SeparateChainingHashTableQ3<String> a = new SeparateChainingHashTableQ3<String>();
    private static String[] inserted_string;
    public static long insert_time(int NUMS) //NUMS is the total number of inserted string
    {
    	 long start,end;
        RandomStringGenerator rng = new RandomStringGenerator();
        inserted_string = new String[NUMS];
        start = System.nanoTime();     
        for( int i = 0; i < NUMS; i++) {
       	 	 inserted_string[i] = rng.RandomString(10);
            a.insert( inserted_string[i] );
          
        }  
        end = System.nanoTime();
        System.out.println( "This is "+NUMS+"th time, Insert Time used: " + ( (end - start )/NUMS) );
        return ((end-start)/NUMS);
    }
    
   //Q2:finds n random strings in the hash table. The method should delete the string if found. It should also compute the average time of each search.
    public static long remove_time(int NUMS)
    {
    	 long start,end;
        RandomStringGenerator rng = new RandomStringGenerator();
        
        start = System.nanoTime();     
        for( int i = 0; i < NUMS; i++) {
         if (a.contains( inserted_string[i] ))
        	 	a.remove(inserted_string[i]);
        } 
        end = System.nanoTime();
        System.out.println( "This is "+NUMS+"th time, Remove Time used: " + ( (end - start )/NUMS) );
        return ((end-start)/NUMS);
    }
        // Simple main
    public static void main( String [ ] args )
    {
        SeparateChainingHashTableQ3<Integer> H = new SeparateChainingHashTableQ3<>( );

        long startTime = System.currentTimeMillis( );
        
    //    final int NUMS = 2000;

 //       System.out.println( "Checking... (no more output means success)" );
        for (int i = 1; i < 21; i++) {
    	    
    	 		int NUMS = (int) Math.pow(2, i);  
    	 		insert_time(NUMS);
    	 		remove_time(NUMS);
	}
        System.out.println( "Finished. ");
        
        
     //   long endTime = System.currentTimeMillis( );       
     //   System.out.println( "Elapsed time: " + (endTime - startTime) );
    }

}
