package no.woact.pg4600.assignment1.ronesp13.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import no.woact.pg4600.assignment1.ronesp13.R;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class History extends Activity {

    private Button backButton;
    private Context context;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        context = this;
        initWidgets();
        initAdapters();
        initListeners();
    }

    private void initWidgets() {
        list = (ListView) findViewById(R.id.history_list);
        backButton = (Button) findViewById(R.id.history_back_button);
    }

    private String[] createListContent() {
        SharedPreferences settings = getSharedPreferences("history", MODE_PRIVATE);
        long[] ids = new long[settings.getAll().size()];
        int count = 0;
        for (String key : settings.getAll().keySet()) {
            ids[count++] = Long.parseLong(key);
        }
        Arrays.sort(ids);
        String[] items = new String[settings.getAll().size()];
        for (int i = ids.length - 1, j = 0; i >= 0; i--, j++) {
            String value = (String) settings.getAll().get(String.valueOf(ids[i]));
            items[j] = parseContent(ids[i], value);
        }
        return items;
    }

    private String parseContent(long key, String value) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return dateFormat.format(new Date(key)) + ": " + value;
    }

    private void initAdapters() {
        list.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, createListContent()));
    }

    private void initListeners() {
     backButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             finish();
         }
     });
    }
}
