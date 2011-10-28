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
 * Created: 9/25/11 5:54 PM
 */

package com.webguys.djinn.ifrit;

import com.webguys.djinn.ifrit.Mk1_Mod0Parser.lambda_return;
import com.webguys.djinn.ifrit.Mk1_Mod0Parser.statement_return;
import com.webguys.djinn.ifrit.model.*;
import com.webguys.djinn.marid.runtime.Dictionary;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Mk1_Mod0Test
{
    private Dictionary dictionary;

    @Before
    public void setUp() throws Exception
    {
        this.dictionary = new Dictionary(Dictionary.getRootDictionary());
    }

    @Test
    public void stringLiteralAssignment() throws Exception
    {
        Mk1_Mod0Walker walker = this.parseAndWalk("mk1_mod0/stringLiteralAssignment.djinn");
        Executable result = walker.assignment_statement();

        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof SingleDeclaration);
        SingleDeclaration declaration = (SingleDeclaration)result;
        Assert.assertEquals("name", declaration.getName());
        Assert.assertNotNull(declaration.getDefinition());
        Assert.assertEquals(1, declaration.getDefinition().getBody().size());
        Assert.assertTrue(this.dictionary.isNameDefined("name"));
    }

    @Test
    public void integerLiteralAssignment() throws Exception
    {
        Mk1_Mod0Walker walker = this.parseAndWalk("mk1_mod0/integerLiteralAssignment.djinn");
        Executable result = walker.assignment_statement();

        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof SingleDeclaration);
        SingleDeclaration declaration = (SingleDeclaration)result;
        Assert.assertEquals("answer", declaration.getName());
        Assert.assertNotNull(declaration.getDefinition());
        Assert.assertEquals(1, declaration.getDefinition().getBody().size());
        Assert.assertTrue(this.dictionary.isNameDefined("answer"));
    }

    @Test
    public void decimalLiteralAssignment() throws Exception
    {
        Mk1_Mod0Walker walker = this.parseAndWalk("mk1_mod0/decimalLiteralAssignment.djinn");
        Executable result = walker.assignment_statement();

        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof SingleDeclaration);
        SingleDeclaration declaration = (SingleDeclaration)result;
        Assert.assertEquals("pi", declaration.getName());
        Assert.assertNotNull(declaration.getDefinition());
        Assert.assertEquals(1, declaration.getDefinition().getBody().size());
        Assert.assertTrue(this.dictionary.isNameDefined("pi"));
    }

    @Test
    public void compoundAssignment() throws Exception
    {
        Mk1_Mod0Walker walker = this.parseAndWalk("mk1_mod0/compoundAssignment.djinn");
        Declaration result = walker.assignment_statement();

        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof CompoundDeclaration);
        CompoundDeclaration declaration = (CompoundDeclaration)result;
        Assert.assertTrue(declaration.getNames().contains("a"));
        Assert.assertTrue(declaration.getNames().contains("b"));
        Assert.assertTrue(declaration.getNames().contains("c"));
        Assert.assertNotNull(declaration.getDefinition());
        Assert.assertEquals(3, declaration.getDefinition().getBody().size());
        Assert.assertTrue(this.dictionary.isNameDefined(result.getName()));
    }

    @Test
    public void functionWithPattern() throws Exception
    {
        Mk1_Mod0Walker walker = this.parseAndWalk("mk1_mod0/functionWithPattern.djinn");
        Function result = walker.function();
        
        Assert.assertNotNull(result);
        Assert.assertTrue(this.dictionary.isFunctionDefined("ifel"));
        Assert.assertTrue(this.dictionary.isMethodDefined("ifel"));
        Function function = this.dictionary.getFunction("ifel");
        Assert.assertNotNull(function);
        Assert.assertNotNull(function.getPattern());
        Assert.assertEquals(1, function.getPattern().getBody().size());
        Assert.assertEquals(4, function.getBody().size());
    }

    @Test
    public void functionWithoutPattern() throws Exception
    {
        Mk1_Mod0Walker walker = this.parseAndWalk("mk1_mod0/functionWithoutPattern.djinn");
        Function result = walker.function();

        Assert.assertNotNull(result);

        Assert.assertTrue(this.dictionary.isFunctionDefined("ifel"));
        Assert.assertTrue(this.dictionary.isMethodDefined("ifel"));
        Function function = this.dictionary.getFunction("ifel");
        Assert.assertNotNull(function);
        Assert.assertNull(function.getPattern());
        Assert.assertEquals(4, function.getBody().size());
    }

    @Test
    public void immediate() throws Exception
    {
        Mk1_Mod0Walker walker = this.parseAndWalk("mk1_mod0/immediate.djinn");
        Lambda result = walker.immediate_statement();

        Assert.assertNotNull(result);

        Assert.assertNotNull(result.getBody());
        Assert.assertNotNull(result.getValue());
        Assert.assertEquals(5, result.getBody().size());
    }

    @Test
    public void lambda() throws Exception
    {
        ClassLoader loader = this.getClass().getClassLoader();
        ANTLRInputStream input = new ANTLRInputStream(loader.getResourceAsStream("mk1_mod0/lambda.djinn"));
        Mk1_Mod0Lexer lexer = new Mk1_Mod0Lexer(input);

        CommonTokenStream stream = new CommonTokenStream(lexer);
        Mk1_Mod0Parser parser = new Mk1_Mod0Parser(stream);

        lambda_return result11 = parser.lambda();
        CommonTree tree = (CommonTree)result11.getTree();

        CommonTreeNodeStream nodes = new CommonTreeNodeStream(tree);
        nodes.setTokenStream(stream);

        Mk1_Mod0Walker walker = new Mk1_Mod0Walker(nodes, this.dictionary);
        Lambda result = walker.lambda();

        Assert.assertNotNull(result);

        Assert.assertNotNull(result.getBody());
        Assert.assertNotNull(result.getValue());
        Assert.assertEquals(4, result.getBody().size());
    }

    private Mk1_Mod0Walker parseAndWalk(String path) throws Exception
    {
        ClassLoader loader = this.getClass().getClassLoader();
        ANTLRInputStream input = new ANTLRInputStream(loader.getResourceAsStream(path));
        Mk1_Mod0Lexer lexer = new Mk1_Mod0Lexer(input);

        CommonTokenStream stream = new CommonTokenStream(lexer);
        Mk1_Mod0Parser parser = new Mk1_Mod0Parser(stream);

        statement_return result = parser.statement();
        CommonTree tree = (CommonTree)result.getTree();

        CommonTreeNodeStream nodes = new CommonTreeNodeStream(tree);
        nodes.setTokenStream(stream);

        return new Mk1_Mod0Walker(nodes, this.dictionary);
    }
}
