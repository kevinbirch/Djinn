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
 * Created: 9/29/11 12:27 AM
 */

package com.webguys.djinn.ifrit.model;

import java.io.*;

public class Context
{
    private Stack stack;
    private Dictionary dictionary;
    private BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    private PrintWriter stdout = new PrintWriter(System.out, true);

    public Context(Stack stack, Dictionary dictionary)
    {
        this.stack = stack;
        this.dictionary = dictionary;
    }

    public Context(Stack stack, Dictionary dictionary, InputStream stdin, Writer stdout)
    {
        this.stack = stack;
        this.dictionary = dictionary;
        this.stdin = new BufferedReader(new InputStreamReader(stdin));
        this.stdout = new PrintWriter(stdout, true);
    }

    @Override
    public Context clone()
    {
        try
        {
            Context clone = (Context)super.clone();
            clone.stack = this.stack.clone();
            return clone;
        }
        catch(CloneNotSupportedException e)
        {
            throw new RuntimeException("Received unexpected exception when cloning.", e);
        }
    }

    public Stack getStack()
    {
        return this.stack;
    }

    public Dictionary getDictionary()
    {
        return this.dictionary;
    }

    public BufferedReader getStdin()
    {
        return this.stdin;
    }

    public void setStdin(InputStream stdin)
    {
        this.stdin = new BufferedReader(new InputStreamReader(stdin));
    }

    public PrintWriter getStdout()
    {
        return this.stdout;
    }

    public void setStdout(OutputStream stdout)
    {
        this.stdout = new PrintWriter(stdout, true);
    }
}
