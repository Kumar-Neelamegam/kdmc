package kdmc_kumar.MyPatient_Module;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.PDFReportItems;
import kdmc_kumar.Adapters_GetterSetter.PdfReportAdapter;
import kdmc_kumar.Core_Modules.BaseConfig;

import static displ.mobydocmarathi.com.R.id.swipeRefreshLayout;

/**
 * Created by Android on 12-Mar-18.
 */

public class PDFReport_Profile extends android.support.v4.app.Fragment {


//**********************************************************************************************

    private RecyclerView listView = null;
    private PdfReportAdapter adapter = null;

    private final ArrayList<PDFReportItems> rowItems = new ArrayList<>();

    private String BUNDLE_PATIENT_ID = null;
    private ImageView no_data = null;
    private SwipeRefreshLayout mSwipeRefreshLayout = null;
    private GridLayoutManager lLayout = null;

    public PDFReport_Profile() {
    }

    //**********************************************************************************************
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_mypatient_reportgalleryfragment, container, false);

        Bundle args = getArguments();
        BUNDLE_PATIENT_ID = args.getString(BaseConfig.BUNDLE_PATIENT_ID);

        listView = rootView.findViewById(R.id.listView1);
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);

        lLayout = new GridLayoutManager(getActivity(), 1);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(lLayout);
        listView.setNestedScrollingEnabled(false);

        no_data = rootView.findViewById(R.id.img_nodata);

        try {

            SelectedGetPatientPDFReports("select * from Bind_PDFInfo where PATIENTID='" + BUNDLE_PATIENT_ID + "';");

        } catch (Exception e) {
            e.printStackTrace();
        }


        mSwipeRefreshLayout = rootView.findViewById(swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_red_dark, android.R.color.holo_blue_bright);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {

            try {

                SelectedGetPatientPDFReports("select * from Bind_PDFInfo where PATIENTID='" + BUNDLE_PATIENT_ID + "';");

            } catch (Exception e) {
                e.printStackTrace();
            }


        });


        return rootView;
    }

    //**********************************************************************************************
    private void SelectedGetPatientPDFReports(String Query) {


        try {
            SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        int Id = Integer.parseInt(c.getString(c.getColumnIndex("Id")));
                        String PATIENTNAME = c.getString(c.getColumnIndex("PATIENTNAME"));
                        String PATIENTID = c.getString(c.getColumnIndex("PATIENTID"));
                        String SYMP = c.getString(c.getColumnIndex("SYMPTOMS"));
                        String DIAGNOSIS = c.getString(c.getColumnIndex("DIAGNOSIS"));
                        String VISITEDON = c.getString(c.getColumnIndex("ACT_DATETIME"));


                        PDFReportItems item = new PDFReportItems(Id, PATIENTID, PATIENTNAME, SYMP, DIAGNOSIS, VISITEDON);
                        rowItems.add(item);


                    } while (c.moveToNext());

                }
            }

            listView.setHasFixedSize(true);
            assert listView != null;

            adapter = new PdfReportAdapter(rowItems, BUNDLE_PATIENT_ID, getActivity());

            listView.setAdapter(adapter);

            c.close();
            db.close();

            if (no_data != null) {
                if (rowItems.size() > 0) {
                    no_data.setVisibility(View.GONE);
                } else if (rowItems.size() == 0) {
                    no_data.setVisibility(View.VISIBLE);
                }
            }

            // Stop refresh animation
            mSwipeRefreshLayout.setRefreshing(false);


        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    //**********************************************************************************************
}//END
