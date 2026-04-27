package com.adskipper.youtube;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityManager;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.graphics.Color;
import android.view.View;
import java.util.List;

public class MainActivity extends Activity {

    private TextView statusText;
    private Button enableButton;
    private ImageView statusIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.statusText);
        enableButton = findViewById(R.id.enableButton);
        statusIcon = findViewById(R.id.statusIcon);

        enableButton.setOnClickListener(v -> {
            // Accessibility Settings open karo
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateStatus();
    }

    private void updateStatus() {
        if (isAccessibilityServiceEnabled()) {
            statusText.setText("✅ Service Active\nYouTube ads automatically skip ho rahi hain!");
            statusText.setTextColor(Color.parseColor("#00C853"));
            enableButton.setText("Settings Dekhein");
            enableButton.setBackgroundColor(Color.parseColor("#757575"));
        } else {
            statusText.setText("❌ Service Band Hai\nNeeche button dabao aur\n'YouTube Ad Skipper' enable karo");
            statusText.setTextColor(Color.parseColor("#FF1744"));
            enableButton.setText("Accessibility Enable Karo");
            enableButton.setBackgroundColor(Color.parseColor("#FF0000"));
        }
    }

    private boolean isAccessibilityServiceEnabled() {
        AccessibilityManager am = (AccessibilityManager) 
            getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> enabledServices = 
            am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
        
        for (AccessibilityServiceInfo service : enabledServices) {
            if (service.getResolveInfo().serviceInfo.packageName.equals(getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
