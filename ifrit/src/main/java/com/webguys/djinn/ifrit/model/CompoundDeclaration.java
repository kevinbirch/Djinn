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
 * Created: 10/26/11 7:28 PM
 */

package com.webguys.djinn.ifrit.model;

import com.webguys.djinn.ifrit.metamodel.Attribute;
import com.webguys.djinn.ifrit.metamodel.Container;
import com.webguys.djinn.ifrit.metamodel.Member;
import com.webguys.ponzu.api.RichIterable;
import com.webguys.ponzu.api.block.procedure.Procedure;
import com.webguys.ponzu.api.list.ImmutableList;
import com.webguys.ponzu.api.map.MutableMap;
import com.webguys.ponzu.impl.map.mutable.UnifiedMap;

public class CompoundDeclaration<T extends Container<? extends CompoundDeclaration>> extends Attribute<T>
    implements Member<T>, Declaration
{
    private ImmutableList<String> names;
    private Lambda definition;
    private MutableMap<String, Declaration> cache = UnifiedMap.newMap();

    public CompoundDeclaration(ImmutableList<String> names, Lambda definition)
    {
        super(gensym("compound-declaration"));
        this.names = names;
        this.definition = definition;
    }

    @Override
    public void initialize(final Context context)
    {
        final Stack declarationStack = context.getStack().clone();
        this.definition.execute(new Context(declarationStack, context.getDictionary()));
        this.names.reverseForEach(new Procedure<String>()
        {
            @Override
            public void value(String name)
            {
                ChildDeclarationProxy proxy = new ChildDeclarationProxy(name, declarationStack.pop());
                CompoundDeclaration.this.cache.put(name, proxy);
                context.getDictionary().defineName(proxy);
            }
        });
    }

    public RichIterable<String> getNames()
    {
        return this.names;
    }

    public Lambda getDefinition()
    {
        return this.definition;
    }

    @Override
    public void setParent(T parent)
    {
        super.setParent(parent);
    }

    public RichIterable<Declaration> getChildren()
    {
        return this.cache.valuesView();
    }

    @Override
    public T getContainer()
    {
        return super.getContainer();
    }

    @Override
    public void execute(Context context)
    {
    }

    @Override
    public String toSourceRep()
    {
        StringBuffer sb = new StringBuffer();
        for(String name : this.names)
        {
            sb.append("\\");
            sb.append(name);
            sb.append(", ");
        }
        sb.replace(sb.lastIndexOf(","), sb.length(), " ");
        sb.append(this.definition.toSourceRep());
        return sb.toString();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<<").append(this.getTypeName()).append(">> ");
        for(String name : this.names)
        {
            sb.append("\"").append(name).append("\"");
            sb.append(", ");
        }
        sb.replace(sb.lastIndexOf(","), sb.length(), " ");
        sb.append(this.definition.toSourceRep());
        return sb.toString();
    }

    @Override
    public String getTypeName()
    {
        return "CompoundDeclaration";
    }

    private class ChildDeclarationProxy implements Declaration
    {
        private String name;
        private Atom cache;

        public ChildDeclarationProxy(String name, Atom cacheValue)
        {
            this.name = name;
            this.cache = cacheValue;
        }

        @Override
        public void initialize(Context context)
        {
        }

        @Override
        public String getName()
        {
            return this.name;
        }

        @Override
        public Lambda getDefinition()
        {
            return CompoundDeclaration.this.definition;
        }

        @Override
        public void execute(Context context)
        {
            context.getStack().push(this.cache);
        }

        @Override
        public String toSourceRep()
        {
            throw new RuntimeException("this method should not be called.");
        }
    }
}
