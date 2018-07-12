package kdmc_kumar.Adapters_GetterSetter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import displ.mobydocmarathi.com.R;


class DrugListViewAdapter extends ArrayAdapter<CommonDataObjects.DrugItem> {

    private final Context context;

    public DrugListViewAdapter(Context context, int resourceId, List<CommonDataObjects.DrugItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    @NonNull
    public final View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View convertView1 = convertView;
        ViewHolder holder = null;
        CommonDataObjects.DrugItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView1 == null) {
            convertView1 = mInflater.inflate(R.layout.drug_listview, null);
            holder = new ViewHolder();
            holder.systemView = convertView1.findViewById(R.id.system);
            holder.CompanyName = convertView1.findViewById(R.id.company_name);
            holder.BrandName = convertView1.findViewById(R.id.brand_name);
            holder.Dosage = convertView1.findViewById(R.id.dosage);
            //holder.mv = (MedallionImageView) convertView.findViewById(R.id.medallionImageView1);
            convertView1.setTag(holder);
        } else

            holder = (ViewHolder) convertView1.getTag();

        holder.BrandName.setText(rowItem.getBrandName());
        holder.CompanyName.setText(rowItem.getPharmaCompany());
        holder.Dosage.setText(rowItem.getDosage());
        holder.systemView.setText(rowItem.getSystem());

        return convertView1;
    }

    /* private view holder class */
    private static class ViewHolder {
        ImageView imageView = null;

        TextView systemView = null;
        TextView CompanyName = null;
        TextView BrandName = null;
        TextView Dosage = null;

        private ViewHolder() {
        }
    }

}

