package com.example.mycontacts3;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts_table") //если хотим задать таблицу не так как класс указываем в tableName
public class Contact {

    @PrimaryKey(autoGenerate = true) //так как хотим чтобы ID генерировался автоматически указываем true
    private long concatId;
    private String firstName, lastName, email, phoneNumber;

    @Ignore //хотим чтобы этот конструктор игнорировался
    public Contact() {
    }

    public Contact(long concatId, String firstName, String lastName, String email, String phoneNumber) {
        this.concatId = concatId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public long getConcatId() {
        return concatId;
    }

    public void setConcatId(long concatId) {
        this.concatId = concatId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
