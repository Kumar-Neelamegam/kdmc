package kdmc_kumar.Adapters_GetterSetter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Utilities_Others.CircleImageView;

/**
 * Created by Android on 4/6/2017.
 */

public class OnlineConsultation_Detailed_Adapter extends ArrayAdapter<CommonDataObjects.OnlineConsultationReport> {

    private final Context context;

    public OnlineConsultation_Detailed_Adapter(@NonNull Context context, int resourceId, List<CommonDataObjects.OnlineConsultationReport> items) {
        super(context, resourceId, items);
        this.context = context;

    }

    @NonNull
    public final View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View convertView1 = convertView;
        ViewHolder holder = null;
        CommonDataObjects.OnlineConsultationReport rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView1 == null) {
            convertView1 = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.Report_Name = convertView1.findViewById(R.id.title);
            holder.Report_Photo = convertView1.findViewById(R.id.icon);
            holder.rootLayout = convertView1.findViewById(R.id.list_root);
            convertView1.setTag(holder);

        } else

            holder = (ViewHolder) convertView1.getTag();


        holder.Report_Name.setText(rowItem.getReportName());
        BaseConfig.LoadPatientImage(rowItem.getReportPhoto(), holder.Report_Photo, 50);

        holder.rootLayout.setLayoutParams(new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        return convertView1;
    }

    private static class ViewHolder {

        TextView Report_Name = null;
        CircleImageView Report_Photo = null;
        LinearLayout rootLayout = null;


        private ViewHolder() {
        }
    }


}

