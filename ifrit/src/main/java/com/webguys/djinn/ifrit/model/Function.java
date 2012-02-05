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

import com.webguys.djinn.ifrit.metamodel.*;
import ponzu.api.list.ImmutableList;

public abstract class Function<FamilyType extends Cluster<Function>, ContainerType extends Container<? extends Member>>
    extends Action<FamilyType, ContainerType> implements ConditionalExecutable
{
    private int depthRequirement = 0;

    protected Lambda condition;
    protected ImmutableList<Atom> body;

    public Function(String name, FamilyType family, ImmutableList<Atom> body)
    {
        super(name, family);
        this.body = body;
        this.getFamily().addMember(this);
    }

    public Function(String name, FamilyType family, ImmutableList<Atom> body, Lambda condition)
    {
        super(name, family);
        this.body = body;
        this.condition = condition;
        this.getFamily().addMember(this);
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
            this.condition.execute(new Context(patternStack, context.getDictionary()));

            if(!(patternStack.peek() instanceof BooleanAtom))
            {
                throw new PatternResultException(patternStack.peek());
            }
            return patternStack.peek().getValue().equals(Boolean.TRUE);
        }
    }

    public Lambda getCondition()
    {
        return this.condition;
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

    protected <T extends Atom> T ensureStackTop(Stack stack, Class<T> clazz, String typeName)
    {
        if(!(clazz.isInstance(stack.peek())))
        {
            throw new DoesNotUnderstandException("The top of the stack is not a " + typeName + " value.");
        }

        return stack.pop();
    }

    protected Atom ensureStackTopIsSimpleType(Stack stack)
    {
        if(!(stack.peek() instanceof SimpleType))
        {
            throw new DoesNotUnderstandException("The top of the stack is not a simple value.");
        }

        return stack.pop();
    }
}
