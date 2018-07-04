package kdmc_kumar.Masters_Modules.PreferredTestTemplate;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by bpncool on 2/23/2016.
 */
public class SectionedExpandableLayoutHelper implements SectionStateChangeListener {

    //recycler view
    private final RecyclerView mRecyclerView;
    //data list
    private final LinkedHashMap<Section, ArrayList<Item>> mSectionDataMap = new LinkedHashMap<>();
    private final ArrayList<Object> mDataArrayList = new ArrayList<>();
    //section map
    //TODO : look for a way to avoid this
    private final HashMap<String, Section> mSectionMap = new HashMap<>();
    //adapter
    private final SectionedExpandableGridAdapter mSectionedExpandableGridAdapter;

    public SectionedExpandableLayoutHelper(Context context, RecyclerView recyclerView, ItemClickListener itemClickListener,
                                           int gridSpanCount) {

        //setting the recycler view
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, gridSpanCount);
        recyclerView.setLayoutManager(gridLayoutManager);
        mSectionedExpandableGridAdapter = new SectionedExpandableGridAdapter(context, mDataArrayList,
                gridLayoutManager, itemClickListener, this);
        recyclerView.setAdapter(mSectionedExpandableGridAdapter);

        mRecyclerView = recyclerView;
    }

    public final void notifyDataSetChanged() {
        //TODO : handle this condition such that these functions won't be called if the recycler view is on scroll
        generateDataList();
        mSectionedExpandableGridAdapter.notifyDataSetChanged();
    }

    public final void addSection(String section, ArrayList<Item> items) {
        Section newSection;
        mSectionMap.put(section, (newSection = new Section(section)));
        mSectionDataMap.put(newSection, items);
    }

    public final void addItem(String section, Item item) {
        mSectionDataMap.get(mSectionMap.get(section)).add(item);
    }

    public final void removeItem(String section, Item item) {
        mSectionDataMap.get(mSectionMap.get(section)).remove(item);
    }

    public final void removeSection(String section) {
        mSectionDataMap.remove(mSectionMap.get(section));
        mSectionMap.remove(section);
    }

    private void generateDataList() {
        mDataArrayList.clear();
        for (Iterator<Map.Entry<Section, ArrayList<Item>>> iterator = mSectionDataMap.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<Section, ArrayList<Item>> entry = iterator.next();
            Section key;
            mDataArrayList.add((key = entry.getKey()));
            if (key.isExpanded)
                mDataArrayList.addAll(entry.getValue());
        }
    }

    @Override
    public final void onSectionStateChanged(Section section, boolean isOpen) {
        if (!mRecyclerView.isComputingLayout()) {
            section.isExpanded = isOpen;
            notifyDataSetChanged();
        }
    }
}
