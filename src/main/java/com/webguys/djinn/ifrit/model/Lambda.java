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

import com.webguys.djinn.ifrit.metamodel.MetaObject;
import com.webguys.djinn.marid.runtime.Context;
import com.webguys.djinn.marid.runtime.Stack;

public class Lambda extends MetaObject implements Atom<List<Atom>>
{
    private List<Atom> body;

    public Lambda(List<Atom> body)
    {
        super(gensym("lambda"));
        this.body = body;
    }

    @Override
    public Stack execute(Context context)
    {
        return null;
    }

    public List<Atom> getBody()
    {
        return this.body;
    }

    @Override
    public List<Atom> getValue()
    {
        return this.body;
    }

    @Override
    public String toSourceRep()
    {
        StringBuilder sb = new StringBuilder("[");
        for(Atom atom : this.body)
        {
            sb.append(atom.toSourceRep());
            sb.append(' ');
        }
        sb.replace(sb.lastIndexOf(" "), sb.length(), "]");
        return sb.toString();
    }

    @Override
    public String getTypeName()
    {
        return "Lambda";
    }
}
