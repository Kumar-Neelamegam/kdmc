package kdmc_kumar.Adapters_GetterSetter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.RowItem;
import kdmc_kumar.Core_Modules.BaseConfig;


/**
 * Created by Ponnusamy M on 4/3/2017.
 */

public class ReportGalleryAdapter extends RecyclerView.Adapter<ReportGalleryAdapter.MyViewHolder> {


    private final ArrayList<RowItem> rowItemr;
    private String Image_Path = null;
    private final String BUNDLE_PATIENTID;

    public ReportGalleryAdapter(ArrayList<RowItem> rowItem, String PatientId) {
        this.rowItemr = rowItem;
        BUNDLE_PATIENTID = PatientId;
    }

    @NonNull
    @Override
    public final MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newreportlistrow, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RowItem rowItem = null;
        try {
            rowItem = rowItemr.get(position);

            holder.txtDesc.setText(rowItem.getDesc());
            holder.txtTitle.setText(rowItem.getTitle());

            //Log.e("Report File Path:", rowItem.getFile_Path());

            if (rowItem.getFile_Path() != null) {

                BaseConfig.Glide_LoadImageView(holder.imageView, BaseConfig.AppDBFolderName + "/Uploads/" + rowItem.getFile_Path());

            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


        final RowItem finalRowItem = rowItem;
        final RowItem finalRowItem1 = rowItem;
        holder.card_view.setOnClickListener(v -> {
            try {


                BaseConfig.reportname = finalRowItem.getTitle();

                //Image_Path= finalRowItem1.getFile_Path();
                Image_Path = BaseConfig.AppDatabaseName + "/Uploads/" + finalRowItem1.getFile_Path();
                LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());
                View promptView = layoutInflater.inflate(R.layout.reportimageview_zoom_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setView(promptView);

                final ImageView reportphoto = promptView.findViewById(R.id.imageZoom);

                final TextView nameagegen1 = promptView.findViewById(R.id.nameagegen);

                final TextView patient_name = promptView.findViewById(R.id.amt);

                final ZoomControls zoom = promptView.findViewById(R.id.zoomControls1);


                //SelectedGetPatientReports(2, "select ImageUrl,ReportPhoto,ReportType from ReportGallery where ReportType='" + BaseConfig.reportname.toString().trim() + "' and Patid='" + BUNDLE_PATIENTID + "';", v.getContext());

                nameagegen1.setText(String.format("Report Name: %s", BaseConfig.reportname));

                BaseConfig.Glide_LoadImageView( reportphoto,  Image_Path);



                String Patient_Name = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + BUNDLE_PATIENTID + '\'');


                patient_name.setText(String.format("Patient Name: %s-%s", Patient_Name, BUNDLE_PATIENTID));


                zoom.setOnZoomInClickListener(v12 -> {
                    // TODO Auto-generated method stub

                    int w = reportphoto.getWidth();
                    int h = reportphoto.getHeight();

                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(w + 10, h + 10);
                    params.addRule(RelativeLayout.CENTER_IN_PARENT);

                    reportphoto.setLayoutParams(params);
                });

                zoom.setOnZoomOutClickListener(v1 -> {
                    // TODO Auto-generated method stub

                    int w = reportphoto.getWidth();
                    int h = reportphoto.getHeight();

                    RelativeLayout.LayoutParams params =
                            new RelativeLayout.LayoutParams(w - 10, h - 10);
                    params.addRule(RelativeLayout.CENTER_IN_PARENT);

                    reportphoto.setLayoutParams(params);
                });


                alertDialogBuilder.setCancelable(false).setPositiveButton(promptView.getContext().getString(R.string.ok),
                        (dialog, id) -> {


                        });


                AlertDialog alertD = alertDialogBuilder.create();
                alertD.show();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public final int getItemCount() {
        return rowItemr.size();
    }

    private void SelectedGetPatientReports(int id, String Query, Context context) {
        // TODO Auto-generated method stub
        String report_name = "";
        String report64 = "";
        SQLiteDatabase db = BaseConfig.GetDb();//context);


        Cursor c = db.rawQuery(Query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    Image_Path = c.getString(c.getColumnIndex("ImageUrl"));


                } while (c.moveToNext());

            }
        }

        c.close();
        db.close();

    }

    public static final Bitmap Convert64ToImage(String base64) {
        // BaseConfig.Zoomimg=base64;

        byte[] decodedString = Base64.decode(base64.trim(), Base64.DEFAULT);
        // bimatp factory
        BitmapFactory.Options options = new BitmapFactory.Options();

        // downsizing image as it throws OutOfMemory Exception for larger
        // images
        options.inSampleSize = 1;

        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final LinearLayout rootLayout;

        final TextView txtTitle;
        final TextView txtDesc;
        final CardView card_view;

        MyViewHolder(View itemView) {
            super(itemView);

            txtDesc = itemView.findViewById(R.id.desc);
            txtTitle = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.img_icon);
            rootLayout = itemView.findViewById(R.id.list_root);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }


}
