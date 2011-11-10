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

import java.lang.reflect.Constructor;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.webguys.djinn.ifrit.AstToModelTransformer;
import com.webguys.djinn.ifrit.DjinnLexer;
import com.webguys.djinn.ifrit.DjinnParser;
import com.webguys.djinn.ifrit.DjinnParser.statement_return;
import com.webguys.djinn.ifrit.DjinnParser.translation_unit_return;
import com.webguys.djinn.ifrit.model.Executable;
import com.webguys.djinn.ifrit.model.Method;
import com.webguys.djinn.ifrit.model.Module;
import com.webguys.djinn.ifrit.model.ModuleFunction;
import com.webguys.djinn.marid.primitive.Builtin;
import com.webguys.djinn.marid.runtime.Dictionary;
import com.webguys.djinn.marid.runtime.InitializationException;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.reflections.Reflections;

public class Runtime
{
    private static final Reflections reflections = Reflections.collect();
    private static final ImmutableMap<String, Class<? extends ModuleFunction>> primitiveImplementations;

    static
    {
        Builder<String, Class<? extends ModuleFunction>> builder = ImmutableMap.builder();
        Set<Class<?>> builtins = reflections.getTypesAnnotatedWith(Builtin.class);
        for(Class<?> builtin : builtins)
        {
            String name = builtin.getAnnotation(Builtin.class).value();
            builder.put(name, (Class<? extends ModuleFunction>)builtin);
        }
        primitiveImplementations = builder.build();
    }

    public static ModuleFunction getBuiltinFunction(Method method) throws Exception
    {
        Class<? extends ModuleFunction> builtin = primitiveImplementations.get(method.getName());
        ModuleFunction result = null;
        if(null != builtin)
        {
            Constructor<? extends ModuleFunction> constructor = builtin.getConstructor(Method.class);
            result = constructor.newInstance(method);
        }

        return result;
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

    public Executable parseStatement(String input, Dictionary dictionary) throws Exception
    {
        CharStream inputStream = new ANTLRStringStream(input);
        DjinnLexer lexer = new DjinnLexer(inputStream);

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        DjinnParser parser = new DjinnParser(tokenStream);

        statement_return result = parser.statement();
        CommonTree tree = (CommonTree)result.getTree();

        CommonTreeNodeStream nodes = new CommonTreeNodeStream(tree);
        nodes.setTokenStream(tokenStream);

        AstToModelTransformer walker = new AstToModelTransformer(nodes, dictionary);
        return walker.statement();
    }
}
