package ebj.awesome.yujinnotes.notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ebj.awesome.yujinnotes.R;
import ebj.awesome.yujinnotes.data.DatabaseHelper;
import ebj.awesome.yujinnotes.model.Note;
import ebj.awesome.yujinnotes.notes.create.CreateNoteActivity;
import ebj.awesome.yujinnotes.notes.detail.NoteDetailsActivity;

import static android.app.Activity.RESULT_OK;
import static ebj.awesome.yujinnotes.util.RequestCodeConstants.CREATE_NOTE_REQUEST_CODE;
import static ebj.awesome.yujinnotes.util.RequestCodeConstants.UPDATE_NOTE_REQUEST_CODE;

public class NotesFragment extends Fragment implements NotesContract.View, NotesAdapter.NoteInteractionListener {

    private static final String TAG = NotesFragment.class.getSimpleName();

    private static final String ARG_COLUMN_COUNT = "column-count";

    private int columnCount = 1;

    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private NotesAdapter.NoteInteractionListener listener;

    private NotesContract.Presenter presenter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NotesFragment() {}

    @SuppressWarnings("unused")
    public static NotesFragment newInstance(int columnCount) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            columnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        presenter = new NotesPresenter(this, DatabaseHelper.getInstance(getActivity()));
        presenter.start();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_notes_list);

        Context context = recyclerView.getContext();

        if (columnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
        }
        adapter = new NotesAdapter(new ArrayList<Note>(0), listener);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.attemptNoteCreation();
            }
        });

        presenter.loadNotes();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = this;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /*@Override
    public void onViewNote(Note note) {
        Intent intent = new Intent(getActivity(), NoteDetailsActivity.class);
        intent.putExtra(Note.TAG, note);
        startActivityForResult(intent, UPDATE_NOTE_REQUEST_CODE);
    }*/

    @Override
    public void OnNoteClicked(Note note) {
        presenter.onViewNote(note);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == UPDATE_NOTE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Note note = data.getParcelableExtra(Note.TAG);
                presenter.updateNote(note);
            }
        }

        if (requestCode == CREATE_NOTE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Note note = data.getParcelableExtra(Note.TAG);
                presenter.addNote(note);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*public void showNoteUpdated(Note note) {
        int index = NotesHelper.indexOf(note, notes);
        if (note.isTrashed()) {
            notes.remove(index);
            repository.deleteNote(note.getId());
        } else {
            notes.set(index, note);
            repository.showNoteUpdated(note);
        }

        notifyAdapter();
    }*/

    @Override
    public void displayNotes(List<Note> notes) {
        adapter.replaceNotes(notes);
    }

    @Override
    public void displayNoteDetails(Note note) {
        Intent intent = new Intent(getActivity(), NoteDetailsActivity.class);
        intent.putExtra(Note.TAG, note);
        startActivityForResult(intent, UPDATE_NOTE_REQUEST_CODE);
    }

    @Override
    public void showCreateNoteView() {
        Intent intent = new Intent(getActivity(), CreateNoteActivity.class);
        startActivityForResult(intent, CREATE_NOTE_REQUEST_CODE);
    }

    @Override
    public void showNoteCreated(Note note) {
        adapter.addNote(note);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showNoteUpdated(Note note) {
        adapter.updateNote(note);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showNoteTrashed(Note note) {
        adapter.removeNote(note);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showNoteCreatedMessage() {
        Snackbar.make(getView(), "Note created successfully.", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showNoteTrashedMessage() {
        Snackbar.make(getView(), "Note moved to trash.", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(NotesContract.Presenter presenter) {
        this.presenter = presenter;
    }
}