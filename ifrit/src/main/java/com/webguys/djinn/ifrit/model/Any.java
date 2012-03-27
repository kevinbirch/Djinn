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

/**
 * Created: 3/26/12 7:37 PM
 */
public class Any implements Atom<Object>
{
    private static final String TYPE_NAME = "Any";
    private static final Metatype METATYPE = new Metatype();

    public static com.webguys.djinn.ifrit.model.Metatype<Object> getMetatype()
    {
        return METATYPE;
    }

    public static final class Metatype implements com.webguys.djinn.ifrit.model.Metatype<Object>
    {
        @Override
        public String getTypeName()
        {
            return TYPE_NAME;
        }

        @Override
        public boolean isImplementation(Atom<?> atom)
        {
            return true;
        }

        @Override
        public Class<? extends Atom<Object>> getImplementationClass()
        {
            return Any.class;
        }

        @Override
        public Atom<Object> makeInstance(Object value)
        {
            return new Any(value);
        }
    }

    private Object value;

    public Any(Object value)
    {
        this.value = value;
    }

    @Override
    public Object getValue()
    {
        return this.value;
    }

    @Override
    public String getTypeName()
    {
        return TYPE_NAME;
    }

    @Override
    public void execute(Context context)
    {
    }

    @Override
    public String toSourceRep()
    {
        return "any";
    }
}
