package com.example.cryptotracker;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
//import androidx.lifecycle.viewModelScope;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private final LiveData<List<Note>> allNotes;
    private final NoteRepository repository;

    public NoteViewModel(Application application) {
        super(application);
        NoteDao dao = NoteDatabase.getDatabase(application).getNoteDao();
        repository = new NoteRepository(dao);
        allNotes = repository.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void deleteNote(Note note) {
        // You may want to perform this operation in a background thread or coroutine.
        // For simplicity, this example doesn't include threading.
        repository.delete(note);
    }

    public void insertNote(Note note) {
        // You may want to perform this operation in a background thread or coroutine.
        // For simplicity, this example doesn't include threading.
        repository.insert(note);
    }
}

