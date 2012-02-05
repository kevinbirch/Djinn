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

import com.webguys.djinn.ifrit.metamodel.Cluster;
import ponzu.api.map.MutableMap;
import ponzu.impl.map.mutable.UnifiedMap;

public class Method extends Cluster<Function> implements Executable
{
    private boolean sealed;
    private MutableMap<Lambda, Function> functionsByCondition = UnifiedMap.newMap();

    public Method(String name)
    {
        super(name);
    }

    @Override
    public void addMember(final Function function)
    {
        this.functionsByCondition.put(function.getCondition(), function);
        super.addMember(function);
    }

    public boolean isConditionDefined(Lambda condition)
    {
        return this.functionsByCondition.containsKey(condition);
    }

    @Override
    public void execute(Context context)
    {
        if(1 == this.functionsByCondition.size())
        {
            ConditionalExecutable function = this.functionsByCondition.getFirst();
            if(function.canExecute(context))
            {
                this.executeOne(context, function);
            }
        }
        else
        {
            this.executeAll(context);
        }
    }

    private void executeOne(Context context, ConditionalExecutable executable)
    {
        Stack stack = context.getStack();
        if(stack.depth() < executable.getDepthRequirement())
        {
            throw new StackUnderflowException(executable.getDepthRequirement(), stack.depth());
        }
        executable.execute(context);
    }

    private void executeAll(Context context)
    {
        Function defaultFunction = null;
        if(this.functionsByCondition.containsKey(null))
        {
            defaultFunction = this.functionsByCondition.get(null);
        }
        boolean found = false;
        for(Function function : this.functionsByCondition.values())
        {
            if(null == function.getCondition())
            {
                continue;
            }
            if(function.canExecute(context))
            {
                found = true;
                this.executeOne(context, function);
                break;
            }
        }
        if(!found && null != defaultFunction)
        {
            this.executeOne(context, defaultFunction);
        }
    }

    @Override
    public String toSourceRep()
    {
        return this.getName() + " :: [] -> []";
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
