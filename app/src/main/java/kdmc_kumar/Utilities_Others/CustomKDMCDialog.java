package kdmc_kumar.Utilities_Others;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import displ.mobydocmarathi.com.R;

/**
 * Created by Ponnusamy on 15-03-2018.
 */

public class CustomKDMCDialog extends Dialog implements
        View.OnClickListener {

    private possitiveOnClick possitiveOnClick = null;
    private negativeOnClick negativeOnClick = null;
    private LinearLayout layout = null;
    private final Context c;
    private final Dialog d;
    private Button yes = null;
    private Button no = null;
    private TextView title = null;
    private TextView description = null;
    private AppCompatImageView icon = null;

    public CustomKDMCDialog(Context a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.d = this;
        show();
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_common_dialog);
        yes = findViewById(R.id.bt_close_yes);
        no = findViewById(R.id.bt_close_no);
        icon = findViewById(R.id.icon);
        title = findViewById(R.id.title);
        description = findViewById(R.id.content);
        layout = findViewById(R.id.panelcolor);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        d.setCancelable(false);

    }


    public final CustomKDMCDialog setLayoutColor(int color) {
        this.layout.setBackgroundColor(c.getResources().getColor(color));
        return this;
    }

    public final CustomKDMCDialog setImage(int image) {
        this.icon.setImageResource(image);



  //     Drawable d = this.icon.getDrawable();
//       ((AnimatedVectorDrawable)d).start();

        return this;
    }

    public final CustomKDMCDialog setTitle(String title) {
        this.title.setText(title);
        return this;
    }

    public final CustomKDMCDialog setTitleColor(int title) {
        this.title.setTextColor(title);
        return this;
    }

    public final CustomKDMCDialog setTitleBackgroundColor(int color) {
        this.title.setBackgroundColor(color);
        return this;
    }

    public final CustomKDMCDialog setDescription(String title) {
        this.description.setText(title);
        return this;
    }

    public final CustomKDMCDialog setDescriptionColor(int color) {
        this.description.setTextColor(color);
        return this;
    }

    public final CustomKDMCDialog setDescriptionBackgroundColor(int color) {
        this.description.setBackgroundColor(color);
        return this;
    }

    public final CustomKDMCDialog setPossitiveButtonTitle(String color) {
        this.yes.setText(color);
        return this;
    }

    public final CustomKDMCDialog setNegativeButtonTitle(String color) {
        this.no.setText(color);
        return this;
    }


    public final CustomKDMCDialog setPassitiveButtonBackground(int color) {
        this.yes.setBackgroundColor(color);
        return this;
    }

    public final CustomKDMCDialog setNegativeButtonBackground(int color) {
        this.no.setBackgroundColor(color);
        return this;
    }


    public final CustomKDMCDialog setNegativeButtonVisible(int visible) {
        this.no.setVisibility(visible);
        return this;
    }


    public final CustomKDMCDialog setOnPossitiveListener(possitiveOnClick possitiveListener) {
        this.possitiveOnClick = possitiveListener;
        return this;
    }

    public final CustomKDMCDialog setOnNegativeListener(negativeOnClick negativeListener) {
        this.negativeOnClick = negativeListener;
        return this;
    }



    @Override
    public final void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_close_yes:
                //c.finish();
                try {
                    possitiveOnClick.onPossitivePerformed();
                    dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;
            case R.id.bt_close_no:
                dismiss();
                //negativeOnClick.onNegativePerformed(d);
                break;
            default:
                break;
        }

        dismiss();
    }

    public interface possitiveOnClick {
        void onPossitivePerformed();
    }

    interface negativeOnClick {
        void onNegativePerformed();
    }

}