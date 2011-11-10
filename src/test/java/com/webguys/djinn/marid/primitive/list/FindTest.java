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
 * Created: 11/9/11 2:45 PM
 */

package com.webguys.djinn.marid.primitive.list;

import java.util.ArrayList;

import com.google.common.collect.Lists;
import com.webguys.djinn.ifrit.model.IntegerAtom;
import com.webguys.djinn.ifrit.model.Lambda;
import com.webguys.djinn.ifrit.model.ListAtom;
import com.webguys.djinn.ifrit.model.Symbol;
import com.webguys.djinn.marid.primitive.AbstractBuiltinTest;
import org.junit.Before;
import org.junit.Test;

public class FindTest extends AbstractBuiltinTest
{
    @Before
    public void setUp() throws Exception
    {
        super.setUp(Find.NAME);
    }

    @Test
    public void found() throws Exception
    {
        ArrayList<IntegerAtom> atoms = Lists.newArrayList(new IntegerAtom(1), new IntegerAtom(2), new IntegerAtom(3), new IntegerAtom(4));
        this.stack.push(new ListAtom(atoms));
        this.stack.push(new Lambda(Lists.newArrayList(new Symbol("even?"))));

        this.method.execute(this.context);

        this.assertStackSize(1);
        this.assertStackTop(2);
    }

    @Test
    public void notFound() throws Exception
    {
        ArrayList<IntegerAtom> atoms = Lists.newArrayList(new IntegerAtom(1), new IntegerAtom(3));
        this.stack.push(new ListAtom(atoms));
        this.stack.push(new Lambda(Lists.newArrayList(new Symbol("even?"))));

        this.method.execute(this.context);

        this.assertStackSize(0);
    }
}
