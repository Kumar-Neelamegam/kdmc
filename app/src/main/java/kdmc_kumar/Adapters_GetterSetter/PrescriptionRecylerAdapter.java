package kdmc_kumar.Adapters_GetterSetter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;


/**
 * Created by Ponnusamy M on 4/7/2017.
 */

public class PrescriptionRecylerAdapter extends RecyclerView.Adapter<PrescriptionRecylerAdapter.MyViewHolder> {

    private final ArrayList<CommonDataObjects.PrescriptionItem> prescriptionItems;

    public PrescriptionRecylerAdapter(ArrayList<CommonDataObjects.PrescriptionItem> prescriptionItems) {
        this.prescriptionItems = prescriptionItems;
    }

    @NonNull
    @Override
    public final MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_prescription_recyler_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CommonDataObjects.PrescriptionItem item = prescriptionItems.get(position);

        holder.sno.setText(item.getSno());
        holder.medicinename.setText(item.getMedicine_Name());
        holder.interval.setText(item.getInterval());
        holder.frequency.setText(item.getFrequency());
        holder.Duration.setText(item.getDuration());
        holder.doctorname.setText(item.getDoctorname());
    }

    @Override
    public final int getItemCount() {
        return prescriptionItems.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView sno;
        final TextView medicinename;
        final TextView interval;
        final TextView frequency;
        final TextView Duration;
        final TextView doctorname;

        MyViewHolder(View itemView) {
            super(itemView);
            sno = itemView.findViewById(R.id.text1);
            medicinename = itemView.findViewById(R.id.text2);
            interval = itemView.findViewById(R.id.text3);
            frequency = itemView.findViewById(R.id.text4);
            Duration = itemView.findViewById(R.id.text5);
            doctorname = itemView.findViewById(R.id.text6);
        }
    }
}
