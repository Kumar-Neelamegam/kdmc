package kdmc_kumar.ReportFileUpload;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;

/**
 * Created by Ponnusamy M on 4/21/2017.
 */

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.MyViewHolder> {

    private ArrayList<FileGetSet> fileGetSets = new ArrayList<>();
    private final RecyclerView recyclerView;


    public FileAdapter(ArrayList<FileGetSet> fileGetSets, RecyclerView recyclerView) {
        this.fileGetSets = fileGetSets;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public final MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.screen_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final FileGetSet item = fileGetSets.get(position);

        holder.name.setText(item.getUploadFileDetail());
        holder.type.setText(item.getReportType());

        BaseConfig.Glide_LoadImageView( holder.image, item.getImagePath() );
        holder.card_view.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

            builder.setTitle("Confirm");
            builder.setMessage("Do you want to remove?");
            builder.setPositiveButton("Ok", (dialog, which) -> {
                deleteContacts(item.getId(), v.getContext());

                fileGetSets.remove(position);
                recyclerView.setAdapter(new FileAdapter(fileGetSets, recyclerView));
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {

            });
            AlertDialog alertDialog = builder.create();

            alertDialog.show();


        });

    }

    @Override
    public final int getItemCount() {
        return fileGetSets.size();
    }

    private static final void deleteContacts(String id, Context context) {


        SQLiteDatabase db = BaseConfig.GetDb();//context);

        db.execSQL("DELETE FROM contacts where Id='" + id + '\'');
        db.close();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView type;
        final ImageView image;
        final CardView card_view;

        MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.upload_file_details);
            type = itemView.findViewById(R.id.upload_filetype);
            image = itemView.findViewById(R.id.upload_image);
            card_view = itemView.findViewById(R.id.card_view);


        }
    }

}
