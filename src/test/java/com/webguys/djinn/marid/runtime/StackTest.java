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

import com.webguys.djinn.AbstractDjinnTest;
import com.webguys.djinn.ifrit.model.Atom;
import com.webguys.djinn.ifrit.model.StringAtom;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StackTest extends AbstractDjinnTest
{
    @Before
    public void setUp() throws Exception
    {
        super.setUp();
    }

    @Test
    public void push() throws Exception
    {
        this.assertStackSize(0);

        this.stack.push(new StringAtom("foo"));

        this.assertStackSize(1);
        System.out.println(this.stack);
    }

    @Test
    public void pop() throws Exception
    {
        this.assertStackSize(0);

        this.stack.push(new StringAtom("foo"));

        this.assertStackSize(1);

        Atom atom = this.stack.pop();

        this.assertStackSize(0);
        Assert.assertNotNull(atom);
        Assert.assertEquals("foo", atom.getValue());
    }

    @Test
    public void peek() throws Exception
    {
        this.assertStackSize(0);

        this.stack.push(new StringAtom("foo"));

        this.assertStackSize(1);

        Atom atom = this.stack.peek();

        this.assertStackSize(1);
        Assert.assertNotNull(atom);
        Assert.assertEquals("foo", atom.getValue());
    }

    @Test
    public void peekDepth() throws Exception
    {
        this.assertStackSize(0);

        this.stack.push(new StringAtom("foo"));
        this.stack.push(new StringAtom("bar"));

        this.assertStackSize(2);

        Atom atom = this.stack.peek(1);

        this.assertStackSize(2);
        Assert.assertNotNull(atom);
        Assert.assertEquals("foo", atom.getValue());
    }

    @Test
    public void drop() throws Exception
    {
        this.assertStackSize(0);

        this.stack.push(new StringAtom("foo"));

        this.assertStackSize(1);

        this.stack.drop();

        this.assertStackSize(0);
    }

    @Test
    public void dup() throws Exception
    {
        this.assertStackSize(0);

        this.stack.push(new StringAtom("foo"));

        this.assertStackSize(1);

        this.stack.dup();

        this.assertStackSize(2);
        Assert.assertEquals("foo", this.stack.peek().getValue());
        Assert.assertEquals("foo", this.stack.peek(1).getValue());
    }

    @Test
    public void swap() throws Exception
    {
        this.assertStackSize(0);

        this.stack.push(new StringAtom("foo"));
        this.stack.push(new StringAtom("bar"));

        this.assertStackSize(2);

        this.stack.swap();

        this.assertStackSize(2);
        Assert.assertEquals("foo", this.stack.peek().getValue());
        Assert.assertEquals("bar", this.stack.peek(1).getValue());
    }
}
