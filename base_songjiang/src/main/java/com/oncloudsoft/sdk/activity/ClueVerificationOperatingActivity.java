package com.oncloudsoft.sdk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.oncloudsoft.sdk.R;
import com.oncloudsoft.sdk.activity.base_activity.BaseActivity;
import com.oncloudsoft.sdk.adapter.MultiImageAdapter;
import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.okhttprequest.MyOkhttpClient;
import com.oncloudsoft.sdk.utils.FileUtil;
import com.oncloudsoft.sdk.view.BaseTitleView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 作者 黄继栋
 * 创建时间 2019/3/14 14:10
 * 描述 线索核查  核查操作
 */

public class ClueVerificationOperatingActivity extends BaseActivity {

    private BaseTitleView btTitle;
    private EditText etRemark;
    private GridView gridView;
    private RadioButton rbOpreCaichanTrue;
    private RadioButton rbOpreCaichanFalse;
    private RadioGroup rgOperating;

    private String id;


    private MultiImageAdapter adapter;
    private List<String> items = new ArrayList<>();
    private final int REQUEST_IMAGE = 100;
    private int canSelectCount = 9;

    public static void startActivityForResult(BaseActivity activity, String id, int requestCode) {

        Intent intent = new Intent();
        intent.putExtra(Global.ID, id);
        intent.setClass(activity, ClueVerificationOperatingActivity.class);
        activity.startActivityForResult(intent, requestCode);


    }

    private void submit(String shzt) {//通过是 1   拒绝是2
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);


        for (int i = 0; i < items.size(); i++) {
            String path = items.get(i);
            if (!path.equals("")) {
                File file = new File(path);
                RequestBody.create(MediaType.parse("image/png"), file);
                builder.addFormDataPart("file", FileUtil.getEnglishName(file), RequestBody.create(MediaType.parse("image/png"), file));
            }

        }

        builder.addFormDataPart("id", id);
        builder.addFormDataPart("shbz", etRemark.getText().toString().trim());
        builder.addFormDataPart("shzt", shzt);


        MyOkhttpClient.getOkhttpInstance().upLoadMultipartBody(ClueVerificationOperatingActivity.this, Global.URL_VERIFICATION_VERIFY, builder, true, new MyOkhttpClient.MyOkhttpCallBack() {
            @Override
            public void onRequestSccess(String json) {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public boolean onRequestFaild(String mes) {
                return false;
            }
        }, "上传中...");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_clueverificationoperating);
        findViews();
        init();


    }

    private void findViews() {
        btTitle = findViewById(R.id.bt_title);
        etRemark = findViewById(R.id.et_remark);
        gridView = findViewById(R.id.grid_view);
        rbOpreCaichanTrue = findViewById(R.id.rb_opre_caichan_true);
        rbOpreCaichanFalse = findViewById(R.id.rb_opre_caichan_false);
        rgOperating = findViewById(R.id.rg_operating);
        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int buttonId = rgOperating.getCheckedRadioButtonId();
                if (buttonId == R.id.rb_opre_caichan_true) {
                    submit("1");//属实
                } else if (buttonId == R.id.rb_opre_caichan_false) {
                    submit("2");//不属实
                }
            }
        });

    }

    private void init() {
        id = getIntent().getStringExtra(Global.ID);
        setStatusBarLightcolor(ClueVerificationOperatingActivity.this, R.color.white);
        btTitle.setOnLeftImageClickListener(() -> finish());
        items.add("");
        adapter = new MultiImageAdapter(this, items, true, 20, 4);

        gridView.setAdapter(adapter);
    }


    public void selectPictrue() {
        canSelectCount = 9 - items.size() + 1;
        MultiImageSelector.create().showCamera(true) // show camera or not. true by default
                .count(canSelectCount) // max select image size, 9 by default. used width #.multi()
                //.single() // single mode
                .multi() // multi mode, default mode;
                //                .origin(imgs) // original select data set, used width #.multi()
                .start(this, REQUEST_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK) {

            switch (requestCode) {
                case REQUEST_IMAGE:
                    List<String> imgs;
                    imgs = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    if (imgs == null || imgs.size() == 0) {
                        return;
                    }
                    if ("".equals(items.get(items.size() - 1))) {
                        items.remove(items.size() - 1);
                    }
                    items.addAll(imgs);
                    if (items.size() < 9) {
                        items.add("");
                    }
                    adapter.notifyDataSetChanged();

                default:
                    break;
            }
        }
    }
}
