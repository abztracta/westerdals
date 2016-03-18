package no.woact.pg4600.assignment2.ronesp13.views;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class HighscoreAdapter extends ArrayAdapter<String> {

    public HighscoreAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }
}
