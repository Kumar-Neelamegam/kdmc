package kdmc_kumar.Core_Modules;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.OnlineConsultationRecylerAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.DashBoardAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;


public class OnlineConsultation extends AppCompatActivity {


    @BindView(R.id.parent_layout)
    CoordinatorLayout parentLayout;
    @BindView(R.id.img_nodata)
    AppCompatImageView imgNodata;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.list)
    RecyclerView list;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_back)
    AppCompatImageView toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_exit)
    AppCompatImageView toolbarExit;

    private OnlineConsultationRecylerAdapter onlineConsultationRecylerAdapter = null;
    private GridLayoutManager lLayout = null;


    public OnlineConsultation() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.onlineconsultation_list);

        try {

            GETINITIALIZE();

            CONTROLLISTENERS();

            AUTOREFRESHPATIENTLIST();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private Handler timerHandler;
    private Runnable timerRunnable;

    private void AUTOREFRESHPATIENTLIST() {


        timerHandler = new Handler();

        timerRunnable = new Runnable() {
            @Override
            public void run() {

                SelectedGetPatientDetails();

                timerHandler.postDelayed(this, 180000); //run every second

            }
        };

        timerHandler.postDelayed(timerRunnable, 180000); //Start timer after 1 sec

    }


    @Override
    protected void onStop() {
        super.onStop();
        if (timerHandler != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerHandler != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
    }


    private void CONTROLLISTENERS() {

        toolbarExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(OnlineConsultation.this, null));

        toolbarBack.setOnClickListener(view -> LoadBack());

        SelectedGetPatientDetails();


    }

    private void GETINITIALIZE() {

        ButterKnife.bind(this);


        BaseConfig.welcometoast = 0;

        toolbarTitle.setText(String.format("%s - %s", getString(R.string.app_name), getString(R.string.onlin_consultations)));

        setSupportActionBar(toolbar);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(toolbar, DashBoardAdapter.VIEW_NAME_HEADER_TITLE);
        }


        lLayout = new GridLayoutManager(OnlineConsultation.this, 2);
        list.setHasFixedSize(true);
        list.setLayoutManager(lLayout);
        list.setNestedScrollingEnabled(false);
        list.setItemAnimator(new DefaultItemAnimator());

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_red_dark, android.R.color.holo_blue_bright);
        swipeRefreshLayout.setOnRefreshListener(() -> {

            try {

                SelectedGetPatientDetails();

            } catch (RuntimeException e) {
                e.printStackTrace();
            }

        });


    }

    private void LoadBack() {

        BaseConfig.globalStartIntent(this, Dashboard_NavigationMenu.class, null);
    }


    private final void SelectedGetPatientDetails() {

        ArrayList<CommonDataObjects.OnlineConsultation_DataObjects> allItems = new ArrayList<>();

        try {

            SQLiteDatabase db = BaseConfig.GetDb();//OnlineConsultation.this);

            Cursor c = db.rawQuery("select * from OnlineConsultancy where (IsActive='True' or IsActive='1')  order by ServerId", null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        String Id = c.getString(c.getColumnIndex("id"));
                        String PatientName = c.getString(c.getColumnIndex("pname"));
                        String PatientId = c.getString(c.getColumnIndex("pid"));
                        String Age = c.getString(c.getColumnIndex("age"));
                        String Gender = c.getString(c.getColumnIndex("gender"));
                        //String PatientPhoto = c.getString(c.getColumnIndex("pphoto"));
                        String PatientPhoto = BaseConfig.GetValues("select PC as ret_values from Patreg where Patid='"+PatientId+"'");
                        String ConsultationId = c.getString(c.getColumnIndex("ServerId"));
                        String MedId = c.getString(c.getColumnIndex("Medid"));

                        allItems.add(new CommonDataObjects.OnlineConsultation_DataObjects(Id, PatientName, PatientId, Age, Gender, PatientPhoto, ConsultationId, MedId));

                    } while (c.moveToNext());
                }
            }

            db.close();
            c.close();

            onlineConsultationRecylerAdapter = new OnlineConsultationRecylerAdapter(allItems, OnlineConsultation.this);
            list.setAdapter(onlineConsultationRecylerAdapter);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        //nabeel
        if (imgNodata != null) {
            if (allItems.size() > 0) {
                imgNodata.setVisibility(View.GONE);
            } else {
                imgNodata.setVisibility(View.VISIBLE);
            }
        }


        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public final void onBackPressed() {

        LoadBack();

    }


}//END

		
		
		
		
		
