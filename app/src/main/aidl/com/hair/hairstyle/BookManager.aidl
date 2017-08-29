// BookManager.aidl
package com.hair.hairstyle;

// Declare any non-default types here with import statements
import com.hair.hairstyle.Book;

interface BookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<Book> getBooks();

    void addBook(in Book book);
}
