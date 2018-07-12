package kdmc_kumar.Adapters_GetterSetter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;


/**
 * Created by Ponnusamy M on 4/3/2017.
 */

public class FavMedicineRecylerAdapter extends RecyclerView.Adapter<FavMedicineRecylerAdapter.MyViewHolder> {

    private final ArrayList<CommonDataObjects.Fav_MedicineList> favmedicineItems;
    //**********************************************************************************************

    public FavMedicineRecylerAdapter(ArrayList<CommonDataObjects.Fav_MedicineList> drugItems) {

        this.favmedicineItems = drugItems;
    }
    //**********************************************************************************************

    @NonNull
    @Override
    public final MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_premed_adap, parent, false);

        return new MyViewHolder(view);
    }
    //**********************************************************************************************

    @Override
    public final void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final CommonDataObjects.Fav_MedicineList rowItem = favmedicineItems.get(position);

        holder.Medicine.setText(rowItem.getMedicine());

        holder.Medicine.setOnClickListener(view -> {

            new CustomKDMCDialog(view.getContext())
                    .setLayoutColor(R.color.red_500)
                    .setImage(R.drawable.ic_delete_forever_black_24dp)
                    .setTitle(view.getContext().getString(R.string.information))
                    .setDescription("Are you sure want to delete\nthis Prefered Medicine?")
                    .setPossitiveButtonTitle("Yes, delete it")
                    .setNegativeButtonTitle("No")
                    .setOnPossitiveListener(() -> {
                        favmedicineItems.remove(position);
                        notifyDataSetChanged();

                    });

        });
    }
    //**********************************************************************************************

    @Override
    public final int getItemCount() {
        return favmedicineItems.size();
    }

    //**********************************************************************************************

    static class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView Medicine;
        final LinearLayout ParentView;

        MyViewHolder(View itemView) {
            super(itemView);

            Medicine = itemView.findViewById(R.id.desc);

            ParentView = itemView.findViewById(R.id.list_root);


        }
    }
    //**********************************************************************************************

}
