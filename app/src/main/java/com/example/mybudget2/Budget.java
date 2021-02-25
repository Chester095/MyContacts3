package com.example.mybudget2;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "budget_table") //если хотим задать таблицу не так как класс указываем в tableName
public class Budget {

    @PrimaryKey(autoGenerate = true) //так как хотим чтобы ID генерировался автоматически указываем true
    private long contactId;
    private String item, subItem, value, account;

    @Ignore //хотим чтобы этот конструктор игнорировался
    public Budget() {
    }

    public Budget(long contactId, String item, String subItem, String value, String account) {
        this.contactId = contactId;
        this.item = item;
        this.subItem = subItem;
        this.value = value;
        this.account = account;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long concatId) {
        this.contactId = contactId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getSubItem() {
        return subItem;
    }

    public void setSubItem(String subItem) {
        this.subItem = subItem;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
