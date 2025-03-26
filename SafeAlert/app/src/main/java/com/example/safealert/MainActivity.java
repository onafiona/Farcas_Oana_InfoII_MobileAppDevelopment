package com.example.safealert;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Insets;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import android.speech.RecognizerIntent;
import java.util.ArrayList;
import java.util.Locale;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int SMS_PERMISSION_CODE = 1;
    private static final int LOCATION_PERMISSION_CODE = 2;
    private static final int NOTIFICATION_PERMISSION_CODE = 3;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int REQUEST_CALL_PERMISSION=1;
    List<FaveContacts>faveContacts;
    private CountDownTimer countDownTimer;
    private boolean alertaAnulata = false;
    private static final long INACTIVITY_TIMEOUT = 30 * 1000; // 2 minute în milisecunde
    private long ultimaMiscare = System.currentTimeMillis();
    private SensorManager sensorManager;
    private Sensor accelerometru;
    private Sensor giroscop;
    private boolean inactiv = false;
    private long inactivityDelay = 2* 60 * 1000;///30 secunde - testing
    private final float MOVEMENT_THRESHOLD = 0.2f; /// pentru testing 20f si normal 0.2f
    private Handler inactivityHandler = new Handler();
    private Runnable inactivityRunnable;
    private boolean amtrimismesajul=false;
    private boolean seinchideTelefonul=false;
    private static final String CHANNEL_ID = "Battery notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissions();
        faveContacts=getFavoriteContacts(this);
        fusedLocationClient= LocationServices.getFusedLocationProviderClient(this);
        Button sendButton = findViewById(R.id.send_button);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometru = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        giroscop = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);

        NotifChannel();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()).toPlatformInsets();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            }
            return insets;
        });

        //verif shortcut
        if (getIntent().getBooleanExtra("emergency", false)) {
            emergencyClicked(findViewById(R.id.send_button));
        }
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCountDown();
                emergencyClicked(v);
            }
        });
    }

    public void permissions() {
        ///permisiuni SMS
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
        }
        ///perimisiuni locatie
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        }
        ///permisiuni contacte
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }
        ///permisiuni apel
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
        }
        ///permisiuni notificari
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }

    public List<FaveContacts> getFavoriteContacts(Context context) {
        List<FaveContacts> contacts = new ArrayList<>();

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        String selection = ContactsContract.Contacts.STARRED + "=?";
        String[] selectionArgs = new String[]{"1"};

        try (Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int phoneIndex = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);

                do {
                    String name = cursor.getString(nameIndex);
                    String phone = cursor.getString(phoneIndex);

                    contacts.add(new FaveContacts(name, phone));
                } while (cursor.moveToNext());

                Toast.makeText(context, "Contacte favorite încărcate!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Nu ai contacte favorite în agendă!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Eroare la încărcarea contactelor!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return contacts;
    }
    private void sendLocationAndMessage(FaveContacts contact) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        // Obține locația curentă
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    String mesaj="Am nevoie de ajutor! Locatia mea este: https://www.google.com/maps?q=" + latitude + "," + longitude;
                        SmsManager smsManager=SmsManager.getDefault();
                        smsManager.sendTextMessage(contact.getTelefon(), null, mesaj, null, null);
                        Toast.makeText(this, "Mesaj trimis!", Toast.LENGTH_SHORT).show();
                }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permisiune locație acordată!", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permisiune SMS acordată!", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == NOTIFICATION_PERMISSION_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permisiune notificări acordată!", Toast.LENGTH_SHORT).show();
        }
    }


    public void emergencyClicked(View view){
        for(FaveContacts contact: faveContacts){
            sendLocationAndMessage(contact);
        }
    }

    ///PARTEA AUDIO
    private void inregistrareAjutor() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ro-RO,en-US");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Spune 'Ajutor' sau 'Help' pentru a trimite mesajul!");

        try {
            startActivityForResult(intent, 100);
        } catch (Exception e) {
            Toast.makeText(this, "Microfonul nu e disponibil!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String voiceCommand = result.get(0).toLowerCase();

            if (voiceCommand.contains("ajutor") || voiceCommand.contains("help")) {
                Toast.makeText(this, "Comandă detectată: Trimit mesaj!", Toast.LENGTH_SHORT).show();
                emergencyClicked(findViewById(R.id.send_button)); // Declanșăm trimiterea mesajului
            } else {
                Toast.makeText(this, "Comandă nerecunoscută!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void AudioAlert(View view){
        inregistrareAjutor();
    }


    ///APEL DE URGENTA - COUNTDOWN
    private void startCountDown() {
        alertaAnulata = false;

        // Afisăm un text sau un progress bar pentru a arăta utilizatorului countdown-ul
        TextView countdownText = findViewById(R.id.countdown_text); // Adaugă un TextView în layout
        countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                countdownText.setText("Rămase: " + millisUntilFinished / 1000 + " secunde");
            }

            public void onFinish() {
                if (!alertaAnulata) {
                    // Dacă nu a fost anulat, efectuează apelul
                    ApelUrgenta();
                }
            }
        };
        countDownTimer.start();
    }

    public void cancelAlert(View view) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            alertaAnulata = true;
            Toast.makeText(this, "Alertă anulată!", Toast.LENGTH_SHORT).show();
        }
    }

    private void ApelUrgenta() {
        String emergencyNumber = "0787596450"; // Număr de urgență sau altul predefinit
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + emergencyNumber));
            startActivity(callIntent);
        }
    }

    /// INACTIVITATE
    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            double acceleration = Math.sqrt(x * x + y * y + z * z);

            if (Math.abs(acceleration - SensorManager.GRAVITY_EARTH) > MOVEMENT_THRESHOLD) {
                resetInactivityTimer();
                System.out.println("salut");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    };
    private void startInactivityTimer() {
        inactivityRunnable = () -> {
            sendInactivityAlert();
        };
        inactivityHandler.postDelayed(inactivityRunnable, inactivityDelay);
    }

    private void resetInactivityTimer() {
        inactivityHandler.removeCallbacks(inactivityRunnable);
        inactivityHandler.postDelayed(inactivityRunnable, inactivityDelay);
    }
    private void sendInactivityAlert() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        String mesaj="Nu am mai fost activ de " + (inactivityDelay/60000) + " minute. Locatia mea este: https://www.google.com/maps?q=" + latitude + "," + longitude;
                        faveContacts=getFavoriteContacts(this);
                        for(FaveContacts contact : faveContacts){
                            SmsManager smsManager=SmsManager.getDefault();
                            smsManager.sendTextMessage(contact.getTelefon(), null, mesaj, null, null);
                            Toast.makeText(this, "Mesaj de inactivitate trimis!", Toast.LENGTH_SHORT).show();
                        }
                }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometru != null)
            sensorManager.registerListener(sensorListener, accelerometru, SensorManager.SENSOR_DELAY_NORMAL);
        if (giroscop != null)
            sensorManager.registerListener(sensorListener, giroscop, SensorManager.SENSOR_DELAY_NORMAL);

        startInactivityTimer();
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorListener);
        inactivityHandler.removeCallbacks(inactivityRunnable);
    }


    ///BATERIE SCAZUTA
    private BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int batteryPct = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

            if (batteryPct <= 15 && batteryPct>5 && !amtrimismesajul && !inactiv) {
                alertaBaterieScazuta();
                sendNotification("Bateria ta este sub 15%. Poți activa economisirea bateriei pentru a o prelungi.");
                amtrimismesajul=true;
            } else if(batteryPct<=15 && !seinchideTelefonul && !inactiv){
                alertaSeInchideTelefonul();
                seinchideTelefonul=true;
            }
        }
    };

    private void NotifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Light Notifications", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void sendNotification(String message) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent= new Intent(this, MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Save Battery")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        manager.notify(1, builder.build());
    }

    private void alertaSeInchideTelefonul(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String mesaj="Telefonul meu urmeaza sa se inchida. Locatia mea este: https://www.google.com/maps?q=" + latitude + "," + longitude;
                faveContacts=getFavoriteContacts(this);
                for(FaveContacts contact : faveContacts){
                    SmsManager smsManager=SmsManager.getDefault();
                    smsManager.sendTextMessage(contact.getTelefon(), null, mesaj, null, null);
                    Toast.makeText(this, "Mesaj de alerta inchidere telefon trimis!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void alertaBaterieScazuta() {
        String mesaj = "Bateria mea e sub 15%! Dacă nu răspund, s-ar putea să mi se închidă telefonul.";

        faveContacts=getFavoriteContacts(this);
        for(FaveContacts contact : faveContacts){
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendMultipartTextMessage(contact.getTelefon(), null, smsManager.divideMessage(mesaj), null, null);
            Toast.makeText(this, "Mesaj de baterie scazuta trimis!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryReceiver);
    }

}
