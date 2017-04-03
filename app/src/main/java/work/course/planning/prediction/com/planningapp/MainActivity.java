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
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import work.course.planning.prediction.com.planningapp.dto.response.ModelInfoDto;
import work.course.planning.prediction.com.planningapp.graphics.ModelsListAdapter;
import work.course.planning.prediction.com.planningapp.service.PlanningApiService;
import work.course.planning.prediction.com.planningapp.service.impl.PlanningApiServiceImpl;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ModelInfoDto> image_details = null;
        try {
            image_details = getListData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final ListView lv1 = (ListView) findViewById(R.id.listView);
        lv1.setAdapter(new ModelsListAdapter(this, image_details));
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv1.getItemAtPosition(position);
                ModelInfoDto modelInfoDto = (ModelInfoDto) o;
                Toast.makeText(MainActivity.this, "Selected :" + " " + modelInfoDto, Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<ModelInfoDto> getListData() throws IOException {
        PlanningApiService planningApiService = new PlanningApiServiceImpl();
        return planningApiService.getModels().getModels();
    }
}