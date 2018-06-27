package codepath.com.to_dolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //test

    //declaring stateful objects here; these will be null before onCreate is called
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    public void onAddItem(View v) {
        //obtain reference to the EditText created with the layout
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);

        //grab the EditTest's content as a String
        String itemName = etNewItem.getText().toString();

        //add the item to the list via the adapter
        itemsAdapter.add(itemName);

        //clear the EditText by setting it to an empty String
        etNewItem.setText("");

        //display a notification to indicate successful adding
        Toast.makeText(getApplicationContext(), "Item successfully added!", Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //obtain a reference to the ListView created with the layout
        lvItems = (ListView) findViewById(R.id.lvItems);

        //initialize the items list
        readItems();

        //initialize the adapter using the items list
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

        //wire the adapter to the view
        lvItems.setAdapter(itemsAdapter);

        /*
        //add mock items to the list
        items.add("Laundry!");
        items.add("Call Mom");
        items.add("Buy milk");
        */

        setupListViewListener();
    }

    private void setupListViewListener() {

        //set the ListView's itemLongClickListener
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();

                return true;
            }
        });
    }

    private File getDataFile() {
        return new File(getFilesDir(), "todo.txt");
    }

    private void readItems() {
        try {
            items = new ArrayList<String>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
            items = new ArrayList<>();
        }
    }

    private void writeItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            e.printStackTrace();;
            items = new ArrayList<>();
        }
    }

}
