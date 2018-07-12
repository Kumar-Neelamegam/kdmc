package kdmc_kumar.Adapters_GetterSetter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;

public class DateTimelineRowitemAdapter extends RecyclerView.Adapter<DateTimelineRowitemAdapter.MyViewHolder>  {


    List<Timeline_Objects> objects;
    Context context;
    LayoutInflater layoutInflater;

    private static TextView selectedView=null;
    private static LinearLayout linearLayout=null;

    OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {

        void onItemClick(MyViewHolder view, int position, List<Timeline_Objects> items);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }


    public DateTimelineRowitemAdapter(Context context, List<Timeline_Objects> objects) {
        this.context = context;
        this.objects = objects;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        String VISITED_DATE= "";
        String UNIQUE_ID ="";

        VISITED_DATE = objects.get(position).getUnique_Id();
        UNIQUE_ID = objects.get(position).getVisitedDate();

//        Log.e("VISITED DATE: ", VISITED_DATE);
//        Log.e("UNIQUE ID: ",UNIQUE_ID);

        holder.txtvwYear.setText(VISITED_DATE);

        try
        {
            String date = UNIQUE_ID;
            SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
            Date newDate=spf.parse(date);
            spf= new SimpleDateFormat("dd-MMM");
            date = spf.format(newDate);
            System.out.println(date);

            holder.txtvwDaymonth.setText(String.format("%s-%s", date, objects.get(position).getVisitedDate().substring(0, 4)));
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.fab.setOnClickListener(view -> {
            selectItem(view, holder);
            mOnItemClickListener.onItemClick(holder, position, objects);
        });



        if(position==0)
        {
            holder.fab.performClick();
        }


    }

    private void selectItem(View view, MyViewHolder holder) {
        //If FirstTime Checked
        if (selectedView==null) {

            selectedView=holder.txtvwDaymonth;
            linearLayout=holder.linearLayout;

            selectedView.setBackgroundColor(Color.parseColor("#0a4991"));
            selectedView.setTextColor(Color.parseColor("#ffffff"));
            linearLayout.setBackgroundColor(view.getContext().getResources().getColor(R.color.colorPrimaryDark));

        }else
        {
            //Already Selected set White
            selectedView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
            selectedView.setTextColor(Color.parseColor("#ffffff"));
            linearLayout.setBackgroundColor(view.getContext().getResources().getColor(R.color.colorPrimary));


            //New Initilize to set Control
            selectedView=holder.txtvwDaymonth;
            linearLayout=holder.linearLayout;

            selectedView.setBackgroundColor(Color.parseColor("#0a4991"));
            selectedView.setTextColor(Color.parseColor("#ffffff"));
            linearLayout.setBackgroundColor(view.getContext().getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_timeline_rowitem, parent, false);

        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtvw_daymonth)
        public TextView txtvwDaymonth;
        @BindView(R.id.fab)
        public ImageView fab;
        @BindView(R.id.txtvw_year)
        public TextView txtvwYear;
        @BindView(R.id.linearlayout)
        public LinearLayout linearLayout;


        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }



}
