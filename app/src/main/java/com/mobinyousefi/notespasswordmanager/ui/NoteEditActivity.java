package com.mobinyousefi.notespasswordmanager.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.mobinyousefi.notespasswordmanager.data.model.Note;
import com.mobinyousefi.notespasswordmanager.data.repo.NotesRepository;
import com.mobinyousefi.notespasswordmanager.databinding.ActivityNoteEditBinding;
import com.mobinyousefi.notespasswordmanager.util.Constants;

public class NoteEditActivity extends AppCompatActivity {

    private ActivityNoteEditBinding binding;
    private NotesRepository notesRepository;
    private String noteId; // null = new note

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNoteEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        notesRepository = new NotesRepository();
        noteId = getIntent().getStringExtra(Constants.EXTRA_NOTE_ID);

        binding.btnSave.setOnClickListener(v -> saveNote());

        if (noteId != null) {
            loadNote();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadNote() {
        binding.progressBar.setVisibility(View.VISIBLE);
        notesRepository.getNoteById(noteId)
                .addOnSuccessListener(this::bindNote)
                .addOnFailureListener(e -> {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Failed to load note: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    finish();
                });
    }

    private void bindNote(DocumentSnapshot snapshot) {
        binding.progressBar.setVisibility(View.GONE);
        Note note = snapshot.toObject(Note.class);
        if (note == null) {
            Toast.makeText(this, "Note not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        binding.etTitle.setText(note.getTitle());
        binding.etContent.setText(note.getContent());
    }

    private void saveNote() {
        String title = binding.etTitle.getText().toString().trim();
        String content = binding.etContent.getText().toString().trim();

        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(content)) {
            Toast.makeText(this, "Note is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.progressBar.setVisibility(View.VISIBLE);

        if (noteId == null) {
            notesRepository.addNote(title, content)
                    .addOnSuccessListener(docRef -> {
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "Save failed: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    });
        } else {
            notesRepository.updateNote(noteId, title, content)
                    .addOnSuccessListener(unused -> {
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "Update failed: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    });
        }
    }
}
