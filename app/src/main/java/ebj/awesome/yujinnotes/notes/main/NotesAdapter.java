package ebj.awesome.yujinnotes.notes.main;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ebj.awesome.yujinnotes.R;
import ebj.awesome.yujinnotes.model.Note;
import ebj.awesome.yujinnotes.util.NotesHelper;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private static final String TAG = NotesAdapter.class.getSimpleName();

    private List<Note> notes;
    private NotesContract.Presenter presenter;
    private NoteInteractionListener listener;

    public NotesAdapter(List<Note> notes, NotesContract.Presenter presenter, NoteInteractionListener listener) {
        this.notes = notes;
        this.presenter = presenter;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.note = note;
        holder.titleView.setText(note.getTitle());
        holder.descView.setText(note.getDescription());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onNoteClicked(holder.note);
                }
            }
        });

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.onNoteLongClicked(holder);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public void updateNote(Note note) {
        notes.set(note.getPosition(), note);
    }

    public void deleteNote(Note note) {
        notes.remove(note.getPosition());
        presenter.updatePositions(notes);
    }

    public void moveNote(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(notes, i, i + 1);
                notifyItemMoved(i, i+1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(notes, i, i - 1);
                notifyItemMoved(i, i-1);
            }
        }
        presenter.updatePositions(notes);
    }

    public void replaceNotes(List<Note> notes) {
        this.notes = notes;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements NoteViewHolder {

        public final View view;
        @BindView(R.id.title) TextView titleView;
        @BindView(R.id.description) TextView descView;
        public Note note;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
            titleView = (TextView) view.findViewById(R.id.title);
            descView = (TextView) view.findViewById(R.id.description);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titleView.getText() + "'";
        }

        @Override
        public void onNoteSelected() {
            view.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onNoteClear() {
            view.setBackgroundColor(0);
        }

        @Override
        public Note getNote() {
            return note;
        }
    }

    public interface NoteInteractionListener {

        void onNoteClicked(Note note);
        void onNoteLongClicked(RecyclerView.ViewHolder holder);

    }

    public interface NoteViewHolder {

        void onNoteSelected();
        void onNoteClear();
        Note getNote();

    }

}
