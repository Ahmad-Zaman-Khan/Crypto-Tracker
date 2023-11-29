package com.example.cryptotracker;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    private final NoteDao noteDao;
    private final LiveData<List<Note>> allNotes;

    public NoteRepository(NoteDao noteDao) {
        this.noteDao = noteDao;
        this.allNotes = noteDao.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void insert(Note note) {
        // Use a coroutine to perform the insert operation on a background thread
        new InsertAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        // Use a coroutine to perform the delete operation on a background thread
        new DeleteAsyncTask(noteDao).execute(note);
    }

    private static class InsertAsyncTask extends AsyncTask<Note, Void, Void> {
        private final NoteDao noteDao;

        InsertAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(final Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {
        private final NoteDao noteDao;

        DeleteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(final Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
}




//public class NoteRepository {
//
//    private final NoteDao noteDao;
//    private final LiveData<List<Note>> allNotes;
//
//    public NoteRepository(NoteDao noteDao) {
//        this.noteDao = noteDao;
//        this.allNotes = noteDao.getAllNotes();
//    }
//
//    public LiveData<List<Note>> getAllNotes() {
//        return allNotes;
//    }
//
//    public void insert(Note note) {
//        // You may want to perform this operation in a background thread or coroutine.
//        // For simplicity, this example doesn't include threading.
//        noteDao.insert(note);
//    }
//
//    public void delete(Note note) {
//        // You may want to perform this operation in a background thread or coroutine.
//        // For simplicity, this example doesn't include threading.
//        noteDao.delete(note);
//    }
//}

