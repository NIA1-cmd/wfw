//package com.oncloudsoft.sdk.yunxin.session.fragment;
//
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.EditorInfo;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.alibaba.fastjson.JSON;
//import com.bumptech.glide.Glide;
//import com.oncloudsoft.sdk.R;
//import com.oncloudsoft.sdk.adapter.SearchAdapter;
//import com.oncloudsoft.sdk.app.Global;
//import com.oncloudsoft.sdk.asr.AsrPopuWindow;
//import com.oncloudsoft.sdk.event.SearchDataEvent;
//import com.oncloudsoft.sdk.fragment.base_fragment.BaseFragment;
//import com.oncloudsoft.sdk.utils.MyLog;
//import com.oncloudsoft.sdk.utils.SharedPreferencesUtils;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * 文件描述：首页--案件
// * 作者：黄继栋
// * 创建时间：2019/5/17
// */
//public class HomeFragment extends BaseFragment {
//    private FrameLayout frameLayout;
//    private EditText mEtSearch;
//    private RecyclerView mRvSearch;
//    private List<String> keyWord = new ArrayList<>();
//    private SearchAdapter mSearchAdapter;//搜索历史记录adapter
//
//    private ImageView ivStartAsr;
//    private ImageView search_clear;
//
//    private CaseListFragment caseListFragment;
//    private FragmentTransaction transition;
//    private FragmentManager fragmentManager;
//
//    private String queryString = "";
//
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View inflate = inflater.inflate(R.layout.fragment_caselist, container, false);
//        init(inflate);
//        EventBus.getDefault().register(this);
//
//        caseListFragment = new CaseListFragment();
////        Bundle bundle=new Bundle();
////        bundle.putBoolean(Global.TYPE,false);
////        caseListFragment.setArguments(bundle);
//        fragmentManager = getChildFragmentManager();
//
//        transition = fragmentManager.beginTransaction();
//
//        transition.add(R.id.fl_container, caseListFragment);
//        transition.show(caseListFragment);
//
//        transition.commit();
//
//        return inflate;
//    }
//    /**
//     * 查询案件
//     *
//     * @param Keyword 关键字
//     */
//    private void queryCase(String Keyword) {
//
//        caseListFragment.queryData(Keyword);
//
//
//        /*int flag = caseListFragment.getIndex();
//        MyOkhttpClient.getOkhttpInstance().sentGet(getActivity(), Global.URL_SEARCH_CASE, HttpParams.HttpParams().add("key", Keyword).add("flag",flag+"").build(), new MyOkhttpClient.MyOkhttpCallBack() {
//            @Override
//            public void onRequestSccess(String json) throws JSONException {
//                ActivityHelper.getInstance().getUiThread(getActivity(), new Runnable() {
//                    @Override
//                    public void run() {
////                        adapter.query(json);
//                        controlVis(true);
//
//
//                        com.alibaba.fastjson.JSONArray objects = JSONObject.parseArray(json);
//                        if (objects.size() == 0) {//没有符合的群
////                            lvContacts.setVisibility(View.GONE);
//                            mRvSearch.setVisibility(View.GONE);
////                            rlEmpty.setVisibility(View.VISIBLE);
//
//                        } else {
////                            rlEmpty.setVisibility(View.GONE);
//
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public boolean onRequestFaild(String mes) {
//                return false;
//            }
//        }, true);*/
//    }
//    private String json;
//
//    /**
//     * 初始化 关键词联想
//     */
//    private void initSearchDictionary() {
//        if (json == null || json.equals("")) {
//            json = (String) SharedPreferencesUtils.getParam(Global.DICTIONARY_SEARCH, "");
//        }
//    }
//    /**
//     * 关键字 字典查询
//     *
//     * @param content 拼音
//     */
//    private void query(String content) {
//        initSearchDictionary();
//        controlVis(true);
//        Map<String, List<String>> map = JSON.parseObject(json, Map.class);
//        List<String> strings = map.get(content);
//        keyWord.clear();
//        if (strings != null) {
//            keyWord.addAll(strings);
//            caseListFragment.setSearchData(keyWord);
//            controlVis(false);
//        }
//        mSearchAdapter.notifyDataSetChanged();
//    }
//    private void init(View view) {
//
//        findViews(view);
//        setParames();
//        Glide.with(this).load(R.drawable.icon_voice_gif).into(ivStartAsr);
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    /**
//     * 控制listView 和recyclerView 的显示与隐藏
//     *
//     * @param isNormal true ：显示案件列表  false:显示推荐搜索字典
//     */
//    private void controlVis(boolean isNormal) {
//        if (isNormal) {
//            mRvSearch.setVisibility(View.GONE);
//        } else {
//            mRvSearch.setVisibility(View.VISIBLE);
//        }
//
//    }
//    /**
//     * 准备数据源，以及初始化控件的一些属性，创建adapter
//     */
//    private void setParames() {
//        mEtSearch.setHint(getResources().getString(R.string.caseno_peoplename));
//        mEtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
////        mEtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
////            @Override
////            public void onFocusChange(View v, boolean hasFocus) {
////
////            }
////        });
//
//        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
//                    hideSoftKeyboard();
//                    String content = mEtSearch.getText().toString();
//                    content=content.trim();
//                    if (TextUtils.isEmpty(content)) {
//                        return true;
//                    }
//                    queryString = content;
//                    queryCase(content);
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        mSearchAdapter = new SearchAdapter(getActivity(), keyWord, new SearchAdapter.OnSearchClickListener() {
//            @Override
//            public void textClick(int pos) {
//                String s = keyWord.get(pos);
//                mEtSearch.setText(s);
//                queryCase(s);
//            }
//        });
//
//        mRvSearch.setAdapter(mSearchAdapter);
//        mRvSearch.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        mEtSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                s=s.toString().trim();
//                if (s.length() > 0) {
//                    search_clear.setVisibility(View.VISIBLE);
//                } else {
//                    queryString = "";
//                    search_clear.setVisibility(View.INVISIBLE);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String trim = s.toString().trim();
//
//                if (trim.length() > 0) {
//                    queryString = trim;
//                    query(trim);
//                } else {
////                    adapter.load(true);
//                    controlVis(true);
//                    caseListFragment.setSearchData(null);
//
//                }
//            }
//        });
//
//        ivStartAsr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AsrPopuWindow asrPopuWindow = new AsrPopuWindow(getActivity());
//                asrPopuWindow.showAsrPopuwindow(ivStartAsr, null);
//
//            }
//        });
//    }
//
//    private void findViews(View view) {
//        frameLayout = view.findViewById(R.id.fl_container);
//
//        mEtSearch = view.findViewById(R.id.query);
//        mRvSearch = view.findViewById(R.id.rv_search);
//        ivStartAsr = view.findViewById(R.id.iv_startasr);
//        search_clear = view.findViewById(R.id.search_clear);
//
//        search_clear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mEtSearch.getText().clear();
//            }
//        });
//    }
//
//
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(SearchDataEvent searchData) {
//        MyLog.i("info","   ="+searchData.getSearch());
//        if (searchData.isClear()){
//            mEtSearch.getText().clear();
//
//            return;
//        }
//        mEtSearch.setText(searchData.getSearch());
//        mEtSearch.setSelection(searchData.getSearch().length());
//    }
//
//
//
////
////    /**
////     * 加载数据
////     */
////    public void upDate() {
////        int count = NIMClient.getService(TeamService.class).queryTeamCountByTypeBlock(itemType == ItemTypes.TEAMS.ADVANCED_TEAM ? TeamTypeEnum.Advanced : TeamTypeEnum.Normal);
////        if (count == 0) {
////            if (itemType == ItemTypes.TEAMS.ADVANCED_TEAM) {
////                //                ToastHelper.showToast(TeamListActivity.this, "R.string.no_team");
////                ActivityHelper.getInstance().showToast(getActivity(), "no_team");
////            } else if (itemType == ItemTypes.TEAMS.NORMAL_TEAM) {
////                ActivityHelper.getInstance().showToast(getActivity(), "no_normal_team");
////                //                ToastHelper.showToast(TeamListActivity.this, "R.string.no_normal_team");
////            }
////        }
////        adapter.load(true);
////    }
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        //EventBus.getDefault().unregister(this);
//    }
//}
