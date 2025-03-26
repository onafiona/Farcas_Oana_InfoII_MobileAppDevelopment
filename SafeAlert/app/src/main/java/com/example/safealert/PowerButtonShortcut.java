package com.example.safealert;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

public class PowerButtonShortcut extends AccessibilityService {

    private long lastPressTime = 0;
    private  int powerPressCount=0;

    @Override
    public boolean onKeyEvent(KeyEvent event) {
        // Detectăm apăsările pe butonul Volume Up
        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP && event.getAction() == KeyEvent.ACTION_DOWN) {
            long currentTime = System.currentTimeMillis();

            // Dacă a trecut prea mult timp, resetăm contorul
            if (currentTime - lastPressTime > 500) {
                powerPressCount = 1;
            } else {
                powerPressCount++;
            }

            lastPressTime = currentTime;

            // Dacă detectăm apăsarea butonului Volume Up
            if (powerPressCount == 1) { // Numai o apăsare
                // Pornim activitatea principală și declanșăm funcția de urgență
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("emergency", true);
                startActivity(intent);
            }
        }
        return super.onKeyEvent(event);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }
}
