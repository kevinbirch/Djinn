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
 * Created: 10/30/11 7:48 PM
 */

package com.webguys.djinn.marid.builtin;

import com.webguys.djinn.ifrit.model.Function;
import com.webguys.djinn.ifrit.model.Method;
import com.webguys.djinn.ifrit.model.StringAtom;
import com.webguys.djinn.marid.runtime.Context;
import com.webguys.djinn.marid.runtime.Dictionary;
import com.webguys.djinn.marid.runtime.Stack;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SwapTest
{
    private Function function;
    private Context context;
    private Stack stack;

    @Before
    public void setUp() throws Exception
    {
        Method method = new Method("swap");
        this.function = new Swap(method);

        this.stack = new Stack();
        this.stack.push(new StringAtom("bar"));
        this.stack.push(new StringAtom("foo"));

        this.context = new Context(this.stack, Dictionary.getRootDictionary());
    }

    @Test
    public void execute() throws Exception
    {
        Assert.assertEquals("foo", this.stack.peek().getValue());

        this.function.execute(this.context);

        Assert.assertEquals("bar", this.stack.peek().getValue());
    }
}
