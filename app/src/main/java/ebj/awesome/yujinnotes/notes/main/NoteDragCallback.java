package ebj.awesome.yujinnotes.notes.main;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import ebj.awesome.yujinnotes.model.Note;

public class NoteDragCallback extends ItemTouchHelper.Callback {

    public static final String TAG = NoteDragCallback.class.getSimpleName();

    private static final float ALPHA_FULL = 1.0f;

    private final NoteDragListener listener;

    public NoteDragCallback(NoteDragListener listener) {
        this.listener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // Set movement flags based on the layout manager
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        if (viewHolder instanceof NotesAdapter.NoteViewHolder && target instanceof NotesAdapter.NoteViewHolder) {
            NotesAdapter.NoteViewHolder noteViewHolder = (NotesAdapter.NoteViewHolder) viewHolder;
            NotesAdapter.NoteViewHolder noteTarget = (NotesAdapter.NoteViewHolder) target;
            listener.onNoteMove(noteViewHolder.getNote(), noteTarget.getNote());
        }

        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (viewHolder instanceof NotesAdapter.NoteViewHolder) {
            NotesAdapter.NoteViewHolder noteViewHolder = (NotesAdapter.NoteViewHolder) viewHolder;
            listener.onNoteSwiped(noteViewHolder.getNote());
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            final float alpha = ALPHA_FULL - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof NotesAdapter.NoteViewHolder) {
                NotesAdapter.NoteViewHolder noteViewHolder = (NotesAdapter.NoteViewHolder) viewHolder;
                noteViewHolder.onNoteSelected();
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        viewHolder.itemView.setAlpha(ALPHA_FULL);
        if (viewHolder instanceof NotesAdapter.NoteViewHolder) {
            NotesAdapter.NoteViewHolder noteViewHolder = (NotesAdapter.NoteViewHolder) viewHolder;
            noteViewHolder.onNoteClear();
        }
    }

    public interface NoteDragListener {

        void onNoteMove(Note from, Note to);
        void onNoteSwiped(Note note);

    }
}
