package me.leozdgao.mydemoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class NumberListActivity extends AppCompatActivity implements GreenAdapter.ListItemClickListener {
    private static final int NUM_LIST_ITEMS = 100;
    private GreenAdapter mAdapter;
    private RecyclerView mNumbersList;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_list);

        mNumbersList = (RecyclerView) findViewById(R.id.rv_numbers);
        mAdapter = new GreenAdapter(NUM_LIST_ITEMS, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumbersList.setLayoutManager(layoutManager);
        mNumbersList.setHasFixedSize(true);
        mNumbersList.setAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(int index) {
        String message = "Item #" + index + " clicked.";
        toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
