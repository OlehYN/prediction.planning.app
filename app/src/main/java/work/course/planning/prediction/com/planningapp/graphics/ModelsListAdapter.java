package work.course.planning.prediction.com.planningapp.graphics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import work.course.planning.prediction.com.planningapp.R;
import work.course.planning.prediction.com.planningapp.dto.response.ModelInfoDto;

/**
 * Created by Oleh Yanivskyy on 04.04.2017.
 */

public class ModelsListAdapter extends BaseAdapter {
    private List<ModelInfoDto> listData;
    private LayoutInflater layoutInflater;

    public ModelsListAdapter(Context aContext, List<ModelInfoDto> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    public  List<ModelInfoDto> getData(){
        return listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
            holder = new ViewHolder();
            holder.headlineView = (TextView) convertView.findViewById(R.id.title);
            holder.quantityView = (TextView) convertView.findViewById(R.id.quantity);
            holder.dateView = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.headlineView.setText(listData.get(position).getName());
        holder.quantityView.setText("Examples: " + listData.get(position).getExamplesQuantity());
        holder.dateView.setText(new SimpleDateFormat("yyyy-MM-dd").format(listData.get(position).getCreationDate()));
        return convertView;
    }

    static class ViewHolder {
        TextView headlineView;
        TextView quantityView;
        TextView dateView;
    }
}
