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
 * Created: 10/30/11 10:35 PM
 */

package com.webguys.djinn.ifrit.model;

public class BooleanAtom extends AbstractAtom<Boolean>
{
    private static final BooleanAtom TRUE = new BooleanAtom("true", Boolean.TRUE);
    private static final BooleanAtom FALSE = new BooleanAtom("false", Boolean.FALSE);

    private static final String TYPE_NAME = "Boolean";
    private static final Meta METACLASS = new Meta();

    public static Meta getMetaclass()
    {
        return METACLASS;
    }

    public static final class Meta implements Metaclass<Boolean>
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
        public Class<? extends Atom<Boolean>> getImplementationClass()
        {
            return BooleanAtom.class;
        }

        @Override
        public Atom<Boolean> makeInstance(Boolean value)
        {
            return value ? TRUE : FALSE;
        }
    }

    public static BooleanAtom getTrue()
    {
        return TRUE;
    }

    public static BooleanAtom getFalse()
    {
        return FALSE;
    }

    protected BooleanAtom(String name, Boolean value)
    {
        super(name, value);
    }

    @Override
    public String getTypeName()
    {
        return TYPE_NAME;
    }
}
