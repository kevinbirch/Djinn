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

import java.util.List;

import com.webguys.djinn.ifrit.metamodel.Attribute;
import com.webguys.djinn.ifrit.metamodel.Container;
import com.webguys.djinn.ifrit.metamodel.Member;
import com.webguys.djinn.marid.runtime.Context;
import com.webguys.djinn.marid.runtime.Stack;

public class CompoundDeclaration<T extends Container<? extends CompoundDeclaration>> extends Attribute<T> implements Member<T>, Declaration
{
    private List<String> names;
    private Lambda definition;

    public CompoundDeclaration(List<String> names, Lambda definition)
    {
        super(gensym("compound-declaration"));
        this.names = names;
        this.definition = definition;
    }

    public List<String> getNames()
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

    @Override
    public T getContainer()
    {
        return super.getContainer();
    }

    @Override
    public Stack execute(Context context)
    {
        return null;
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
        return "Declaration";
    }
}
