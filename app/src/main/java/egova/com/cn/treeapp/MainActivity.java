package egova.com.cn.treeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.com.egova.tree.bean.Node;

public class MainActivity extends Activity implements View.OnClickListener {
    private EditText etRegion;
    private Button btnChoose;
    private TextView tvId;
    private TextView tvName;
    private TextView tvType;
    private LinearLayout llInfoContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();


    }

    private void initView() {
        etRegion = (EditText) findViewById(R.id.et_region);
        btnChoose = (Button) findViewById(R.id.btn_choose);
        llInfoContainer = (LinearLayout) findViewById(R.id.ll_info_container);
        llInfoContainer.setVisibility(View.GONE);
        tvId = (TextView) findViewById(R.id.tv_id);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvType = (TextView) findViewById(R.id.tv_type);

        btnChoose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_choose:
                Intent intent = new Intent(this, RegionTreeActivity.class);
                startActivityForResult(intent, 100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            Node node = (Node) data.getSerializableExtra(TreeConst.KEY_NODE);
            RegionBean region = (RegionBean) node.getT();
            fill(region);
        } catch (Exception e) {
            e.printStackTrace();
            llInfoContainer.setVisibility(View.GONE);
        }

        try {
            List<String> nodeNames = data.getStringArrayListExtra(TreeConst.KEY_NODES);
            if (nodeNames == null || nodeNames.size() == 0) {
                etRegion.setText("");
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (String nodeName : nodeNames) {
                sb.append(nodeName + ",");
            }
            String regions = sb.deleteCharAt(sb.length() - 1).toString();
            etRegion.setText(regions);
            etRegion.setSelection(regions.length());
        } catch (Exception e) {
            e.printStackTrace();
            llInfoContainer.setVisibility(View.GONE);
        }

    }


    private void fill(RegionBean region) {
        llInfoContainer.setVisibility(View.VISIBLE);
        etRegion.setText(region.getName());
        tvId.setText(String.valueOf(region.getId()));
        tvName.setText(String.valueOf(region.getName()));
        tvType.setText(String.valueOf(region.getType()));
    }
}
