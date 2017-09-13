package ebj.awesome.yujinnotes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import ebj.awesome.yujinnotes.R;
import ebj.awesome.yujinnotes.model.Note;

public class ViewNoteActivity extends AppCompatActivity {

    Note note;

    EditText titleField;
    EditText descField;

    MenuItem editBtn;
    MenuItem doneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        titleField = (EditText) findViewById(R.id.titleField);
        descField = (EditText) findViewById(R.id.descField);

        setEditable(false);

        Bundle data = getIntent().getExtras();
        note = data.getParcelable(Note.TAG);

        titleField.setText(note.getTitle());
        descField.setText(note.getDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_note, menu);
        editBtn = menu.findItem(R.id.viewNote_action_edit);
        doneBtn = menu.findItem(R.id.viewNote_action_editDone);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.viewNote_action_edit) {
            Log.i(ViewNoteActivity.class.getName(), "Editing");
            setEditable(true);
            editBtn.setVisible(false);
            doneBtn.setVisible(true);
        } else if (id == R.id.viewNote_action_editDone) {
            Log.i(ViewNoteActivity.class.getName(), "Done");
            setEditable(false);
            doneBtn.setVisible(false);
            editBtn.setVisible(true);
            updateNote();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void updateNote() {
        note.setTitle(titleField.getText().toString());
        note.setDescription(descField.getText().toString());
        Intent intent = new Intent();
        intent.putExtra(Note.TAG, note);
        setResult(RESULT_OK, intent);
    }

    private void setEditable(boolean editable) {

        setEditTextEnabled(titleField, editable);
        setEditTextEnabled(descField, editable);

        Log.i(ViewNoteActivity.class.getName(), "1) Focused: " + descField.isFocused()
                + "Focusable: " + descField.isFocusable());

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if (editable) {
            titleField.setEllipsize(TextUtils.TruncateAt.END);
            titleField.requestFocus();
            inputMethodManager.showSoftInput(titleField, InputMethodManager.SHOW_IMPLICIT);
        }
        else {
            inputMethodManager.hideSoftInputFromWindow(titleField.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        Log.i(ViewNoteActivity.class.getName(), "2) Focused: " + descField.isFocused()
                + "Focusable: " + descField.isFocusable());
    }

    private void setEditTextEnabled(EditText editText, boolean enabled) {
        editText.setFocusable(enabled);
        editText.setFocusableInTouchMode(enabled);
        editText.setCursorVisible(enabled);
    }

}
