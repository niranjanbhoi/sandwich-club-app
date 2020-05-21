package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView mIngredientsImageView;
    private TextView mOriginTextView;
    private TextView mAlsoKnowAsTextView;
    private TextView mIngredientsTextView;
    private TextView mDescriptionTextView;

    private Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mIngredientsImageView = findViewById(R.id.image_iv);
        mOriginTextView = findViewById(R.id.tv_origin);
        mAlsoKnowAsTextView = findViewById(R.id.tv_also_known);
        mIngredientsTextView = findViewById(R.id.tv_ingredients);
        mDescriptionTextView = findViewById(R.id.tv_description);

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
        mSandwich = JsonUtils.parseSandwichJson(json);
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(mIngredientsImageView);

        setTitle(mSandwich.getMainName());
        mOriginTextView.setText(mSandwich.getPlaceOfOrigin());
        mAlsoKnowAsTextView.setText(TextUtils.join(", ", mSandwich.getAlsoKnownAs()));
        mIngredientsTextView.setText(TextUtils.join(", ", mSandwich.getIngredients()));
        mDescriptionTextView.setText(mSandwich.getDescription());
    }
}
