package kdmc_kumar.Adapters_GetterSetter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.*;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Inpatient_Module.AddDietActivity;


public class DietAdapter extends RecyclerView.Adapter<DietAdapter.MyViewHolder> {

    public final List<Addgetset> moviesList;

    public DietAdapter(List<Addgetset> moviesList, Context ctx) {
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public final MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_diet_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public final void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Addgetset movie = moviesList.get(position);
        holder.title.setText(movie.getDiet_name());
        holder.genre.setText(movie.getFood_name());
        holder.year.setText(movie.getCalc());

        Integer valuess=position+1;
        holder.serialno.setText(String.valueOf(valuess));




        holder.rel_layout.setOnClickListener(view -> {
            final AlertDialog.Builder adb = new AlertDialog.Builder(view.getContext());


            adb.setTitle(R.string.delte);

            adb.setIcon(android.R.drawable.ic_dialog_alert);


            adb.setPositiveButton(R.string.ok, (dialog, which) -> {

                SQLiteDatabase db = BaseConfig.GetDb();//view.getContext());


                String Query = "UPDATE diet_entry SET IsDelete = 1 WHERE  Id='" + moviesList.get(position).getId() + "' ";

                db.execSQL(Query);
                //Log.e("delete Query", Query);


                //Update Total Calories Label

                Integer alreadyValue=Integer.parseInt(AddDietActivity.calories_total.getText().toString());
                Integer removeValue=Integer.parseInt(moviesList.get(position).getCalc());
                Integer updateValue=alreadyValue-removeValue;
                AddDietActivity.calories_total.setText(String.valueOf(updateValue));

                db.close();
                moviesList.remove(position);


                notifyDataSetChanged();

            });


            adb.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());
            adb.show();
        });
    }

    @Override
    public final int getItemCount() {
        return moviesList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView year;
        final TextView genre;
        final TextView serialno;
        final CardView card_view;
        final LinearLayout rel_layout;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            genre = view.findViewById(R.id.genre);
            year = view.findViewById(R.id.year);
            serialno = view.findViewById(R.id.serialno);
            card_view = view.findViewById(R.id.card_view);
            rel_layout = view.findViewById(R.id.rel_layout);
        }
    }


}