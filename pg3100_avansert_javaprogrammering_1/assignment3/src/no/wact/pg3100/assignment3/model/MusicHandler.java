package no.wact.pg3100.assignment3.model;

import no.wact.pg3100.assignment3.config.Config;
import no.wact.pg3100.assignment3.db.MySQLConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MusicHandler {

    private final String SELECT_ALBUMS_STATEMENT =
            "SELECT `artist`, `title`, `tracks`, `released`, `genre` FROM `" + Config.DB_TABLE + "` WHERE `genre` LIKE ? ORDER BY `artist` ASC, `title` ASC;";

    public List<Album> getAlbums(String genre) throws Exception {
        List<Album> albumList = new ArrayList<>();
        if (genre == null) {
            return albumList;
        }
        try (
                MySQLConnector connector = new MySQLConnector(Config.DB_USERNAME, Config.DB_PASSWORD);
                PreparedStatement statement = connector.getConnection().prepareStatement(SELECT_ALBUMS_STATEMENT)
        ) {
            statement.setString(1, genre);
            getListFromDatabase(statement, albumList);
        }
        return albumList;
    }

    private void getListFromDatabase(PreparedStatement statement, List<Album> albumList) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Album album = new Album(
                        resultSet.getString("title"),
                        resultSet.getString("artist"),
                        resultSet.getString("genre"),
                        resultSet.getInt("tracks"),
                        resultSet.getInt("released")
                );
                albumList.add(album);
            }
        }
    }
}
