package cn.com.egova.tree.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.com.egova.tree.R;
import cn.com.egova.tree.bean.Node;


public class SimpleTreeAdapter<T> extends TreeListViewAdapter<T> {
    public SimpleTreeAdapter(ListView mTree, Context context, List<T> data, int defaultExpandLevel, boolean isMultiChoice) throws IllegalArgumentException,
            IllegalAccessException {
        super(mTree, context, data, defaultExpandLevel, isMultiChoice);
    }

    @Override
    public View getConvertView(final Node node, int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.lst_item_tree, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.id_treenode_icon);
            viewHolder.label = (TextView) convertView.findViewById(R.id.id_treenode_label);
            viewHolder.choose = (CheckBox) convertView.findViewById(R.id.id_treenode_choose);
            viewHolder.choose.setVisibility(isMultiChoice ? View.VISIBLE : View.GONE);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (node.getIcon() == -1) {
            viewHolder.icon.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.icon.setVisibility(View.VISIBLE);
            viewHolder.icon.setImageResource(node.getIcon());
        }
        viewHolder.label.setText(node.getName());
        viewHolder.choose.setChecked(node.isChoosed());

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.处理自身选中状态;2.处理父节点选中状态;3.处理子节点选中状态;4.处理多选事件;5.刷新界面
                handleSelfCheckState(node, finalViewHolder.choose.isChecked());
                handleParentCheckState(node, finalViewHolder.choose.isChecked());
                handleChildrenCheckState(node, finalViewHolder.choose.isChecked());
                handleMultiChoiceEvent(node, finalViewHolder.choose.isChecked());
                notifyDataSetChanged();
            }
        });

        return convertView;
    }



    private final class ViewHolder {
        ImageView icon;
        TextView label;
        CheckBox choose;
    }

}
