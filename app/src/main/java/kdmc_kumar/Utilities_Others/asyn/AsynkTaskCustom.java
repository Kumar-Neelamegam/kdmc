package kdmc_kumar.Utilities_Others.asyn;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Ponnusamy on 12/20/2017.
 * DemoTemplate
 */

public class AsynkTaskCustom extends AsyncTask<onWriteCode, Void, Void> {
    private onWriteCode object = null;
    private Object returnobject = null;
    private final Context context;
    private final ProgressDialog p;

    public AsynkTaskCustom(Context context, String Message) {
        this.context = context;
        p = new ProgressDialog(context);
        p.setMessage(Message);
        p.setIndeterminate(false);
        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setCancelable(false);
        p.show();

    }


    @Override
    protected final Void doInBackground(onWriteCode... params) {

        try {
            object = params[0];
            returnobject = object.onExecuteCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected final void onPostExecute(Void result) {
        super.onPostExecute(result);

        p.dismiss();

        try {
            object.onSuccess(returnobject);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
