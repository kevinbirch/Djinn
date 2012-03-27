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
 * Created: 11/6/11 9:32 PM
 */

package com.webguys.djinn.ifrit.model;

import ponzu.api.RichIterable;

public class ListAtom extends AbstractAtom<RichIterable<? extends Atom>>
{
    public static final String TYPE_NAME = "List";
    private static final Meta METACLASS = new Meta();

    public static Metaclass<RichIterable<? extends Atom>> getMetaclass()
    {
        return METACLASS;
    }

    public static final class Meta implements Metaclass<RichIterable<? extends Atom>>
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
        public Class<? extends Atom<RichIterable<? extends Atom>>> getImplementationClass()
        {
            return ListAtom.class;
        }

        @Override
        public Atom<RichIterable<? extends Atom>> makeInstance(RichIterable<? extends Atom> value)
        {
            return new ListAtom(value);
        }
    }

    public ListAtom(RichIterable<? extends Atom> value)
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
        return this.getValue().transform(TO_SOURCE_REP).makeString("(", " ", ")");
    }
}
