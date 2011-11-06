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
 * Created: 11/6/11 1:36 AM
 */

package com.webguys.djinn.marid.primitive;

import com.google.common.collect.ImmutableList;
import com.webguys.djinn.ifrit.model.Atom;
import com.webguys.djinn.ifrit.model.Lambda;
import com.webguys.djinn.ifrit.model.Symbol;
import org.junit.Before;
import org.junit.Test;

public class ComposeTest extends AbstractBuiltinTest
{
    @Before
    public void setUp()
    {
        super.setUp(Compose.NAME, Compose.FACTORY);
    }

    @Test
    public void execute() throws Exception
    {
        this.stack.push(new Lambda(ImmutableList.<Atom>of(new Symbol("add"))));
        this.stack.push(new Lambda(ImmutableList.<Atom>of(new Symbol("sub"))));

        this.function.execute(this.context);

        this.assertStackSize(1);
        this.assertStackTop(ImmutableList.of(new Symbol("add"), new Symbol("sub")));
    }
}
