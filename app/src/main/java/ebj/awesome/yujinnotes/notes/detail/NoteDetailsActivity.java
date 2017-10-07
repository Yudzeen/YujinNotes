package ebj.awesome.yujinnotes.notes.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import ebj.awesome.yujinnotes.R;
import ebj.awesome.yujinnotes.model.Note;
import ebj.awesome.yujinnotes.util.FieldsHelper;

import static ebj.awesome.yujinnotes.util.RequestCodeConstants.ACTION_CODE;
import static ebj.awesome.yujinnotes.util.RequestCodeConstants.DELETE_NOTE_ACTION_CODE;
import static ebj.awesome.yujinnotes.util.RequestCodeConstants.UPDATE_NOTE_ACTION_CODE;

public class NoteDetailsActivity extends AppCompatActivity implements NoteDetailsContract.View {

    private Note note;

    @BindView(R.id.titleField) EditText titleField;
    @BindView(R.id.descField) EditText descField;

    private MenuItem editBtn;
    private MenuItem doneBtn;
    private MenuItem deleteBtn;

    private NoteDetailsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.create_note_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        Bundle data = getIntent().getExtras();
        note = data.getParcelable(Note.TAG);

        titleField.setText(note.getTitle());
        descField.setText(note.getDescription());

        presenter = new NoteDetailsPresenter(this);
        presenter.start();

        setEditableFields(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_note, menu);
        editBtn = menu.findItem(R.id.viewNote_action_edit);
        doneBtn = menu.findItem(R.id.viewNote_action_editDone);
        deleteBtn = menu.findItem(R.id.viewNote_action_delete);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.viewNote_action_edit) {
            presenter.onEdit();
        } else if (id == R.id.viewNote_action_editDone) {
            updateNoteDetails();
            presenter.onEditDone();
        } else if (id == R.id.viewNote_action_delete) {
            presenter.onAttemptTrash();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void setPresenter(NoteDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setEditable(boolean editable) {
        setEditableFields(editable);
        setEditButtons(editable);
    }

    @Override
    public void displayDeleteConfirmation() {
        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
        confirmDialog.setTitle("Delete");
        confirmDialog.setMessage("Are you sure you want to delete this note?");
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    presenter.onConfirmTrash();
                } else if (which == DialogInterface.BUTTON_NEGATIVE) {
                    // not deleted
                }
            }
        };
        confirmDialog.setPositiveButton("Yes", listener);
        confirmDialog.setNegativeButton("No", listener);

        confirmDialog.show();
    }

    @Override
    public void showNoteDeleted() {
        finishViewActivity(DELETE_NOTE_ACTION_CODE);
    }

    @Override
    public void showNoteUpdated() {
        updateNoteDetails();
        finishViewActivity(UPDATE_NOTE_ACTION_CODE);
    }

    private void setEditableFields(boolean editable) {
        FieldsHelper.setEditTextEnabled(descField, editable);
        FieldsHelper.setEditTextEnabled(titleField, editable);

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if (editable) {
            titleField.setEllipsize(TextUtils.TruncateAt.END);
            titleField.requestFocus();
            inputMethodManager.showSoftInput(titleField, InputMethodManager.SHOW_IMPLICIT);
        } else {
            inputMethodManager.hideSoftInputFromWindow(titleField.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void setEditButtons(boolean editable) {
        editBtn.setVisible(!editable);
        doneBtn.setVisible(editable);
        deleteBtn.setVisible(!editable);
    }

    private void updateNoteDetails() {
        String title = titleField.getText().toString();
        String description = descField.getText().toString();

        note.setTitle(title);
        note.setDescription(description);
    }

    private void finishViewActivity(int actionCode) {
        Intent intent = new Intent();
        intent.putExtra(Note.TAG, note);
        intent.putExtra(ACTION_CODE, actionCode);
        setResult(RESULT_OK, intent);
        finish();
    }
}
