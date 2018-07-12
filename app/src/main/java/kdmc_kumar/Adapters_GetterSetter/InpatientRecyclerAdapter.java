package kdmc_kumar.Adapters_GetterSetter;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Inpatient_Module.Inpatient_Detailed_View;
import kdmc_kumar.Utilities_Others.CircleImageView;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.customenu.ListViewType;

/**
 * Created by Ponnusamy M on 4/3/2017.
 */

public class InpatientRecyclerAdapter extends RecyclerView.Adapter<InpatientRecyclerAdapter.MyViewHolder> implements FastScrollRecyclerView.SectionedAdapter {

    private final ArrayList<CommonDataObjects.MyPatientGetSet> rowItems;

    public InpatientRecyclerAdapter(ArrayList<CommonDataObjects.MyPatientGetSet> rowItems) {
        this.rowItems = rowItems;
    }

    private static final void showDichargeRequestDialog(final Context context, final String pat_id, final String patientid) {

        View view = LayoutInflater.from(context).inflate(R.layout.inpatient_dicharge_request, null);
        final TextView patientAuto = view.findViewById(R.id.patient_detail_values);
        final EditText remarksEdt = view.findViewById(R.id.request_remarks_edt);
        Button submitBtn = view.findViewById(R.id.submit);
        Button cancelBtn = view.findViewById(R.id.cancel);

        patientAuto.setText(String.format("%s-%s", pat_id, patientid));

        SimpleDateFormat dateformt = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.ENGLISH);
        Date date = new Date();

        final String dttm = dateformt.format(date);

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        submitBtn.setOnClickListener(v -> {


            ContentValues values = new ContentValues();
            values.put("patid", patientid.trim());
            values.put("request_remarks", remarksEdt.getText().toString());
            values.put("IsActive", "1");
            values.put("IsUpdate", "0");
            values.put("docid", BaseConfig.doctorId);
            values.put("ActDate", dttm);

            SQLiteDatabase Db = BaseConfig.GetDb();//v.getContext());
            Db.insert("inpatient_discharge_request", null, values);
            dialog.dismiss();


            new CustomKDMCDialog(v.getContext())
                    .setLayoutColor(R.color.green_500)
                    .setImage(R.drawable.ic_success_done)
                    .setTitle(v.getContext().getString(R.string.information))
                    .setNegativeButtonVisible(View.GONE)
                    .setDescription(context.getString(R.string.requeste_sent_discharge))
                    .setPossitiveButtonTitle(v.getContext().getString(R.string.ok));


        });
        cancelBtn.setOnClickListener(v -> dialog.dismiss());

        //dialog.getWindow().setLayout(350, 500);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();


    }

    @NonNull
    @Override
    public final MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = BaseConfig.listViewType.equals(ListViewType.ListView) ? LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_inpatient, parent, false) :
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_inpatient, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final CommonDataObjects.MyPatientGetSet rowItem = rowItems.get(position);


        try {
            holder.txtDesc.setText(rowItems.get(position).getPatient_Age());
            holder.gender.setText(rowItems.get(position).getPatient_Gender());

            holder.txtTitle.setText(rowItems.get(position).getPatient_Name());
            holder.pid.setText(rowItems.get(position).getPatient_Id());

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        BaseConfig.LoadPatientImage(rowItems.get(position).getPatient_Image(), holder.imageView, 50);


        holder.cardView.setOnClickListener(v -> {
            try {

                Bundle b=new Bundle();
                b.putString(BaseConfig.BUNDLE_PATIENT_ID, rowItem.getPatient_Id());
                BaseConfig.globalStartIntent2(v.getContext(), Inpatient_Detailed_View.class, b);

            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        });

        holder.closeOnlineButton.setOnClickListener(v -> showDichargeRequestDialog(holder.cardView.getContext(), rowItem.getPatient_Name(), rowItem.getPatient_Id()));
    }

    @Override
    public final int getItemCount() {
        return rowItems.size();
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        CommonDataObjects.MyPatientGetSet p = rowItems.get(position);
        String str = p.getPatient_Name();
        return String.valueOf(str.charAt(0));
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        final Button closeOnlineButton;
        final CircleImageView imageView;
        final TextView txtTitle;
        final LinearLayout layout;
        final TextView txtDesc;
        final TextView gender;
        final TextView pid;

        final CardView cardView;

        MyViewHolder(View itemView) {
            super(itemView);
            txtDesc = itemView.findViewById(R.id.desc);
            txtTitle = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.icon);

            layout = itemView.findViewById(R.id.online_patient);
            closeOnlineButton = itemView.findViewById(R.id.close_inpatient_patient);
            gender = itemView.findViewById(R.id.gender);
            pid = itemView.findViewById(R.id.pid);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }

}
