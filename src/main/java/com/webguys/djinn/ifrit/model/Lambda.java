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
 * Created: 9/25/11 10:22 PM
 */

package com.webguys.djinn.ifrit.model;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.webguys.djinn.ifrit.metamodel.MetaObject;
import com.webguys.djinn.marid.runtime.Context;
import org.apache.commons.lang3.StringUtils;

public class Lambda extends MetaObject implements Atom<List<Atom>>
{
    private static final String TYPE_NAME = "Lambda";

    private ImmutableList<Atom> body;

    public Lambda(List<Atom> body)
    {
        super(gensym(TYPE_NAME));
        this.body = ImmutableList.copyOf(body);
    }

    protected Lambda(String name, List<Atom> body)
    {
        super(name);
        this.body = ImmutableList.copyOf(body);
    }

    @Override
    public void execute(Context context)
    {
        for(Atom atom : this.body)
        {
            atom.execute(context);
        }
    }

    public ImmutableList<Atom> getBody()
    {
        return this.body;
    }

    @Override
    public ImmutableList<Atom> getValue()
    {
        return this.body;
    }

    @Override
    public String toSourceRep()
    {
        return "[" + StringUtils.join(Lists.transform(this.body, Atom.TO_SOURCE_REP), " ") + "]";
    }

    @Override
    public String getTypeName()
    {
        return TYPE_NAME;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(o == null || getClass() != o.getClass())
        {
            return false;
        }

        Lambda lambda = (Lambda)o;

        return this.body.equals(lambda.body);

    }

    @Override
    public int hashCode()
    {
        return this.body.hashCode();
    }
}
