package organization.tho.entertaiment.Common;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by QUOC CUONG on 18/10/2017.
 */

public class ConvertDpToPx {
    /**
     * Converting dp to pixel
     */
    public static int dpToPx(Context context, int dp) {
        int result = 0;
        if (context != null) {
            Resources r = context.getResources();
            result = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
        }
        return result;
    }
}
