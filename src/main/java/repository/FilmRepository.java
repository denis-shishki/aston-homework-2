package repository;

import entity.Film;
import entity.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilmRepository {
//    private final GenreRepository genreRepository;
//
//    public FilmRepository(GenreRepository genreRepository) {
//        this.genreRepository = genreRepository;
//    }

    public Film postFilm(Film film) {
        boolean presentGenres = false;

        if (film.getGenres() != null) {
            presentGenres = true;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/film", "postgres", "postgres")) {
            String sql = "INSERT INTO films (name, description) VALUES( ?, ?);";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { //сохранили фильм
                preparedStatement.setString(1, film.getName());
                preparedStatement.setString(2, film.getDescription());

                preparedStatement.executeUpdate();

                ResultSet keys = preparedStatement.getGeneratedKeys();
                if (keys.next()) {
                    film.setId(keys.getLong("films_id"));
                }

            }

            if (presentGenres) saveGenreInFilm(film, connection);

        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }
        return film;
    }

    public void saveGenreInFilm(Film film, Connection connection) throws SQLException {
        String sql = "INSERT INTO films_genre (films_id, genre_id) VALUES(?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (Genre genre : film.getGenres()) {
                preparedStatement.setLong(1, film.getId());
                preparedStatement.setLong(2, genre.getId());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }
    }


    public Optional<Film> getFilmById(long filmId) {
        Film film;

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/film", "postgres", "postgres")) {
            String sql = "select * from films where film_id = ?;";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, filmId);

                ResultSet rs = preparedStatement.executeQuery();
                rs.next();
                film = Film.builder()
                        .id(rs.getLong("film_id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .build();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            addGenres(film, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(film);
    }

    private void addGenres(Film film, Connection connection) {
        String sqlForGenres = "select * from genre where genre_id in (select genre_id from films_genre where film_id = ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlForGenres)) {
            preparedStatement.setLong(1, film.getId());

            ResultSet rs = preparedStatement.executeQuery();
            List<Genre> genres = new ArrayList<>();
            while (rs.next()) {
                genres.add(Genre.builder()
                        .id(rs.getLong("genre_id"))
                        .name(rs.getString("name"))
                        .build());
            }

            film.setGenres(genres);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Film removeFilm(Film film) {
        return null;
    }

    public Film patchFilm(Film film) {
        return null;
    }
}
