package ebj.awesome.yujinnotes.notes.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import ebj.awesome.yujinnotes.R;
import ebj.awesome.yujinnotes.model.Note;

public class CreateNoteActivity extends AppCompatActivity implements CreateNoteContract.View {

    private EditText titleField;
    private EditText descField;

    private CreateNoteContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_cancel);
        actionBar.setDisplayShowTitleEnabled(false);

        titleField = (EditText) findViewById(R.id.titleField);
        descField = (EditText) findViewById(R.id.descField);

        titleField.requestFocus();

        presenter = new CreateNotePresenter(this);
        presenter.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_done) {
            onNoteDone();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void setPresenter(CreateNoteContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showNoTitleError() {
        Toast.makeText(this, "Title is required.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoteCreated(Note note) {
        Intent intent = new Intent();
        intent.putExtra(Note.TAG, note);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void onNoteDone() {
        String title = titleField.getText().toString();
        String description = descField.getText().toString();
        Note note = new Note(title, description);
        presenter.onSubmit(note);
    }

}
