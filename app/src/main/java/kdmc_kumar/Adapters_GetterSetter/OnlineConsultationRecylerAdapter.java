package kdmc_kumar.Adapters_GetterSetter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.OnlineConsultation_DataObjects;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Core_Modules.OnlineConsultation_Details;
import kdmc_kumar.Utilities_Others.CircleImageView;

/**
 * Created by Ponnusamy M on 4/2/2017.
 */

public class OnlineConsultationRecylerAdapter extends RecyclerView.Adapter<OnlineConsultationRecylerAdapter.MyViewHolder> {

    private final ArrayList<OnlineConsultation_DataObjects> items;
    private final Context context;

    public OnlineConsultationRecylerAdapter(ArrayList<OnlineConsultation_DataObjects> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public final MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newlistrowonlineconsultation, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final OnlineConsultation_DataObjects rowItem = items.get(position);

        holder.PatientName.setText(rowItem.getPatient_Name());
        holder.PatientId.setText(rowItem.getPatient_Id());
        holder.Age.setText(rowItem.getPatient_Age());
        holder.Gender.setText(rowItem.getPatient_Gender());
        holder.ConsultationId.setText(rowItem.getConsultationId());
        //Log.e("Photo Path: ", rowItem.getPatient_Photo());
        BaseConfig.LoadPatientImage(rowItem.getPatient_Photo(), holder.image, 50);

        holder.list_root.setOnClickListener(v -> {
                    try {


                        ((Activity) context).finish();
                        Intent lib = new Intent(context, OnlineConsultation_Details.class);
                        lib.putExtra("ServerId", rowItem.getConsultationId());
                        lib.putExtra("PatientId", rowItem.getPatient_Id());
                        lib.putExtra("MedId", rowItem.getMedId());
                        context.startActivity(lib);

                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                }
        );


    }

    @Override
    public final int getItemCount() {
        return items.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        final CircleImageView image;

        final LinearLayout list_root;

        final TextView PatientName;
        final TextView PatientId;
        final TextView Age;
        final TextView Gender;
        final TextView ConsultationId;

        MyViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.icon);
            PatientName = itemView.findViewById(R.id.txtvw_patient_name);
            PatientId = itemView.findViewById(R.id.txtvw_patient_id);
            Age = itemView.findViewById(R.id.txtvw_age);
            Gender = itemView.findViewById(R.id.txtvw_gender);
            ConsultationId = itemView.findViewById(R.id.txtvw_consultation);
            list_root = itemView.findViewById(R.id.list_root);
        }
    }
}
