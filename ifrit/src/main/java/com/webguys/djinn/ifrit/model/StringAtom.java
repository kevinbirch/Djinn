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
 * Created: 9/29/11 12:21 AM
 */

package com.webguys.djinn.ifrit.model;

public class StringAtom extends AbstractAtom<String>
{
    private static final String TYPE_NAME = "String";
    private static final Metatype METATYPE = new Metatype();

    public static com.webguys.djinn.ifrit.model.Metatype<String> getMetatype()
    {
        return METATYPE;
    }

    public static final class Metatype implements com.webguys.djinn.ifrit.model.Metatype<String>
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
        public Class<? extends Atom<String>> getImplementationClass()
        {
            return StringAtom.class;
        }

        @Override
        public Atom<String> makeInstance(String value)
        {
            return new StringAtom(value);
        }
    }

    public StringAtom(String value)
    {
        super(gensym(TYPE_NAME), value);
    }

    @Override
    public String getTypeName()
    {
        return TYPE_NAME;
    }

    @Override
    public String toSourceRep()
    {
        return "\"" + super.toSourceRep() + "\"";
    }
}
