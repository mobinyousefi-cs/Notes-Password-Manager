# 📒 Notes & Password Manager (Android, Java, Firebase)

A secure and minimal **Notes & Password Manager** built using **Java**,
**Android**, **XML**, and **Firebase**.\
This application allows users to record notes, track daily activities,
and protect their data with user authentication.\
All notes are stored in **Firestore**, isolated by user account, and
synced in real time.

------------------------------------------------------------------------

## ✨ Features

### 🔐 Secure User Authentication

-   Firebase Email/Password login\
-   Account registration\
-   Logout functionality\
-   Per-user data isolation

### 📝 Notes Management

-   Create, edit, and delete notes\
-   Real-time updates across devices\
-   Title + content support\
-   Empty state UI when no notes exist

### ☁️ Firestore Cloud Storage

-   Notes stored at:\
    `users/{uid}/notes/{noteId}`
-   Uses Firestore timestamps (`createdAt`, `updatedAt`)\
-   Automatic ordering by last update

### 🎨 Modern UI

-   Material Design\
-   RecyclerView with ViewBinding\
-   Floating Action Button (FAB)\
-   Clean and minimal layout

### 🧱 Clean Architecture

-   `auth/` → Login/registration\
-   `ui/` → Notes list & edit screens\
-   `data/` → Models + Firestore repository\
-   `util/` → App constants

------------------------------------------------------------------------

## 🏗️ Project Structure

    NotesPasswordManager/
    ├─ settings.gradle
    ├─ build.gradle
    └─ app/
       ├─ build.gradle
       └─ src/main/
          ├─ AndroidManifest.xml
          ├─ java/com/mobinyousefi/notespasswordmanager/
          │  ├─ MainActivity.java
          │  ├─ auth/
          │  │   ├─ LoginActivity.java
          │  │   └─ RegisterActivity.java
          │  ├─ ui/
          │  │   ├─ NotesListActivity.java
          │  │   ├─ NoteEditActivity.java
          │  │   └─ NotesListAdapter.java
          │  ├─ data/model/
          │  │   └─ Note.java
          │  ├─ data/repo/
          │  │   └─ NotesRepository.java
          │  └─ util/
          │      └─ Constants.java
          └─ res/layout/
             ├─ activity_main.xml
             ├─ activity_login.xml
             ├─ activity_register.xml
             ├─ activity_notes_list.xml
             ├─ activity_note_edit.xml
             └─ item_note.xml

------------------------------------------------------------------------

## 🛠️ Tech Stack

-   Java\
-   Android XML\
-   Firebase Authentication\
-   Cloud Firestore\
-   Material Design Components\
-   Gradle

------------------------------------------------------------------------

## 🚀 Getting Started

### 1️⃣ Clone the Repository

``` bash
git clone https://github.com/mobinyousefi-cs/notes-password-manager.git
cd notes-password-manager
```

### 2️⃣ Firebase Setup

1.  Go to Firebase Console\
2.  Create a new project\
3.  Add Android App
    -   Package name: `com.mobinyousefi.notespasswordmanager`\
4.  Download `google-services.json`\
5.  Move it into:

```{=html}
<!-- -->
```
    app/google-services.json

### 3️⃣ Enable Firebase Services

#### ✔ Authentication

Enable Email/Password authentication.

#### ✔ Firestore

Create database → Start in Test Mode (for development)

------------------------------------------------------------------------

## 📱 Application Flow

### 🔐 Authentication

-   **LoginActivity** → Login and redirect\
-   **RegisterActivity** → Account creation

### 📝 Note Screens

-   **NotesListActivity** → List, delete, sync\
-   **NoteEditActivity** → Create/update note

------------------------------------------------------------------------

## 🗂️ Firestore Structure

    users/{uid}/notes/{noteId}

Each note:

``` json
{
  "title": "Example Note",
  "content": "Some content...",
  "createdAt": "<Timestamp>",
  "updatedAt": "<Timestamp>"
}
```

------------------------------------------------------------------------

## 🔒 Security Notes

-   Use strict Firestore rules in production\
-   Consider encrypting note content\
-   Follow secure authentication practices

------------------------------------------------------------------------

## 🌟 Future Enhancements

-   Search notes\
-   Dark mode\
-   Encrypted notes\
-   Pinned notes\
-   Attachments\
-   UI tests & unit tests

------------------------------------------------------------------------

## 🧑‍💻 Author

**Mobin Yousefi**\
Master's in Computer Science --- Android + Firebase Development\
GitHub: https://github.com/mobinyousefi-cs

------------------------------------------------------------------------

## 📄 License (MIT)

    MIT License
    Permission is hereby granted...
    (Include full MIT license in LICENSE file)
