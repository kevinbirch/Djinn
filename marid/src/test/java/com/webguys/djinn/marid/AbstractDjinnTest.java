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
 * Created: 11/3/11 9:19 PM
 */

package com.webguys.djinn.marid;

import com.webguys.djinn.ifrit.model.Context;
import com.webguys.djinn.ifrit.model.Dictionary;
import com.webguys.djinn.ifrit.model.Stack;
import com.webguys.djinn.marid.runtime.FullStack;
import com.webguys.djinn.marid.runtime.Runtime;
import com.webguys.djinn.marid.runtime.SystemDictionary;
import org.junit.Assert;

public abstract class AbstractDjinnTest
{
    protected Stack stack;
    protected Dictionary dictionary;
    protected Context context;
    protected Runtime runtime;

    public void setUp() throws Exception
    {
        this.stack = new FullStack();
        this.dictionary = SystemDictionary.getRootDictionary().newChild();
        this.context = new Context(this.stack, this.dictionary);

        this.runtime = new Runtime();
        this.runtime.loadSourceFile("djinn/prelude.djinn", SystemDictionary.getRootDictionary());
    }

    protected void assertStackSize(int expected)
    {
        Assert.assertEquals(expected, this.stack.depth());
    }

    protected void assertStackTop(Object expected)
    {
        Assert.assertEquals(expected, this.stack.peek().getValue());
    }

    protected void assertStackIndex(int index, Object expected)
    {
        Assert.assertEquals(expected, this.stack.peek(index).getValue());
    }
}
