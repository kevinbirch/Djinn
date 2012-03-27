/*
 * The MIT License
 *
 * Copyright (c) 2012 Kevin Birch <kevin.birch@gmail.com>. Some rights reserved.
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
 */

package com.webguys.djinn.marid.primitive;

import com.webguys.djinn.ifrit.model.*;
import ponzu.api.list.ImmutableList;
import ponzu.impl.factory.Lists;

public abstract class BuiltinFunction extends ModuleFunction
{
    private static final ImmutableList<? extends Atom> BODY = Lists.immutable.of(new StringAtom("__primitive__"));

    private Metaclass<?>[] stackItems;

    public BuiltinFunction(String name, Method family, int depthRequirement, Metaclass<?>... stackItems)
    {
        super(name, family, BODY);
        this.setDepthRequirement(depthRequirement);
        this.stackItems = stackItems;
    }

    @Override
    public void execute(Context context)
    {
        Stack stack = context.getStack();
        if(this.getDepthRequirement() > stack.depth())
        {
            throw new StackUnderflowException(this.getFamily(), this.getDepthRequirement(), stack.depth());
        }

        for(int i = 0; i < stackItems.length; i++)
        {
            Metaclass<?> stackItem = stackItems[i];
            ensureStackItem(stack, i, stackItem);
        }
    }

    private void ensureStackItem(Stack stack, int position, Metaclass<?> metaclass)
    {
        Atom<?> atom = stack.peek(position);
        if(!(metaclass.isImplementation(atom)))
        {
            throw new DoesNotUnderstandException(this.getFamily(), position, metaclass, atom);
        }
    }
}
