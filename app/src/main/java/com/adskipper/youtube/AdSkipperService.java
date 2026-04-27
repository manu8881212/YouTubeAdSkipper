package com.adskipper.youtube;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.List;

public class AdSkipperService extends AccessibilityService {

    private static final String TAG = "AdSkipperService";
    private static final String YOUTUBE_PACKAGE = "com.google.android.youtube";
    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean isYouTubeRunning = false;

    // Skip button ke possible text values (English + Hindi)
    private static final String[] SKIP_BUTTON_TEXTS = {
        "Skip Ad", "Skip ad", "SKIP AD", "Skip Ads", "skip ad",
        "विज्ञापन छोड़ें", "Skip", "SKIP"
    };

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null) return;

        String packageName = "";
        if (event.getPackageName() != null) {
            packageName = event.getPackageName().toString();
        }

        // YouTube chal raha hai to check karo
        if (YOUTUBE_PACKAGE.equals(packageName)) {
            isYouTubeRunning = true;
            // Thodi delay ke baad skip button dhundho
            handler.postDelayed(this::findAndClickSkipButton, 500);
        }
    }

    private void findAndClickSkipButton() {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode == null) return;

        // Sab text values try karo
        for (String skipText : SKIP_BUTTON_TEXTS) {
            List<AccessibilityNodeInfo> nodes = rootNode.findAccessibilityNodeInfosByText(skipText);
            if (nodes != null && !nodes.isEmpty()) {
                for (AccessibilityNodeInfo node : nodes) {
                    if (node != null && node.isClickable()) {
                        node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        Log.d(TAG, "Skip button clicked: " + skipText);
                        return;
                    }
                    // Parent ko bhi click karne ki koshish karo
                    if (node != null) {
                        AccessibilityNodeInfo parent = node.getParent();
                        if (parent != null && parent.isClickable()) {
                            parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            Log.d(TAG, "Skip button parent clicked: " + skipText);
                            return;
                        }
                    }
                }
            }
        }

        // ViewId se bhi dhundho (YouTube ke internal IDs)
        tryClickByViewId(rootNode, "skip_ad_button");
        tryClickByViewId(rootNode, "skip_button");
        
        rootNode.recycle();
    }

    private void tryClickByViewId(AccessibilityNodeInfo root, String viewId) {
        List<AccessibilityNodeInfo> nodes = root.findAccessibilityNodeInfosByViewId(
            YOUTUBE_PACKAGE + ":id/" + viewId);
        if (nodes != null && !nodes.isEmpty()) {
            for (AccessibilityNodeInfo node : nodes) {
                if (node != null) {
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    Log.d(TAG, "Clicked by view ID: " + viewId);
                }
            }
        }
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "Service interrupted");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED |
                          AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED |
                          AccessibilityEvent.TYPE_VIEW_CLICKED;
        info.packageNames = new String[]{YOUTUBE_PACKAGE};
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.notificationTimeout = 100;
        info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS |
                     AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS;
        setServiceInfo(info);
        Log.d(TAG, "AdSkipper Service Connected");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        isYouTubeRunning = false;
        return super.onUnbind(intent);
    }
}
