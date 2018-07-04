package kdmc_kumar.Adapters_GetterSetter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import displ.mobydocmarathi.com.R;


public class CustomListViewAdapter extends ArrayAdapter<CommonDataObjects.RowItem> {

    private final Context context;

    public CustomListViewAdapter(Context context, int resourceId, List<CommonDataObjects.RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    @NonNull
    public final View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View convertView1 = convertView;
        ViewHolder holder = null;
        CommonDataObjects.RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView1 == null) {
            convertView1 = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.txtDesc = convertView1.findViewById(R.id.desc);
            holder.txtTitle = convertView1.findViewById(R.id.title);
            holder.imageView = convertView1.findViewById(R.id.icon);
            holder.rootLayout = convertView1.findViewById(R.id.list_root);
            //holder.mv = (MedallionImageView) convertView.findViewById(R.id.medallionImageView1);
            convertView1.setTag(holder);

        } else

            holder = (ViewHolder) convertView1.getTag();
        holder.txtDesc.setText(rowItem.getDesc());
        holder.txtTitle.setText(rowItem.getTitle());
        // holder.imageView.setImageResource(rowItem.getImageId());
        if (rowItem.getImageId() != null) {
            holder.imageView.setImageBitmap(rowItem.getImageId());
        }
        holder.rootLayout.setLayoutParams(new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        return convertView1;
    }

    /* private view holder class */
    private static class ViewHolder {
        ImageView imageView = null;
        LinearLayout rootLayout = null;

        TextView txtTitle = null;
        TextView txtDesc = null;

        private ViewHolder() {
        }
    }

}
