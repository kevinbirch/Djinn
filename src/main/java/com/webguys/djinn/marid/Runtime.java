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
 * Created: 10/30/11 7:00 PM
 */

package com.webguys.djinn.marid;

import com.google.common.collect.ImmutableMap;
import com.webguys.djinn.ifrit.AstToModelTransformer;
import com.webguys.djinn.ifrit.DjinnLexer;
import com.webguys.djinn.ifrit.DjinnParser;
import com.webguys.djinn.ifrit.DjinnParser.translation_unit_return;
import com.webguys.djinn.ifrit.model.Module;
import com.webguys.djinn.marid.primitive.*;
import com.webguys.djinn.marid.runtime.Dictionary;
import com.webguys.djinn.marid.runtime.InitializationException;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

public class Runtime
{
    private static final ImmutableMap<String, BuiltinFactory> factories = ImmutableMap.<String, BuiltinFactory>builder()
        .put(Add.NAME, Add.FACTORY)
        .put(And.NAME, And.FACTORY)
        .put(Apply.NAME, Apply.FACTORY)
        .put(Bind.NAME, Bind.FACTORY)
        .put(Compose.NAME, Compose.FACTORY)
        .put(Dip.NAME, Dip.FACTORY)
        .put(Div.NAME, Div.FACTORY)
        .put(Drop.NAME, Drop.FACTORY)
        .put(Dup.NAME, Dup.FACTORY)
        .put(Eol.NAME, Eol.FACTORY)
        .put(Eq.NAME, Eq.FACTORY)
        .put(False.NAME, False.FACTORY)
        .put(Gt.NAME, Gt.FACTORY)
        .put(Gte.NAME, Gte.FACTORY)
        .put(Id.NAME, Id.FACTORY)
        .put(Lt.NAME, Lt.FACTORY)
        .put(Lte.NAME, Lte.FACTORY)
        .put(Mod.NAME, Mod.FACTORY)
        .put(Mul.NAME, Mul.FACTORY)
        .put(Ne.NAME, Ne.FACTORY)
        .put(Not.NAME, Not.FACTORY)
        .put(Or.NAME, Or.FACTORY)
        .put(Pow.NAME, Pow.FACTORY)
        .put(Println.NAME, Println.FACTORY)
        .put(Print.NAME, Print.FACTORY)
        .put(Quote.NAME, Quote.FACTORY)
        .put(Readln.NAME, Readln.FACTORY)
        .put(Sub.NAME, Sub.FACTORY)
        .put(Swap.NAME, Swap.FACTORY)
        .put(True.NAME, True.FACTORY)
        .build();

    public static BuiltinFactory getBuiltinFactory(String name)
    {
        return factories.get(name);
    }

    public Runtime()
    {
        try
        {
            loadSourceFile("djinn/primitives.djinn", Dictionary.getRootDictionary());
        }
        catch(Exception e)
        {
            throw new InitializationException(e);
        }
    }

    public Module loadSourceFile(String path, Dictionary dictionary) throws Exception
    {
        ClassLoader loader = Runtime.class.getClassLoader();
        ANTLRInputStream input = new ANTLRInputStream(loader.getResourceAsStream(path));
        DjinnLexer lexer = new DjinnLexer(input);

        CommonTokenStream stream = new CommonTokenStream(lexer);
        DjinnParser parser = new DjinnParser(stream);

        translation_unit_return result = parser.translation_unit();
        CommonTree tree = (CommonTree)result.getTree();

        CommonTreeNodeStream nodes = new CommonTreeNodeStream(tree);
        nodes.setTokenStream(stream);

        AstToModelTransformer generator = new AstToModelTransformer(nodes, dictionary);
        return generator.translation_unit();
    }
}
