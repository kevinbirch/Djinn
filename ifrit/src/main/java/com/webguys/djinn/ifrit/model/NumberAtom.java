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

package com.webguys.djinn.ifrit.model;

public abstract class NumberAtom<T extends Number> extends AbstractAtom<T>
{
    private static final String TYPE_NAME = "Number";
    private static final Metatype METATYPE = new Metatype();

    public static com.webguys.djinn.ifrit.model.Metatype getMetatype()
    {
        return METATYPE;
    }

    protected static class Metatype<T extends Number> implements com.webguys.djinn.ifrit.model.Metatype<T>
    {
        @Override
        public String getTypeName()
        {
            return TYPE_NAME;
        }

        @Override
        public boolean isImplementation(Atom<?> atom)
        {
            return this.getImplementationClass().isInstance(atom);
        }

        @Override
        public Class getImplementationClass()
        {
            return NumberAtom.class;
        }

        @Override
        public Atom<T> makeInstance(Number value)
        {
            if(value instanceof Integer)
            {
                return (Atom<T>)new IntegerAtom((Integer)value);
            }
            else if(value instanceof Double)
            {
                return (Atom<T>)new DecimalAtom((Double)value);
            }
            else
            {
                throw new IllegalArgumentException("Only double and integer values are supported.");
            }
        }
    }

    public NumberAtom(String name, T value)
    {
        super(name, value);
    }

    public abstract Atom<T> add(NumberAtom<? extends Number> value);

    public abstract Atom<T> sub(NumberAtom<? extends Number> value);

    public abstract Atom<T> div(NumberAtom<? extends Number> value);

    public abstract Atom<T> mul(NumberAtom<? extends Number> value);

    public abstract Atom<T> mod(NumberAtom<? extends Number> value);

    public abstract BooleanAtom lt(NumberAtom<? extends Number> value);

    public abstract BooleanAtom lte(NumberAtom<? extends Number> value);

    public abstract BooleanAtom gt(NumberAtom<? extends Number> value);

    public abstract BooleanAtom gte(NumberAtom<? extends Number> value);

    public abstract int intValue();

    public abstract double doubleValue();
}
