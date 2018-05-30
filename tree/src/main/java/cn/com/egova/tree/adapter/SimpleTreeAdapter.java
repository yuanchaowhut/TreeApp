package cn.com.egova.tree.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.com.egova.tree.R;
import cn.com.egova.tree.bean.Node;


public class SimpleTreeAdapter<T> extends TreeListViewAdapter<T> {
    public SimpleTreeAdapter(ListView mTree, Context context, List<T> data, int defaultExpandLevel) throws IllegalArgumentException,
            IllegalAccessException {
        super(mTree, context, data, defaultExpandLevel);
    }

    @Override
    public View getConvertView(Node node, int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.lst_item_tree, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.id_treenode_icon);
            viewHolder.label = (TextView) convertView.findViewById(R.id.id_treenode_label);
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

        return convertView;
    }

    private final class ViewHolder {
        ImageView icon;
        TextView label;
    }

}
