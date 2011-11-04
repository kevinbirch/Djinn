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
 * Created: 9/29/11 12:07 AM
 */

package com.webguys.djinn.ifrit.model;

public class IntegerAtom extends AbstractAtom<Integer> implements NumericAtom
{
    private static final String TYPE_NAME = "Integer";

    public IntegerAtom(Integer value)
    {
        super(gensym(TYPE_NAME), value);
    }

    @Override
    public String getTypeName()
    {
        return TYPE_NAME;
    }

    @Override
    public Atom add(NumericAtom value)
    {
        int result = this.value.intValue() + value.intValue();

        return new IntegerAtom(result);
    }

    @Override
    public Atom sub(NumericAtom value)
    {
        int result = this.value.intValue() - value.intValue();
        return new IntegerAtom(result);
    }

    @Override
    public Atom div(NumericAtom value)
    {
        int result = this.value.intValue() / value.intValue();
        return new IntegerAtom(result);
    }

    @Override
    public Atom mul(NumericAtom value)
    {
        int result = this.value.intValue() * value.intValue();
        return new IntegerAtom(result);
    }

    @Override
    public Atom lt(NumericAtom value)
    {
        int that = value.intValue();
        return this.value.intValue() < that ? BooleanAtom.getTrue() : BooleanAtom.getFalse();
    }

    @Override
    public Atom lte(NumericAtom value)
    {
        int that = value.intValue();
        return this.value.intValue() <= that ? BooleanAtom.getTrue() : BooleanAtom.getFalse();
    }

    @Override
    public Atom gt(NumericAtom value)
    {
        int that = value.intValue();
        return this.value.intValue() > that ? BooleanAtom.getTrue() : BooleanAtom.getFalse();
    }

    @Override
    public Atom gte(NumericAtom value)
    {
        int that = value.intValue();
        return this.value.intValue() >= that ? BooleanAtom.getTrue() : BooleanAtom.getFalse();
    }

    @Override
    public int intValue()
    {
        return this.value.intValue();
    }

    @Override
    public double doubleValue()
    {
        return this.value.doubleValue();
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof IntegerAtom))
        {
            return false;
        }

        IntegerAtom that = (IntegerAtom)o;

        return this.value.equals(that.value);

    }
}
