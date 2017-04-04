package work.course.planning.prediction.com.planningapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import work.course.planning.prediction.com.planningapp.dto.response.CreateModelDto;
import work.course.planning.prediction.com.planningapp.dto.response.ModelInfoDto;
import work.course.planning.prediction.com.planningapp.graphics.ModelsListAdapter;
import work.course.planning.prediction.com.planningapp.service.PlanningApiService;
import work.course.planning.prediction.com.planningapp.service.impl.PlanningApiServiceImpl;

public class MainActivity extends AppCompatActivity {

    private PlanningApiService planningApiService = new PlanningApiServiceImpl();

    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ModelInfoDto> models = null;
        models = getListData();

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ModelsListAdapter(this, models));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                ModelInfoDto modelInfoDto = (ModelInfoDto) o;
                Toast.makeText(MainActivity.this, "Selected :" + " " + modelInfoDto, Toast.LENGTH_LONG).show();
            }
        });

        final Button createModelButton = (Button) findViewById(R.id.newModelButton);
        createModelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createModel();
            }
        });
    }

    private List<ModelInfoDto> getListData() {

        //TODO validate output
        try {
            planningApiService = new PlanningApiServiceImpl();
            return planningApiService.getModels().getModels();
        } catch (Exception e) {
            //TODO show toast
            return new ArrayList<ModelInfoDto>();
        }
    }

    private void createModel() {
        //TODO validate output

        EditText modelNameEditText = (EditText) findViewById(R.id.modelNameEditText);
        String name = modelNameEditText.getText().toString();
        if (name == null || name.length() == 0 || name.length() > 100) {
            Toast.makeText(getApplicationContext(), "Name should not be empty and length should be less than 100 characters", Toast.LENGTH_SHORT).show();
        } else {
            try {
                CreateModelDto createModelDto = planningApiService.createModel(name);

                List<ModelInfoDto> models = ((ModelsListAdapter) listView.getAdapter()).getData();
                models.add(createModelDto.getModelInfoDto());
                ((ModelsListAdapter) listView.getAdapter()).notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
                //TODO show appropriate message
                //TODO handle different exceptions
            }
        }


    }
}