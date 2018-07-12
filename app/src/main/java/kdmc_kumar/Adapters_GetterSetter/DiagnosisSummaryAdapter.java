package kdmc_kumar.Adapters_GetterSetter;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;


/**
 * Created by Ponnusamy M on 4/6/2017.
 */

public class DiagnosisSummaryAdapter extends RecyclerView.Adapter<DiagnosisSummaryAdapter.MyViewHolder> {

    private final ArrayList<CommonDataObjects.DiagnosisItem> diagnosisItems;

    public DiagnosisSummaryAdapter(ArrayList<CommonDataObjects.DiagnosisItem> diagnosisItems) {
        this.diagnosisItems = diagnosisItems;
    }

    @NonNull
    @Override
    public final MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_diagnosis_listrow, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final CommonDataObjects.DiagnosisItem item = diagnosisItems.get(position);


        holder.sno.setText(item.getS_No());
        holder.doctor.setText(item.getDoctor());
        holder.operaitonotes.setText(item.getOPerationNotes());
        holder.position.setText(item.getPosition());
        holder.operationprocedure.setText(item.getOperationProcedure());
        holder.postoperativediagnosis.setText(item.getPostOPerativeDiagnosis());
        holder.postoperativeinstruction.setText(item.getPostOpertiveinstruction());
        holder.closure.setText(item.getClosure());
        holder.preoperativediagnosis.setText(item.getPreOperativeDiagnosis());

        BaseConfig.Glide_LoadImageView(holder.remarks, item.getRemark_Image());

        holder.remarks.setOnClickListener(v -> {

            View view = LayoutInflater.from(holder.remarks.getContext()).inflate(R.layout.pop_view_image, null, false);

            ImageView imageView = view.findViewById(R.id.imageview);
            TextView doctorname = view.findViewById(R.id.doctorname);

            doctorname.setText(String.format("%s %s", view.getContext().getString(R.string.report), item.getDoctor()));

            BaseConfig.Glide_LoadImageView(imageView, item.getRemark_Image());


            AlertDialog.Builder builder = new AlertDialog.Builder(imageView.getContext());

            builder.setView(view);
            builder.setCancelable(false).setPositiveButton(view.getContext().getString(R.string.ok), (dialog, which) -> {

            });

            AlertDialog alertDialog = builder.create();

            alertDialog.show();
        });
    }

    @Override
    public final int getItemCount() {
        return diagnosisItems.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView sno;
        final TextView doctor;
        final TextView operaitonotes;
        final TextView position;
        final TextView operationprocedure;
        final TextView postoperativediagnosis;
        final TextView postoperativeinstruction;
        final TextView closure;
        final TextView preoperativediagnosis;
        final ImageView remarks;


        MyViewHolder(View itemView) {
            super(itemView);
            sno = itemView.findViewById(R.id.text_1);
            doctor = itemView.findViewById(R.id.text_2);
            operaitonotes = itemView.findViewById(R.id.text_3);
            position = itemView.findViewById(R.id.text_4);
            operationprocedure = itemView.findViewById(R.id.text_5);
            postoperativediagnosis = itemView.findViewById(R.id.text_6);
            postoperativeinstruction = itemView.findViewById(R.id.text_7);
            closure = itemView.findViewById(R.id.text_8);
            preoperativediagnosis = itemView.findViewById(R.id.text_9);

            remarks = itemView.findViewById(R.id.text_10);

        }
    }

}
