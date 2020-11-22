package ex1.src;



import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph gr;

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     *
     * @param src - start node
     * @param dest - end (target) node
     * @param gr - the given graph
     * @return list of nodes
     */

   public List <node_info> Dijkstra(int src, int dest, weighted_graph gr) {
        Iterator<node_info> resetnodes = gr.getV().iterator();
        while (resetnodes.hasNext()) {
            node_info r = resetnodes.next();
            r.setTag(Double.MAX_VALUE);
            r.setInfo(" ");
        }
        PriorityQueue<node_info> q = new PriorityQueue<>();
        HashMap<Integer, Integer> parents = new HashMap();
        q.add(gr.getNode(src));
        gr.getNode(src).setTag(0);
        while (!q.isEmpty()) {
            node_info curr = q.poll();
            if (curr.getInfo() == " ") {
                curr.setInfo("v");
                if (curr.getKey() == dest) break;
                Iterator<node_info> it = gr.getV(curr.getKey()).iterator();
                while (it.hasNext()) {
                    node_info n = it.next();
                    if (n.getInfo() == " ") {
                        double temp = gr.getEdge(curr.getKey(), n.getKey());
                        if (temp != -1 && temp + curr.getTag() < n.getTag()) {
                            n.setTag(temp + curr.getTag());
                            if (parents.containsKey(n.getKey())) parents.remove(n.getKey());
                            parents.put(n.getKey(), curr.getKey());
                            if (!q.contains(n)) q.add(n);
                        }
                    }
                }
            }
        }
            List<node_info> path = new ArrayList<node_info>();
            if (gr.getNode(dest).getTag() == Double.MAX_VALUE) return null;
            int assemble = dest;
            while (assemble != src) {
                node_info par = gr.getNode(assemble);
                path.add(par);
                assemble = parents.get(assemble);
            }
            path.add(gr.getNode(src));
            List<node_info> reversedpath = new ArrayList<node_info>();
            for (int i = path.size() - 1; i >= 0; i--) {
                reversedpath.add(path.get(i));
            }
            return reversedpath;
        }

    @Override
    public void init(weighted_graph g) {
        this.gr = g;
    }

    @Override
    public weighted_graph getGraph() {
        return gr;
    }

    @Override
    public weighted_graph copy() {
       if (gr.nodeSize() == 0) return gr;
       weighted_graph c = new WGraph_DS();
       Iterator<node_info> it1 = gr.getV().iterator();
       for (node_info n : gr.getV()){
            c.addNode(n.getKey());
       }
       for (node_info n : gr.getV()){
       for (node_info m : gr.getV(n.getKey())){
            c.connect(n.getKey(),m.getKey(),gr.getEdge(n.getKey(),m.getKey()));
            }
       }
       return c;
    }

    @Override
    public boolean isConnected() {
        if (gr == null) return false;
        if (gr.getV().size() == 1 || gr.getV().size() == 0) return true;
        Iterator<node_info> resetag = gr.getV().iterator();
        while (resetag.hasNext()){
            node_info r = resetag.next();
            r.setTag(0);
        }
        int id = 1;
        Queue<node_info> q = new LinkedList<node_info>();
        Iterator<node_info> it = gr.getV().iterator();
        while (it.hasNext()) {
            node_info l = it.next();
            if (l.getTag() == 0){
                q.add(l);
                l.setTag(id);
                while (!q.isEmpty()) {
                    node_info n = q.poll();
                    Iterator<node_info> it1 = gr.getV(n.getKey()).iterator();
                    while (it1.hasNext()) {
                        node_info a = it1.next();
                        if (a.getTag() == 0) {
                            q.add(a);
                            a.setTag(id);
                        }
                    }
                }
                id++;
            }
        }
        Iterator<node_info> check = gr.getV().iterator();
        while (check.hasNext()) {
            node_info c = check.next();
            if (c.getTag() != 1) return false;
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (gr.nodeSize() == 0 || gr.getNode(src) == null || gr.getNode(dest) == null) return -1;
        if (src == dest ) return 0;
        List <node_info> path = Dijkstra(src,dest,gr);
        if (path == null) return -1;
        if (path.get(0) == gr.getNode(src)) return gr.getNode(dest).getTag();
        return -1;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if (src == dest || gr.getNode(src) == null || gr.getNode(dest) == null) return null;
        List <node_info> path= Dijkstra(src,dest,gr);
        if (path.get(0) == gr.getNode(src)) return path;
        else return null;
    }


    @Override
    public boolean save(String file) {
        try {
            PrintWriter pw = new PrintWriter(new File(file));
            StringBuilder sb = new StringBuilder();
            for (node_info n : gr.getV()){
                sb.append(n.getKey());
                sb.append(",");
                sb.append(n.getInfo());
                sb.append(",");
                sb.append(n.getTag());
                for (node_info a : gr.getV(n.getKey())){
                    sb.append(",");
                    sb.append(a.getKey());
                    sb.append(",");
                    sb.append(gr.getEdge(n.getKey(),a.getKey()));
                }
                sb.append("\n");
                pw.write(sb.toString());
                sb.setLength(0);
            }
            pw.close();
        }catch (FileNotFoundException e){
                e.printStackTrace();
                return false;
        }


        return true;
    }


    @Override
    public boolean load(String file) {
        weighted_graph ng = new WGraph_DS();
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null){
                String [] graphinfo = line.split(",");
                int key = Integer.parseInt(graphinfo[0]);
                ng.addNode(key);
                ng.getNode(key).setInfo(graphinfo[1]);
                ng.getNode(key).setTag(Double.parseDouble(graphinfo[2]));
                for (int i=3 ; i<graphinfo.length ; i++){
                    int nikey = Integer.parseInt(graphinfo[i]);
                    ng.addNode(nikey);
                    ng.connect(key,nikey,Double.parseDouble(graphinfo[i+1]));
                    i++;
                }

            }

        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        init(ng);
        return true;
    }
}
