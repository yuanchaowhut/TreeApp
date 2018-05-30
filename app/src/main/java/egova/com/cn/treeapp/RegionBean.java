package egova.com.cn.treeapp;


import java.io.Serializable;

import cn.com.egova.tree.annotation.TreeNodeId;
import cn.com.egova.tree.annotation.TreeNodeLabel;
import cn.com.egova.tree.annotation.TreeNodePid;

/**
 * Created by yuanchao on 2017/7/12.
 */

public class RegionBean implements Serializable{
    private static final long serialVersionUID = 1142354686070936L;
    @TreeNodeId
    private int id;
    @TreeNodePid
    private int pid;
    @TreeNodeLabel
    private String name;
    //省级、市级、县区级、乡镇级
    private String type;

    public RegionBean() {
    }

    public RegionBean(int id, int pid, String name, String type) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RegionBean{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
