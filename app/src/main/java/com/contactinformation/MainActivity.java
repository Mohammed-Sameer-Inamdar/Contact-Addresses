package com.contactinformation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText personname, personcontact, personaddress, deletName;
    private EditText findname, upAddress;

    public static final int REQUEST_ID_MULTILE_PERMISSIONS=1;
    MyDataBase handler;
    ArrayList<Long> result_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        personname = findViewById(R.id.personNAME);
        personcontact = findViewById(R.id.personCONTACT);
        personaddress =findViewById(R.id.personADDRESS);
        findname  = findViewById(R.id.name);
        upAddress = findViewById(R.id.addAdress);

        deletName = findViewById(R.id.deletname);
        result_ = new ArrayList<Long>();

        handler = new MyDataBase(this);
        boolean flag = checkAndRequestPermission();
        if(flag==false){
            Message.message(getApplicationContext(),"Provide permissions to use all features");
        }



    }
    public void getContact(){

        Cursor phonecursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);
        while (phonecursor.moveToNext()){
            String phnName = phonecursor.getString(phonecursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phnNumber = phonecursor.getString(phonecursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String phnAddress = "null";
            long phnid = handler.insertData(phnName,phnNumber,phnAddress);
            result_.add(phnid);

        }
        if (result_.isEmpty()==false){
            Message.message(getApplicationContext(),"Contacts loaded succesfully");
        }else {
            Message.message(getApplicationContext(),"Failed may be already exist");
        }
    }
    private boolean checkAndRequestPermission() {
        int contacts = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        int storage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int call_Logs = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if(storage!= PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(contacts!= PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }
        if(call_Logs!= PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(Manifest.permission.READ_CALL_LOG);
        }
        if(!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTILE_PERMISSIONS);
            return false;
        }
        return true;
    }


    public void addPerson(View view) {
        String nameIn = personname.getText().toString();
        String contactIn = personcontact.getText().toString();
        String addressIn = personaddress.getText().toString();
        if(nameIn.isEmpty() || contactIn.isEmpty() || addressIn.isEmpty())
        {
            Message.message(getApplicationContext(), "All fields are mandatory");
        }
        else
        {
            long id = handler.insertData(nameIn,contactIn,addressIn);
            if(id<=0)
            {
                Message.message(getApplicationContext(), "Faild may be already added");
            }
            else
            {
                Message.message(getApplicationContext(), "Added Successfully");
                personname.setText("");
                personcontact.setText("");
                personaddress.setText("");
            }
        }
    }

    public void viewdata(View view)
    {
        Intent intent = new Intent(MainActivity.this, allPersons.class);
        startActivity(intent);

    }

    public void deletperson(View view)
    {
        String uname = deletName.getText().toString();
        if(uname.isEmpty()){
            Message.message(getApplicationContext(),"Enter name to delet");
        }
        else
        {
            int a= handler.delete(uname);
            if(a<=0)
            {
                Message.message(getApplicationContext(),"Sorry Not deleted");
                deletName.setText("");
            } else {
                Message.message(this, "Done Removed");
                deletName.setText("");
            }
        }
    }

    public void load(View view) {
        getContact();
    }

    public void update(View view) {
        String upname = findname.getText().toString();
        String upaddress = upAddress.getText().toString();
        if(upname.isEmpty()||upaddress.isEmpty()){
            Message.message(getApplicationContext(),"provide name and address to update");
        }else {

            int res = handler.addAddress(upname,upaddress);
            if(res>0){
                Message.message(getApplicationContext(),"Updated successfull");
                findname.setText("");
                upAddress.setText("");
            }else{
                Message.message(getApplicationContext(),"Failed to update");
            }
        }

    }
}
