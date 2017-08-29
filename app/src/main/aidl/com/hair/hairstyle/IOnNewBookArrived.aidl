// BookManager.aidl
package com.hair.hairstyle;

// Declare any non-default types here with import statements
import com.hair.hairstyle.Book;

interface IOnNewBookArrived {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   void onNewBookArrived(in Book newBook);
}
