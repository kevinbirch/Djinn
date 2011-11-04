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
 * Created: 10/30/11 7:57 PM
 */

package com.webguys.djinn.ifrit.model;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.webguys.djinn.AbstractDjinnTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LambdaTest extends AbstractDjinnTest
{
    private Lambda lambda;
    private List<Atom> body;

    @Before
    public void setUp()
    {
        super.setUp();

        this.body = ImmutableList.<Atom>of(new StringAtom("foo"), new StringAtom("bar"));
        this.lambda = new Lambda(this.body);
    }

    @Test
    public void execute() throws Exception
    {
        this.assertStackSize(0);

        this.lambda.execute(this.context);

        this.assertStackSize(2);
        Assert.assertEquals("bar", this.stack.peek().getValue());
        Assert.assertEquals("foo", this.stack.peek(1).getValue());
    }

    @Test
    public void getBody() throws Exception
    {
        Assert.assertEquals(this.body, this.lambda.getBody());
    }

    @Test
    public void getValue() throws Exception
    {
        Assert.assertEquals(this.body, this.lambda.getValue());
    }

    @Test
    public void toSourceRep() throws Exception
    {
        Assert.assertEquals("[\"foo\" \"bar\"]", this.lambda.toSourceRep());
    }

    @Test
    public void getTypeName() throws Exception
    {
        Assert.assertEquals("Lambda", this.lambda.getTypeName());
    }
}
