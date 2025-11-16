package com.mobinyousefi.notespasswordmanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.ListenerRegistration;
import com.mobinyousefi.notespasswordmanager.auth.LoginActivity;
import com.mobinyousefi.notespasswordmanager.data.model.Note;
import com.mobinyousefi.notespasswordmanager.data.repo.NotesRepository;
import com.mobinyousefi.notespasswordmanager.databinding.ActivityNotesListBinding;
import com.mobinyousefi.notespasswordmanager.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity
        implements NotesRepository.NotesListener, NotesListAdapter.NoteClickListener {

    private ActivityNotesListBinding binding;
    private NotesRepository notesRepository;
    private ListenerRegistration notesRegistration;
    private NotesListAdapter adapter;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNotesListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            goToLogin();
            return;
        }

        notesRepository = new NotesRepository();

        adapter = new NotesListAdapter(new ArrayList<>(), this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        binding.fabAddNote.setOnClickListener(v ->
                startActivity(new Intent(NotesListActivity.this, NoteEditActivity.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.progressBar.setVisibility(View.VISIBLE);
        notesRegistration = notesRepository.listenToNotes(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (notesRegistration != null) {
            notesRegistration.remove();
            notesRegistration = null;
        }
    }

    @Override
    public void onNotesLoaded(List<Note> notes) {
        binding.progressBar.setVisibility(View.GONE);
        binding.tvEmptyState.setVisibility(notes.isEmpty() ? View.VISIBLE : View.GONE);
        adapter.updateNotes(notes);
    }

    @Override
    public void onError(Exception e) {
        binding.progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Error loading notes: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNoteClicked(@NonNull Note note) {
        Intent intent = new Intent(this, NoteEditActivity.class);
        intent.putExtra(Constants.EXTRA_NOTE_ID, note.getId());
        startActivity(intent);
    }

    @Override
    public void onNoteLongClicked(@NonNull Note note) {
        notesRepository.deleteNote(note.getId())
                .addOnSuccessListener(unused ->
                        Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Delete failed: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.mobinyousefi.notespasswordmanager.R.menu.menu_notes_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == com.mobinyousefi.notespasswordmanager.R.id.action_logout) {
            auth.signOut();
            goToLogin();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
