package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDBStore {

    private final BasicDataSource pool;
    private static final String TRUNCATE_TABLE_QUERY = "TRUNCATE TABLE post RESTART IDENTITY";
    private static final String SELECT_QUERY = "SELECT * FROM post";
    private static final String INSERT_QUERY = "INSERT INTO post(name, city_id , description, created, visible) VALUES (?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE post SET name = ?,city_id =?, description=?, created =?, visible =?  WHERE id = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM post WHERE id = ?";
    private static final Logger LOG = LoggerFactory.getLogger(PostDBStore.class.getName());

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }


    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT_QUERY)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(getPost(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method findAll", e);
        }
        return posts;
    }

    public void truncateTable() {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(TRUNCATE_TABLE_QUERY)
        ) {
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in method TruncateTable", e);
        }
    }


    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getCity().getId());
            ps.setString(3, post.getDescription());
            ps.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            ps.setBoolean(5, post.isVisible());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method add", e);
        }
        return post;
    }

    public void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getCity().getId());
            ps.setString(3, post.getDescription());
            ps.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            ps.setBoolean(5, post.isVisible());
            ps.setInt(6, post.getId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method update", e);
        }
    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID_QUERY)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return getPost(it);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method findById", e);
        }
        return null;
    }

    private Post getPost(ResultSet it) throws SQLException {
        return new Post(it.getInt("id"),
                it.getString("name"),
                new City(it.getInt("city_id")),
                it.getString("description"),
                it.getTimestamp("created").toLocalDateTime(),
                it.getBoolean("visible"));
    }
}