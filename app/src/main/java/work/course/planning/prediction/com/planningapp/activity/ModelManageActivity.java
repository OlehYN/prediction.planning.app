package work.course.planning.prediction.com.planningapp.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import work.course.planning.prediction.com.planningapp.R;
import work.course.planning.prediction.com.planningapp.dto.info.ModelInfoDto;
import work.course.planning.prediction.com.planningapp.dto.response.DeleteModelDto;
import work.course.planning.prediction.com.planningapp.dto.response.ExamplesListDto;
import work.course.planning.prediction.com.planningapp.dto.response.ModelsListDto;
import work.course.planning.prediction.com.planningapp.graphics.ExamplesListAdapter;
import work.course.planning.prediction.com.planningapp.graphics.FeaturesListAdapter;
import work.course.planning.prediction.com.planningapp.graphics.ModelsListAdapter;
import work.course.planning.prediction.com.planningapp.service.PlanningApiService;
import work.course.planning.prediction.com.planningapp.service.impl.PlanningApiServiceImpl;

public class ModelManageActivity extends AppCompatActivity {

    private PlanningApiService planningApiService = new PlanningApiServiceImpl();
    private ExpandableListView expandableListView;

    private String modelName;
    private Long modelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_manage);

        modelName = getIntent().getStringExtra("modelInfoName");
        setTitle("Planning app: model " + modelName);

        modelId = getIntent().getLongExtra("modelInfoId", -1L);

        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteModelAsyncTask().execute(modelId);
            }
        });

        Button featuresButton = (Button) findViewById(R.id.featuresButton);
        featuresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ModelManageActivity.this, FeaturesActivity.class);
                intent.putExtra("modelInfoId", modelId);
                intent.putExtra("modelInfoName", modelName);
                startActivity(intent);
            }
        });

        Button createExampleButton = (Button) findViewById(R.id.createExample);
        createExampleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ModelManageActivity.this, CreateExampleActivity.class);
                intent.putExtra("modelInfoId", modelId);
                intent.putExtra("modelInfoName", modelName);
                startActivity(intent);
            }
        });

        Button predictButton = (Button) findViewById(R.id.predictButton);
        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO async task
            }
        });

        Button updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO async task
            }
        });

        Button backToModelsButton = (Button) findViewById(R.id.backToModels);
        backToModelsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModelManageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        new ListExamplesAsyncTask().execute(modelId);
    }

    private class DeleteModelAsyncTask extends AsyncTask<Long, Void, DeleteModelDto> {
        @Override
        protected DeleteModelDto doInBackground(Long... params) {

            Long id = params[0];
            if (id == null || id == -1L) {
                return new DeleteModelDto();
            } else {
                try {
                    return planningApiService.deleteModel(id);
                } catch (Exception e) {
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(DeleteModelDto result) {
            if(result == null) {
                Toast.makeText(ModelManageActivity.this, "Cannot connect to the server, please, try again later", Toast.LENGTH_LONG).show();
                return;
            }

            if(result.getCode() == 0) {
                Toast.makeText(getApplicationContext(), "Something gone wrong, please, try again later", Toast.LENGTH_SHORT).show();
                return;
            }
            if(result.getCode() != 200){
                Toast.makeText(ModelManageActivity.this, result.getErrorCode(), Toast.LENGTH_LONG).show();
                return;
            }

            Intent intent = new Intent(ModelManageActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private class ListExamplesAsyncTask extends AsyncTask<Long, Void, ExamplesListDto> {
        @Override
        protected ExamplesListDto doInBackground(Long... params) {
            try {
                planningApiService = new PlanningApiServiceImpl();
                return planningApiService.getExamples(params[0]);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ExamplesListDto examplesListDto) {

            if(examplesListDto == null){
                Toast.makeText(ModelManageActivity.this, "Cannot connect to the server, please, try again later", Toast.LENGTH_LONG).show();
                return;
            }

            if(examplesListDto.getCode() != 200){
                Toast.makeText(ModelManageActivity.this, examplesListDto.getErrorCode(), Toast.LENGTH_LONG).show();
                return;
            }

            expandableListView = (ExpandableListView) findViewById(R.id.examplesExpList);
            System.out.println(examplesListDto);
            ExamplesListAdapter examplesListAdapter = new ExamplesListAdapter(ModelManageActivity.this, examplesListDto.getExampleDtos(), ModelManageActivity.this, modelId, modelName);
            expandableListView.setAdapter(examplesListAdapter);
        }
    }
}
