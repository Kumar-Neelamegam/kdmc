package kdmc_kumar.Adapters_GetterSetter.DashboardAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.CaseNotes_Modules.CaseNotes;
import kdmc_kumar.Core_Modules.ClinicalInformation;
import kdmc_kumar.Core_Modules.CloudData;
import kdmc_kumar.Core_Modules.DoctorReferral;
import kdmc_kumar.Drug_Directory.Cims;
import kdmc_kumar.Core_Modules.Investigations;
import kdmc_kumar.Core_Modules.MedicinePrescription;
import kdmc_kumar.Core_Modules.MyPatient;
import kdmc_kumar.Core_Modules.OnlineConsultation;
import kdmc_kumar.Core_Modules.Patient_Registration;
import kdmc_kumar.Inpatient_Module.Inpatient_List;
import kdmc_kumar.Masters_Modules.Masters_New;
import kdmc_kumar.Utilities_Others.CustomIntent;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.Explode;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.Fade;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.Transition;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.TransitionManager;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.TransitionSet;

/**
 * Created by Ponnusamy on 9/17/2017.
 * KDMCMobyDoctor
 */

public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.MyView> {


    public static final String VIEW_NAME_HEADER_TITLE = "0";
    /*private static final int[] fabbgstr = {
            R.color.ic_CaseNotes,
            R.color.ic_ClinicalInformation,
            R.color.ic_Investigation,
            R.color.ic_Prescription,
            R.color.ic_Referrals,
            R.color.ic_Mypatient,
            R.color.ic_DrugDirectory,
            R.color.ic_OnlineConsultation,
            R.color.ic_Inpatient,
            R.color.ic_Masters,
            R.color.ic_Cloud,
    };*/

    private static final int[] fabbgstr = {
            R.color.colorPrimary,
            R.color.ic_ClinicalInformation,
            R.color.ic_Investigation,
            R.color.ic_Prescription,
            R.color.ic_Referrals,
            R.color.ic_Mypatient,
            R.color.ic_DrugDirectory,
            R.color.ic_OnlineConsultation,
            R.color.ic_Inpatient,
            R.color.ic_Masters,
            R.color.ic_Cloud,
    };

    private final String[] web;

    private final int[] Imageid;
    private final Context mContext;

    public DashBoardAdapter(Context mContext, String[] web, int[] imageid) {
        this.mContext = mContext;
        this.web = web;
        Imageid = imageid;
    }

    @NonNull
    @Override
    public final MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.task_items, parent, false);
        return new MyView(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull MyView holder, int position) {

        holder.textView.setText(web[position]);

        holder.fab.setImageDrawable(mContext.getResources().getDrawable(Imageid[position]));

        holder.fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(mContext.getResources().getString(fabbgstr[0]))));

        holder.fab.setOnClickListener(v ->   letsExplodeIt(holder.reippedlayout,holder,position,holder.textView));

        holder.textView.setOnClickListener(v -> letsExplodeIt(holder.reippedlayout,holder,position,holder.textView));

        holder.reippedlayout.setOnClickListener(v -> letsExplodeIt(holder.reippedlayout,holder,position,holder.textView));

    }



    @Override
    public final int getItemCount() {
        return Imageid.length;
    }

    public void onClickItem(Context context, int position, TextView textView) {

        Activity activity = (Activity) context;

        Intent intent=null;


        switch (position) {

            case 0:
                intent = new Intent(context, Patient_Registration.class);
                break;


            case 1:
                intent = new Intent(context, CaseNotes.class);
                break;

            case 2:
                intent = new Intent(context, ClinicalInformation.class);
                break;

            case 3:
                intent = new Intent(context, Investigations.class);
                break;

            case 4:
                intent = new Intent(context, MedicinePrescription.class);
                break;

            case 5:
                intent = new Intent(context, DoctorReferral.class);
                break;
            case 6:
                intent = new Intent(context, MyPatient.class);
                break;

            case 7:
                intent = new Intent(context, Cims.class);
                break;

            case 8:
                intent = new Intent(context, OnlineConsultation.class);
                break;

            case 9:
                intent = new Intent(context, Inpatient_List.class);
                break;

            case 10:
                intent = new Intent(context, Masters_New.class);
                break;


            case 11:
                intent = new Intent(context, CloudData.class);
                break;

        }


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, textView, DashBoardAdapter.VIEW_NAME_HEADER_TITLE);
            ActivityCompat.startActivity(context, intent, activityOptions.toBundle());

        } else {
            context.startActivity(intent);
            CustomIntent.customType(context, 1);
            activity.finish();

        }


    }



    private void letsExplodeIt(View clickedView ,MyView holder,int position, TextView txtvw) {
        // save rect of view in screen coordinated
        final Rect viewRect = new Rect();
        clickedView.getGlobalVisibleRect(viewRect);

        TransitionSet set = new TransitionSet()
                .addTransition(new Explode().setEpicenterCallback(new Transition.EpicenterCallback() {
                    @Override
                    public Rect onGetEpicenter(Transition transition) {
                        return viewRect;
                    }
                }).excludeTarget(clickedView, true))
                .addTransition(new Fade().addTarget(clickedView))
                .addListener(new Transition.TransitionListenerAdapter() {
                    @Override
                    public void onTransitionEnd(Transition transition) {

                        //   ((Activity)clickedView.getContext()).onBackPressed();
                        onClickItem(clickedView.getContext(), position, txtvw);
                        transition.removeListener(this);
                    }
                });

        TransitionManager.beginDelayedTransition(TaskSelection_Final.grid, set);

        // remove all views from Recycler View
        TaskSelection_Final.grid.setAdapter(null);
    }


    public static class MyView extends RecyclerView.ViewHolder {
        final TextView textView;
        //public ImageView imageView;
        final android.support.design.widget.FloatingActionButton fab;

        final LinearLayout reippedlayout;


        MyView(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_species);
            fab = itemView.findViewById(R.id.img_thumbnail);

            reippedlayout = itemView.findViewById(R.id.grid_items);
        }
    }
}
