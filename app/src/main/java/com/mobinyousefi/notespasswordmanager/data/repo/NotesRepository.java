package com.mobinyousefi.notespasswordmanager.data.repo;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;
import com.mobinyousefi.notespasswordmanager.data.model.Note;
import com.mobinyousefi.notespasswordmanager.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class NotesRepository {

    private final FirebaseFirestore firestore;
    private final FirebaseAuth auth;

    public interface NotesListener {
        void onNotesLoaded(List<Note> notes);
        void onError(Exception e);
    }

    public NotesRepository() {
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    private CollectionReference userNotesRef() {
        String uid = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;
        if (uid == null) {
            throw new IllegalStateException("User not authenticated");
        }
        return firestore.collection(Constants.FIRESTORE_COLLECTION_USERS)
                .document(uid)
                .collection(Constants.FIRESTORE_COLLECTION_NOTES);
    }

    public ListenerRegistration listenToNotes(@NonNull final NotesListener listener) {
        return userNotesRef()
                .orderBy("updatedAt", Query.Direction.DESCENDING)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        listener.onError(e);
                        return;
                    }
                    if (snapshots == null) {
                        listener.onNotesLoaded(new ArrayList<>());
                        return;
                    }
                    List<Note> notes = new ArrayList<>();
                    for (DocumentSnapshot doc : snapshots.getDocuments()) {
                        Note note = doc.toObject(Note.class);
                        if (note != null) {
                            note.setId(doc.getId());
                            notes.add(note);
                        }
                    }
                    listener.onNotesLoaded(notes);
                });
    }

    public Task<DocumentReference> addNote(String title, String content) {
        Timestamp now = Timestamp.now();
        Note note = new Note(title, content, now, now);
        return userNotesRef().add(note);
    }

    public Task<Void> updateNote(String noteId, String title, String content) {
        Timestamp now = Timestamp.now();
        return userNotesRef()
                .document(noteId)
                .update("title", title,
                        "content", content,
                        "updatedAt", now);
    }

    public Task<Void> deleteNote(String noteId) {
        return userNotesRef()
                .document(noteId)
                .delete();
    }

    public Task<DocumentSnapshot> getNoteById(String noteId) {
        return userNotesRef()
                .document(noteId)
                .get();
    }
}
