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
 * Created: 9/28/11 11:53 PM
 */

package com.webguys.djinn.ifrit.metamodel;

import java.nio.charset.Charset;
import java.util.UUID;

public abstract class MetaObject
{
    private String name;

    public MetaObject(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<<").append(this.getTypeName()).append(">> ");
        if(this instanceof Element)
        {
            Container container = ((Element)this).getContainer();
            if(null != container)
            {
                sb.append(container.getTypeName()).append("::");
            }
        }
        sb.append("\"").append(this.getName()).append("\"");
        return sb.toString();
    }

    public abstract String getTypeName();

    protected static String gensym(String base)
    {
        String tag = String.format("tag:djinn,%s:%s", System.currentTimeMillis(), base);
        return UUID.nameUUIDFromBytes(tag.getBytes(Charset.forName("UTF-8"))).toString();
    }
}
