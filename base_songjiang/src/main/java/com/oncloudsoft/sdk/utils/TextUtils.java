package com.oncloudsoft.sdk.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 作者 caomingduo
 * 创建时间 2019/1/28 17:48
 * 描述
 */

public class TextUtils {
    /**
     *  
     *      * 半角转换为全角  TextView 换行排版不规则
     *      *  
     *      * @param input 
     *      * @return 
     *      
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public static void setEditext(EditText editText, long max, int xiaoshu) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (xiaoshu > 0) {
                    int xiaoshu = 4;
                    if (s.toString().contains(".")) {
                        if (s.length() - 1 - s.toString().indexOf(".") > xiaoshu) {
                            s = s.toString().subSequence(0, s.toString().indexOf(".") + xiaoshu + 1);
                            editText.setText(s);
                            editText.setSelection(s.length());
                        }
                    }
                    if (s.toString().trim().substring(0).equals(".")) {
                        s = "0" + s;
                        editText.setText(s);
                        editText.setSelection(2);
                    }

                    if (s.toString().startsWith("0")
                            && s.toString().trim().length() > 1) {
                        if (!s.toString().substring(1, 2).equals(".")) {
                            editText.setText(s.subSequence(0, 1));
                            editText.setSelection(1);
                            return;
                        }
                    }
                }

                if (max > 0) {//需要做金额限制
                    Double num = android.text.TextUtils.isEmpty(s) ? 0 : Double.parseDouble(s.toString().trim());

                    if (num > max) {
                        editText.setText(max + "");
                        editText.setSelection(editText.getText().toString().trim().length());

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


}
