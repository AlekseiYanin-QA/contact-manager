package com.example.contactmanager;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactDaoImpl implements ContactDao {
    private final JdbcTemplate jdbcTemplate;

    public ContactDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Contact> getAllContacts() {
        String sql = "SELECT * FROM contacts";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Contact(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("phone_number"),
                rs.getString("email")
        ));
    }

    @Override
    public Contact getContactById(Long id) {
        String sql = "SELECT * FROM contacts WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> new Contact(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("phone_number"),
                rs.getString("email")
        ));
    }

    @Override
    public void addContact(Contact contact) {
        String sql = "INSERT INTO contacts (first_name, last_name, phone_number, email) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getPhoneNumber(), contact.getEmail());
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
}
