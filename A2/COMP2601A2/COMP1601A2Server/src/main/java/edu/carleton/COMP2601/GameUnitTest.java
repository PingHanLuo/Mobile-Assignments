package edu.carleton.COMP2601;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Luo on 2017-02-22.
 */

public class GameUnitTest {
    @Test
    public void testGame(){
        Game g = new Game("bob","alice");
        Assert.assertTrue(g.place(0,"bob"));
        Assert.assertTrue(g.getPreviousSymbol()=='x');
        Assert.assertTrue(g.getRecentSymbol()=='o');
        Assert.assertFalse(g.place(1,"bob"));
        Assert.assertFalse(g.place(0,"alice"));
        Assert.assertTrue(g.place(1,"alice"));
        g.place(2,"bob");
        g.place(3,"alice");
        g.place(4,"bob");
        g.place(5,"alice");
        g.place(6,"bob");
        Assert.assertTrue(g.getResult().equals("bob"));
    }
}
