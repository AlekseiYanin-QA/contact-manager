
import com.example.contactmanager.Contact;
import com.example.contactmanager.ContactDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ContactDaoImplTest {

    @Autowired
    private ContactDao contactDao;

    @BeforeEach
    void setUp() {
        contactDao.deleteContact(1L); // Убедитесь, что вы очищаете состояние перед каждым тестом
    }

    @Test
    void testAddAndGetContact() {
        Contact contact = new Contact(null, "John", "Doe", "1234567890", "john.doe@example.com");
        contactDao.addContact(contact);

        Contact retrievedContact = contactDao.getContactById(1L);
        assertEquals("John", retrievedContact.getFirstName());
        assertEquals("Doe", retrievedContact.getLastName());
        assertEquals("1234567890", retrievedContact.getPhoneNumber());
        assertEquals("john.doe@example.com", retrievedContact.getEmail());
    }

    // Другие тесты...
}