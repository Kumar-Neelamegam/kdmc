package kdmc_kumar.Adapters_GetterSetter;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;


/**
 * Created by Ponnusamy M on 4/8/2017.
 */

public class TestRecylerAdapter extends RecyclerView.Adapter<TestRecylerAdapter.MyViewHolder> {

    private final ArrayList<CommonDataObjects.TestItem> testItems;

    public TestRecylerAdapter(ArrayList<CommonDataObjects.TestItem> testItems) {
        this.testItems = testItems;
    }

    @NonNull
    @Override
    public final MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_testreport_listitem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final CommonDataObjects.TestItem item = testItems.get(position);
        holder.repname.setText(item.getReport_Name());
        holder.reptype.setText(item.getReport_Type());
        holder.repdate.setText(item.getReport_Date().split("T")[0]);
        holder.reportsummary.setText(item.getReport_Summary());

        BaseConfig.Glide_LoadImageView(holder.imageView ,  item.getReport_Image());

        holder.imageView.setOnClickListener(view -> {

            View promptView = LayoutInflater.from(holder.imageView.getContext()).inflate(R.layout.imageview_zoom_dialog_report, null);
            final ImageView Photo_imgvw = promptView.findViewById(R.id.imvw_patientphoto);
            final TextView Report_Name = promptView.findViewById(R.id.tv_report_name);
            final TextView Report_Type = promptView.findViewById(R.id.tv_report_type);
            final TextView Report_Date = promptView.findViewById(R.id.tv_report_date);
            final TextView Report_Summary = promptView.findViewById(R.id.tv_report_summary);
            final Button Close = promptView.findViewById(R.id.cancel);

            Report_Name.setText(item.getReport_Name());
            Report_Type.setText(item.getReport_Type());
            try {
                Report_Date.setText(item.getReport_Date().split("T")[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Report_Summary.setText(item.getReport_Summary());

            BaseConfig.Glide_LoadImageView( Photo_imgvw, item.getReport_Image() );


            final Dialog dialog = new Dialog(holder.imageView.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(promptView);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.show();

            Close.setOnClickListener(view1 -> dialog.dismiss());


        });


    }

    @Override
    public final int getItemCount() {
        return testItems.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageView;
        final TextView repname;
        final TextView reptype;
        final TextView repdate;
        final TextView reportsummary;

        MyViewHolder(View itemView) {
            super(itemView);
            repname = itemView.findViewById(R.id.reportname);
            reptype = itemView.findViewById(R.id.report_type);
            repdate = itemView.findViewById(R.id.report_date);
            reportsummary = itemView.findViewById(R.id.report_summary);
            imageView = itemView.findViewById(R.id.imageview);

        }
    }
}
