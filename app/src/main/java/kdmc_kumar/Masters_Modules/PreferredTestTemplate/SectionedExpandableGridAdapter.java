package kdmc_kumar.Masters_Modules.PreferredTestTemplate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;

/**
 * Created by lenovo on 2/23/2016.
 */
public class SectionedExpandableGridAdapter extends RecyclerView.Adapter<SectionedExpandableGridAdapter.ViewHolder> {

    //view type
    private static final int VIEW_TYPE_SECTION = R.layout.layout_test_template_section;
    private static final int VIEW_TYPE_ITEM = R.layout.layout_test_template_item; //TODO : change this
    //context
    private final Context mContext;
    //listeners
    private final ItemClickListener mItemClickListener;
    private final SectionStateChangeListener mSectionStateChangeListener;
    //**********************************************************************************************
    //data array
    private final ArrayList<Object> mDataArrayList;

    //**********************************************************************************************
    public SectionedExpandableGridAdapter(Context context, ArrayList<Object> dataArrayList,
                                          final GridLayoutManager gridLayoutManager, ItemClickListener itemClickListener,
                                          SectionStateChangeListener sectionStateChangeListener) {
        mContext = context;
        mItemClickListener = itemClickListener;
        mSectionStateChangeListener = sectionStateChangeListener;
        mDataArrayList = dataArrayList;

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return isSection(position) ? gridLayoutManager.getSpanCount() : 1;
            }
        });
    }

    //**********************************************************************************************
    private boolean isSection(int position) {
        return mDataArrayList.get(position) instanceof Section;
    }

    //**********************************************************************************************
    @NonNull
    @Override
    public final ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(viewType, parent, false), viewType);
    }

    //**********************************************************************************************
    @Override
    public final void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (holder.viewType) {

            case VIEW_TYPE_ITEM:
                final Item item = (Item) mDataArrayList.get(position);

                holder.itemTextView.setText(item.getTestName());
                holder.getItemTextView2.setText(item.getSubtestname());


                holder.view.setOnClickListener(v -> mItemClickListener.itemClicked(item));


                break;

            case VIEW_TYPE_SECTION:
                final Section section = (Section) mDataArrayList.get(position);
                holder.sectionTextView.setText(section.getName());
                holder.sectionTextView.setOnClickListener(v -> mItemClickListener.itemClicked(section));
                holder.sectionToggleButton.setChecked(section.isExpanded);
                holder.sectionToggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    mSectionStateChangeListener.onSectionStateChanged(section, isChecked);
                    if (holder.sectionToggleButton.isChecked()) {
                        holder.sectionToggleButton.setBackground(mContext.getResources().getDrawable(R.drawable.icon_minus_control));
                    } else {
                        holder.sectionToggleButton.setBackground(mContext.getResources().getDrawable(R.drawable.icon_plus_control));
                    }
                });


                break;
        }


    }

    //**********************************************************************************************
    @Override
    public final int getItemCount() {
        return mDataArrayList.size();
    }

    //**********************************************************************************************
    @Override
    public final int getItemViewType(int position) {
        return isSection(position) ? VIEW_TYPE_SECTION : VIEW_TYPE_ITEM;
    }

    //**********************************************************************************************
    protected static class ViewHolder extends RecyclerView.ViewHolder {

        //common
        final View view;
        final int viewType;

        //for section
        TextView sectionTextView = null;
        ToggleButton sectionToggleButton = null;

        //for item
        TextView itemTextView = null;
        TextView getItemTextView2 = null;

        ViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;
            this.view = view;
            if (viewType == VIEW_TYPE_ITEM) {
                itemTextView = view.findViewById(R.id.txt_testname);
                getItemTextView2 = view.findViewById(R.id.txt_subtestname);

            } else {
                sectionTextView = view.findViewById(R.id.text_section);
                sectionToggleButton = view.findViewById(R.id.toggle_button_section);
            }
        }
    }
    //**********************************************************************************************

}
