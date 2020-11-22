package ex1.src;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class WGraph_DS implements weighted_graph{
    private HashMap<Integer, HashMap<Integer, Double>> edges;
    private HashMap<Integer, node_info> map;
    private int size;
    private int change;
    private int edgesamount;

    private class NodeInfo implements node_info, Comparable<node_info> {
        private int key;
        private String info;
        private double tag;

        public NodeInfo (int key) {
            this.key = key;
            this.info = " ";
            this.tag = 0;
        }


        @Override
        public int getKey() {
            return key;
        }


        @Override
        public String getInfo() {
            return info;
        }


        @Override
        public void setInfo(String s) {
            this.info = s;
        }


        @Override
        public double getTag() {
            return tag;
        }


        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        @Override
        public int compareTo(node_info o) {
            if (this.getTag() < o.getTag()) return -1;
            else if (this.getTag() > o.getTag()) return 1;
            return 0;
        }
        public boolean equals(Object other){
            if (!(other instanceof NodeInfo)) return false;
            NodeInfo n = (NodeInfo) other;
            return (n.getKey()==this.getKey() && n.getTag()==this.getTag() && n.getInfo()==this.getInfo());

        }
    }

    public WGraph_DS (){
        this.size = 0;
        this.map = new HashMap<Integer, node_info>();
        this.edges = new HashMap<Integer, HashMap<Integer, Double>> ();
        this.change = 0;
        this.edgesamount = 0;
    }

    public boolean equals(Object other){
        if (!(other instanceof WGraph_DS)) return false;
        WGraph_DS g = (WGraph_DS)other;
        ArrayList<node_info> gnodes = new ArrayList<node_info>();
        ArrayList<node_info> thisnodes = new ArrayList<node_info>();
        for (node_info n : g.getV()) {
            gnodes.add(n);
        }
        for (node_info m : this.getV()){
            thisnodes.add(m);
        }
        if  (gnodes.size()!=thisnodes.size()) return false;
        for (int i=0; i<gnodes.size(); i++){
            node_info c = gnodes.get(i);
            node_info d = thisnodes.get(i);
            if (!c.equals(d)) return false;
            ArrayList<node_info> thisni = new ArrayList<node_info>();
            for (node_info o : this.getV(c.getKey())) {
                thisni.add(o);
            }
            if (thisni.size()!=g.getV(c.getKey()).size()) return false;
            int j = 0;
            for (node_info e : g.getV(c.getKey())){
                node_info f = thisni.get(j);
                if (!e.equals(f)) return false;
                if (getEdge(c.getKey(),e.getKey())!=getEdge(d.getKey(),f.getKey())) return false;
                j++;
            }
        }
        return true;
    }



    @Override
    public node_info getNode(int key) {
        if (map.containsKey(key)) return map.get(key);
        else return null;
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        if (map.containsKey(node1) && map.containsKey(node2) && node1 != node2){
            if (edges.containsKey(node1) && edges.containsKey(node2)){
                if (edges.get(node1).containsKey(node2) || edges.get(node2).containsKey(node1)) return true;
            }
        }
        return false;
    }

    @Override
    public double getEdge(int node1, int node2) {
        if (hasEdge(node1,node2) && node1 != node2){
            return edges.get(node1).get(node2);
        }
        return -1;
    }

    @Override
    public void addNode(int key) {
        if (!map.containsKey(key)){
            node_info n = new NodeInfo(key);
            map.put(key, n);
            HashMap e = new HashMap<Integer, Double> ();
            edges.put(key,e);
            size++;
            change++;
        }
    }

    @Override
    public void connect(int node1, int node2, double w) {
        if (node1 != node2){
            if (hasEdge(node1,node2)){
                edges.get(node1).replace(node2,w);
                edges.get(node2).replace(node1,w);
            }
            else {
                edges.get(node1).put(node2,w);
                edges.get(node2).put(node1,w);
                edgesamount++;
            }
            change++;
        }
    }

    @Override
    public Collection<node_info> getV() {
        return map.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        if (map.containsKey(node_id)){
            Collection<node_info> col = new ArrayList<node_info>();
            Iterator<Integer> it = edges.get(node_id).keySet().iterator();
            while (it.hasNext()){
                int i = it.next();
                if (map.get(i)!=null) col.add(map.get(i));
            }
            return col;
        }
        return null;
    }

    @Override
    public node_info removeNode(int key) {
        if (!map.containsKey(key)) return null;
        else {
            node_info out = map.get(key);
            Iterator <node_info> it = getV(key).iterator();
            while (it.hasNext()){
                node_info n = it.next();
                edges.get(n.getKey()).remove(key);
                edgesamount--;
            }
            edges.remove(key);
            map.remove(key);
            size--;
            change++;
            return out;
        }
    }

    @Override
    public void removeEdge(int node1, int node2) {
        if (map.containsKey(node1) && map.containsKey(node2) && hasEdge(node1, node2)){
            edges.get(node1).remove(node2);
            edges.get(node2).remove(node1);
            change++;
            edgesamount--;
        }
    }

    @Override
    public int nodeSize() {
        return size;
    }

    @Override
    public int edgeSize() {
        return edgesamount;
    }

    @Override
    public int getMC() {
        return change;
    }
}
