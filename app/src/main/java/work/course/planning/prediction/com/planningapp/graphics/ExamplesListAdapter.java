package work.course.planning.prediction.com.planningapp.graphics;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

import work.course.planning.prediction.com.planningapp.R;
import work.course.planning.prediction.com.planningapp.activity.ModelManageActivity;
import work.course.planning.prediction.com.planningapp.dto.info.ExampleDto;
import work.course.planning.prediction.com.planningapp.dto.info.ExampleInstanceDto;
import work.course.planning.prediction.com.planningapp.dto.response.GenericResponse;
import work.course.planning.prediction.com.planningapp.service.PlanningApiService;
import work.course.planning.prediction.com.planningapp.service.impl.PlanningApiServiceImpl;

/**
 * Created by Oleh Yanivskyy on 04.04.2017.
 */

public class ExamplesListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<ExampleDto> examples;
    private AppCompatActivity activity;

    private PlanningApiService planningApiService;
    private Long modelId;
    private String modelName;

    public ExamplesListAdapter(Context context, List<ExampleDto> examples, AppCompatActivity activity, Long modelId, String modelName) {
        this.context = context;
        this.examples = examples;
        this.activity = activity;
        this.modelId = modelId;
        this.modelName = modelName;
    }

    @Override
    public Object getChild(int examplePosition, int childPosition) {
        List<ExampleInstanceDto> exampleInstanceDtos = examples.get(examplePosition).getExampleInstances();
        return exampleInstanceDtos.get(childPosition);
    }

    @Override
    public long getChildId(int examplePosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int examplePosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        ExampleInstanceDto detailInfo = (ExampleInstanceDto) getChild(examplePosition, childPosition);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.example_values_list_row, null);
        }

        TextView sequence = (TextView) view.findViewById(R.id.title);
        sequence.setText(detailInfo.getName() + ": " + detailInfo.getValue());

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(examples.get(groupPosition).getExampleInstances() == null)
            return 0;
        return examples.get(groupPosition).getExampleInstances().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return examples.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return examples.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        final ExampleDto exampleDto = (ExampleDto) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.example_list_row, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.title);
        heading.setText(Integer.valueOf(exampleDto.getOutputLabel())/60/60 + ":" +Integer.valueOf(exampleDto.getOutputLabel())/60%60);

        TextView bottom = (TextView) view.findViewById(R.id.date);
        bottom.setText(new SimpleDateFormat("yyyy-MM-dd").format(exampleDto.getCreationDate()));

        Button removeExampleButton = (Button) view.findViewById(R.id.removeExampleButton);
        removeExampleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RemoveExampleAsyncTatk().execute(exampleDto.getId());
            }
        });

        return view;
    }

    private class RemoveExampleAsyncTatk extends AsyncTask<Long, Void, GenericResponse<Boolean>> {
        @Override
        protected GenericResponse<Boolean> doInBackground(Long... params) {
            try {
                planningApiService = new PlanningApiServiceImpl();
                return planningApiService.deleteExample(params[0]);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(GenericResponse<Boolean> removeExampleResultDto) {

            if(removeExampleResultDto == null){
                Toast.makeText(activity, "Cannot connect to the server, please, try again later", Toast.LENGTH_LONG).show();
                return;
            }

            if(removeExampleResultDto.getCode() != 200){
                Toast.makeText(activity, removeExampleResultDto.getErrorCode(), Toast.LENGTH_LONG).show();
                return;
            }

            Toast.makeText(activity, "Example was removed successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(activity, ModelManageActivity.class);
            intent.putExtra("modelInfoId", ExamplesListAdapter.this.modelId);
            intent.putExtra("modelInfoName", ExamplesListAdapter.this.modelName);
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