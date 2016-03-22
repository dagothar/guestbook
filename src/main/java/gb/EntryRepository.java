package gb;

import java.sql.Timestamp;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import org.joda.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EntryRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Entry> findAll() {
        String sql = "SELECT * FROM Entries";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        List<Entry> entries = new ArrayList<>();

        for (Map row : rows) {
            Entry entry = new Entry((Integer) row.get("id"), (String) row.get("message"), (String) row.get("author"), new LocalDateTime(row.get("dateTime")));
            entries.add(entry);
        }

        return entries;
    }

    public List<Entry> findEntries(Long offset, Long limit) {
        String sql = "SELECT * FROM Entries ORDER BY Entries.dateTime DESC OFFSET ? LIMIT ?";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[]{offset, limit});

        List<Entry> entries = new ArrayList<>();

        for (Map row : rows) {
            Entry entry = new Entry(
                    (Integer) row.get("id"),
                    (String) row.get("message"),
                    (String) row.get("author"),
                    new LocalDateTime(row.get("dateTime"))
            );
            entries.add(entry);
        }

        return entries;
    }

    public void addEntry(Entry entry) {
        String sql = "INSERT INTO Entries(message, author, dateTime) VALUES(?, ?, NOW())";

        jdbcTemplate.update(sql, new Object[]{entry.getMessage(), entry.getAuthor()});
    }

};
