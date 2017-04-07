package work.course.planning.prediction.com.planningapp.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import work.course.planning.prediction.com.planningapp.R;
import work.course.planning.prediction.com.planningapp.dto.info.FeatureDto;
import work.course.planning.prediction.com.planningapp.dto.info.FeatureListValueDto;
import work.course.planning.prediction.com.planningapp.dto.request.AddExampleDto;
import work.course.planning.prediction.com.planningapp.dto.request.AddExampleInstanceDto;
import work.course.planning.prediction.com.planningapp.dto.response.AddExampleResultDto;
import work.course.planning.prediction.com.planningapp.dto.response.FeaturesListDto;
import work.course.planning.prediction.com.planningapp.service.PlanningApiService;
import work.course.planning.prediction.com.planningapp.service.impl.PlanningApiServiceImpl;

public class CreateExampleActivity extends AppCompatActivity {

    private PlanningApiService planningApiService;

    private Map<Long, Spinner> spinners = new HashMap<>();
    private Map<Long, EditText> inputs = new HashMap<>();

    private LinearLayout linearLayout;
    private EditText outputLabel;

    private Spinner featuresSpinner;
    private Button addFeaturesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_example);

        linearLayout = (LinearLayout) findViewById(R.id.createExampleActivity);
        featuresSpinner = (Spinner) findViewById(R.id.featuresSpinner);
        addFeaturesButton = (Button) findViewById(R.id.addFeatureInput);
        addFeaturesButton.setEnabled(false);

        final Long modelId = getIntent().getLongExtra("modelInfoId", -1L);
        final String modelName = getIntent().getStringExtra("modelInfoName");

        outputLabel = (EditText) findViewById(R.id.outputLabelEditText);

        Button backToModel = (Button) findViewById(R.id.backToModelFromExample);
        backToModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateExampleActivity.this, ModelManageActivity.class);
                intent.putExtra("modelInfoName", modelName);
                intent.putExtra("modelInfoId", modelId);

                startActivity(intent);
            }
        });

        new ListFeatureAsyncTask().execute(modelId);

        addFeaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeatureDto featureDto = (FeatureDto) featuresSpinner.getSelectedItem();

                if(inputs.get(featureDto.getId()) != null || spinners.get(featureDto.getId()) != null){
                    Toast.makeText(CreateExampleActivity.this, "Feature was selected already", Toast.LENGTH_LONG).show();
                    return;
                }

                TextView textView = new TextView(CreateExampleActivity.this);
                textView.setText(featureDto.getName()+": ");
                linearLayout.addView(textView);

                if(featureDto.isCategory()){
                    Spinner spinner = new Spinner(CreateExampleActivity.this);
                    ArrayAdapter<FeatureListValueDto> adapter = new ArrayAdapter<>(CreateExampleActivity.this, android.R.layout.simple_spinner_dropdown_item, featureDto.getValues());
                    spinner.setAdapter(adapter);
                    linearLayout.addView(spinner);

                    spinners.put(featureDto.getId(), spinner);
                }else{
                    EditText editText = new EditText(CreateExampleActivity.this);
                    inputs.put(featureDto.getId(), editText);
                    linearLayout.addView(editText);
                }
            }
        });

        Button createButton = (Button) findViewById(R.id.createExampleButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddExampleDto addExampleDto = new AddExampleDto();
                addExampleDto.setModelId(modelId);

                List<AddExampleInstanceDto> exampleInstanceDtos = new ArrayList<>();
                addExampleDto.setExamples(exampleInstanceDtos);

                for(Map.Entry<Long, EditText> entry: inputs.entrySet()){
                    String text = entry.getValue().getText().toString();
                    double value;

                    try{
                        value = Double.valueOf(text);
                    }catch (Exception e){
                        Toast.makeText(CreateExampleActivity.this, "Invalid data", Toast.LENGTH_LONG).show();
                        return;
                    }

                    AddExampleInstanceDto addExampleInstanceDto = new AddExampleInstanceDto();
                    addExampleInstanceDto.setId(entry.getKey());
                    addExampleInstanceDto.setValue(value);

                    exampleInstanceDtos.add(addExampleInstanceDto);
                }

                for(Map.Entry<Long, Spinner> entry: spinners.entrySet()){
                    AddExampleInstanceDto addExampleInstanceDto = new AddExampleInstanceDto();
                    addExampleInstanceDto.setId(entry.getKey());
                    addExampleInstanceDto.setListValueId(((FeatureListValueDto) entry.getValue().getSelectedItem()).getId());

                    exampleInstanceDtos.add(addExampleInstanceDto);
                }

                try{
                    DateFormat formatter = new SimpleDateFormat("hh:mm");
                    Date date = formatter.parse(outputLabel.getText().toString());

                    addExampleDto.setOutputLabel((int) (date.getTime() / 1000));
                }catch (Exception e){
                    Toast.makeText(CreateExampleActivity.this, "Invalid data", Toast.LENGTH_LONG).show();
                    return;
                }

                if(exampleInstanceDtos.size() > 0) {
                    new CreateExampleAsyncTask().execute(addExampleDto);
                }else{
                    Toast.makeText(CreateExampleActivity.this, "Invalid data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private class CreateExampleAsyncTask extends AsyncTask<AddExampleDto, Void, AddExampleResultDto> {
        @Override
        protected AddExampleResultDto doInBackground(AddExampleDto... params) {
            try {
                planningApiService = new PlanningApiServiceImpl();
                return planningApiService.addExample(params[0]);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(AddExampleResultDto addExampleResultDto) {

            if(addExampleResultDto == null){
                Toast.makeText(CreateExampleActivity.this, "Cannot connect to the server, please, try again later", Toast.LENGTH_LONG).show();
                return;
            }

            if(addExampleResultDto.getCode() != 200){
                Toast.makeText(CreateExampleActivity.this, addExampleResultDto.getErrorCode(), Toast.LENGTH_LONG).show();
                return;
            }

            Toast.makeText(CreateExampleActivity.this, "Example was successfully created!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(CreateExampleActivity.this, FeaturesActivity.class);
            intent.putExtra("modelInfoName", CreateExampleActivity.this.getIntent().getStringExtra("modelInfoName"));
            intent.putExtra("modelInfoId", CreateExampleActivity.this.getIntent().getLongExtra("modelInfoId", -1L));
            startActivity(intent);
        }
    }

    private class ListFeatureAsyncTask extends AsyncTask<Long, Void, FeaturesListDto> {
        @Override
        protected FeaturesListDto doInBackground(Long... params) {
            try {
                planningApiService = new PlanningApiServiceImpl();
                return planningApiService.getFeatures(params[0]);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(FeaturesListDto featuresListDto) {

            if(featuresListDto == null){
                Toast.makeText(CreateExampleActivity.this, "Cannot connect to the server, please, try again later", Toast.LENGTH_LONG).show();
                return;
            }

            if(featuresListDto.getCode() != 200){
                Toast.makeText(CreateExampleActivity.this, featuresListDto.getErrorCode(), Toast.LENGTH_LONG).show();
                return;
            }

            if(featuresListDto.getFeatures().size() > 0)
                addFeaturesButton.setEnabled(true);
            else{
                Toast.makeText(CreateExampleActivity.this, "No features", Toast.LENGTH_LONG).show();
                return;
            }

            List<FeatureDto> featureDtos = featuresListDto.getFeatures();

            ArrayAdapter<FeatureDto> adapter = new ArrayAdapter<>(CreateExampleActivity.this, android.R.layout.simple_spinner_dropdown_item, featureDtos);
            featuresSpinner.setAdapter(adapter);

            addFeaturesButton.setEnabled(true);
        }
    }
}
