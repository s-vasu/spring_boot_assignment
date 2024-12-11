package com.zemoso.author_monolithic.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantTest {

    @Test
    void testApiEndpoints() {
        // Ensure API endpoints constants are accessed and used
        assertEquals("/api/v1/authors", Constant.API_AUTHORS_URL);
        assertEquals("/api/v1/books", Constant.API_BOOKS_URL);

        // Directly accessing the constants, this is required for coverage tools to count them
        String authorsUrl = Constant.API_AUTHORS_URL;
        String booksUrl = Constant.API_BOOKS_URL;

        assertNotNull(authorsUrl);
        assertNotNull(booksUrl);

        // Additional assertion for empty string check
        assertNotEquals("", authorsUrl);
        assertNotEquals("", booksUrl);
    }

    @Test
    void testErrorMessages() {
        // Error message constants
        assertEquals("Author not found in the database with ID: ", Constant.AUTHOR_NOT_FOUND);
        assertEquals("Book not found in the database with ID: ", Constant.BOOK_NOT_FOUND);
        assertEquals("Error in accessing Authors from the database", Constant.ERROR_IN_AUTHOR_RETRIEVAL);
        assertEquals("Error in accessing Books from the database", Constant.ERROR_IN_BOOK_RETRIEVAL);
        assertEquals("An error occurred while saving to the database", Constant.ERROR_IN_SAVE);
        assertEquals("An error occurred while updating the record", Constant.ERROR_IN_UPDATE);
        assertEquals("An error occurred while deleting the record", Constant.ERROR_IN_DELETION);
        assertEquals("You have provided invalid data. Please check and try again.", Constant.VALIDATION_ERROR);

        // Directly accessing the constants for coverage
        String errorAuthorNotFound = Constant.AUTHOR_NOT_FOUND;
        String errorBookNotFound = Constant.BOOK_NOT_FOUND;

        assertNotNull(errorAuthorNotFound);
        assertNotNull(errorBookNotFound);

        // Additional assertion for empty string check
        assertNotEquals("", errorAuthorNotFound);
        assertNotEquals("", errorBookNotFound);
    }

    @Test
    void testSuccessMessages() {
        // Success message constants
        assertEquals("Author deleted successfully with ID: ", Constant.AUTHOR_DELETED_SUCCESS);
        assertEquals("Book deleted successfully with ID: ", Constant.BOOK_DELETED_SUCCESS);
        assertEquals("Author updated successfully with ID: ", Constant.AUTHOR_UPDATED_SUCCESS);
        assertEquals("Book updated successfully with ID: ", Constant.BOOK_UPDATED_SUCCESS);
        assertEquals("Book removed successfully from the author", Constant.BOOK_REMOVED_FROM_AUTHOR);

        // Directly accessing the constants for coverage
        String successAuthorDeleted = Constant.AUTHOR_DELETED_SUCCESS;
        String successBookDeleted = Constant.BOOK_DELETED_SUCCESS;

        assertNotNull(successAuthorDeleted);
        assertNotNull(successBookDeleted);

        // Additional assertion for empty string check
        assertNotEquals("", successAuthorDeleted);
        assertNotEquals("", successBookDeleted);
    }

    @Test
    void testMiscellaneousMessages() {
        // Miscellaneous constants
        assertEquals("No such mapped page/unknown error", Constant.NO_SUCH_PAGE);
        assertEquals("No such mapped page for author endpoints", Constant.NO_SUCH_AUTHOR_PAGE);
        assertEquals("No such mapped page for book endpoints", Constant.NO_SUCH_BOOK_PAGE);

        // Directly accessing the constants for coverage
        String noSuchPage = Constant.NO_SUCH_PAGE;
        String noSuchAuthorPage = Constant.NO_SUCH_AUTHOR_PAGE;

        assertNotNull(noSuchPage);
        assertNotNull(noSuchAuthorPage);

        // Additional assertion for empty string check
        assertNotEquals("", noSuchPage);
        assertNotEquals("", noSuchAuthorPage);
    }
}