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

import java.util.HashMap;
import java.util.Map;

import com.webguys.djinn.ifrit.model.Declaration;
import com.webguys.djinn.ifrit.model.Function;
import com.webguys.djinn.ifrit.model.Method;

public class Dictionary
{
    private static final Dictionary ROOT = new Dictionary();

    private Map<String, Function> functions = new HashMap<String, Function>();
    private Map<String, Method> methods = new HashMap<String, Method>();
    private Map<String, Declaration> declarations = new HashMap<String, Declaration>();
    
    private Dictionary parent;

    public static Dictionary getRootDictionary()
    {
        return ROOT;
    }

    public Dictionary(Dictionary parent)
    {
        this.parent = parent;
    }

    private Dictionary()
    {
        this.parent = null;
    }

    public void defineFunction(Function function)
    {
        this.functions.put(function.getName(), function);
        this.methods.put(function.getFamily().getName(), function.getFamily());
    }

    public boolean isFunctionDefined(String name)
    {
        return this.functions.containsKey(name) || (null != this.parent && this.parent.isFunctionDefined(name));
    }

    public Function getFunction(String name)
    {
        Function result = this.functions.get(name);
        return null == result && null != this.parent ? this.parent.getFunction(name) : result;
    }

    public boolean isMethodDefined(String name)
    {
        return this.methods.containsKey(name) || (null != this.parent && this.parent.isMethodDefined(name));
    }

    public Method getMethod(String name)
    {
        Method result = this.methods.get(name);
        return null == result && null != this.parent ? this.parent.getMethod(name) : result;
    }

    public void defineName(Declaration declaration)
    {
        this.declarations.put(declaration.getName(), declaration);
    }

    public boolean isNameDefined(String name)
    {
        return this.declarations.containsKey(name) || (null != this.parent && this.parent.isNameDefined(name));
    }

    public Declaration getDeclaration(String name)
    {
        Declaration result = this.declarations.get(name);
        return null == result && null != this.parent ? this.parent.getDeclaration(name) : result;
    }
}
