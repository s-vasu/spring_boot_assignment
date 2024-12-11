package com.zemoso.author_monolithic.constants;

public class Constant {

    // API Endpoints
    public static final String API_AUTHORS_URL = "/api/v1/authors";
    public static final String API_BOOKS_URL = "/api/v1/books";

    // Error Messages
    public static final String AUTHOR_NOT_FOUND = "Author not found in the database with ID: ";
    public static final String BOOK_NOT_FOUND = "Book not found in the database with ID: ";
    public static final String ERROR_IN_AUTHOR_RETRIEVAL = "Error in accessing Authors from the database";
    public static final String ERROR_IN_BOOK_RETRIEVAL = "Error in accessing Books from the database";
    public static final String ERROR_IN_SAVE = "An error occurred while saving to the database";
    public static final String ERROR_IN_UPDATE = "An error occurred while updating the record";
    public static final String ERROR_IN_DELETION = "An error occurred while deleting the record";
    public static final String VALIDATION_ERROR = "You have provided invalid data. Please check and try again.";

    // Success Messages
    public static final String AUTHOR_DELETED_SUCCESS = "Author deleted successfully with ID: ";
    public static final String BOOK_DELETED_SUCCESS = "Book deleted successfully with ID: ";
    public static final String AUTHOR_UPDATED_SUCCESS = "Author updated successfully with ID: ";
    public static final String BOOK_UPDATED_SUCCESS = "Book updated successfully with ID: ";
    public static final String BOOK_REMOVED_FROM_AUTHOR = "Book removed successfully from the author";

    // Miscellaneous
    public static final String NO_SUCH_PAGE = "No such mapped page/unknown error";
    public static final String NO_SUCH_AUTHOR_PAGE = "No such mapped page for author endpoints";
    public static final String NO_SUCH_BOOK_PAGE = "No such mapped page for book endpoints";

}
