package com.example.contactmanager;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ContactDaoImpl implements ContactDao {

    private final JdbcTemplate jdbcTemplate;

    public ContactDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Contact> getAllContacts() {
        String sql = "SELECT * FROM contacts";
        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    @Override
    public Optional<Contact> getContactById(Long id) {
        String sql = "SELECT * FROM contacts WHERE id = ?";
        return jdbcTemplate.query(sql, new ContactRowMapper(), id) // Здесь мы передаем id как аргумент
                .stream()
                .findFirst();
    }

    @Override
    public Long addContact(Contact contact) {
        String sql = "INSERT INTO contacts (first_name, last_name, phone_number, email) VALUES (?, ?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(sql, Long.class,
                contact.getFirstName(),
                contact.getLastName(),
                contact.getPhoneNumber(),
                contact.getEmail()); // Передаем параметры как аргументы
    }

    @Override
    public void updatePhoneNumber(Long id, String phoneNumber) {
        String sql = "UPDATE contacts SET phone_number = ? WHERE id = ?";
        jdbcTemplate.update(sql, phoneNumber, id);
    }

    @Override
    public void updateEmail(Long id, String email) {
        String sql = "UPDATE contacts SET email = ? WHERE id = ?";
        jdbcTemplate.update(sql, email, id);
    }

    @Override
    public void deleteContact(Long id) {
        String sql = "DELETE FROM contacts WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteAllContacts() {
        String sql = "DELETE FROM contacts";
        jdbcTemplate.update(sql);
    }

    private static class ContactRowMapper implements RowMapper<Contact> {
        @Override
        public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Contact(
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("phone_number"),
                    rs.getString("email")
            );
        }
    }
}