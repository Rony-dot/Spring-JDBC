package com.rakibul.movie;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MovieDataAccessService implements MovieDao {

    private final JdbcTemplate jdbcTemplate;

    public MovieDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> selectMovies() {
        var sql = """
                Select id, name, release_date From movie LIMIT 100;
                """;
//        #_____ 1. using resultSet ______
//        List<Movie> movies = jdbcTemplate.query(sql, (resultSet, i) -> {
//            return new Movie(
//                    resultSet.getInt("id"),
//                    resultSet.getString("name"),
//                    null,
//                    LocalDate.parse(resultSet.getString("release_date"))
//            );
//        });
//        #_____ 2. Using MovieRowMapper _____
//        List<Movie> movies = jdbcTemplate.query(sql, new MovieRowMapper());

        return jdbcTemplate.query(sql, new MovieRowMapper());
    }

    @Override
    public int insertMovie(Movie movie) {
        String sql = """
            INSERT INTO movie (name, release_date) 
            values (?,?);
        """;
        return jdbcTemplate.update(sql,movie.name(),movie.releaseDate());
    }

    @Override
    public int deleteMovie(int id) {
        var sql = """
                Delete from movie where id = ?;
                """;
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Movie> selectMovieById(int id) {
        throw new UnsupportedOperationException("not implemented");
    }
    
}
