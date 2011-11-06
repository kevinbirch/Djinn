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
 * Created: 11/5/11 1:27 PM
 */

package com.webguys.djinn.ifrit.model;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.webguys.djinn.ifrit.metamodel.Action;
import com.webguys.djinn.ifrit.metamodel.Cluster;
import com.webguys.djinn.ifrit.metamodel.Container;
import com.webguys.djinn.ifrit.metamodel.Member;
import com.webguys.djinn.marid.runtime.Context;
import com.webguys.djinn.marid.runtime.Dictionary;
import com.webguys.djinn.marid.runtime.PatternResultException;
import com.webguys.djinn.marid.runtime.Stack;

public abstract class Function<FamilyType extends Cluster, ContainerType extends Container<? extends Member>>
    extends Action<FamilyType, ContainerType> implements ConditionalExecutable
{
    private int depthRequirement = 0;

    protected Lambda condition;
    protected ImmutableList<Atom> body;

    protected Dictionary localDictionary;

    public Function(String name, FamilyType family, List<Atom> body)
    {
        super(name, family);
        this.body = ImmutableList.copyOf(body);
    }

    @Override
    public boolean canExecute(Context context)
    {
        if(null == this.condition)
        {
            return true;
        }
        else
        {
            Stack patternStack = context.getStack().clone();
            Dictionary patternDictionary = null != this.localDictionary ? this.localDictionary : context.getDictionary();
            this.condition.execute(new Context(patternStack, patternDictionary));

            if(!(patternStack.peek() instanceof BooleanAtom))
            {
                throw new PatternResultException(patternStack.peek());
            }
            return patternStack.peek().getValue().equals(Boolean.TRUE);
        }
    }

    public void setLocalDictionary(Dictionary localDictionary)
    {
        this.localDictionary = localDictionary;
    }

    public Lambda getCondition()
    {
        return this.condition;
    }

    public void setCondition(Lambda pattern)
    {
        this.condition = pattern;
    }

    @Override
    public int getDepthRequirement()
    {
        return this.depthRequirement;
    }

    public void setDepthRequirement(int depthRequirement)
    {
        this.depthRequirement = depthRequirement;
    }
}
