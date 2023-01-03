package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Repository
public class UserDBStore {

    private final BasicDataSource pool;
    private static final String TRUNCATE_TABLE_QUERY = "TRUNCATE TABLE user RESTART IDENTITY";
    private static final String SELECT_QUERY = "SELECT * FROM user";
    private static final String INSERT_QUERY = "INSERT INTO user(email, password) VALUES (?,?)";
    private static final String UPDATE_QUERY = "UPDATE user SET email = ?,password =?  WHERE id = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM user WHERE id = ?";
    private static final String FIND_BY_EMAIL_AND_PASSWORD_QUERY = "SELECT * FROM user WHERE email = ?, password = ?";
    private static final Logger LOG = LoggerFactory.getLogger(UserDBStore.class.getName());

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }


    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT_QUERY)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(getUser(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method findAll", e);
        }
        return users;
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


    public Optional<User> add(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method add", e);
        }
        return Optional.of(user);
    }

    public void update(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method update", e);
        }
    }

    public User findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID_QUERY)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return getUser(it);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method findById", e);
        }
        return null;
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_EMAIL_AND_PASSWORD_QUERY)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(getUser(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method findUserByEmailAndPassword", e);
        }
        return null;
    }

    private User getUser(ResultSet it) throws SQLException {
        return new User(it.getInt("id"),
                it.getString("email"),
                it.getString("password"));
    }
}
