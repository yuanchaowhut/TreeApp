package cn.com.egova.tree;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.com.egova.tree.annotation.TreeNodeId;
import cn.com.egova.tree.annotation.TreeNodeLabel;
import cn.com.egova.tree.annotation.TreeNodePid;
import cn.com.egova.tree.bean.Node;

public class TreeHelper {
    /**
     * 传入我们的普通bean，转化为我们排序后的Node
     *
     * @param data
     * @param defaultExpandLevel
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static <T> List<Node> getSortedNodes(List<T> data, int defaultExpandLevel) throws IllegalArgumentException,
            IllegalAccessException {
        List<Node> result = new ArrayList<Node>();
        // List<T>转化为List<Node>并且明确父子节点关系
        List<Node> nodes = convertData2Node(data);
        // 获取所有根节点(即第一级节点，通过parent==null?区分)
        List<Node> rootNodes = getRootNodes(nodes);
        // 排序以及设置Node间关系
        for (Node node : rootNodes) {
            addNode(result, node, defaultExpandLevel, 1);
        }
        return result;
    }

    /**
     * 过滤出所有可见的Node
     *
     * @param nodes
     * @return
     */
    public static List<Node> filterVisibleNode(List<Node> nodes) {
        List<Node> result = new ArrayList<Node>();

        for (Node node : nodes) {
            // 如果为根节点，或者上层目录为展开状态
            if (node.isRoot() || node.isParentExpand()) {
                setNodeIcon(node);
                result.add(node);
            }
        }
        return result;
    }

    /**
     * 将我们的数据转化为树的节点
     *
     * @param data
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private static <T> List<Node> convertData2Node(List<T> data) throws IllegalArgumentException, IllegalAccessException {
        List<Node> nodes = new ArrayList<Node>();
        Node node = null;

        for (T t : data) {
            int id = -1;
            int pId = -1;
            String label = null;
            Class<? extends Object> clazz = t.getClass();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field f : declaredFields) {
                if (f.getAnnotation(TreeNodeId.class) != null) {
                    f.setAccessible(true);
                    id = f.getInt(t);
                }
                if (f.getAnnotation(TreeNodePid.class) != null) {
                    f.setAccessible(true);
                    pId = f.getInt(t);
                }
                if (f.getAnnotation(TreeNodeLabel.class) != null) {
                    f.setAccessible(true);
                    label = (String) f.get(t);
                }
                if (id != -1 && pId != -1 && label != null) {
                    break;
                }
            }
            node = new Node(id, pId, label);
            node.setT(t);
            nodes.add(node);
        }

        /**
         * 设置Node间，父子关系;让每两个节点都比较一次，即可设置其中的关系
         */
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                Node m = nodes.get(j);
                if (m.getpId() == n.getId()) {
                    n.getChildren().add(m);
                    m.setParent(n);
                } else if (m.getId() == n.getpId()) {
                    m.getChildren().add(n);
                    n.setParent(m);
                }
            }
        }

        // 设置图片
        for (Node n : nodes) {
            setNodeIcon(n);
        }
        return nodes;
    }

    private static List<Node> getRootNodes(List<Node> nodes) {
        List<Node> root = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node.isRoot()) {
                root.add(node);
            }
        }
        return root;
    }

    /**
     * 把一个节点上的所有的内容都挂上去
     */
    private static void addNode(List<Node> result, Node root, int defaultExpandLeval, int currentLevel) {
        result.add(root);
        if (defaultExpandLeval >= currentLevel) {
            root.setExpand(true);
        }

        if (root.isLeaf()){
            return;
        }

        for (int i = 0; i < root.getChildren().size(); i++) {
            addNode(result, (Node) root.getChildren().get(i), defaultExpandLeval, currentLevel + 1);
        }
    }

    /**
     * 设置节点的图标
     *
     * @param node
     */
    private static void setNodeIcon(Node node) {
        if (node.getChildren().size() > 0 && node.isExpand()) {
            node.setIcon(R.drawable.tree_arrow_bottom);
        } else if (node.getChildren().size() > 0 && !node.isExpand()) {
            node.setIcon(R.drawable.tree_arrow_right);
        } else
            node.setIcon(-1);

    }

}
