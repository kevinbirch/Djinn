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

package com.webguys.djinn.marid.primitive.compare;

import com.webguys.djinn.ifrit.model.*;
import com.webguys.djinn.marid.primitive.BinaryFunction;
import com.webguys.djinn.marid.primitive.Builtin;

@Builtin(Eq.NAME)
public class Eq extends BinaryFunction
{
    public static final String NAME = "eq";

    public Eq(Method family)
    {
        super(NAME, family, Any.getMetaclass(), Any.getMetaclass());
    }

    @Override
    public void execute(Context context)
    {
        super.execute(context);

        Stack stack = context.getStack();
        Atom b = stack.pop();
        Atom a = stack.pop();

        if(a.equals(b))
        {
            stack.push(BooleanAtom.getTrue());
        }
        else
        {
            stack.push(BooleanAtom.getFalse());
        }
    }
}
