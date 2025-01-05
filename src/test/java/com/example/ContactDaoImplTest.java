package com.example;

import com.example.contactmanager.Contact;
import com.example.contactmanager.ContactDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ContactManagerApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class ContactDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ContactDao contactDao;

    @BeforeEach
    void setUp() {
        contactDao.deleteAllContacts(); // Удаляем всех контактов перед каждым тестом
    }

    @Test
    void testDatabaseConnection() {
        assertNotNull(jdbcTemplate, "JdbcTemplate должен быть инициализирован");
    }

    @Test
    void testAddAndGetContact() {
        Contact contact = new Contact(null, "John", "Doe", "1234567890", "john.doe@example.com");
        Long contactId = contactDao.addContact(contact);

        Optional<Contact> retrievedContact = contactDao.getContactById(contactId);
        assertTrue(retrievedContact.isPresent(), "Контакт должен быть найден по ID");
        assertEquals("John", retrievedContact.get().getFirstName(), "Имя контакта не совпадает");
        assertEquals("Doe", retrievedContact.get().getLastName(), "Фамилия контакта не совпадает");
        assertEquals("1234567890", retrievedContact.get().getPhoneNumber(), "Номер телефона контакта не совпадает");
        assertEquals("john.doe@example.com", retrievedContact.get().getEmail(), "Email контакта не совпадает");
    }

    @Test
    void testUpdatePhoneNumber() {
        Contact contact = new Contact(null, "John", "Doe", "1234567890", "john.doe@example.com");
        Long contactId = contactDao.addContact(contact);

        contactDao.updatePhoneNumber(contactId, "0987654321");
        Optional<Contact> updatedContact = contactDao.getContactById(contactId);
        assertTrue(updatedContact.isPresent(), "Контакт должен существовать");
        assertEquals("0987654321", updatedContact.get().getPhoneNumber(), "Номер телефона не был обновлён");
    }

    @Test
    void testUpdateEmail() {
        Contact contact = new Contact(null, "John", "Doe", "1234567890", "john.doe@example.com");
        Long contactId = contactDao.addContact(contact);

        contactDao.updateEmail(contactId, "john.new@example.com");
        Optional<Contact> updatedContact = contactDao.getContactById(contactId);
        assertTrue(updatedContact.isPresent(), "Контакт должен существовать");
        assertEquals("john.new@example.com", updatedContact.get().getEmail(), "Email не был обновлён");
    }

    @Test
    void testDeleteContact() {
        Contact contact = new Contact(null, "Jane", "Doe", "9876543210", "jane.doe@example.com");
        Long contactId = contactDao.addContact(contact);
        assertNotNull(contactId, "Контакт должен быть добавлен и идентификатор должен быть не null");

        contactDao.deleteContact(contactId);
        assertFalse(contactDao.getContactById(contactId).isPresent(), "Контакт должен быть удалён");
    }

    @Test
    void testGetAllContacts() {
        Contact contact1 = new Contact(null, "John", "Doe", "1234567890", "john.doe@example.com");
        Contact contact2 = new Contact(null, "Jane", "Doe", "9876543210", "jane.doe@example.com");

        contactDao.addContact(contact1);
        contactDao.addContact(contact2);

        List<Contact> contacts = contactDao.getAllContacts();
        assertEquals(2, contacts.size(), "Количество контактов должно быть 2");
    }
}