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
 * Created: 11/9/11 8:47 PM
 */

package com.webguys.djinn.ifrit.model;

import com.webguys.djinn.marid.runtime.Stack;

class ShortStack implements Stack
{
    private Atom top;

    @Override
    public void push(Atom atom)
    {
        this.top = atom;
    }

    @Override
    public <T extends Atom> T pop()
    {
        return (T)this.top;
    }

    @Override
    public Atom peek()
    {
        return this.top;
    }

    @Override
    public Atom peek(int depth)
    {
        return 0 == depth ? this.top : null;
    }

    @Override
    public void drop()
    {
    }

    @Override
    public void nip()
    {
    }

    @Override
    public void dup()
    {
    }

    @Override
    public void swap()
    {
    }

    @Override
    public void roll(int movement)
    {
    }

    @Override
    public int depth()
    {
        return 1;
    }

    @Override
    public void clear()
    {
        this.top = null;
    }

    @Override
    public Stack clone()
    {
        throw new RuntimeException("not supported");
    }
}
