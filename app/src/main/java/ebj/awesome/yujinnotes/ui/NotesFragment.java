package ebj.awesome.yujinnotes.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ebj.awesome.yujinnotes.R;
import ebj.awesome.yujinnotes.db.DatabaseHelper;
import ebj.awesome.yujinnotes.model.Note;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class NotesFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";

    private List<Note> notes;
    private int columnCount = 1;

    private RecyclerView recyclerView;
    private OnListFragmentInteractionListener listener;

    DatabaseHelper dbHelper;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NotesFragment() {
    }

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

        dbHelper = new DatabaseHelper(getContext());
        notes = dbHelper.getNotes();
        if (!notes.isEmpty()) {
            Note.ID_COUNT = notes.get(notes.size()-1).getId() + 1;
        } else {
            Note.ID_COUNT = 0;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_notes_list, container, false);

        Context context = recyclerView.getContext();

        if (columnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
        }
        recyclerView.setAdapter(new NotesRecyclerViewAdapter(notes, listener));

        return recyclerView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            listener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnListFragmentInteractionListener {

        void onListFragmentInteraction(Note note);
    }

    public void addNote(Note note) {
        notes.add(note);
        dbHelper.insertNote(note);
        notifyAdapter();
    }

    public void updateNote(Note note) {
        if (note.isDeleted()) {
            notes.remove(indexOf(note));
            dbHelper.deleteNote(note.getId());
        } else {
            notes.set(indexOf(note), note);
            dbHelper.updateNote(note);
        }

        notifyAdapter();
    }

    private int indexOf(Note note) {
        int index = -1;
        int id = note.getId();
        for (int i = 0; i < notes.size(); i++) {
            if (id == notes.get(i).getId()) {
                index = i;
                break;
            }
        }
        return index;
    }

    private void notifyAdapter() {
        NotesRecyclerViewAdapter adapter = (NotesRecyclerViewAdapter) recyclerView.getAdapter();
        adapter.notifyDataSetChanged();
    }
}
