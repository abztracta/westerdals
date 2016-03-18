package no.woact.pg4100.assignment2.ronesp13.server.model.handler;

import no.woact.pg4100.assignment2.ronesp13.server.db.MySQLConnector;
import no.woact.pg4100.assignment2.ronesp13.server.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Logger;

/**
* @author Espen Rønning - ronesp13@student.westerdals.no
*/
public class BookHandler {

    private static final Logger log = Logger.getLogger(BookHandler.class.getName());
    private static final String GET_BOOKS_SQL = "SELECT `author`, `title`, `isbn`, `pages`, `release_year` FROM `booklist`;";
    private static final Random RANDOM = new Random();

    public Book getBook() {
        Book book = null;
        Connection connection = MySQLConnector.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(GET_BOOKS_SQL);
             ResultSet result = statement.executeQuery()) {
            result.last();
            int size = result.getRow();
            result.beforeFirst();
            result.relative(RANDOM.nextInt(size) + 1);
            book = buildBook(result);
        } catch (SQLException e) {
            log.severe("Something went wrong with the SQL query. " + e.getMessage());
        }
        return book;
    }

    private Book buildBook(ResultSet result) throws SQLException {
        String author = result.getString("author");
        String title = result.getString("title");
        String ISBN = result.getString("isbn");
        Integer pages = result.getInt("pages");
        Integer releaseYear = result.getInt("release_year");
        return new Book(author, title, ISBN, pages, releaseYear);
    }
}
