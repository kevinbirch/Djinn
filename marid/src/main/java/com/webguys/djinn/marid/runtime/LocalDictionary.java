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
 * Created: 9/24/11 5:05 PM
 */

package com.webguys.djinn.marid.runtime;

import java.util.Map;

import com.webguys.djinn.ifrit.model.Declaration;
import com.webguys.djinn.ifrit.model.Dictionary;
import com.webguys.djinn.ifrit.model.Executable;
import com.webguys.djinn.ifrit.model.Method;
import ponzu.impl.map.mutable.UnifiedMap;

public class LocalDictionary implements Dictionary
{
    private static final LocalDictionary ROOT = new LocalDictionary();

    private static boolean redefinitionAllowed = true;

    private Map<String, Method> methods = UnifiedMap.newMap();
    private Map<String, Declaration> declarations = UnifiedMap.newMap();

    private Dictionary parent;

    public static LocalDictionary getRootDictionary()
    {
        return ROOT;
    }

    public LocalDictionary(Dictionary parent)
    {
        this.parent = parent;
    }

    private LocalDictionary()
    {
        this.parent = null;
    }

    public static boolean isRedefinitionAllowed()
    {
        return redefinitionAllowed;
    }

    public static void setRedefinitionAllowed(boolean redefinitionAllowed)
    {
        LocalDictionary.redefinitionAllowed = redefinitionAllowed;
    }

    @Override
    public Dictionary newChild()
    {
        return new LocalDictionary(this);
    }

    @Override
    public Method getOrCreateMethod(String name)
    {
        if(this.isMethodDefined(name))
        {
            return this.getMethod(name);
        }
        else
        {
            Method method = new Method(name);
            this.defineMethod(method);
            return method;
        }
    }

    @Override
    public void defineMethod(Method method)
    {
        this.methods.put(method.getName(), method);
    }

    @Override
    public boolean isMethodDefined(String name)
    {
        return this.methods.containsKey(name) || (null != this.parent && this.parent.isMethodDefined(name));
    }

    @Override
    public Method getMethod(String name)
    {
        Method result = this.methods.get(name);
        return null == result && null != this.parent ? this.parent.getMethod(name) : result;
    }

    @Override
    public void defineName(Declaration declaration)
    {
        this.declarations.put(declaration.getName(), declaration);
    }

    @Override
    public boolean isNameDefined(String name)
    {
        return this.declarations.containsKey(name) || (null != this.parent && this.parent.isNameDefined(name));
    }

    @Override
    public Declaration getDeclaration(String name)
    {
        Declaration result = this.declarations.get(name);
        return null == result && null != this.parent ? this.parent.getDeclaration(name) : result;
    }

    @Override
    public boolean isSymbolDefined(String symbol)
    {
        return this.isMethodDefined(symbol) || this.isNameDefined(symbol);
    }

    @Override
    public Executable getSymbol(String symbol)
    {
        Executable result = null;
        if(this.isMethodDefined(symbol))
        {
            result = this.getMethod(symbol);
        }
        else if(this.isNameDefined(symbol))
        {
            result = this.getDeclaration(symbol);
        }

        return result;
    }
}
