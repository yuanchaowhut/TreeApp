package egova.com.cn.treeapp;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import cn.com.egova.tree.BaseTreeActivity;
import cn.com.egova.tree.bean.Node;
import cn.com.egova.tree.inter.ICallBack;

public class RegionTreeActivity extends BaseTreeActivity<RegionBean> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List<RegionBean> getDataList() {
        return Mock.getData();
    }

    @Override
    protected ICallBack<Node> getClickCallBack() {
        return new ICallBack<Node>() {
            @Override
            public void onResult(int obj, Node resultObj) {
                Intent intent = new Intent(RegionTreeActivity.this, MainActivity.class);
                //注意:如果自定义的实体类RegionBean不愿意实现Serializable接口，则此处这些数据需要挨个putExtra进行传递.
                //建议如非特殊情况，实现Serializable接口比较方便，直接传递对象即可。
//                intent.putExtra(TreeConst.KEY_NODE_ID,resultObj.getId());
//                intent.putExtra(TreeConst.KEY_NODE_ID,resultObj.getName());
//                RegionBean regionBean = (RegionBean) resultObj.getT();
//                intent.putExtra(TreeConst.KEY_REGION_TYPE,regionBean.getType());

                intent.putExtra(TreeConst.KEY_NODE, resultObj);
                if (obj == ICallBack.TYPE_CLICK) {                   //单击
                    if (resultObj.isLeaf()) {
                        setResult(TreeConst.RESULT_CODE_CLICK, intent);
                        finish();
                    }
                } else if (obj == ICallBack.TYPE_LONG_CLICK) {       //长按
                    if (!resultObj.isLeaf()) {
                        setResult(TreeConst.RESULT_CODE_LONG_CLICK, intent);
                        finish();
                    }
                }

            }
        };
    }

    @Override
    protected boolean isNeedThread() {
        return true;
    }

}



