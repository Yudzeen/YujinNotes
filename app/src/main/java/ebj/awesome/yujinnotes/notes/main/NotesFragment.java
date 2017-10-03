package ebj.awesome.yujinnotes.notes.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ebj.awesome.yujinnotes.R;
import ebj.awesome.yujinnotes.data.local.DatabaseHelper;
import ebj.awesome.yujinnotes.data.web.ServerDatabase;
import ebj.awesome.yujinnotes.model.Note;
import ebj.awesome.yujinnotes.notes.create.CreateNoteActivity;
import ebj.awesome.yujinnotes.notes.detail.NoteDetailsActivity;

import static android.app.Activity.RESULT_OK;
import static ebj.awesome.yujinnotes.util.RequestCodeConstants.ACTION_CODE;
import static ebj.awesome.yujinnotes.util.RequestCodeConstants.CREATE_NOTE_REQUEST_CODE;
import static ebj.awesome.yujinnotes.util.RequestCodeConstants.DELETE_NOTE_ACTION_CODE;
import static ebj.awesome.yujinnotes.util.RequestCodeConstants.UPDATE_NOTE_ACTION_CODE;
import static ebj.awesome.yujinnotes.util.RequestCodeConstants.VIEW_NOTE_REQUEST_CODE;

public class NotesFragment extends Fragment implements NotesContract.View, NotesAdapter.NoteInteractionListener, NoteDragCallback.NoteDragListener {

    private static final String TAG = NotesFragment.class.getSimpleName();

    private static final String ARG_COLUMN_COUNT = "column-count";

    private int columnCount = 1;

    private NotesAdapter adapter;
    private NotesAdapter.NoteInteractionListener listener;
    private ItemTouchHelper itemTouchHelper;

    private NotesContract.Presenter presenter;

    private ProgressDialog progressDialog;

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

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean useWebServer = preferences.getBoolean(getString(R.string.db_switch_key), false);

        if (useWebServer) {
            presenter = new AsyncNotesPresenter(this, ServerDatabase.getInstance());
        } else {
            presenter = new NotesPresenter(this, DatabaseHelper.getInstance(getActivity()));
        }

        presenter.start();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_notes_list);

        Context context = recyclerView.getContext();

        if (columnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
        }
        adapter = new NotesAdapter(new ArrayList<Note>(), presenter, listener);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new NoteDragCallback(this);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VIEW_NOTE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Note note = data.getParcelableExtra(Note.TAG);
                int actionCode = data.getIntExtra(ACTION_CODE, 0);
                switch (actionCode) {
                    case DELETE_NOTE_ACTION_CODE:
                        presenter.deleteNote(note);
                        break;
                    case UPDATE_NOTE_ACTION_CODE:
                        presenter.updateNote(note);
                        break;
                    default:
                        Log.i(TAG, "View note returns unknown action code.");
                }
            }
        }

        if (requestCode == CREATE_NOTE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Note note = data.getParcelableExtra(Note.TAG);
                note.setPosition(adapter.getItemCount());
                presenter.addNote(note);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void displayNotes(List<Note> notes) {
        adapter.replaceNotes(notes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void displayNoNotes() {
        Log.i(TAG, "You have no notes.");
    }

    @Override
    public void displayNoteDetails(Note note) {
        Intent intent = new Intent(getActivity(), NoteDetailsActivity.class);
        intent.putExtra(Note.TAG, note);
        startActivityForResult(intent, VIEW_NOTE_REQUEST_CODE);
    }

    @Override
    public void showCreateNoteView() {
        Intent intent = new Intent(getActivity(), CreateNoteActivity.class);
        startActivityForResult(intent, CREATE_NOTE_REQUEST_CODE);
    }

    @Override
    public void showNoteCreated(Note note) {
        adapter.addNote(note);
        adapter.notifyItemInserted(note.getPosition());
        Log.i(TAG, note.getTitle() + " created");
    }

    @Override
    public void showNoteUpdated(Note note) {
        adapter.updateNote(note);
        adapter.notifyItemChanged(note.getPosition());
        Log.i(TAG, note.getTitle() + " updated");
    }

    @Override
    public void showNoteDeleted(Note note) {
        adapter.deleteNote(note);
        adapter.notifyItemRemoved(note.getPosition());
        Log.i(TAG, note.getTitle() + " deleted.");
    }

    @Override
    public void showNoteCreatedMessage() {
        if (getView() != null) {
            Snackbar.make(getView(), "Note created successfully.", Snackbar.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showNoteDeletedMessage() {
        if (getView() != null) {
            Snackbar.make(getView(), "Note deleted.", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showNoteMoved(Note from, Note to) {
        adapter.updateNote(from);
        adapter.updateNote(to);
        adapter.notifyItemMoved(from.getPosition(), to.getPosition());
        Log.i(TAG, "Note ["+from.getPosition()+"]" + from.getTitle() +  " swapped with ["+to.getPosition()+"]" + to.getTitle());
    }

    @Override
    public void showProgressIndicator() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    public void hideProgressIndicator() {
        progressDialog.dismiss();
    }

    @Override
    public void showFailedAccessingServerMessage() {
        Toast.makeText(getActivity(), "No response from web server.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(NotesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onNoteClicked(Note note) {
        presenter.viewNote(note);
    }

    @Override
    public void onNoteLongClicked(RecyclerView.ViewHolder holder) {
        itemTouchHelper.startDrag(holder);
    }

    @Override
    public void onNoteMove(Note from, Note to) {
        presenter.moveNote(from, to);
    }

    @Override
    public void onNoteSwiped(Note note) {
        presenter.deleteNote(note);
    }

}
