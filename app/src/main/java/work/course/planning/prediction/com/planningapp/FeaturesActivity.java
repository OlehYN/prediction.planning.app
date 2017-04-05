package work.course.planning.prediction.com.planningapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import work.course.planning.prediction.com.planningapp.dto.response.FeaturesListDto;
import work.course.planning.prediction.com.planningapp.graphics.FeaturesListAdapter;
import work.course.planning.prediction.com.planningapp.service.PlanningApiService;
import work.course.planning.prediction.com.planningapp.service.impl.PlanningApiServiceImpl;

public class FeaturesActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private FeaturesListAdapter featureListAdapter;

    private PlanningApiService planningApiService = new PlanningApiServiceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);

        final Long modelId = getIntent().getLongExtra("modelInfoId", -1L);
        final String modelName = getIntent().getStringExtra("modelInfoName");
        new ListFeatureAsyncTask().execute(modelId);

        Button modelsButtons = (Button) findViewById(R.id.backToModel);
        modelsButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeaturesActivity.this, ModelManageActivity.class);
                intent.putExtra("modelInfoId", modelId);
                intent.putExtra("modelInfoName", modelName);

                startActivity(intent);
            }
        });
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
                Toast.makeText(FeaturesActivity.this, "Cannot connect to the server, please, try again later", Toast.LENGTH_LONG).show();
                return;
            }

            if(featuresListDto.getCode() != 200){
                Toast.makeText(FeaturesActivity.this, featuresListDto.getErrorCode(), Toast.LENGTH_LONG).show();
                return;
            }

            expandableListView = (ExpandableListView) findViewById(R.id.featuresExpList);
            featureListAdapter = new FeaturesListAdapter(FeaturesActivity.this, featuresListDto.getFeatures());
            expandableListView.setAdapter(featureListAdapter);
        }
    }
}
