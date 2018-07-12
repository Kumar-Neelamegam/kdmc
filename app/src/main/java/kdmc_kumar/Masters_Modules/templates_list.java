package kdmc_kumar.Masters_Modules;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.RowItem;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;

public class templates_list extends AppCompatActivity implements View.OnClickListener {


    protected static final int RESULT_SPEECH = 1;

    private ImageView imgNoMedia = null;
    ArrayAdapter<RowItem> adapter = null;

    private ListView listView = null;
    private List<RowItem> rowItems = null;

    private RecyclerView recyler_view = null;
    private GridLayoutManager gridLayoutManager = null;

    private TemplateRecylerAdapter templateRecylerAdapter = null;

    private Toolbar toolbar = null;

    private ImageView back = null;
    private ImageView home = null;
    private ImageView exit = null;
    private ImageView add_template = null;

    public templates_list() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.templates_list);

        toolbar = findViewById(R.id.toolbar);

        back = toolbar.findViewById(R.id.ic_back);
        home = toolbar.findViewById(R.id.ic_home);
        exit = toolbar.findViewById(R.id.ic_exit);
        add_template = toolbar.findViewById(R.id.ic_add);
        back.setOnClickListener(this);
        home.setOnClickListener(this);
        exit.setOnClickListener(this);

        add_template.setOnClickListener(view -> {

            templates_list.this.finish();
            Intent add = new Intent(getApplicationContext(), templates_addnew.class);
            BaseConfig.temp_flag = "True";
            startActivity(add);

        });

        recyler_view = findViewById(R.id.recyler_view);

        gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanCount(1);
        recyler_view.setHasFixedSize(true);
        recyler_view.setLayoutManager(gridLayoutManager);


        BaseConfig.welcometoast = 0;

        rowItems = new ArrayList<>();

        SelectedGetPatientDetails();

        imgNoMedia = findViewById(R.id.imgNoMedia);
        imgNoMedia.setVisibility(View.GONE);
        listView = findViewById(R.id.list);


    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // =================================
    private final void SelectedGetPatientDetails() {
        ArrayList<TemplateGetSet> templateGetSets = new ArrayList<>();
        SQLiteDatabase db = BaseConfig.GetDb();
        String pimg64 = "";
        int i = 1;

        Cursor c = db.rawQuery("select id,TemplateId,TemplateName from TemplateDtls group by TemplateName;", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    String pdtls = c.getString(c.getColumnIndex("TemplateName"));


                    Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.medical_history_icon);

                    // RowItem item = new RowItem(img, pdtls, "", "");
                    //rowItems.add(item);

                    templateGetSets.add(new TemplateGetSet(c.getString(c.getColumnIndex("TemplateName")), c.getString(c.getColumnIndex("id"))));


                    ++i;

                } while (c.moveToNext());
            }
        }

        listView = findViewById(R.id.list);

        //adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
        //listView.setAdapter(adapter);


        templateRecylerAdapter = new TemplateRecylerAdapter(templateGetSets);

        recyler_view.setLayoutManager(new GridLayoutManager(recyler_view.getContext(), 2));
        recyler_view.setAdapter(templateRecylerAdapter);

        c.close();

    }

    @Override
    public final void onClick(View view) {
        switch (view.getId()) {
            case R.id.ic_back:
                templates_list.this.finish();
                Intent task1 = new Intent(getApplicationContext(), Masters_New.class);
                startActivity(task1);
                break;
            case R.id.ic_home:
                templates_list.this.finish();
                Intent task = new Intent(getApplicationContext(), Dashboard_NavigationMenu.class);
                startActivityForResult(task, 500);
                overridePendingTransition(R.anim.abc_slide_in_top,
                        R.anim.abc_slide_in_top);
                break;
            case R.id.ic_exit:


                BaseConfig.ExitSweetDialog(templates_list.this, null);


                break;
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////

    static class TemplateGetSet {
        String name;
        String sno;

        TemplateGetSet(String name, String sno) {
            this.name = name;
            this.sno = sno;
        }

        final String getName() {
            return name;
        }

        public final void setName(String name) {
            this.name = name;
        }

        final String getSno() {
            return sno;
        }

        public final void setSno(String sno) {
            this.sno = sno;
        }
    }

    public class TemplateRecylerAdapter extends RecyclerView.Adapter<TemplateRecylerAdapter.MyViewHolder> {
        ArrayList<TemplateGetSet> templateGetSets = new ArrayList<>();

        TemplateRecylerAdapter(ArrayList<TemplateGetSet> templateGetSets) {
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

            final TemplateGetSet item = templateGetSets.get(position);

            holder.sno.setText(item.getSno());
            //holder.template_name.setText("" + item.getName().split(":")[1]);
            holder.template_name.setText(item.getName());

            holder.card_view.setOnClickListener(v -> {
                BaseConfig.selectedtemplate = item.getName();
                BaseConfig.temp_flag = "false";
                templates_list.this.finish();
                Intent lib = new Intent(v.getContext(), templates_addnew.class);
                startActivity(lib);
            });

            holder.Delete.setOnClickListener(view -> {


                // Toast.makeText(templates_list.this, "Delete clicked" + item.getSno(), Toast.LENGTH_SHORT).show();


                new CustomKDMCDialog(templates_list.this)
                        .setLayoutColor(R.color.orange_500)
                        .setImage(R.drawable.ic_warning_black_24dp)
                        .setTitle(templates_list.this.getString(R.string.close_online))
                        .setDescription("Are you sure want to delete?")
                        .setPossitiveButtonTitle(templates_list.this.getString(R.string.yes))
                        .setNegativeButtonTitle(templates_list.this.getString(R.string.no))
                        .setOnPossitiveListener(() -> {

                            try {
                                SQLiteDatabase db = BaseConfig.GetDb();
                                db.execSQL("delete from TemplateDtls where TemplateName='" + item.getName() + '\'');
                                db.close();

                                SelectedGetPatientDetails();

                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            new CustomKDMCDialog(templates_list.this).setNegativeButtonVisible(View.GONE)
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


}
