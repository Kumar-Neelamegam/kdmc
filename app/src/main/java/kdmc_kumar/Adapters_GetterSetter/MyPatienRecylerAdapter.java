package kdmc_kumar.Adapters_GetterSetter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.List;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Core_Modules.PatientTestResult;
import kdmc_kumar.MyPatient_Module.MyPatientDrawer;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.customenu.ListViewType;

public class MyPatienRecylerAdapter extends RecyclerView.Adapter<MyPatienRecylerAdapter.ViewHolder> implements FastScrollRecyclerView.SectionedAdapter {

    private final List<CommonDataObjects.MyPatientGetSet> mValues;

    public MyPatienRecylerAdapter(List<CommonDataObjects.MyPatientGetSet> mValues) {
        this.mValues = mValues;
    }


    @NonNull
    @Override
    public final ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = BaseConfig.listViewType.equals(ListViewType.ListView) ? LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_patient, parent, false) :
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_patient, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        try {

            holder.txtDesc.setText(mValues.get(position).getPatient_Age());
            holder.gender.setText(mValues.get(position).getPatient_Gender());

            holder.txtTitle.setText(String.format("%s.%s", mValues.get(position).getPatient_Prefix(), mValues.get(position).getPatient_Name()));
            holder.pid.setText(mValues.get(position).getPatient_Id());

        } catch (RuntimeException e) {
            e.printStackTrace();
        }


        String PatientId1 = mValues.get(position).getPatient_Id();

        String status = mValues.get(position).IsLabReport;
        if (status.equalsIgnoreCase("1")) {

            holder.labreport.setVisibility(View.VISIBLE);
            holder.labreport_text.setTextColor(Color.RED);
        } else if (status.equalsIgnoreCase("2")) {

            holder.labreport.setVisibility(View.VISIBLE);
            holder.labreport_text.setTextColor(Color.GREEN);
        } else if ((status.equalsIgnoreCase("0"))) {
            holder.labreport.setVisibility(View.GONE);
        }

        holder.labreport.setOnClickListener(v -> {

            ((Activity) v.getContext()).finish();
            Intent in = new Intent(v.getContext(), PatientTestResult.class);
            in.putExtra("PatientId", PatientId1);
            v.getContext().startActivity(in);
        });

        try {
            BaseConfig.LoadPatientImage(mValues.get(position).getPatient_Image(), holder.imageView, 50);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (mValues.get(position).IsOnlinePatient) {
            holder.layout.setVisibility(View.VISIBLE);
            holder.closeOnlineButton.setVisibility(View.VISIBLE);

        } else if (!mValues.get(position).IsOnlinePatient) {
            holder.closeOnlineButton.setVisibility(View.GONE);
            holder.layout.setVisibility(View.GONE);
        }

        holder.closeOnlineButton.setOnClickListener(v -> ShowClosePatientDialog(v.getContext(), mValues, position));


        holder.card_view.setOnClickListener(v -> {


            String values = mValues.get(position).getPatient_Id();
            Intent lib = new Intent(v.getContext(), MyPatientDrawer.class);
            lib.putExtra(BaseConfig.BUNDLE_PATIENT_ID, values);
            v.getContext().startActivity(lib);

        });

    }

    private void ShowClosePatientDialog(final Context ctx, final List<CommonDataObjects.MyPatientGetSet> mValues, int position) {

        new CustomKDMCDialog(ctx)
                .setLayoutColor(R.color.orange_500)
                .setImage(R.drawable.ic_warning_black_24dp)
                .setTitle(ctx.getString(R.string.close_online))
                .setDescription(ctx.getString(R.string.close_online_txt))
                .setPossitiveButtonTitle(ctx.getString(R.string.yes))
                .setNegativeButtonTitle(ctx.getString(R.string.no))
                .setOnPossitiveListener(() -> {


                    String Query = "Update current_patient_list set closed = '1' where patid = '" + mValues.get(position).getPatient_Id() + '\'';

                    SQLiteDatabase db = BaseConfig.GetDb();
                    db.execSQL(Query);
                    db.close();
                    mValues.get(position).IsOnlinePatient = false;
                    notifyDataSetChanged();
                });

    }

    @Override
    public final int getItemCount() {
        return mValues.size();
    }

    @NonNull
    @Override
    public final String getSectionName(int position) {

        CommonDataObjects.MyPatientGetSet p = mValues.get(position);
        String str = p.getPatient_Name();
        return String.valueOf(str.charAt(0));
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView closeOnlineButton;
        final ImageView imageView;

        final TextView txtTitle;
        final LinearLayout layout;
        final TextView txtDesc;
        final TextView gender;
        final TextView pid;

        final TextView labreport_text;
        final LinearLayout labreport;
        final CardView card_view;


        ViewHolder(View view) {
            super(view);
            txtDesc = view.findViewById(R.id.desc);
            txtTitle = view.findViewById(R.id.title);
            imageView = view.findViewById(R.id.icon);

            layout = view.findViewById(R.id.online_patient);
            closeOnlineButton = view.findViewById(R.id.close_online_patient);
            gender = view.findViewById(R.id.gender);
            pid = view.findViewById(R.id.pid);
            labreport = view.findViewById(R.id.labreport);
            labreport_text = view.findViewById(R.id.labreport_text);
            card_view = view.findViewById(R.id.card_view);

        }

    }

}
//**********************************************************************************************

