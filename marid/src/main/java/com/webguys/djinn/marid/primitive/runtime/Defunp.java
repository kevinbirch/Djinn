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

package com.webguys.djinn.marid.primitive.runtime;

import com.webguys.djinn.ifrit.model.*;
import com.webguys.djinn.marid.primitive.Builtin;

@Builtin(Defunp.NAME)
public class Defunp extends ModuleFunction
{
    public static final String NAME = "defunp";

    public Defunp(Method family)
    {
        super(NAME, family);
    }

    @Override
    public int getDepthRequirement()
    {
        return 3;
    }

    @Override
    public void execute(Context context)
    {
        super.execute(context);

        Stack stack = context.getStack();

        if(2 > stack.depth())
        {
            throw new StackUnderflowException(2, stack.depth());
        }

        StringAtom name = ensureStackTop(stack, StringAtom.class, "string");
        Lambda predicate = ensureStackItem(stack, "second", Lambda.class, "lambda");
        Lambda body = ensureStackItem(stack, "third", Lambda.class, "lambda");

        Method method = context.getDictionary().getOrCreateMethod(name.getValue());
        if(method.isConditionDefined(predicate))
        {
            // xxx - need a warning system
            System.out.println("WARNING: Method \"" + method.getName() + "\" already has a function defined for the predicate " + predicate.toSourceRep());
        }
        method.addMember(new ModuleFunction(name.getValue(), method, body.getBody(), predicate));
    }
}
