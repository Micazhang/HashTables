package hashTable;


//QuadraticProbing Hash table class
//
//CONSTRUCTION: an approximate initial size or default of 101
//
//******************PUBLIC OPERATIONS*********************
//bool insert( x )       --> Insert x
//bool remove( x )       --> Remove x
//bool contains( x )     --> Return true if x is present
//void makeEmpty( )      --> Remove all items


/**
* Probing table implementation of hash tables.
* Note that all "matching" is based on the equals method.
* @author Mark Allen Weiss
*/
public class QuadraticProbingHashTableQ3<AnyType>
{
 /**
  * Construct the hash table.
  */
 public QuadraticProbingHashTableQ3( )
 {
     this( DEFAULT_TABLE_SIZE );
 }

 /**
  * Construct the hash table.
  * @param size the approximate initial size.
  */
 public QuadraticProbingHashTableQ3( int size )
 {
     allocateArray( size );
     doClear( );
 }

 /**
  * Insert into the hash table. If the item is
  * already present, do nothing.
  * @param x the item to insert.
  */
 public boolean insert( AnyType x )
 {
         // Insert x as active
     int currentPos = findPos( x );
     if( isActive( currentPos ) )
         return false;

     array[ currentPos ] = new HashEntry<>( x, true );
     theSize++;
     
         // Rehash; see Section 5.5
     if( ++occupied > array.length / 2 )
         rehash( );
     
     return true;
 }

 /**
  * Expand the hash table.
  */
 private void rehash( )
 {
     HashEntry<AnyType> [ ] oldArray = array;

         // Create a new double-sized, empty table
     allocateArray( 2 * oldArray.length );
     occupied = 0;
     theSize = 0;

         // Copy table over
     for( HashEntry<AnyType> entry : oldArray )
         if( entry != null && entry.isActive )
             insert( entry.element );
 }

 /**
  * Method that performs quadratic probing resolution.
  * @param x the item to search for.
  * @return the position where the search terminates.
  */
 private int findPos( AnyType x )
 {
     int offset = 1;
     int currentPos = myhash( x );
     
     while( array[ currentPos ] != null &&
             !array[ currentPos ].element.equals( x ) )
     {
         currentPos += offset;  // Compute ith probe
         offset += 2;
         if( currentPos >= array.length )
             currentPos -= array.length;
     }
     
     return currentPos;
 }

 /**
  * Remove from the hash table.
  * @param x the item to remove.
  * @return true if item removed
  */
 public boolean remove( AnyType x )
 {
     int currentPos = findPos( x );
     if( isActive( currentPos ) )
     {
         array[ currentPos ].isActive = false;
         theSize--;
         return true;
     }
     else
         return false;
 }
 
 /**
  * Get current size.
  * @return the size.
  */
 public int size( )
 {
     return theSize;
 }
 
 /**
  * Get length of internal table.
  * @return the size.
  */
 public int capacity( )
 {
     return array.length;
 }

 /**
  * Find an item in the hash table.
  * @param x the item to search for.
  * @return the matching item.
  */
 public boolean contains( AnyType x )
 {
     int currentPos = findPos( x );
     return isActive( currentPos );
 }

 /**
  * Return true if currentPos exists and is active.
  * @param currentPos the result of a call to findPos.
  * @return true if currentPos is active.
  */
 private boolean isActive( int currentPos )
 {
     return array[ currentPos ] != null && array[ currentPos ].isActive;
 }

 /**
  * Make the hash table logically empty.
  */
 public void makeEmpty( )
 {
     doClear( );
 }

 private void doClear( )
 {
     occupied = 0;
     for( int i = 0; i < array.length; i++ )
         array[ i ] = null;
 }
 
 private int myhash( AnyType x )
 {
     int hashVal = x.hashCode( );

     hashVal %= array.length;
     if( hashVal < 0 )
         hashVal += array.length;

     return hashVal;
 }
 
 private static class HashEntry<AnyType>
 {
     public AnyType  element;   // the element
     public boolean isActive;  // false if marked deleted

     public HashEntry( AnyType e )
     {
         this( e, true );
     }

     public HashEntry( AnyType e, boolean i )
     {
         element  = e;
         isActive = i;
     }
 }

 private static final int DEFAULT_TABLE_SIZE = 101;

 private HashEntry<AnyType> [ ] array; // The array of elements
 private int occupied;                 // The number of occupied cells
 private int theSize;                  // Current size

 /**
  * Internal method to allocate array.
  * @param arraySize the size of the array.
  */
 private void allocateArray( int arraySize )
 {
     array = new HashEntry[ nextPrime( arraySize ) ];
 }

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
private static QuadraticProbingHashTableQ3<String> a = new QuadraticProbingHashTableQ3<String>();
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
//Simple main
public static void main( String [ ] args )
{


   // final int NUMS = 2000;

    
   System.out.println( "Fill in the table..." );

   QuadraticProbingHashTableQ3<String> H = new QuadraticProbingHashTableQ3<>( );

//   RandomStringGenerator rng = new RandomStringGenerator();
   
   for (int i = 1; i < 21; i++) {
	    
	 	int NUMS = (int) Math.pow(2, i);  
	 	insert_time(NUMS);
	 	remove_time(NUMS);
}
   
//   for( int i = 0; i < NUMS; i++)
//       H.remove( ""+i );
   
   System.out.println( "Finishing... ");
}

}

