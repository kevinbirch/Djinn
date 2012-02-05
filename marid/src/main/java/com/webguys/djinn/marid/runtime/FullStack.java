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
 * Created: 9/29/11 12:26 AM
 */

package com.webguys.djinn.marid.runtime;

import com.webguys.djinn.ifrit.model.Atom;
import com.webguys.djinn.ifrit.model.Stack;
import com.webguys.djinn.ifrit.model.StackUnderflowException;
import ponzu.api.list.MutableList;
import ponzu.impl.list.mutable.FastList;

public class FullStack implements Stack
{
    private MutableList<Atom> data = FastList.newList();

    @Override
    public void push(Atom atom)
    {
        this.data.add(0, atom);
    }

    @Override
    public <T extends Atom> T pop()
    {
        if(this.data.isEmpty())
        {
            throw new StackUnderflowException(1, 0);
        }
        return (T)this.data.remove(0);
    }

    @Override
    public Atom peek()
    {
        return this.peek(0);
    }

    @Override
    public Atom peek(int depth)
    {
        return this.data.size() > depth ? this.data.get(depth) : null;
    }

    @Override
    public void drop()
    {
        this.pop();
    }

    @Override
    public void dup()
    {
        if(this.data.isEmpty())
        {
            throw new StackUnderflowException(1, 0);
        }
        this.data.add(0, this.data.get(0));
    }

    @Override
    public void swap()
    {
        if(2 > this.data.size())
        {
            throw new StackUnderflowException(2, this.data.size());
        }
        Atom top = this.pop();
        this.data.add(1, top);
    }

    @Override
    public void roll(int movement)
    {
        if(this.data.size() > 0)
        {
            if(movement > 0)
            {
                Atom last = this.data.remove(this.data.size() - 1);
                this.data.add(0, last);
            }
            else
            {
                Atom first = this.data.remove(0);
                this.data.add(first);
            }
        }
    }

    @Override
    public int depth()
    {
        return this.data.size();
    }

    @Override
    public void clear()
    {
        this.data.clear();
    }

    @Override
    public Stack clone()
    {
        try
        {
            FullStack clone = (FullStack)super.clone();
            clone.data = this.data.clone();
            return clone;
        }
        catch(CloneNotSupportedException e)
        {
            throw new RuntimeException("Received unexpected exception when cloning.", e);
        }
    }

    @Override
    public String toString()
    {
        return this.data.reverseThis().transform(Atom.TO_SOURCE_REP).makeString(" ");
    }
}
