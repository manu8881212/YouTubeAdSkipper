# 📺 YouTube Ad Skipper — Setup Guide

## Kya karta hai?
- YouTube pe ad aaye to **automatically "Skip Ad" button click** kar deta hai
- **Background mein chalta hai** — dikhai nahi deta
- YouTube close karo to service bhi band ho jaati hai
- Bilkul **free**, koi subscription nahi

---

## Android Studio Mein Kaise Build Karein?

### Step 1 — Android Studio Download Karo
https://developer.android.com/studio

### Step 2 — Project Import Karo
1. Android Studio open karo
2. **File → Open** karo
3. `YouTubeAdSkipper` folder select karo
4. Gradle sync hone do (internet chahiye)

### Step 3 — APK Build Karo
1. **Build → Build Bundle(s) / APK(s) → Build APK(s)**
2. APK yahan milegi:
   `app/build/outputs/apk/debug/app-debug.apk`

### Step 4 — Phone pe Install Karo
1. APK phone mein copy karo
2. **Unknown Sources** enable karo (Settings → Security)
3. APK install karo

---

## App Use Kaise Karein?

### Pehli baar setup:
1. **"YT Ad Skipper"** app open karo
2. **"Accessibility Enable Karo"** button dabaao
3. Accessibility Settings khulengi
4. **"Downloaded Apps"** ya **"Installed Apps"** mein jaao
5. **"YouTube Ad Skipper"** dhundho
6. Toggle **ON** karo → **Allow** karo
7. Wapas app mein aao — ✅ green status dikhega

### Roz use:
- Bas **YouTube kholo** — app khud kaam karega
- App icon background mein invisible rehta hai
- YouTube band karo → service apne aap band

---

## Project Structure

```
YouTubeAdSkipper/
├── app/src/main/
│   ├── java/com/adskipper/youtube/
│   │   ├── AdSkipperService.java   ← Main service (skip logic)
│   │   └── MainActivity.java       ← App screen
│   ├── res/
│   │   ├── layout/activity_main.xml
│   │   ├── xml/accessibility_service_config.xml
│   │   ├── values/strings.xml
│   │   └── drawable/card_bg.xml
│   └── AndroidManifest.xml
├── build.gradle
└── settings.gradle
```

---

## Troubleshooting

**Skip nahi ho raha?**
- YouTube app update ho gayi hai — YouTube versions change karte rehte hain
- `AdSkipperService.java` mein `SKIP_BUTTON_TEXTS` array mein naya text add karo

**Service dikhi nahi Accessibility mein?**
- App dobara install karo
- Phone restart karo

**MIUI / One UI phones pe?**
- Battery optimization mein app ko **"No restrictions"** pe set karo
- Settings → Battery → App Launch → Manual → Background activity ON

---

## Important Note
Yeh app sirf **Accessibility Service** use karta hai — koi data collect nahi karta, koi permission nahi maangta. 100% safe hai.
