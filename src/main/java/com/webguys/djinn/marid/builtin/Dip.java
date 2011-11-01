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
 * Created: 10/28/11 10:06 PM
 */

package com.webguys.djinn.marid.builtin;

import com.webguys.djinn.ifrit.model.Atom;
import com.webguys.djinn.ifrit.model.Function;
import com.webguys.djinn.ifrit.model.Lambda;
import com.webguys.djinn.ifrit.model.Method;
import com.webguys.djinn.marid.runtime.Context;
import com.webguys.djinn.marid.runtime.DoesNotUnderstandException;
import com.webguys.djinn.marid.runtime.Stack;

public class Dip extends BinaryFunction
{

    public static final String NAME = "dip";

    public static final BuiltinFactory FACTORY = new BuiltinFactory()
    {
        @Override
        public Function makeInstance(Method method)
        {
            return new Dip(method);
        }
    };

    public Dip(Method family)
    {
        super(NAME, family);
    }

    @Override
    public void execute(Context context)
    {
        Stack stack = context.getStack();

        if(!(stack.peek() instanceof Lambda))
        {
            throw new DoesNotUnderstandException("Top of stack is not a lambda");
        }

        Lambda lambda = (Lambda)stack.pop();
        Atom second = stack.pop();

        lambda.execute(context);
        stack.push(second);
    }
}
