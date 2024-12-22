package com.example.contactmanager;

import java.util.List;

public interface ContactDao {
    List<Contact> getAllContacts();
    Contact getContactById(Long id);
    void addContact(Contact contact);
    void updatePhoneNumber(Long id, String phoneNumber);
    void updateEmail(Long id, String email);
    void deleteContact(Long id);
}
