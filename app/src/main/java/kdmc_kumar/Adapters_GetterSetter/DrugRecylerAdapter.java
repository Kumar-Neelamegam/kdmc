package kdmc_kumar.Adapters_GetterSetter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Drug_Directory.Cims_Display;

/**
 * Created by Ponnusamy M on 4/3/2017.
 */

public class DrugRecylerAdapter extends RecyclerView.Adapter<DrugRecylerAdapter.MyViewHolder> {

    private final ArrayList<CommonDataObjects.DrugItem> drugItems;

    public DrugRecylerAdapter(ArrayList<CommonDataObjects.DrugItem> drugItems) {
        this.drugItems = drugItems;
    }

    @NonNull
    @Override
    public final MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newdruglistrow, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final CommonDataObjects.DrugItem rowItem = drugItems.get(position);

        holder.BrandName.setText(rowItem.getBrandName());
        holder.CompanyName.setText(rowItem.getPharmaCompany());
        holder.Dosage.setText(rowItem.getDosage());
        holder.systemView.setText(rowItem.getSystem());
        holder.card_view.setOnClickListener(v -> {
            BaseConfig.Druglistselindex = rowItem.getServerid();
            Intent lib = new Intent(v.getContext(), Cims_Display.class);
            v.getContext().startActivity(lib);
        });

    }

    @Override
    public final int getItemCount() {
        return drugItems.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView = null;

        final TextView systemView;
        final TextView CompanyName;
        final TextView BrandName;
        final TextView Dosage;
        final CardView card_view;

        MyViewHolder(View itemView) {
            super(itemView);

            systemView = itemView.findViewById(R.id.system);
            CompanyName = itemView.findViewById(R.id.company_name);
            BrandName = itemView.findViewById(R.id.brand_name);
            Dosage = itemView.findViewById(R.id.dosage);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }
}
