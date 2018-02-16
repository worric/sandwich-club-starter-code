package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;
import java.util.ListIterator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String NEW_LINE = "\n";
    private static final String NOT_AVAILABLE = "N/A";

    @BindView(R.id.description_tv)
    TextView mDiscription;
    @BindView(R.id.ingredients_tv)
    TextView mIngredients;
    @BindView(R.id.also_known_tv)
    TextView mAlsoKnownAs;
    @BindView(R.id.origin_tv)
    TextView mOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich actualSandwich) {
        mDiscription.setText(actualSandwich.getDescription());
        mOrigin.setText(actualSandwich.getPlaceOfOrigin());
        appendListOfStringsToTextView(actualSandwich.getIngredients(), mIngredients);
        appendListOfStringsToTextView(actualSandwich.getAlsoKnownAs(), mAlsoKnownAs);
    }

    private void appendListOfStringsToTextView(List<String> ingredients, TextView textView) {
        if (ingredients == null) {
            textView.setText(NOT_AVAILABLE);
            return;
        }

        ListIterator<String> it = ingredients.listIterator();

        while (it.hasNext()) {
            String entry = it.next();

            entry = entry + (it.hasNext() ? NEW_LINE : "");

            textView.append(entry);
        }
    }
}
