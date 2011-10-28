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

import java.util.ArrayList;
import java.util.List;

import com.webguys.djinn.ifrit.metamodel.Action;
import com.webguys.djinn.marid.runtime.Context;
import com.webguys.djinn.marid.runtime.Stack;

public class Function extends Action<Method, Module> implements Executable, Entry
{
    private Lambda pattern;
    private List<Atom> body = new ArrayList<Atom>();
    private List<Function> inner;
    private List<Declaration> declarations;

    public Function(String name, List<Atom> body)
    {
        this(name, new Method(name), body);
    }

    public Function(String name, Method family, List<Atom> body)
    {
        super(name, family);
        this.body = body;
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
    public Stack execute(Context context)
    {
        return null;
    }

    public Lambda getPattern()
    {
        return this.pattern;
    }

    public void setPattern(Lambda pattern)
    {
        this.pattern = pattern;
    }

    public List<Function> getInner()
    {
        return this.inner;
    }

    public void setInner(List<Function> inner)
    {
        this.inner = inner;
    }

    public List<Declaration> getDeclarations()
    {
        return declarations;
    }

    public void setDeclarations(List<Declaration> declarations)
    {
        this.declarations = declarations;
    }

    public List<Atom> getBody()
    {
        return body;
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
        return null != this.pattern ? string + " pattern=" + this.pattern.toSourceRep() : string;
    }

    @Override
    public String toSourceRep()
    {
        StringBuilder sb = new StringBuilder("\\[");
        sb.append(this.getName());
        sb.append(" ");
        if(null != this.pattern)
        {
            sb.append(this.pattern.toSourceRep());
            sb.append(" ");
        }
        for(Atom atom : this.body)
        {
            sb.append(atom.toSourceRep());
            sb.append(' ');
        }
        sb.replace(sb.lastIndexOf(" "), sb.length(), "]");
        if(null != this.inner && !this.inner.isEmpty())
        {
            sb.append("\n");
            for(Function function : this.inner)
            {
                sb.append(function.toSourceRep());
                sb.append("\n");
            }
        }
        if(null != this.declarations && !this.declarations.isEmpty())
        {
            sb.append("\n");
            for(Declaration declaration : this.declarations)
            {
                sb.append(declaration.toSourceRep());
                sb.append("\n");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
