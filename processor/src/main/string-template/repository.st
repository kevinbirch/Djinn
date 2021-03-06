repository(types, names) ::= <<
/**
 * The MIT License
 *
 * Copyright (c) 2011 Kevin Birch \<kevin.birch@gmail.com\>. Some rights reserved.
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
 */

package com.webguys.djinn.marid.primitive;

import com.webguys.ponzu.api.map.ImmutableMap;
import com.webguys.ponzu.impl.map.mutable.UnifiedMap;

import com.webguys.djinn.ifrit.model.Method;
import com.webguys.djinn.ifrit.model.ModuleFunction;
<types:{each |
import <each>;
}>

public class BuiltinRepository
{
    private static final ImmutableMap\<String, BuiltinFactory\> factories = UnifiedMap.\<String, BuiltinFactory\>newMap()
        <names:{each |
        .withKeyValue(<each>.NAME, new BuiltinFactory()
        {
            public ModuleFunction makeInstance(Method method)
            {
                return new <each>(method);
            \}
        \})
        }>
        .toImmutable();

    public static ModuleFunction getBuiltinFunction(Method method)
    {
        BuiltinFactory factory = factories.get(method.getName());
        return null == factory ? null : factory.makeInstance(method);
    }

    private static interface BuiltinFactory
    {
        ModuleFunction makeInstance(Method method);
    }
}
>>
