package no.woact.pg4600.assignment2.ronesp13.views;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import no.woact.pg4600.assignment2.ronesp13.models.instance.KimsGameObject;

public class KimsGameAdapter extends ArrayAdapter<KimsGameObject>{

    public KimsGameAdapter(Context context, int resource, int textViewResourceId, List<KimsGameObject> objects) {
        super(context, resource, textViewResourceId, objects);
    }
}
