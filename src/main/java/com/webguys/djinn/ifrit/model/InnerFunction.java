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

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.webguys.djinn.ifrit.metamodel.Action;
import com.webguys.djinn.marid.runtime.Context;

public class InnerFunction extends Action<Method, Function> implements Executable
{
    public static final com.google.common.base.Function<InnerFunction, String> TO_SOURCE_REP = new com.google.common.base.Function<InnerFunction, String>()
    {
        @Override
        public String apply(@Nullable InnerFunction function)
        {
            return function.toSourceRep();
        }
    };

    private Lambda pattern;
    private ImmutableList<Atom> body;
    private int depthRequirement = -1;

    public InnerFunction(String name, Method family, List<Atom> body)
    {
        super(name, family);
        this.body = ImmutableList.copyOf(body);
    }

    protected InnerFunction(String name, Method family)
    {
        super(name, family);
        this.body = ImmutableList.<Atom>of();
    }

    @Override
    public Function getContainer()
    {
        return super.getContainer();
    }

    @Override
    public void setContainer(Function container)
    {
        super.setContainer(container);
    }

    @Override
    public Method getFamily()
    {
        return super.getFamily();
    }

    @Override
    public void execute(Context context)
    {
        throw new RuntimeException("Not yet implemented");
    }

    public Lambda getPattern()
    {
        return this.pattern;
    }

    public void setPattern(Lambda pattern)
    {
        this.pattern = pattern;
    }

    public ImmutableList<Atom> getBody()
    {
        return this.body;
    }

    public int getDepthRequirement()
    {
        return this.depthRequirement;
    }

    public void setDepthRequirement(int depthRequirement)
    {
        this.depthRequirement = depthRequirement;
    }

    @Override
    public String getTypeName()
    {
        return "InnerFunction";
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
        sb.append("]");
        return sb.toString();
    }
}
