package work.course.planning.prediction.com.planningapp.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import work.course.planning.prediction.com.planningapp.R;
import work.course.planning.prediction.com.planningapp.dto.response.CreateModelDto;
import work.course.planning.prediction.com.planningapp.dto.info.ModelInfoDto;
import work.course.planning.prediction.com.planningapp.dto.response.ModelsListDto;
import work.course.planning.prediction.com.planningapp.graphics.ModelsListAdapter;
import work.course.planning.prediction.com.planningapp.service.PlanningApiService;
import work.course.planning.prediction.com.planningapp.service.impl.PlanningApiServiceImpl;

public class MainActivity extends AppCompatActivity {

    private PlanningApiService planningApiService = new PlanningApiServiceImpl();

    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Planning app: models");

        new ListModelAsyncTask().execute();
        final Button createModelButton = (Button) findViewById(R.id.newModelButton);
        createModelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText modelNameEditText = (EditText) findViewById(R.id.modelNameEditText);
                String name = modelNameEditText.getText().toString();

                new CreateModelAsyncTask().execute(name);
            }
        });
    }

    private class CreateModelAsyncTask extends AsyncTask<String, Void, CreateModelDto> {
        @Override
        protected CreateModelDto doInBackground(String... params) {

            String name = params[0];
            if (name == null || name.length() == 0 || name.length() > 100) {
                return new CreateModelDto();
            } else {
                try {
                    return planningApiService.createModel(name);
                } catch (Exception e) {
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(CreateModelDto createModelDto) {
            if(createModelDto == null) {
                Toast.makeText(MainActivity.this, "Cannot connect to the server, please, try again later", Toast.LENGTH_LONG).show();
                return;
            }

            if(createModelDto.getModelInfoDto() == null) {
                Toast.makeText(getApplicationContext(), "Name should not be empty and length should be less than 100 characters", Toast.LENGTH_SHORT).show();
                return;
            }
            if(createModelDto.getCode() != 200){
                Toast.makeText(MainActivity.this, createModelDto.getErrorCode(), Toast.LENGTH_LONG).show();
                return;
            }

            List<ModelInfoDto> models = ((ModelsListAdapter) listView.getAdapter()).getData();
            models.add(createModelDto.getModelInfoDto());
            ((ModelsListAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
    }

    private class ListModelAsyncTask extends AsyncTask<Void, Void, ModelsListDto> {
        @Override
        protected ModelsListDto doInBackground(Void... params) {
            try {
                planningApiService = new PlanningApiServiceImpl();
                return planningApiService.getModels();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ModelsListDto modelsListDto) {

            if(modelsListDto == null){
                Toast.makeText(MainActivity.this, "Cannot connect to the server, please, try again later", Toast.LENGTH_LONG).show();
                return;
            }

            if(modelsListDto.getCode() != 200){
                Toast.makeText(MainActivity.this, modelsListDto.getErrorCode(), Toast.LENGTH_LONG).show();
                return;
            }

            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(new ModelsListAdapter(MainActivity.this, modelsListDto.getModels()));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    Object o = listView.getItemAtPosition(position);
                    ModelInfoDto modelInfoDto = (ModelInfoDto) o;
                    Intent modelManageIntent = new Intent(MainActivity.this, ModelManageActivity.class);
                    modelManageIntent.putExtra("modelInfoId", modelInfoDto.getId());
                    modelManageIntent.putExtra("modelInfoName", modelInfoDto.getName());

                    startActivity(modelManageIntent);
                }
            });
            ((ModelsListAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
    }
}