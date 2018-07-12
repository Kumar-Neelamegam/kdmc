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
import kdmc_kumar.Core_Modules.BaseConfig;


import kdmc_kumar.Adapters_GetterSetter.*;


public class Fragment_Prescription_Templates extends Fragment {


    @BindView(R.id.recyler_View)
    RecyclerView recyclerView;

    View root_view;

    public Fragment_Prescription_Templates() {
    }

    public static Fragment_Prescription_Templates newInstance() {
        Fragment_Prescription_Templates fragment = new Fragment_Prescription_Templates();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root_view = inflater.inflate(R.layout.kdmc_layout_masters_main_recyler, container, false);

        ButterKnife.bind(this, root_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanCount(1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);


        TemplateRecylerAdapter templateRecylerAdapter = new TemplateRecylerAdapter(selectTemplateList());
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
        recyclerView.setAdapter(templateRecylerAdapter);



        return root_view;
    }


    private  ArrayList<CommonDataObjects.TemplateGetSet>  selectTemplateList() {
        ArrayList<CommonDataObjects.TemplateGetSet> templateGetSets = new ArrayList<>();
        SQLiteDatabase db = BaseConfig.GetDb();
        String pimg64 = "";
        int i = 1;

        Cursor c = db.rawQuery("select id,TemplateId,TemplateName from TemplateDtls group by TemplateName;", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    templateGetSets.add(new CommonDataObjects.TemplateGetSet(c.getString(c.getColumnIndex("TemplateName")), c.getString(c.getColumnIndex("id"))));


                    ++i;

                } while (c.moveToNext());
            }
        }

        return templateGetSets;
    }


}