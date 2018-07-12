package kdmc_kumar.Adapters_GetterSetter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.MyPatient_Module.OperationListSummary;

/**
 * Created by Ponnusamy M on 4/6/2017.
 */

public class PatientSummaryRecyclerAdapter extends RecyclerView.Adapter<PatientSummaryRecyclerAdapter.MyViewHolder> {

    private final ArrayList<CommonDataObjects.OperationItem> operationItems;
    private final String BUNDLE_PATIENT_ID;

    public PatientSummaryRecyclerAdapter(ArrayList<CommonDataObjects.OperationItem> operationItems, String PATIENT_ID) {
        this.operationItems = operationItems;
        BUNDLE_PATIENT_ID = PATIENT_ID;
    }

    @NonNull
    @Override
    public final MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_summary_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        CommonDataObjects.OperationItem item = operationItems.get(position);

        holder.sno.setText(item.getSerialNo());
        holder.operation_name.setText(item.getOperation_name());
        String operationdate[] = item.getOperation_date().split("T");
        String fromdate[] = item.getFrom_time().split("T");
        String todate[] = item.getTo_Time().split("T");
        holder.opreation_date.setText(operationdate[0]);
        holder.from_time.setText(fromdate[1]);
        holder.to_time.setText(todate[1]);
        holder.doc_name.setText(item.getReference_doc());


        holder.tableLayout.setOnClickListener(v -> {

            Intent nxtintent = new Intent(holder.doc_name.getContext(), OperationListSummary.class);
            nxtintent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, BUNDLE_PATIENT_ID);
            holder.from_time.getContext().startActivity(nxtintent);


        });
    }

    @Override
    public final int getItemCount() {
        return operationItems.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView sno;
        final TextView operation_name;
        final TextView opreation_date;
        final TextView from_time;
        final TextView to_time;
        final TextView doc_name;
        final LinearLayout linearLayout;
        final TableLayout tableLayout;

        MyViewHolder(View itemView) {
            super(itemView);
            sno = itemView.findViewById(R.id.count_1);
            operation_name = itemView.findViewById(R.id.count_2);
            opreation_date = itemView.findViewById(R.id.count_3);
            from_time = itemView.findViewById(R.id.count_4);
            to_time = itemView.findViewById(R.id.count_5);
            doc_name = itemView.findViewById(R.id.count_6);

            linearLayout = itemView.findViewById(R.id.linearlayout);

            tableLayout = itemView.findViewById(R.id.tablelayout);

        }
    }
}
