package ebj.awesome.yujinnotes.util;

import android.widget.EditText;

/**
 * Created by Yujin on 25/09/2017.
 */

public class FieldsHelper {

    public static void setEditTextEnabled(EditText editText, boolean enabled) {
        editText.setFocusable(enabled);
        editText.setFocusableInTouchMode(enabled);
        editText.setCursorVisible(enabled);
    }
}
