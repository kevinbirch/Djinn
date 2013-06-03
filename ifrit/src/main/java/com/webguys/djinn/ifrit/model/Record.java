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
 * Created: 10/26/11 10:11 PM
 */

package com.webguys.djinn.ifrit.model;

import com.webguys.djinn.ifrit.metamodel.Association;
import com.webguys.djinn.ifrit.metamodel.ComplexType;
import ponzu.api.RichIterable;
import ponzu.api.list.MutableList;
import ponzu.impl.factory.Lists;

public class Record extends ComplexType<SingleDeclaration> implements Entry
{
    private Module parent;
    private MutableList<Association> associations = Lists.mutable.of();

    public Record(String name)
    {
        this(name, Module.getRootModule());
    }

    public Record(String name, Module parent)
    {
        super(name);
        this.parent = parent;
    }

    @Override
    public String getTypeName()
    {
        return "Record";
    }

    @Override
    public Module getContainer()
    {
        return this.parent;
    }
    
    public RichIterable<Association> getAssociations()
    {
        return this.associations;
    }
    
    public void addAssociation(Association association)
    {
        this.associations.add(association);
    }
    
    public void removeAssociation(Association association)
    {
        this.associations.remove(association);
    }
}
