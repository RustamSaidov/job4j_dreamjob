package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidateDBStore {

    private final BasicDataSource pool;
    private static final String SELECT_QUERY = "SELECT * FROM candidate";
    private static final String INSERT_QUERY = "INSERT INTO candidate(name, city_id , desc, created, visible, photo) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE candidate SET name = ?,city_id =?, description=?, date =?, visible =?, photo =?  WHERE id = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM candidate WHERE id = ?";
    private static final Logger LOG = LoggerFactory.getLogger(PostDBStore.class.getName());

    public CandidateDBStore(BasicDataSource pool) {
        this.pool = pool;
    }


    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT_QUERY)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(getCandidate(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method findAll", e);
        }
        return candidates;
    }


    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCity().getId());
            ps.setString(3, candidate.getDesc());
            ps.setTimestamp(4, Timestamp.valueOf(candidate.getCreated()));
            ps.setBoolean(5, candidate.isVisible());
            ps.setBytes(6, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method add", e);
        }
        return candidate;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCity().getId());
            ps.setString(3, candidate.getDesc());
            ps.setTimestamp(4, Timestamp.valueOf(candidate.getCreated()));
            ps.setBoolean(5, candidate.isVisible());
            ps.setBytes(6, candidate.getPhoto());
            ps.setInt(7, candidate.getId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method update", e);
        }
    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID_QUERY)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return getCandidate(it);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method findById", e);
        }
        return null;
    }

    private Candidate getCandidate(ResultSet it) throws SQLException {
        return new Candidate(it.getInt("id"),
                it.getString("name"),
                it.getString("desc"),
                it.getTimestamp("date").toLocalDateTime(),
                it.getBoolean("visible"),
                new City(it.getInt("city_id")),
                it.getBytes("photo"));
    }
}
