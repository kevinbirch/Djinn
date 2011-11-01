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
 * Created: 10/26/11 9:07 PM
 */

package com.webguys.djinn.ifrit.metamodel;

import java.util.Set;

import com.google.common.collect.Sets;

public class ComplexType<T extends Member> extends MetaObject implements Type, Container<T>
{
    private Set<T> members = Sets.newHashSet();
    private Set<ComplexType> generalizations = Sets.newHashSet();
    private Set<ComplexType> specialisation = Sets.newHashSet();
    
    public ComplexType(String name)
    {
        super(name);
    }

    public void addMember(T member)
    {
        this.members.add(member);
    }
    
    @Override
    public Iterable<T> getChildren()
    {
        return this.members;
    }

    public boolean isMember(T member)
    {
        return this.members.contains(member);
    }

    public void addGeneralization(ComplexType complexType)
    {
        this.generalizations.add(complexType);
        complexType.addSpecialisation(this);
    }

    public void addSpecialisation(ComplexType complexType)
    {
        this.specialisation.add(complexType);
    }

    @Override
    public String getTypeName()
    {
        return "ComplexType";
    }
}
