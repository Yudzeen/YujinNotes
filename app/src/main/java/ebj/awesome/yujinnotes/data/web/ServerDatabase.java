package ebj.awesome.yujinnotes.data.web;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ebj.awesome.yujinnotes.data.AsyncNotesRepository;
import ebj.awesome.yujinnotes.data.NotesRepository;
import ebj.awesome.yujinnotes.model.Note;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yujin on 02/10/2017.
 */

public class ServerDatabase implements AsyncNotesRepository {

    private static final String TAG = ServerDatabase.class.getSimpleName();
    private static final String BASE_URL = "http://fathomless-plateau-20819.herokuapp.com/";

    private static ServerDatabase instance;

    private YujinNotesService service;

    public static ServerDatabase getInstance() {
        if (instance == null) {
            instance = new ServerDatabase();
        }
        return instance;
    }

    private ServerDatabase() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(YujinNotesService.class);
    }

    @Override
    public void getNotes(final LoadNotesCallback callback) {
        Log.i(TAG, "Getting notes.");
        Call<JsonResponse> call = service.getNotes();
        Log.i(TAG, "URL: " + call.request().url().toString());

        call.enqueue(new retrofit2.Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                List<Note> notes = response.body().getNotes();
                if (notes != null) {
                    Log.i(TAG, "Successfully fetched notes.");
                    Log.i(TAG, notes.toString());
                    callback.onNotesLoaded(notes);
                } else {
                    callback.onNotesLoaded(new ArrayList<Note>());
                }
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Log.i(TAG, "Failed to fetched notes.");
            }
        });
    }

    @Override
    public void getNote(String id, final GetNoteCallback callback) {
        Call<JsonResponse> call = service.getNote(id);
        Log.i(TAG, "Getting notes.");
        Log.i(TAG, "URL: " + call.request().url().toString());
        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                List<Note> notes = response.body().getNote();
                Note note = notes.get(0);
                Log.i(TAG, "Received: " + note);
                callback.onNoteLoaded(note);
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Log.i(TAG, "Failed to fetched note.");
            }
        });
    }

    @Override
    public void saveNote(Note note) {
        Call<JsonResponse> call = service.insertNote(note.getId(), note.getTitle(), note.getDescription(), note.getPosition());
        Log.i(TAG, "Adding note.");
        Log.i(TAG, "URL: " + call.request().url().toString());
        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                Log.i(TAG, "Inserted Successfully.");
                Log.i(TAG, response.body().getMessage());
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Log.i(TAG, "Insert failed.");
            }
        });
    }

    @Override
    public void updateNote(Note note) {
        Call<JsonResponse> call = service.updateNote(note.getId(), note.getTitle(), note.getDescription(), note.getPosition());
        Log.i(TAG, "Updating note.");
        Log.i(TAG, "URL: " + call.request().url().toString());
        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                Log.i(TAG, "Updated Successfully.");
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Log.i(TAG, "Updated failed.");
            }
        });
    }

    @Override
    public void deleteNote(final Note note, final DeleteNoteCallback callback) {
        Call<JsonResponse> call = service.deleteNote(note.getId());
        Log.i(TAG, "Deleting note.");
        Log.i(TAG, "URL: " + call.request().url().toString());
        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                callback.onNoteDeleted(note);
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Log.i(TAG, "Delete failed.");
            }
        });
    }

}
