package ex1.tests;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest2 {

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
        g.connect(5,8,1);
        g.getNode(1).setTag(1.2);
        g.getNode(1).setInfo("hello");
        return g;
    }

    @Test
    void getNode_addNode() {
        weighted_graph g = creategraph();
        weighted_graph n = new WGraph_DS();
        n.addNode(1);
        n.getNode(1).setTag(1.2);
        n.getNode(1).setInfo("hello");
        assertEquals(n.getNode(1),g.getNode(1));

        g.removeNode(1);
        assertNull(g.getNode(1));
    }

    @Test
    void hasEdge() {
        weighted_graph g = creategraph();
        assertTrue(g.hasEdge(1,3));

        assertFalse(g.hasEdge(1,8));

        assertFalse(g.hasEdge(1,9));
    }

    @Test
    void getEdge() {
        weighted_graph g = creategraph();
        assertEquals(3,g.getEdge(1,2));

        assertEquals(-1,g.getEdge(1,4));
    }


    @Test
    void connect() {
        weighted_graph g = creategraph();
        g.connect(1,2,2);
        assertEquals(2,g.getEdge(1,2));

        g.connect(1,4,7);
        assertEquals(7,g.getEdge(1,4));
    }

    @Test
    void getV() {
        weighted_graph g = creategraph();
        ArrayList<node_info> c = new ArrayList<node_info>();
        c.add(g.getNode(1));
        c.add(g.getNode(2));
        c.add(g.getNode(3));
        c.add(g.getNode(4));
        c.add(g.getNode(5));
        c.add(g.getNode(6));
        c.add(g.getNode(7));
        c.add(g.getNode(8));
        int i= 0;
        for (node_info n : g.getV()){
            assertEquals(c.get(i),n);
            i++;
        }
        i = 0;
        g.removeNode(1);
        for (node_info n : g.getV()){
            assertNotEquals(c.get(i),n);
        }
    }

    @Test
    void testGetV() {
        weighted_graph g = creategraph();
        Collection<node_info> l = new ArrayList<node_info>();
        l.add(g.getNode(1));
        l.add(g.getNode(4));
        l.add(g.getNode(5));
        l.add(g.getNode(6));
        assertEquals(l,g.getV(2));

        l.add(g.getNode(7));
        assertNotEquals(l,g.getV(2));
        }

    @Test
    void removeNode() {
        weighted_graph g = creategraph();
        node_info n = g.getNode(2);
        assertEquals(n,g.removeNode(2));

        assertNull(g.getNode(2));

        assertFalse(g.hasEdge(1,2));

        assertFalse(g.hasEdge(4,2));

        assertFalse(g.hasEdge(5,2));

        assertFalse(g.hasEdge(6,2));
    }

    @Test
    void removeEdge() {
        weighted_graph g = creategraph();
        g.removeEdge(3,7);
        assertFalse(g.hasEdge(3,7));
    }

    @Test
    void nodeSize() {
        weighted_graph g = creategraph();
        assertEquals(8,g.nodeSize());
    }

    @Test
    void edgeSize() {
        weighted_graph g = creategraph();
        assertEquals(9,g.edgeSize());

        g.removeNode(1);
        assertEquals(7,g.edgeSize());

    }

    @Test
    void getMC() {
        weighted_graph g = creategraph();
        assertEquals(17,g.getMC());

        g.addNode(9);
        assertEquals(18,g.getMC());
    }
}
