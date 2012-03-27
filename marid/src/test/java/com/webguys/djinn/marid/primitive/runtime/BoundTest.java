/*
 * The MIT License
 *
 * Copyright (c) 2012 Kevin Birch <kevin.birch@gmail.com>. Some rights reserved.
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
 */

package com.webguys.djinn.marid.primitive.runtime;

import com.webguys.djinn.ifrit.model.IntegerAtom;
import com.webguys.djinn.ifrit.model.Lambda;
import com.webguys.djinn.ifrit.model.SingleDeclaration;
import com.webguys.djinn.ifrit.model.StringAtom;
import com.webguys.djinn.marid.primitive.AbstractBuiltinTest;
import org.junit.Before;
import org.junit.Test;
import ponzu.impl.factory.Lists;

/**
 * Created: 3/20/12 12:21 AM
 */
public class BoundTest extends AbstractBuiltinTest
{
    @Before
    public void setUp() throws Exception
    {
        super.setUp(Bound.NAME);
    }

    @Test
    public void execute() throws Exception
    {
        this.dictionary.defineName(new SingleDeclaration("foo", new Lambda(Lists.immutable.of(new IntegerAtom(1)))));

        this.stack.push(new StringAtom("foo"));

        this.method.execute(this.context);

        this.assertStackTop(true);
    }

    @Test
    public void notBound() throws Exception
    {
        this.stack.push(new StringAtom("bar"));

        this.method.execute(this.context);

        this.assertStackTop(false);
    }
}