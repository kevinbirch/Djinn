/**
 * The MIT License
 *
 * Copyright (c) 2011 Kevin Birch <kevin.birch@gmail.com>. Some rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Created: 10/30/11 8:13 PM
 */

package com.webguys.djinn.marid.runtime;

import com.webguys.djinn.ifrit.model.Atom;
import com.webguys.djinn.ifrit.model.StringAtom;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StackTest
{
    private Stack stack;

    @Before
    public void setUp() throws Exception
    {
        this.stack = new Stack();
    }

    @Test
    public void push() throws Exception
    {
        Assert.assertEquals(0, this.stack.depth());

        this.stack.push(new StringAtom("foo"));

        Assert.assertEquals(1, this.stack.depth());
        System.out.println(this.stack);
    }

    @Test
    public void pop() throws Exception
    {
        Assert.assertEquals(0, this.stack.depth());

        this.stack.push(new StringAtom("foo"));

        Assert.assertEquals(1, this.stack.depth());

        Atom atom = this.stack.pop();

        Assert.assertEquals(0, this.stack.depth());
        Assert.assertNotNull(atom);
        Assert.assertEquals("foo", atom.getValue());
    }

    @Test
    public void peek() throws Exception
    {
        Assert.assertEquals(0, this.stack.depth());

        this.stack.push(new StringAtom("foo"));

        Assert.assertEquals(1, this.stack.depth());

        Atom atom = this.stack.peek();

        Assert.assertEquals(1, this.stack.depth());
        Assert.assertNotNull(atom);
        Assert.assertEquals("foo", atom.getValue());
    }

    @Test
    public void peekDepth() throws Exception
    {
        Assert.assertEquals(0, this.stack.depth());

        this.stack.push(new StringAtom("foo"));
        this.stack.push(new StringAtom("bar"));

        Assert.assertEquals(2, this.stack.depth());

        Atom atom = this.stack.peek(1);

        Assert.assertEquals(2, this.stack.depth());
        Assert.assertNotNull(atom);
        Assert.assertEquals("foo", atom.getValue());
    }

    @Test
    public void drop() throws Exception
    {
        Assert.assertEquals(0, this.stack.depth());

        this.stack.push(new StringAtom("foo"));

        Assert.assertEquals(1, this.stack.depth());

        this.stack.drop();

        Assert.assertEquals(0, this.stack.depth());
    }

    @Test
    public void dup() throws Exception
    {
        Assert.assertEquals(0, this.stack.depth());

        this.stack.push(new StringAtom("foo"));

        Assert.assertEquals(1, this.stack.depth());

        this.stack.dup();

        Assert.assertEquals(2, this.stack.depth());
        Assert.assertEquals("foo", this.stack.peek().getValue());
        Assert.assertEquals("foo", this.stack.peek(1).getValue());
    }

    @Test
    public void swap() throws Exception
    {
        Assert.assertEquals(0, this.stack.depth());

        this.stack.push(new StringAtom("foo"));
        this.stack.push(new StringAtom("bar"));

        Assert.assertEquals(2, this.stack.depth());

        this.stack.swap();

        Assert.assertEquals(2, this.stack.depth());
        Assert.assertEquals("foo", this.stack.peek().getValue());
        Assert.assertEquals("bar", this.stack.peek(1).getValue());
    }
}
