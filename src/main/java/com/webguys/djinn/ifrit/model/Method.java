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
 * Created: 9/24/11 5:04 PM
 */

package com.webguys.djinn.ifrit.model;

import com.google.common.collect.Iterables;
import com.webguys.djinn.ifrit.metamodel.Family;
import com.webguys.djinn.marid.runtime.Context;
import com.webguys.djinn.marid.runtime.Stack;
import com.webguys.djinn.marid.runtime.StackUnderflowException;

public class Method extends Family<Function> implements Executable
{
    private boolean sealed;

    public Method(String name)
    {
        super(name);
    }

    @Override
    protected Iterable<Function> getMembers()
    {
        return super.getMembers();
    }

    @Override
    public void addMember(Function member)
    {
        super.addMember(member);
    }

    @Override
    public boolean isMember(Function action)
    {
        return super.isMember(action);
    }

    @Override
    public void execute(Context context)
    {
        if(1 == this.memberCount())
        {
            this.executeOne(context, Iterables.getOnlyElement(this.getMembers()));
        }
        else
        {
            this.executeAll(context);
        }
    }

    private void executeOne(Context context, Function function)
    {
        Stack stack = context.getStack();
        if(-1 != function.getDepthRequirement() && stack.depth() < function.getDepthRequirement())
        {
            throw new StackUnderflowException(function.getDepthRequirement(), stack.depth());
        }
        function.execute(context);
    }

    private void executeAll(Context context)
    {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public String toSourceRep()
    {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public String getTypeName()
    {
        return "Method";
    }

    public boolean isSealed()
    {
        return this.sealed;
    }

    public void setSealed(boolean sealed)
    {
        this.sealed = sealed;
    }
}
