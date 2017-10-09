package ebj.awesome.yujinnotes.calendar;


import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ebj.awesome.yujinnotes.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    @BindView(R.id.calenderView)
    CalendarView calendarView;

    public CalendarFragment() {
        // Required empty public constructor
    }
    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        ButterKnife.bind(this, view);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getActivity(), dayOfMonth + "/" + (month+1) + "/" + year + " clicked.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
