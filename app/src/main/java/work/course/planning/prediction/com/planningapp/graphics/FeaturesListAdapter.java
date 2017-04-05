package work.course.planning.prediction.com.planningapp.graphics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import work.course.planning.prediction.com.planningapp.R;
import work.course.planning.prediction.com.planningapp.dto.info.FeatureDto;
import work.course.planning.prediction.com.planningapp.dto.info.FeatureListValueDto;
import work.course.planning.prediction.com.planningapp.dto.info.ModelInfoDto;

/**
 * Created by Oleh Yanivskyy on 04.04.2017.
 */

public class FeaturesListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<FeatureDto> features;

    public FeaturesListAdapter(Context context, List<FeatureDto> features) {
        this.context = context;
        this.features = features;
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

        FeatureDto featureDto = (FeatureDto) getGroup(groupPosition);
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

        return view;
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