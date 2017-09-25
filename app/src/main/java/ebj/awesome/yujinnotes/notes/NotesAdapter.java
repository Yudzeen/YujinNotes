package ebj.awesome.yujinnotes.notes;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ebj.awesome.yujinnotes.R;
import ebj.awesome.yujinnotes.model.Note;
import ebj.awesome.yujinnotes.util.NotesHelper;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private static final String TAG = NotesAdapter.class.getSimpleName();

    private List<Note> notes;
    private NoteInteractionListener listener;

    public NotesAdapter(List<Note> notes, NoteInteractionListener listener) {
        this.notes = notes;
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
        holder.note = notes.get(position);
        holder.titleView.setText(notes.get(position).getTitle());
        holder.descView.setText(notes.get(position).getDescription());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.OnNoteClicked(holder.note);
                }
            }
        });
    }

    public void replaceNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public void updateNote(Note note) {
        notes.set(NotesHelper.indexOf(note, notes), note);
    }

    public void removeNote(Note note) {
        notes.remove(NotesHelper.indexOf(note, notes));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        public final TextView titleView;
        public final TextView descView;
        public Note note;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            titleView = (TextView) view.findViewById(R.id.title);
            descView = (TextView) view.findViewById(R.id.description);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titleView.getText() + "'";
        }
    }

    public interface NoteInteractionListener {

        void OnNoteClicked(Note note);

    }
}
