# আমার বাংলাদেশ — Discover Bangladesh

Native Android application built with Kotlin, Jetpack Compose, Material 3, Navigation Compose, DataStore, and Room.

## GitHub থেকে ফোনে APK ইনস্টল করার নিয়ম (Install APK from GitHub)

1. **AI Studio থেকে GitHub-এ Push করুন**  
   উপরের/সেটিংস মেনু থেকে **Push to GitHub** ব্যবহার করে কোড আপনার GitHub রিপোজিটরিতে পাঠান।

2. **GitHub Actions Build শেষ হওয়া পর্যন্ত অপেক্ষা করুন**  
   GitHub রিপোজিটরির **Actions** ট্যাবে গেলে `Build & Publish Android APK` ওয়ার্কফ্লো অটোমেটিক চলতে দেখবেন (~২–৩ মিনিট সময় নেয়)। কোনো আলাদা Secret বা Keystore সেটআপ ছাড়াই এটি `Amar-Bangladesh.apk` তৈরি করবে।

3. **GitHub Releases থেকে সরাসরি ফোনে ডাউনলোড ও ইনস্টল করুন**  
   - আপনার Android ফোনের ব্রাউজার দিয়ে GitHub রিপোজিটরি খুলুন।  
   - **Releases** সেকশনে গিয়ে **`আমার বাংলাদেশ (Latest APK)`** (`latest-apk`) ওপেন করুন।  
   - **Assets** থেকে **`Amar-Bangladesh.apk`** ডাউনলোড করে ফাইলটিতে ট্যাপ করুন এবং **Install** দিন (প্রথমবার হলে ব্রাউজারের জন্য *Install unknown apps* অনুমতি দিন)।

> বিকল্প হিসেবে আপনি **Actions → Build & Publish Android APK → Artifacts (`Amar-Bangladesh-APK`)** থেকেও `.zip` আকারে APK ডাউনলোড করতে পারেন।

## Local / CI Build Notes
- Uses **JDK 21** and **Gradle 9.3.1** (`gradle :app:assembleDebug`).
- Before running locally on a fresh clone, decode the debug keystore if `debug.keystore` is not present:
  ```bash
  base64 -d debug.keystore.base64 > debug.keystore
  ```
