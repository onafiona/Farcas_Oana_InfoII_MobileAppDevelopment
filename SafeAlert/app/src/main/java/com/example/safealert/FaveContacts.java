package com.example.safealert;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class FaveContacts {
    public String nume;
    public String telefon;

    public FaveContacts(String nume, String telefon){
        this.nume=nume;
        this.telefon=telefon;
    }

    public String getNume(){
        return nume;
    }

    public void setNume(String nume){
        this.nume=nume;
    }

    public String getTelefon(){
        return telefon;
    }

    public void setTelefon(){
        this.telefon=telefon;
    }

    public void sendSMS(Context context, String Location, String mesaj){
        try{
            mesaj=mesaj + Location;
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(telefon, null, mesaj, null, null);
            Toast.makeText(context,"Mesaj trimis!", Toast.LENGTH_SHORT).show();
            Log.d("SMS", "Trimit mesaj la: " + telefon);
            Log.d("SMS", "Mesaj: " + mesaj);
        }
        catch(Exception e){
            Toast.makeText(context, "Eroare la trimiterea mesajului!", Toast.LENGTH_SHORT). show();
            e.printStackTrace();
        }
    }

}
