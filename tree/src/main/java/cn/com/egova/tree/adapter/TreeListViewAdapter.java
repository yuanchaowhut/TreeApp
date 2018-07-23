package cn.com.egova.tree.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.com.egova.tree.TreeHelper;
import cn.com.egova.tree.bean.Node;


public abstract class TreeListViewAdapter<T> extends BaseAdapter {
    protected Context mContext;
    //存储所有可见的Node
    protected List<Node> mNodes;
    protected LayoutInflater mInflater;
    //存储所有的Node
    protected List<Node> mAllNodes;
    //是否支持多选
    protected boolean isMultiChoice;
    //点击的回调接口
    private OnTreeNodeClickListener onTreeNodeClickListener;
    private OnTreeNodeMultiChoiceListener onTreeNodeMultiChoiceListener;


    /**
     * @param mTree
     * @param context
     * @param data
     * @param defaultExpandLevel 默认展开几级树
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public TreeListViewAdapter(ListView mTree, Context context, List<T> data, int defaultExpandLevel, boolean isMultiChoice) throws IllegalArgumentException,
            IllegalAccessException {
        this.mContext = context;
        this.isMultiChoice = isMultiChoice;
        this.mInflater = LayoutInflater.from(context);
        //对所有的Node进行排序
        this.mAllNodes = TreeHelper.getSortedNodes(data, defaultExpandLevel);
        //过滤出可见的Node
        this.mNodes = TreeHelper.filterVisibleNode(mAllNodes);

        mTree.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.处理自身的展开和收缩动作
                expandOrCollapse(position);
                //2.传递点击事件
                if (onTreeNodeClickListener != null) {
                    onTreeNodeClickListener.onClick(mNodes.get(position), position);
                }
            }

        });

        mTree.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (onTreeNodeClickListener != null) {
                    onTreeNodeClickListener.onLongClick(mNodes.get(position), position);
                }
                return true;
            }
        });

    }

    /**
     * 相应ListView的点击事件 展开或关闭某节点
     *
     * @param position
     */
    public void expandOrCollapse(int position) {
        Node node = mNodes.get(position);
        if (node != null) {
            if (!node.isLeaf()) {
                node.setExpand(!node.isExpand());
                mNodes = TreeHelper.filterVisibleNode(mAllNodes);
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getCount() {
        return mNodes.size();
    }

    @Override
    public Object getItem(int position) {
        return mNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Node node = mNodes.get(position);
        convertView = getConvertView(node, position, convertView, parent);
        // 设置内边距
        convertView.setPadding(node.getLevel() * 30, 3, 3, 3);
        return convertView;
    }

    public abstract View getConvertView(Node node, int position, View convertView, ViewGroup parent);


    public void setOnTreeNodeClickListener(OnTreeNodeClickListener onTreeNodeClickListener) {
        this.onTreeNodeClickListener = onTreeNodeClickListener;
    }

    public void setOnTreeNodeMultiChoiceListener(OnTreeNodeMultiChoiceListener onTreeNodeMultiChoiceListener) {
        this.onTreeNodeMultiChoiceListener = onTreeNodeMultiChoiceListener;
    }


    /**
     * 1.处理自身的选中状态
     *
     * @param node
     * @param isChecked
     */
    protected void handleSelfCheckState(Node node, boolean isChecked) {
        node.setChoosed(isChecked);
    }

    /**
     * 2.处理父节点的选中状态
     *
     * @param node
     * @param isChecked
     */
    protected void handleParentCheckState(Node node, boolean isChecked) {
        if (node.getParent() != null) {
            List<Node> nodes = node.getParent().getChildren();
            boolean hasUnChoosed = false;
            for (Node n : nodes) {
                if (!n.isChoosed()) {
                    hasUnChoosed = true;
                    break;
                }
            }
            node.getParent().setChoosed(!hasUnChoosed);

            //递归.
            handleParentCheckState(node.getParent(), node.getParent().isChoosed());
        }
    }

    /**
     * 3.处理子节点的选中状态
     *
     * @param node
     * @param isChecked
     */
    protected void handleChildrenCheckState(Node node, boolean isChecked) {
        List<Node> children = node.getChildren();
        if (children != null && children.size() > 0) {
            for (Node n : children) {
                n.setChoosed(isChecked);

                //递归
                handleChildrenCheckState(n,n.isChoosed());
            }
        }
    }

    /**
     * 4.处理多选事件
     *
     * @param node
     * @param isChecked
     */
    protected void handleMultiChoiceEvent(Node node, boolean isChecked) {
        List<Node> selectedNodes = new ArrayList<Node>();
        for (Node n : mAllNodes) {
            if (n.isChoosed()) {
                selectedNodes.add(n);
            }
        }
        if (onTreeNodeMultiChoiceListener != null) {
            onTreeNodeMultiChoiceListener.onMultiChoice(selectedNodes);
        }
    }


    /**
     * 点击动作回调的接口
     */
    public interface OnTreeNodeClickListener {
        void onClick(Node node, int position);

        void onLongClick(Node node, int position);
    }

    /**
     * 点击动作回调的接口
     */
    public interface OnTreeNodeMultiChoiceListener {
        void onMultiChoice(List<Node> nodes);
    }
}
