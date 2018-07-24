package kdmc_kumar.Adapters_GetterSetter;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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
import kdmc_kumar.Masters_Modules.templates_addnew;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;

public class TemplateRecylerAdapter extends RecyclerView.Adapter<TemplateRecylerAdapter.MyViewHolder> {
    ArrayList<CommonDataObjects.TemplateGetSet> templateGetSets = new ArrayList<>();

    public TemplateRecylerAdapter(ArrayList<CommonDataObjects.TemplateGetSet> templateGetSets) {
        this.templateGetSets = templateGetSets;
    }

    @NonNull
    @Override
    public final MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_list_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final CommonDataObjects.TemplateGetSet item = templateGetSets.get(position);

        holder.sno.setText(item.getSno());
        holder.sno.setVisibility(View.INVISIBLE);
        //holder.template_name.setText("" + item.getName().split(":")[1]);
        holder.template_name.setText(item.getName());

        holder.card_view.setOnClickListener(v -> {
            BaseConfig.selectedtemplate = item.getName();
            BaseConfig.temp_flag = "false";
            //((Activity)holder.card_view.getContext()).finish();
            Intent lib = new Intent(v.getContext(), templates_addnew.class);
            ((Activity)holder.card_view.getContext()).startActivity(lib);
        });



        holder.Delete.setOnClickListener(view -> {


            // Toast.makeText(templates_list.this, "Delete clicked" + item.getSno(), Toast.LENGTH_SHORT).show();


            new CustomKDMCDialog(view.getContext())
                    .setLayoutColor(R.color.orange_500)
                    .setImage(R.drawable.ic_warning_black_24dp)
                    .setTitle(view.getContext().getString(R.string.info))
                    .setDescription("Are you sure want to delete?")
                    .setPossitiveButtonTitle(view.getContext().getString(R.string.yes))
                    .setNegativeButtonTitle(view.getContext().getString(R.string.no))
                    .setOnPossitiveListener(() -> {

                        try {
                            try {
                                SQLiteDatabase db = BaseConfig.GetDb();
                                db.execSQL("delete from TemplateDtls where TemplateName='" + item.getName() + '\'');
                                db.close();

                                templateGetSets.remove(position);
                                //notifyItemRemoved(position);
                                notifyDataSetChanged();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                            //  SelectedGetPatientDetails();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        new CustomKDMCDialog(view.getContext()).setNegativeButtonVisible(View.GONE)
                                .setImage(R.drawable.ic_success_done)
                                .setTitle("Deleted").setNegativeButtonVisible(View.GONE)
                                .setPossitiveButtonTitle("OK");


                    });


        });


    }

    @Override
    public final int getItemCount() {
        return templateGetSets.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView sno;
        final TextView template_name;
        final CardView card_view;
        final ImageView Delete;

        MyViewHolder(View itemView) {
            super(itemView);
            sno = itemView.findViewById(R.id.serial_no);
            template_name = itemView.findViewById(R.id.template_name);
            card_view = itemView.findViewById(R.id.card_view);
            Delete = itemView.findViewById(R.id.ic_delete);
        }
    }
}
