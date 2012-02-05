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
 * Created: 10/30/11 7:32 PM
 */

package com.webguys.djinn.ifrit.model;

import com.webguys.djinn.ifrit.metamodel.SimpleType;

public class AbstractAtom<T> extends SimpleType implements Atom<T>
{
    protected T value;

    public AbstractAtom(String name, T value)
    {
        super(name);
        this.value = value;
    }

    @Override
    public T getValue()
    {
        return this.value;
    }

    @Override
    public void execute(Context context)
    {
        context.getStack().push(this);
    }

    @Override
    public String toString()
    {
        return super.toString() + " : " + this.toSourceRep();
    }

    @Override
    public String toSourceRep()
    {
        return this.value.toString();
    }

    @Override
    public int hashCode()
    {
        return this.value.hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof AbstractAtom))
        {
            return false;
        }

        AbstractAtom atom = (AbstractAtom)o;

        return value.equals(atom.value);

    }
}
