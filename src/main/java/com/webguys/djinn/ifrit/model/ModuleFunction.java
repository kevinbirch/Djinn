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
 * Created: 9/25/11 10:22 PM
 */

package com.webguys.djinn.ifrit.model;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.webguys.djinn.ifrit.metamodel.Container;
import com.webguys.djinn.marid.runtime.Context;
import org.apache.commons.lang3.StringUtils;

public class ModuleFunction extends Function<Method, Module> implements Entry, Container<InnerFunction>
{
    private ImmutableList<InnerFunction> inner = ImmutableList.of();
    private ImmutableList<Declaration> declarations = ImmutableList.of();

    public ModuleFunction(String name, Method family, List<Atom> body)
    {
        super(name, family, body);
    }

    public ModuleFunction(String name, Method family, List<Atom> body, Lambda condition)
    {
        super(name, family, body, condition);
    }

    protected ModuleFunction(String name, Method family)
    {
        super(name, family, ImmutableList.<Atom>of());
    }

    @Override
    public Module getContainer()
    {
        return super.getContainer();
    }

    @Override
    public void setContainer(Module container)
    {
        super.setContainer(container);
    }

    @Override
    public Method getFamily()
    {
        return super.getFamily();
    }

    @Override
    public Iterable<InnerFunction> getChildren()
    {
        return this.inner;
    }

    public void setChildren(List<InnerFunction> inner)
    {
        this.inner = ImmutableList.copyOf(inner);
    }

    public void setDeclarations(List<Declaration> declarations)
    {
        this.declarations = ImmutableList.copyOf(declarations);
    }

    @Override
    public void execute(Context context)
    {
        Context functionContext;
        if(null != this.localDictionary)
        {
            functionContext = new Context(context.getStack(), this.localDictionary);
        }
        else
        {
            functionContext = context;
        }

        for(Declaration declaration : this.declarations)
        {
            Context declarationContext = new Context(functionContext.getStack().clone(), functionContext.getDictionary());
            declaration.execute(declarationContext);
        }

        for(Atom atom : this.body)
        {
            atom.execute(functionContext);
        }
    }

    @Override
    public String getTypeName()
    {
        return "Function";
    }

    @Override
    public String toString()
    {
        String string = super.toString();
        return null != this.condition ? string + " condition=" + this.condition.toSourceRep() : string;
    }

    @Override
    public String toSourceRep()
    {
        StringBuilder sb = new StringBuilder("\\[");
        sb.append(this.getName());
        sb.append(" ");
        if(null != this.condition)
        {
            sb.append(this.condition.toSourceRep());
            sb.append(" ");
        }
        sb.append(StringUtils.join(Lists.transform(this.body, Atom.TO_SOURCE_REP), " "));
        if(null != this.inner && !this.inner.isEmpty())
        {
            sb.append("\n");
            sb.append(StringUtils.join(Lists.transform(this.inner, InnerFunction.TO_SOURCE_REP), "\n"));
        }
        if(null != this.declarations && !this.declarations.isEmpty())
        {
            sb.append("\n");
            sb.append(StringUtils.join(Lists.transform(this.declarations, Declaration.TO_SOURCE_REP), "\n"));
        }
        sb.append("]");
        return sb.toString();
    }
}
