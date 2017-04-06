package work.course.planning.prediction.com.planningapp.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import work.course.planning.prediction.com.planningapp.R;
import work.course.planning.prediction.com.planningapp.dto.request.CreateFeatureDto;
import work.course.planning.prediction.com.planningapp.dto.response.CreateFeatureResultDto;
import work.course.planning.prediction.com.planningapp.service.PlanningApiService;
import work.course.planning.prediction.com.planningapp.service.impl.PlanningApiServiceImpl;

public class CreateFeatureActivity extends AppCompatActivity {

    private PlanningApiService planningApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_feature);
        setTitle("Planning app: create feature");

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
                EditText categoriesEditText = (EditText) findViewById(R.id.categoriesEditText);
                Switch isCategory = (Switch) findViewById(R.id.isCategory);

                String name = nameEditText.getText().toString();
                List<String> categories = new ArrayList<>();

                if(isCategory.isChecked()) {
                    String[] categoriesStrings = categoriesEditText.getText().toString().split("\n");
                    if(categoriesStrings.length == 0){
                        Toast.makeText(CreateFeatureActivity.this, "Incorrect input", Toast.LENGTH_LONG).show();
                        return;
                    }

                    for (String categoryString : categoriesStrings) {
                        if (categoryString == null || categoryString.length() > 100 || categoryString.length() == 0) {
                            Toast.makeText(CreateFeatureActivity.this, "Incorrect input", Toast.LENGTH_LONG).show();
                            return;
                        }
                        categories.add(categoryString);
                    }
                }

                if(name.length() > 100 || name.length() == 0) {
                    Toast.makeText(CreateFeatureActivity.this, "Incorrect input", Toast.LENGTH_LONG).show();
                    return;
                }

                CreateFeatureDto createFeatureDto = new CreateFeatureDto();
                createFeatureDto.setName(name);
                createFeatureDto.setCategory(isCategory.isChecked());
                createFeatureDto.setValues(categories);

                Long modelId = CreateFeatureActivity.this.getIntent().getLongExtra("modelInfoId", -1L);

                createFeatureDto.setModelId(modelId);

                new CreateFeatureAsyncTask().execute(createFeatureDto);
            }
        });
    }

    private class CreateFeatureAsyncTask extends AsyncTask<CreateFeatureDto, Void, CreateFeatureResultDto> {
        @Override
        protected CreateFeatureResultDto doInBackground(CreateFeatureDto... params) {
            try {
                planningApiService = new PlanningApiServiceImpl();
                return planningApiService.createFeature(params[0]);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(CreateFeatureResultDto createFeatureResultDto) {

            if(createFeatureResultDto == null){
                Toast.makeText(CreateFeatureActivity.this, "Cannot connect to the server, please, try again later", Toast.LENGTH_LONG).show();
                return;
            }

            if(createFeatureResultDto.getCode() != 200){
                Toast.makeText(CreateFeatureActivity.this, createFeatureResultDto.getErrorCode(), Toast.LENGTH_LONG).show();
                return;
            }

            Toast.makeText(CreateFeatureActivity.this, "Feature was successfully created!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(CreateFeatureActivity.this, FeaturesActivity.class);
            intent.putExtra("modelInfoName", CreateFeatureActivity.this.getIntent().getStringExtra("modelInfoName"));
            intent.putExtra("modelInfoId", CreateFeatureActivity.this.getIntent().getLongExtra("modelInfoId", -1L));
            startActivity(intent);
        }
    }
}
