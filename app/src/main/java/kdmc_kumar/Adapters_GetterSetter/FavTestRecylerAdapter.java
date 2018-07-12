package kdmc_kumar.Adapters_GetterSetter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;


/**
 * Created by Ponnusamy M on 4/3/2017.
 */

public class FavTestRecylerAdapter extends RecyclerView.Adapter<FavTestRecylerAdapter.MyViewHolder> {

    private final ArrayList<CommonDataObjects.Fav_TestList> favtestItems;
    //**********************************************************************************************

    public FavTestRecylerAdapter(ArrayList<CommonDataObjects.Fav_TestList> drugItems) {

        this.favtestItems = drugItems;
    }
    //**********************************************************************************************

    @NonNull
    @Override
    public final MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pretest_adap, parent, false);

        return new MyViewHolder(view);
    }
    //**********************************************************************************************

    @Override
    public final void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final CommonDataObjects.Fav_TestList rowItem = favtestItems.get(position);

        holder.Testname.setText(rowItem.getTestName());
        holder.Subtestname.setText(rowItem.getSubTestName());

        holder.ParentView.setOnClickListener(view -> {

        });


    }
    //**********************************************************************************************

    @Override
    public final int getItemCount() {
        return favtestItems.size();
    }

    //**********************************************************************************************

    static class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView Testname;
        final TextView Subtestname;
        final CardView ParentView;

        MyViewHolder(View itemView) {
            super(itemView);

            Testname = itemView.findViewById(R.id.testname_tv);
            Subtestname = itemView.findViewById(R.id.subtestname_tv);

            ParentView = itemView.findViewById(R.id.card_view);


        }
    }
    //**********************************************************************************************

}
