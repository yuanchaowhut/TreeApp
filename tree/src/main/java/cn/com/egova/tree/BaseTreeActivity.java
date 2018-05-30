package cn.com.egova.tree;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import cn.com.egova.tree.adapter.SimpleTreeAdapter;
import cn.com.egova.tree.adapter.TreeListViewAdapter;
import cn.com.egova.tree.bean.Node;
import cn.com.egova.tree.inter.ICallBack;


public abstract class BaseTreeActivity<T> extends Activity implements View.OnClickListener {
    public static final String TAG = "BaseTreeActivity";
    public static final int GET_DATA_FAILED = 0;
    public static final int GET_DATA_SUCCESS = 1;
    protected List<T> mData;
    protected FrameLayout llBtnContainer;
    protected Button btnCancel;
    protected ListView mTree;
    protected ProgressBarWithText pbLoading;
    protected TreeListViewAdapter mAdapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //处理进度条
            if (isShowProgress()) {
                pbLoading.setVisibility(View.GONE);
            }
            switch (msg.what) {
                case GET_DATA_SUCCESS:
                    showTree();
                    break;
                case GET_DATA_FAILED:
                    showTip("获取数据为空!");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);

        initView();

        initTree();
    }

    protected void initView() {
        llBtnContainer = (FrameLayout) findViewById(R.id.ll_btn_container);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        mTree = (ListView) findViewById(R.id.lst_tree);
        pbLoading = (ProgressBarWithText) findViewById(R.id.pb_loading);

        btnCancel.setOnClickListener(this);
    }

    protected void initTree() {
        if (!isNeedThread()) {
            mData = getDataList();
            if (mData != null && mData.size() > 0) {
                showTree();
            } else {
                showTip("获取数据为空!");
            }
        } else {
            //处理进度条
            if (isShowProgress()) {
                pbLoading.setVisibility(View.VISIBLE);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mData = getDataList();
                    if (mData != null && mData.size() > 0) {
                        Message msgSuccess = mHandler.obtainMessage(GET_DATA_SUCCESS);
                        mHandler.sendMessage(msgSuccess);
                    } else {
                        Message msgFailed = mHandler.obtainMessage(GET_DATA_FAILED);
                        mHandler.sendMessage(msgFailed);
                    }
                }
            }).start();

        }
    }


    protected void showTree() {
        try {
            llBtnContainer.setVisibility(View.VISIBLE);  //显示顶部2个按钮(请选择、取消)
            mAdapter = new SimpleTreeAdapter<T>(mTree, this, mData, getDefaultExpandLevel());

            //设置监听事件,本Activity并不实际处理，由实现类自行处理.
            mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                @Override
                public void onClick(Node node, int position) {
                    ICallBack<Node> callBack = getClickCallBack();
                    if (callBack != null) {
                        callBack.onResult(ICallBack.TYPE_CLICK, node);
                    }
                }

                @Override
                public void onLongClick(Node node, int position) {
                    ICallBack<Node> callBack = getClickCallBack();
                    if (callBack != null) {
                        callBack.onResult(ICallBack.TYPE_LONG_CLICK, node);
                    }
                }
            });

            mTree.setAdapter(mAdapter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            llBtnContainer.setVisibility(View.GONE);  //隐藏顶部2个按钮(请选择、取消)
        }
    }

    protected abstract List<T> getDataList();

    protected int getDefaultExpandLevel() {
        return 0;
    }

    protected abstract ICallBack<Node> getClickCallBack();  //这个callBack用于实际处理点击事件包括长按事件

    protected boolean isNeedThread() {
        return false;
    }

    protected boolean isShowProgress() {
        return false;
    }

    protected void showTip(String tip) {
        Toast.makeText(BaseTreeActivity.this, tip, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
