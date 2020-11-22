package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest2 {

    weighted_graph creategraph (){
        weighted_graph g = new WGraph_DS();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addNode(8);
        g.connect(1,2,3);
        g.connect(1,3,1);
        g.connect(2,4,2);
        g.connect(2,6,3);
        g.connect(2,5,4);
        g.connect(3,4,3);
        g.connect(3,7,1);
        g.connect(4,7,1);
        g.connect(5,8,6);
        return g;
    }


    @Test
    void init() {
        weighted_graph g = creategraph();
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        assertEquals(g,ga.getGraph());
    }

    @Test
    void getGraph() {
        weighted_graph g = creategraph();
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        assertEquals(g,ga.getGraph());
    }

    @Test
    void copy() {
        weighted_graph g = new WGraph_DS();
        weighted_graph g1 = creategraph();
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        assertEquals(g,ga.copy());

        ga.init(g1);
        assertEquals(g1,ga.copy());
    }

    @Test
    void isConnected() {
        weighted_graph g = new WGraph_DS();
        weighted_graph g1 = creategraph();
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        assertTrue(ga.isConnected());

        g.addNode(2);
        assertTrue(ga.isConnected());

        ga.init(g1);
        assertTrue(ga.isConnected());

        g1.removeNode(2);
        assertFalse(ga.isConnected());
    }

    @Test
    void shortestPathDist() {
        weighted_graph g = new WGraph_DS();
        weighted_graph g1 = creategraph();
        weighted_graph_algorithms ga = new WGraph_Algo();

        ga.init(g1);
        assertEquals(3,ga.shortestPathDist(1,4));

        ga.init(g);
        assertEquals(-1,ga.shortestPathDist(1,2));

        g.addNode(1);
        g.addNode(2);
        assertEquals(-1,ga.shortestPathDist(0,1));

        assertEquals(-1,ga.shortestPathDist(1,2));
    }

    @Test
    void shortestPath() {
        weighted_graph g = new WGraph_DS();
        weighted_graph g1 = creategraph();
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g1);
        List<node_info> l1 = new ArrayList<node_info>();
        l1.add(g1.getNode(1));
        l1.add(g1.getNode(3));
        l1.add(g1.getNode(7));
        l1.add(g1.getNode(4));
        ga.shortestPath(1,4);
        assertEquals(l1,ga.shortestPath(1,4));

        g1.removeNode(7);
        l1.remove(2);
        assertEquals(l1,ga.shortestPath(1,4));

        ga.init(g);
        assertNull(ga.shortestPath(1,0));
    }

    @Test
    void save_load() {
        weighted_graph g = new WGraph_DS();
        weighted_graph g1 = creategraph();
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g1);
        String str = "g0";
        ga.save(str);
        weighted_graph g2 = creategraph();;
        ga.load(str);
        assertEquals(g1,g2);
        g1.removeNode(1);
        assertNotEquals(g1,g2);

        ga.init(g);
        String str1 = "g";
        ga.save(str1);
        weighted_graph e = new WGraph_DS();;
        ga.load(str1);
        assertEquals(g,e);
        g.addNode(1);
        assertNotEquals(g,e);
    }


}