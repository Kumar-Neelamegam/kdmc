package kdmc_kumar.Adapters_GetterSetter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Utilities_Others.CircleImageView;

/**
 * Created by Android on 4/6/2017.
 */

public class OnlineConsultation_Detailed_Adapter extends RecyclerView.Adapter<OnlineConsultation_Detailed_Adapter.ViewHolderOnline> {

    private static OnlineConsultationItemClick onlineConsultationItemClick = null;
    ArrayList<CommonDataObjects.OnlineConsultationReport> items;
    private Context context;


    public OnlineConsultation_Detailed_Adapter(@NonNull Context context, ArrayList<CommonDataObjects.OnlineConsultationReport> items) {
        //super(context, resourceId, items);
        this.context = context;
        this.items = items;

    }

    private static void SelectedGetPatientReports_Image(String Query, ImageView imgvw) {

        try {
            String report_name = "";
            String report64 = "";
            SQLiteDatabase db = BaseConfig.GetDb();

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        BaseConfig.LoadPatientImage(c.getString(c.getColumnIndex("ReportGallery")), imgvw, 100);

                    } while (c.moveToNext());

                }
            }

            c.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public ViewHolderOnline onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderOnline(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_onlineconsultation, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOnline holder, int position) {

        CommonDataObjects.OnlineConsultationReport rowItem = items.get(position);

        holder.Report_Name.setText(rowItem.getReportName());
        BaseConfig.LoadPatientImage(rowItem.getReportPhoto(), holder.Report_Photo, 50);

        holder.rootLayout.setLayoutParams(new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlineConsultationItemClick.onClickItem(rowItem, holder.Report_Name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void onClickItem(OnlineConsultationItemClick consultationItemClick) {
        this.onlineConsultationItemClick = consultationItemClick;
    }

    public interface OnlineConsultationItemClick {
        public void onClickItem(CommonDataObjects.OnlineConsultationReport report, View view);
    }

    /*extends ArrayAdapter<CommonDataObjects.OnlineConsultationReport> {

    private final Context context;

    public OnlineConsultation_Detailed_Adapter(@NonNull Context context, int resourceId, List<CommonDataObjects.OnlineConsultationReport> items) {
        super(context, resourceId, items);
        this.context = context;

    }

    @NonNull
    public final View getView(int position, View convertView, @NonNull ViewGroup parent) {

        CommonDataObjects.OnlineConsultationReport rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        convertView.Report_Name.setText(rowItem.getReportName());
        BaseConfig.LoadPatientImage(rowItem.getReportPhoto(), holder.Report_Photo, 50);

        holder.rootLayout.setLayoutParams(new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        return convertView1;
    }*/

    static class ViewHolderOnline extends RecyclerView.ViewHolder {

        public TextView Report_Name ;
        public CircleImageView Report_Photo ;
        public LinearLayout rootLayout  ;


        public ViewHolderOnline(View itemView) {
            super(itemView);

            Report_Name = itemView.findViewById(R.id.title);
            Report_Photo = itemView.findViewById(R.id.icon);
            rootLayout = itemView.findViewById(R.id.list_root);

        }
    }


}

