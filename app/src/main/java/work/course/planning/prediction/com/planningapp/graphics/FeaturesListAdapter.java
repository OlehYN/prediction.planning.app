package work.course.planning.prediction.com.planningapp.graphics;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import work.course.planning.prediction.com.planningapp.R;
import work.course.planning.prediction.com.planningapp.activity.FeaturesActivity;
import work.course.planning.prediction.com.planningapp.activity.ModelManageActivity;
import work.course.planning.prediction.com.planningapp.dto.info.FeatureDto;
import work.course.planning.prediction.com.planningapp.dto.info.FeatureListValueDto;
import work.course.planning.prediction.com.planningapp.dto.info.ModelInfoDto;
import work.course.planning.prediction.com.planningapp.dto.request.CreateFeatureDto;
import work.course.planning.prediction.com.planningapp.dto.response.AddListValueFeatureDto;
import work.course.planning.prediction.com.planningapp.dto.response.FeaturesListDto;
import work.course.planning.prediction.com.planningapp.dto.response.RenameFeatureDto;
import work.course.planning.prediction.com.planningapp.service.PlanningApiService;
import work.course.planning.prediction.com.planningapp.service.impl.PlanningApiServiceImpl;

/**
 * Created by Oleh Yanivskyy on 04.04.2017.
 */

public class FeaturesListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<FeatureDto> features;
    private AppCompatActivity activity;

    private PlanningApiService planningApiService;
    private Long modelId;
    private String modelName;

    public FeaturesListAdapter(Context context, List<FeatureDto> features, AppCompatActivity activity, Long modelId, String modelName) {
        this.context = context;
        this.features = features;
        this.activity = activity;
        this.modelId = modelId;
        this.modelName = modelName;
    }

    @Override
    public Object getChild(int featurePosition, int childPosition) {
        List<FeatureListValueDto> featureListValueDtos = features.get(featurePosition).getValues();
        return featureListValueDtos.get(childPosition);
    }

    @Override
    public long getChildId(int featurePosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int featurePosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        FeatureListValueDto detailInfo = (FeatureListValueDto) getChild(featurePosition, childPosition);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.feature_values_list_row, null);
        }

        TextView sequence = (TextView) view.findViewById(R.id.title);
        sequence.setText(detailInfo.getValue());

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(features.get(groupPosition).getValues() == null)
            return 0;
        return features.get(groupPosition).getValues().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return features.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return features.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        final FeatureDto featureDto = (FeatureDto) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.features_list_row, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.title);
        heading.setText(featureDto.getName());

        TextView bottom = (TextView) view.findViewById(R.id.type);
        if(featureDto.isCategory())
            bottom.setText("Categorical");
        else
            bottom.setText("Number");

        Button addListValueButton = (Button) view.findViewById(R.id.addListValue);

        if(!featureDto.isCategory())
            addListValueButton.setEnabled(false);

        Button editFeatureNameButton = (Button) view.findViewById(R.id.renameButton);
        editFeatureNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.edit_name_prompt, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextNewFeatureName);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        if(userInput.getText().toString().length() == 0 || userInput.getText().toString().length() > 100){
                                            Toast.makeText(activity, "You should specify new name (from 1 to 100 characters)", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Long featureId = featureDto.getId();
                                            new CreateFeatureAsyncTask().execute(userInput.getText().toString(), featureId);
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }
        });


        addListValueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.add_value_name_prompt, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.addNewValue);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        if(userInput.getText().toString().length() == 0){
                                            Toast.makeText(activity, "Incorrect data)", Toast.LENGTH_SHORT).show();
                                        }else{

                                            List<String> listValues = new ArrayList<>();
                                            String[] values = userInput.getText().toString().split("\n");

                                            for(String value: values){
                                                listValues.add(value);
                                                if(value.length() == 0 || value.length() > 100){
                                                    Toast.makeText(activity, "Incorrect data)", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                            }

                                            Long featureId = featureDto.getId();
                                            new AddListValueFeatureAsyncTask().execute(featureId, listValues);
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }
        });
        return view;
    }

    private class AddListValueFeatureAsyncTask extends AsyncTask<Object, Void, AddListValueFeatureDto> {
        @Override
        protected AddListValueFeatureDto doInBackground(Object... params) {
            try {
                planningApiService = new PlanningApiServiceImpl();
                return planningApiService.addListValues((List<String>) params[1], (Long) params[0]);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(AddListValueFeatureDto addListValueFeatureDto) {

            if(addListValueFeatureDto == null){
                Toast.makeText(activity, "Cannot connect to the server, please, try again later", Toast.LENGTH_LONG).show();
                return;
            }

            if(addListValueFeatureDto.getCode() != 200){
                Toast.makeText(activity, addListValueFeatureDto.getErrorCode(), Toast.LENGTH_LONG).show();
                return;
            }

            Intent intent = new Intent(activity, FeaturesActivity.class);
            intent.putExtra("modelInfoId", FeaturesListAdapter.this.modelId);
            intent.putExtra("modelInfoName", FeaturesListAdapter.this.modelName);
            activity.startActivity(intent);
        }
    }

    private class CreateFeatureAsyncTask extends AsyncTask<Object, Void, RenameFeatureDto> {
        @Override
        protected RenameFeatureDto doInBackground(Object... params) {
            try {
                planningApiService = new PlanningApiServiceImpl();
                return planningApiService.renameFeature((String) params[0], (Long) params[1]);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(RenameFeatureDto featuresListDto) {

            if(featuresListDto == null){
                Toast.makeText(activity, "Cannot connect to the server, please, try again later", Toast.LENGTH_LONG).show();
                return;
            }

            if(featuresListDto.getCode() != 200){
                Toast.makeText(activity, featuresListDto.getErrorCode(), Toast.LENGTH_LONG).show();
                return;
            }

            Intent intent = new Intent(activity, FeaturesActivity.class);
            intent.putExtra("modelInfoId", FeaturesListAdapter.this.modelId);
            intent.putExtra("modelInfoName", FeaturesListAdapter.this.modelName);
            activity.startActivity(intent);
        }
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}