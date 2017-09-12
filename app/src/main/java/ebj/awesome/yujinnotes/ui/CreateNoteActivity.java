package ebj.awesome.yujinnotes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import ebj.awesome.yujinnotes.R;
import ebj.awesome.yujinnotes.model.Note;

public class CreateNoteActivity extends AppCompatActivity {

    TextView titleField;
    TextView descField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_cancel);

        titleField = (TextView) findViewById(R.id.titleField);
        descField = (TextView) findViewById(R.id.descField);
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
        onNoteCancelled();
        return true;
    }

    public void onNoteDone() {
        String title = titleField.getText().toString();
        String description = descField.getText().toString();

        Intent intent = new Intent();
        intent.putExtra(Note.TITLE, title);
        intent.putExtra(Note.DESCRIPTION, description);

        setResult(RESULT_OK, intent);
        finish();
    }

    public void onNoteCancelled() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
