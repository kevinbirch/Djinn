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
 * Created: 11/3/11 10:29 PM
 */

package com.webguys.djinn.ifrit.model;

import com.google.common.collect.Iterables;
import com.webguys.djinn.AbstractDjinnTest;
import com.webguys.djinn.marid.runtime.Dictionary;
import com.webguys.djinn.marid.runtime.FunctionConditionAlreadyDefinedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MethodTest extends AbstractDjinnTest
{
    private Method method;

    @Before
    public void setUp() throws Exception
    {
        super.setUp();
    }

    @Test
    public void simpleFunction() throws Exception
    {
        this.parseAndLoad("djinn/simpleFunction.djinn", "inc");

        Assert.assertNotNull(this.method);
        Assert.assertFalse(Iterables.isEmpty(this.method.getMembers()));

        this.stack.push(new IntegerAtom(5));

        this.method.execute(this.context);

        this.assertStackSize(1);
        this.assertStackTop(Integer.valueOf(6));
    }

    @Test
    public void simpleFunctionWithPattern_match() throws Exception
    {
        this.parseAndLoad("djinn/simpleFunctionWithPattern.djinn", "inc?");

        Assert.assertNotNull(this.method);
        Assert.assertFalse(Iterables.isEmpty(this.method.getMembers()));

        this.stack.push(new IntegerAtom(5));

        this.method.execute(this.context);

        this.assertStackSize(1);
        this.assertStackTop(Integer.valueOf(6));
    }

    @Test
    public void simpleFunctionWithPattern_noMatch() throws Exception
    {
        this.parseAndLoad("djinn/simpleFunctionWithPattern.djinn", "inc?");

        Assert.assertNotNull(this.method);
        Assert.assertFalse(Iterables.isEmpty(this.method.getMembers()));

        this.stack.push(new IntegerAtom(0));

        this.method.execute(this.context);

        this.assertStackSize(1);
        this.assertStackTop(Integer.valueOf(0));
    }

    @Test
    public void method_match() throws Exception
    {
        this.parseAndLoad("djinn/method.djinn", "inc!");

        Assert.assertNotNull(this.method);
        Assert.assertFalse(Iterables.isEmpty(this.method.getMembers()));

        this.stack.push(new IntegerAtom(5));

        this.method.execute(this.context);

        this.assertStackSize(1);
        this.assertStackTop(Integer.valueOf(7));
    }

    @Test
    public void method_noMatch() throws Exception
    {
        this.parseAndLoad("djinn/method.djinn", "inc!");

        Assert.assertNotNull(this.method);
        Assert.assertFalse(Iterables.isEmpty(this.method.getMembers()));

        this.stack.push(new IntegerAtom(2));

        this.method.execute(this.context);

        this.assertStackSize(1);
        this.assertStackTop(Integer.valueOf(3));
    }

    @Test
    public void functionWithSingleDeclaration() throws Exception
    {
        this.parseAndLoad("djinn/functionWithSingleDeclaration.djinn", "dipinc");

        Assert.assertNotNull(this.method);
        Assert.assertFalse(Iterables.isEmpty(this.method.getMembers()));

        this.stack.push(new IntegerAtom(2));
        this.stack.push(new StringAtom("foo"));

        this.method.execute(this.context);

        this.assertStackSize(2);
        this.assertStackTop("foo");
        this.assertStackIndex(1, Integer.valueOf(3));
    }

    @Test
    public void functionWithCompoundDeclaration() throws Exception
    {
        this.parseAndLoad("djinn/functionWithCompoundDeclaration.djinn", "foo");

        Assert.assertNotNull(this.method);
        Assert.assertFalse(Iterables.isEmpty(this.method.getMembers()));

        this.stack.push(new IntegerAtom(3));

        this.method.execute(this.context);

        this.assertStackSize(3);
        this.assertStackTop("foo");
        this.assertStackIndex(1, Integer.valueOf(2));
        this.assertStackIndex(2, Integer.valueOf(9));
    }

    @Test
    public void functionWithInnerFunction() throws Exception
    {
        this.parseAndLoad("djinn/functionWithInnerFunction.djinn", "foo");

        Assert.assertNotNull(this.method);
        Assert.assertFalse(Iterables.isEmpty(this.method.getMembers()));

        this.stack.push(new IntegerAtom(3));

        this.method.execute(this.context);

        this.assertStackSize(1);
        this.assertStackTop(10);
    }

    @Test
    public void functionWithInnerMethod() throws Exception
    {
        this.parseAndLoad("djinn/functionWithInnerMethod.djinn", "foo");

        Assert.assertNotNull(this.method);
        Assert.assertFalse(Iterables.isEmpty(this.method.getMembers()));

        this.stack.push(new IntegerAtom(3));

        this.method.execute(this.context);

        this.assertStackSize(1);
        this.assertStackTop(11);
    }

    @Test
    public void functionWithInnerFunctionAndDeclaration() throws Exception
    {
        this.parseAndLoad("djinn/functionWithInnerFunctionAndDeclaration.djinn", "foo");

        Assert.assertNotNull(this.method);
        Assert.assertFalse(Iterables.isEmpty(this.method.getMembers()));

        this.stack.push(new IntegerAtom(3));

        this.method.execute(this.context);

        this.assertStackSize(1);
        this.assertStackTop(14);
    }

    @Test
    public void redefineFunction_allowed() throws Exception
    {
        this.parseAndLoad("djinn/redefineFunction.djinn", "inc!");

        Assert.assertNotNull(this.method);
        Assert.assertFalse(Iterables.isEmpty(this.method.getMembers()));

        this.stack.push(new IntegerAtom(5));

        this.method.execute(this.context);

        this.assertStackSize(1);
        this.assertStackTop(Integer.valueOf(8));
    }

    @Test(expected = FunctionConditionAlreadyDefinedException.class)
    public void redefineFunction_forbidden() throws Exception
    {
        Dictionary.setRedefinitionAllowed(false);

        this.parseAndLoad("djinn/redefineFunction.djinn", "inc!");
    }

    private void parseAndLoad(String path, String name) throws Exception
    {
        this.runtime.loadSourceFile(path, this.dictionary);

        this.method = this.dictionary.getMethod(name);
    }
}
