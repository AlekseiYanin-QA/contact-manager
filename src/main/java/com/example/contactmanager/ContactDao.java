package com.example.contactmanager;

import java.util.List;
import java.util.Optional;

public interface ContactDao {
    List<Contact> getAllContacts();
    Optional<Contact> getContactById(Long id);
    Long addContact(Contact contact);
    void updatePhoneNumber(Long id, String newPhoneNumber);
    void updateEmail(Long id, String newEmail);
    void deleteContact(Long id);
    void deleteAllContacts(); // Дополнительный метод для очистки
}
