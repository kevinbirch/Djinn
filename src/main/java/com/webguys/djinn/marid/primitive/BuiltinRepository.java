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
 * Created: 11/13/11 12:08 PM
 */

package com.webguys.djinn.marid.primitive;

import com.google.common.collect.ImmutableMap;
import com.webguys.djinn.ifrit.model.Method;
import com.webguys.djinn.ifrit.model.ModuleFunction;
import com.webguys.djinn.marid.primitive.bool.*;
import com.webguys.djinn.marid.primitive.compare.*;
import com.webguys.djinn.marid.primitive.higher.Apply;
import com.webguys.djinn.marid.primitive.higher.Bind;
import com.webguys.djinn.marid.primitive.higher.Compose;
import com.webguys.djinn.marid.primitive.higher.Quote;
import com.webguys.djinn.marid.primitive.io.*;
import com.webguys.djinn.marid.primitive.list.Filter;
import com.webguys.djinn.marid.primitive.list.Find;
import com.webguys.djinn.marid.primitive.math.*;
import com.webguys.djinn.marid.primitive.stack.*;

public class BuiltinRepository
{
    private static final ImmutableMap<String, BuiltinFactory> factories = ImmutableMap.<String, BuiltinFactory>builder()
        .put(Add.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Add(method);
            }
        })
        .put(And.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new And(method);
            }
        })
        .put(Apply.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Apply(method);
            }
        })
        .put(Bind.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Bind(method);
            }
        })
        .put(Compose.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Compose(method);
            }
        })
        .put(Dip.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Dip(method);
            }
        })
        .put(Div.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Div(method);
            }
        })
        .put(Drop.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Drop(method);
            }
        })
        .put(Dup.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Dup(method);
            }
        })
        .put(Eol.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Eol(method);
            }
        })
        .put(Eq.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Eq(method);
            }
        })
        .put(False.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new False(method);
            }
        })
        .put(Filter.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Filter(method);
            }
        })
        .put(Find.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Find(method);
            }
        })
        .put(Gt.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Gt(method);
            }
        })
        .put(Gte.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Gte(method);
            }
        })
        .put(Id.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Id(method);
            }
        })
        .put(Lt.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Lt(method);
            }
        })
        .put(Lte.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Lte(method);
            }
        })
        .put(Mod.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Mod(method);
            }
        })
        .put(Mul.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Mul(method);
            }
        })
        .put(Ne.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Ne(method);
            }
        })
        .put(Not.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Not(method);
            }
        })
        .put(Or.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Or(method);
            }
        })
        .put(Pow.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Pow(method);
            }
        })
        .put(Println.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Println(method);
            }
        })
        .put(Print.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Print(method);
            }
        })
        .put(Quote.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Quote(method);
            }
        })
        .put(Read.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Read(method);
            }
        })
        .put(Readln.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Readln(method);
            }
        })
        .put(Sub.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Sub(method);
            }
        })
        .put(Swap.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new Swap(method);
            }
        })
        .put(True.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new True(method);
            }
        })
        .build();

    public static ModuleFunction getBuiltinFunction(Method method)
    {
        BuiltinFactory factory = factories.get(method.getName());
        return null == factory ? null : factory.makeInstance(method);
    }
}
