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

public class Association extends MetaObject implements Relationship
{
    private Endpoint source;
    private Endpoint target;

    public Association(String name, Endpoint source, Endpoint target)
    {
        super(name);
        this.source = source;
        this.target = target;
    }

    public Endpoint getSource()
    {
        return source;
    }

    public Endpoint getTarget()
    {
        return target;
    }

    @Override
    public String getTypeName()
    {
        return null;
    }

    public static enum Cardinality
    {
        Unspecified,
        One,
        Many
    }

    public static enum Navigability
    {
        Unspecified,
        Navigable,
        Non_Navigable
    }

    public static enum Visibility
    {
        Public,
        Protected,
        Private
    }

    public static enum Associativity
    {
        Unspecified,
        Aggregation,
        Composition
    }

    public static class Endpoint
    {
        private String name;
        private Cardinality cardinality = Cardinality.One;
        private Navigability navigability = Navigability.Navigable;
        private Visibility visibility = Visibility.Public;
        private Associativity associativity = Associativity.Unspecified;
        private ComplexType target;

        public Endpoint(String name, ComplexType target)
        {
            this.target = target;
            this.name = name;
        }

        public String getName()
        {
            return name;
        }

        public ComplexType getTarget()
        {
            return target;
        }

        public Cardinality getCardinality()
        {
            return cardinality;
        }

        public void setCardinality(Cardinality cardinality)
        {
            this.cardinality = cardinality;
        }

        public Navigability getNavigability()
        {
            return navigability;
        }

        public void setNavigability(Navigability navigability)
        {
            this.navigability = navigability;
        }

        public Visibility getVisibility()
        {
            return visibility;
        }

        public void setVisibility(Visibility visibility)
        {
            this.visibility = visibility;
        }

        public Associativity getAssociativity()
        {
            return associativity;
        }

        public void setAssociativity(Associativity associativity)
        {
            this.associativity = associativity;
        }
    }
}
