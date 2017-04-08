package work.course.planning.prediction.com.planningapp.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import work.course.planning.prediction.com.planningapp.R;
import work.course.planning.prediction.com.planningapp.dto.info.FeatureDto;
import work.course.planning.prediction.com.planningapp.dto.info.FeatureListValueDto;
import work.course.planning.prediction.com.planningapp.dto.request.AddExampleInstanceDto;
import work.course.planning.prediction.com.planningapp.dto.request.PredictDto;
import work.course.planning.prediction.com.planningapp.dto.response.FeaturesListDto;
import work.course.planning.prediction.com.planningapp.dto.response.GenericResponse;
import work.course.planning.prediction.com.planningapp.service.PlanningApiService;
import work.course.planning.prediction.com.planningapp.service.impl.PlanningApiServiceImpl;

public class PredictionActivity extends AppCompatActivity {

    private PlanningApiService planningApiService;

    private Map<Long, Spinner> spinners = new HashMap<>();
    private Map<Long, EditText> inputs = new HashMap<>();

    private LinearLayout linearLayout;

    private Spinner featuresSpinner;
    private Button addFeaturesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);

        linearLayout = (LinearLayout) findViewById(R.id.createPredictActivity);
        featuresSpinner = (Spinner) findViewById(R.id.featuresSpinnerPredict);
        addFeaturesButton = (Button) findViewById(R.id.addFeatureInputPredict);
        addFeaturesButton.setEnabled(false);

        final Long modelId = getIntent().getLongExtra("modelInfoId", -1L);
        final String modelName = getIntent().getStringExtra("modelInfoName");

        Button backToModel = (Button) findViewById(R.id.backToModelFromPredict);
        backToModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PredictionActivity.this, ModelManageActivity.class);
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
                    Toast.makeText(PredictionActivity.this, "Feature was selected already", Toast.LENGTH_LONG).show();
                    return;
                }

                TextView textView = new TextView(PredictionActivity.this);
                textView.setText(featureDto.getName()+": ");
                linearLayout.addView(textView);

                if(featureDto.isCategory()){
                    Spinner spinner = new Spinner(PredictionActivity.this);
                    ArrayAdapter<FeatureListValueDto> adapter = new ArrayAdapter<>(PredictionActivity.this, android.R.layout.simple_spinner_dropdown_item, featureDto.getValues());
                    spinner.setAdapter(adapter);
                    linearLayout.addView(spinner);

                    spinners.put(featureDto.getId(), spinner);
                }else{
                    EditText editText = new EditText(PredictionActivity.this);
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    inputs.put(featureDto.getId(), editText);
                    linearLayout.addView(editText);
                }
            }
        });

        Button createButton = (Button) findViewById(R.id.createPredictButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PredictDto predictDto = new PredictDto();
                predictDto.setModelId(modelId);

                List<AddExampleInstanceDto> exampleInstanceDtos = new ArrayList<>();
                predictDto.setExamples(exampleInstanceDtos);

                for(Map.Entry<Long, EditText> entry: inputs.entrySet()){
                    String text = entry.getValue().getText().toString();
                    double value;

                    try{
                        value = Double.valueOf(text);
                    }catch (Exception e){
                        Toast.makeText(PredictionActivity.this, "Invalid data", Toast.LENGTH_LONG).show();
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

                if(exampleInstanceDtos.size() > 0) {
                    new PredictAsyncTask().execute(predictDto);
                }else{
                    Toast.makeText(PredictionActivity.this, "Invalid data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private class PredictAsyncTask extends AsyncTask<PredictDto, Void, GenericResponse<Integer>> {
        @Override
        protected GenericResponse<Integer> doInBackground(PredictDto... params) {
            try {
                planningApiService = new PlanningApiServiceImpl();
                return planningApiService.predict(params[0]);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(GenericResponse<Integer> addExampleResultDto) {

            if(addExampleResultDto == null){
                Toast.makeText(PredictionActivity.this, "Cannot connect to the server, please, try again later", Toast.LENGTH_LONG).show();
                return;
            }

            if(addExampleResultDto.getCode() != 200){
                Toast.makeText(PredictionActivity.this, addExampleResultDto.getErrorCode(), Toast.LENGTH_LONG).show();
                return;
            }

            Toast.makeText(PredictionActivity.this, "Example was successfully created!", Toast.LENGTH_LONG).show();

            AlertDialog.Builder alert = new AlertDialog.Builder(PredictionActivity.this);
            alert.setTitle("Prediction");
            alert.setMessage(addExampleResultDto.getData()/60/60 + ":" +addExampleResultDto.getData()/60%60);
            alert.setPositiveButton("OK",null);
            alert.show();
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
                Toast.makeText(PredictionActivity.this, "Cannot connect to the server, please, try again later", Toast.LENGTH_LONG).show();
                return;
            }

            if(featuresListDto.getCode() != 200){
                Toast.makeText(PredictionActivity.this, featuresListDto.getErrorCode(), Toast.LENGTH_LONG).show();
                return;
            }

            if(featuresListDto.getFeatures().size() > 0)
                addFeaturesButton.setEnabled(true);
            else{
                Toast.makeText(PredictionActivity.this, "No features", Toast.LENGTH_LONG).show();
                return;
            }

            List<FeatureDto> featureDtos = featuresListDto.getFeatures();

            ArrayAdapter<FeatureDto> adapter = new ArrayAdapter<>(PredictionActivity.this, android.R.layout.simple_spinner_dropdown_item, featureDtos);
            featuresSpinner.setAdapter(adapter);

            addFeaturesButton.setEnabled(true);
        }
    }
}
