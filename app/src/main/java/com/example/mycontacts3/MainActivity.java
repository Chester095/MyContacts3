package com.example.mycontacts3;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MyContactsDatabase myContactsDatabase;
    private ArrayList<Contact> contactArrayList = new ArrayList<>();
    private ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        contactAdapter = new ContactAdapter(contactArrayList, MainActivity.this);
        recyclerView.setAdapter(contactAdapter);

        myContactsDatabase = Room.databaseBuilder(getApplicationContext(), MyContactsDatabase.class, "ContactsDB").build();

        loadContacts(); //загружаем данные

        //создаём для реализации свайпа и удаления
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) { //первый параметр - Направление перетягивания(0- не используем)
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) { //код который будет выполняться
                Contact contact = contactArrayList.get(viewHolder.getAdapterPosition());
                deleteContacts(contact);
            }
        }).attachToRecyclerView(recyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAndEditContact(false, null, -1); //false, null - так как добавляем, а не обновляем контакт; -1 - так как нет позиции такого контакта
            }
        });
    }

    public void addAndEditContact(boolean isUpdate, Contact contact, int position) { // чтобы знать обновляем или добавляем контакт
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.add_edit_contact, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);

        TextView contactTitleTextView = view.findViewById(R.id.contactTitleTextView);
        EditText firstNameEditText = view.findViewById(R.id.firstNameEditText);
        EditText lastNameEditText = view.findViewById(R.id.lastNameEditText);
        EditText emailEditText = view.findViewById(R.id.emailEditText);
        EditText phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText);

        //будем устанаваливать диалоги в зависимости от того редактируется контакт или редактируется
        contactTitleTextView.setText(!isUpdate ? "Add contact" : "Edit contact");

        //проводим проверку и редактируем контакт и получаем данные из соответствующего объекта контакт которые мы передаём в этот метод
        if (isUpdate && contact != null) {
            firstNameEditText.setText(contact.getFirstName());
            lastNameEditText.setText(contact.getLastName());
            emailEditText.setText(contact.getEmail());
            phoneNumberEditText.setText(contact.getPhoneNumber());
        }

        // создаём диалог
        builder.setCancelable(false)/*чтобы нельзя было отменить*/
                .setPositiveButton(isUpdate ? "Update" : "Save", /*подменяем наименование кнопки*/
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //проверка на пустые поля
                                if (TextUtils.isEmpty(firstNameEditText.getText().toString())) {
                                    Toast.makeText(MainActivity.this, "Enter first name", Toast.LENGTH_SHORT).show();
                                } else if (TextUtils.isEmpty(lastNameEditText.getText().toString())) {
                                    Toast.makeText(MainActivity.this, "Enter дфые name", Toast.LENGTH_SHORT).show();
                                } else if (TextUtils.isEmpty(emailEditText.getText().toString())) {
                                    Toast.makeText(MainActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                                } else if (TextUtils.isEmpty(phoneNumberEditText.getText().toString())) {
                                    Toast.makeText(MainActivity.this, "Enter phone number", Toast.LENGTH_SHORT).show();
                                } else { //проверяем сохраняем или обновляем и выбираем какой метод запустить
                                    if (isUpdate && contact != null) {
                                        updateContact(firstNameEditText.getText().toString(),
                                                lastNameEditText.getText().toString(),
                                                emailEditText.getText().toString(),
                                                phoneNumberEditText.getText().toString(), position);
                                    } else {
                                        addContacts(firstNameEditText.getText().toString(),
                                                lastNameEditText.getText().toString(),
                                                emailEditText.getText().toString(),
                                                phoneNumberEditText.getText().toString());
                                    }
                                }
                            }
                        });

        //запускаем AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void loadContacts() {
        new GetAllContactsAsyncTask().execute();
    }

    private void deleteContacts(Contact contact) {
        new DeleteContactAsyncTask().execute(contact);
    }

    private void addContacts(String firstName, String lastName, String email, String phoneNumber) {
        Contact contact = new Contact(0, firstName, lastName, email, phoneNumber); //0 - номер будет автоматически добавлен
        new AddContactAsyncTask().execute(contact);
    }

    private void updateContact(String firstName, String lastName, String email, String phoneNumber, int position) {
        Contact contact = contactArrayList.get(position); //получаем из Аррей листа по позиции
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setEmail(email);
        contact.setPhoneNumber(phoneNumber);

        //запускаем AsyncTask
        new UpdateContactAsyncTask().execute(contact);

        // и помещаем в contactArrayList позицию и обновлённый контакт
        contactArrayList.set(position, contact);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetAllContactsAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            contactArrayList = (ArrayList<Contact>) myContactsDatabase.getContactDao().getAllContacts();
            return null;
        }

        @Override  // заполняем RecycleView из Arraylist
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            contactAdapter.setContactArrayList(contactArrayList);
        }
    }


    private class DeleteContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        @Override
        protected Void doInBackground(Contact... contacts) {
            myContactsDatabase.getContactDao().deleteContact(contacts[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            loadContacts();
        }
    }

    private class AddContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        @Override
        protected Void doInBackground(Contact... contacts) {
            myContactsDatabase.getContactDao().insertContact(contacts[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loadContacts();
        }
    }

    private class UpdateContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        @Override
        protected Void doInBackground(Contact... contacts) {
            myContactsDatabase.getContactDao().updateContact(contacts[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loadContacts();
        }
    }
}