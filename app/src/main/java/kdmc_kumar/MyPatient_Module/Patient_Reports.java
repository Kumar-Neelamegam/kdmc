package kdmc_kumar.MyPatient_Module;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.RowItem;
import kdmc_kumar.Adapters_GetterSetter.ReportGalleryAdapter;
import kdmc_kumar.Core_Modules.BaseConfig;

public class Patient_Reports extends Fragment {

    public static Bitmap reportimg = null;
    ArrayList<HashMap<String, String>> titles_list = null;
    ArrayAdapter<RowItem> adapter = null;
    private RecyclerView listView = null;
    private ArrayList<RowItem> rowItems = null;
    Bitmap reportBitmapView = null;

    private ReportGalleryAdapter reportGalleryAdapter = null;
    private String BUNDLE_PATIENT_ID = null;
    private ImageView no_data = null;
    private GridLayoutManager lLayout = null;

    public Patient_Reports() {
    }

    ///////////////////////////////////////////////////////////////////////////////
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mypatient_reportgalleryfragment, container, false);

        Bundle args = getArguments();
        BUNDLE_PATIENT_ID = args.getString(BaseConfig.BUNDLE_PATIENT_ID);

        // initialize
        rowItems = new ArrayList<>();
        listView = rootView.findViewById(R.id.listView1);

        no_data = rootView.findViewById(R.id.img_nodata);


        //listView.setHasFixedSize(true);
        //mLayoutManager = new LinearLayoutManager(getActivity());
        //listView.setLayoutManager(mLayoutManager);


        lLayout = new GridLayoutManager(getActivity(), 2);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(lLayout);
        listView.setNestedScrollingEnabled(false);

        assert listView != null;


        try {
            SelectedGetPatientReports("select * from ReportGallery where Patid='" + BUNDLE_PATIENT_ID + "';");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return rootView;
    }


    private void SelectedGetPatientReports(String Query) {
        // TODO Auto-generated method stub
        String report_name = "";
        String report64 = "";
        String Report_Path = "";

        SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

        Cursor c = db.rawQuery(Query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    report_name = c.getString(c.getColumnIndex("ReportType"));
                    //report64 = c.getString(c.getColumnIndex("ReportPhoto"));
                    Report_Path = c.getString(c.getColumnIndex("FileName")) + c.getString(c.getColumnIndex("FileExtension"));

                    RowItem item = new RowItem(null, report_name, "", Report_Path);
                    rowItems.add(item);

                } while (c.moveToNext());

            }
        }
        listView.setHasFixedSize(true);
        assert listView != null;

        reportGalleryAdapter = new ReportGalleryAdapter(rowItems, BUNDLE_PATIENT_ID);

        listView.setAdapter(reportGalleryAdapter);

        c.close();
        db.close();

        if (no_data != null) {
            if (rowItems.size() > 0) {
                no_data.setVisibility(View.GONE);
            } else if (rowItems.size() == 0) {
                no_data.setVisibility(View.VISIBLE);
            }
        }

    }

    //End

}
