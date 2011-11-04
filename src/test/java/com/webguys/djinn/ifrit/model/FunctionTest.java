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
import com.webguys.djinn.ifrit.Mk1_Mod0Lexer;
import com.webguys.djinn.ifrit.Mk1_Mod0ModelGenerator;
import com.webguys.djinn.ifrit.Mk1_Mod0Parser;
import com.webguys.djinn.ifrit.Mk1_Mod0Parser.translation_unit_return;
import com.webguys.djinn.marid.runtime.Dictionary;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FunctionTest extends AbstractDjinnTest
{
    private Method method;

    @Before
    public void setUp()
    {
        super.setUp();

        try
        {
            this.parse("djinn/primitives.djinn", Dictionary.getRootDictionary());
        }
        catch(Exception e)
        {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void simpleFunction() throws Exception
    {
        this.parseAndLoad("mk1_mod0/simpleFunction.djinn", "inc");

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
        this.parseAndLoad("mk1_mod0/simpleFunctionWithPattern.djinn", "inc?");

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
        this.parseAndLoad("mk1_mod0/simpleFunctionWithPattern.djinn", "inc?");

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
        this.parseAndLoad("mk1_mod0/method.djinn", "inc!");

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
        this.parseAndLoad("mk1_mod0/method.djinn", "inc!");

        Assert.assertNotNull(this.method);
        Assert.assertFalse(Iterables.isEmpty(this.method.getMembers()));

        this.stack.push(new IntegerAtom(2));

        this.method.execute(this.context);

        this.assertStackSize(1);
        this.assertStackTop(Integer.valueOf(3));
    }

    private void parseAndLoad(String path, String name) throws Exception
    {
        this.parse(path, this.dictionary);

        this.method = this.dictionary.getMethod(name);
    }

    private void parse(String path, Dictionary dictionary) throws Exception
    {
        ClassLoader loader = this.getClass().getClassLoader();
        ANTLRInputStream input = new ANTLRInputStream(loader.getResourceAsStream(path));
        Mk1_Mod0Lexer lexer = new Mk1_Mod0Lexer(input);

        CommonTokenStream stream = new CommonTokenStream(lexer);
        Mk1_Mod0Parser parser = new Mk1_Mod0Parser(stream);

        translation_unit_return result = parser.translation_unit();
        CommonTree tree = (CommonTree)result.getTree();

        CommonTreeNodeStream nodes = new CommonTreeNodeStream(tree);
        nodes.setTokenStream(stream);

        Mk1_Mod0ModelGenerator generator = new Mk1_Mod0ModelGenerator(nodes, dictionary);
        Module module = generator.translation_unit();
    }
}
