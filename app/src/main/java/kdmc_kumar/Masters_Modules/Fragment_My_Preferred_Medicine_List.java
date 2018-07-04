package kdmc_kumar.Masters_Modules;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.FavMedicineRecylerAdapter;
import kdmc_kumar.Core_Modules.BaseConfig;

public class Fragment_My_Preferred_Medicine_List extends Fragment {


    @BindView(R.id.recyler_View)
    RecyclerView recyclerView;

    View root_view;
    private GridLayoutManager gridLayoutManager = null;
    public Fragment_My_Preferred_Medicine_List() {
    }

    public static Fragment_My_Preferred_Medicine_List newInstance() {
        Fragment_My_Preferred_Medicine_List fragment = new Fragment_My_Preferred_Medicine_List();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root_view = inflater.inflate(R.layout.kdmc_layout_masters_main_recyler, container, false);

        ButterKnife.bind(this, root_view);



        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanCount(1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        LoadRecyclerView();

        return root_view;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////


    private final void LoadRecyclerView() {


        ArrayList<CommonDataObjects.Fav_MedicineList> Fav_MedicineList = new ArrayList<>();
        SQLiteDatabase db = BaseConfig.GetDb();

        String Value_testname, Value_Id;

        Cursor c = db.rawQuery("select distinct medicine,id from NewMedicine where Isupdate='1';", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    Value_Id = c.getString(c.getColumnIndex("id"));
                    Value_testname = c.getString(c.getColumnIndex("medicine"));
                    CommonDataObjects.Fav_MedicineList item = new CommonDataObjects.Fav_MedicineList(Value_testname, Value_Id);
                    Fav_MedicineList.add(item);

                } while (c.moveToNext());
            }
        }


        FavMedicineRecylerAdapter FavRecylerAdapter = new FavMedicineRecylerAdapter(Fav_MedicineList);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
        recyclerView.setAdapter(FavRecylerAdapter);

        c.close();
        db.close();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

}
