/*
  File generated by Magnet rest2mobile 1.1 - 9 Feb, 2018 2:56:41 AM

  @see {@link http://developer.magnet.com}
 */

package kdmc_kumar.Webservices_NodeJSON.importREST_Services.geAppMaster.controller.api;


import com.magnet.android.mms.async.Call;
import com.magnet.android.mms.async.StateChangedListener;

import kdmc_kumar.Webservices_NodeJSON.importREST_Services.geAppMaster.model.beans.AppMasterResult;

public interface AppMaster {

    /**
     * Generated from URL GET http://192.168.137.3:1234/data/getAppMaster
     * GET data/getAppMaster
     * @param listener
     * @return AppMasterResult
     */
    Call<AppMasterResult> getAppMaster(StateChangedListener listener);
}
