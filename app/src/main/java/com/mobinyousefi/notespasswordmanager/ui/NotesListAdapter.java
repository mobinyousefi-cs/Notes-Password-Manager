package com.mobinyousefi.notespasswordmanager.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobinyousefi.notespasswordmanager.data.model.Note;
import com.mobinyousefi.notespasswordmanager.databinding.ItemNoteBinding;

import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteViewHolder> {

    public interface NoteClickListener {
        void onNoteClicked(@NonNull Note note);
        void onNoteLongClicked(@NonNull Note note);
    }

    private List<Note> notes;
    private final NoteClickListener listener;

    public NotesListAdapter(List<Note> notes, NoteClickListener listener) {
        this.notes = notes;
        this.listener = listener;
    }

    public void updateNotes(List<Note> newNotes) {
        this.notes = newNotes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNoteBinding binding = ItemNoteBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.bind(note);
    }

    @Override
    public int getItemCount() {
        return notes != null ? notes.size() : 0;
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        private final ItemNoteBinding binding;

        NoteViewHolder(ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Note note) {
            binding.tvTitle.setText(note.getTitle().isEmpty() ? "(No title)" : note.getTitle());
            binding.tvContentPreview.setText(note.getContent());

            binding.getRoot().setOnClickListener(v -> listener.onNoteClicked(note));
            binding.getRoot().setOnLongClickListener((View v) -> {
                listener.onNoteLongClicked(note);
                return true;
            });
        }
    }
}
