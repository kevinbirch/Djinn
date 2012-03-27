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

import com.webguys.djinn.ifrit.metamodel.Container;
import ponzu.api.RichIterable;
import ponzu.api.list.ImmutableList;
import ponzu.impl.factory.Lists;

public class ModuleFunction extends Function<Method, Module> implements Entry, Container<InnerFunction>
{
    private ImmutableList<InnerFunction> inner = Lists.immutable.of();
    private ImmutableList<Declaration> declarations = Lists.immutable.of();

    protected Dictionary localDictionary;

    public ModuleFunction(String name, Method family, ImmutableList<? extends Atom> body)
    {
        super(name, family, body);
    }

    public ModuleFunction(String name, Method family, ImmutableList<? extends Atom> body, Lambda predicate)
    {
        super(name, family, body, predicate);
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
    public RichIterable<InnerFunction> getChildren()
    {
        return this.inner;
    }

    public void setChildren(ImmutableList<InnerFunction> inner)
    {
        this.inner = inner;
    }

    public void setDeclarations(ImmutableList<Declaration> declarations)
    {
        this.declarations = declarations;
    }

    public void setLocalDictionary(Dictionary localDictionary)
    {
        this.localDictionary = localDictionary;
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
            declaration.initialize(declarationContext);
        }

        for(Atom atom : this.body)
        {
            if(atom instanceof Lambda)
            {
                functionContext.getStack().push(atom);
            }
            else
            {
                atom.execute(functionContext);
            }
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
        return null != this.predicate ? string + " predicate=" + this.predicate.toSourceRep() : string;
    }

    @Override
    public String toSourceRep()
    {
        StringBuilder sb = new StringBuilder("\\[");
        sb.append(this.getName());
        sb.append(" ");
        if(null != this.predicate)
        {
            sb.append(this.predicate.toSourceRep());
            sb.append(" ");
        }
        sb.append(this.body.transform(TO_SOURCE_REP).makeString(" "));
        if(null != this.inner && !this.inner.isEmpty())
        {
            sb.append("\n");
            sb.append(this.inner.transform(TO_SOURCE_REP).makeString("\n"));
        }
        if(null != this.declarations && !this.declarations.isEmpty())
        {
            sb.append("\n");
            sb.append(this.declarations.transform(TO_SOURCE_REP).makeString("\n"));
        }
        sb.append("]");
        return sb.toString();
    }
}
