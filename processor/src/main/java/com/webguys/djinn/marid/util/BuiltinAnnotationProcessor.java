/*
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
 *　古池や蛙飛び込む水の音
 */

package com.webguys.djinn.marid.util;

import java.io.*;
import java.util.Set;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import org.apache.commons.io.IOUtils;
import org.stringtemplate.v4.AutoIndentWriter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.misc.ErrorBuffer;
import ponzu.api.block.function.Function;
import ponzu.api.set.MutableSet;
import ponzu.impl.set.mutable.UnifiedSet;

@SupportedAnnotationTypes({"com.webguys.djinn.marid.primitive.Builtin"})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedOptions({"baseDirectory"})
public class BuiltinAnnotationProcessor extends AbstractProcessor
{
    private static final String TEMPLATE_DIR = "string-template";
    private static final String TEMPLATE = "repository";
    private static final String CLASSNAME = "com.webguys.djinn.marid.primitive.BuiltinRepository";
    private static final String DB_PATHNAME = "target/builtins-list.txt";

    private static final Function<Element, String> ELEMENT_TO_TYPESTRING = new Function<Element, String>()
    {
        @Override
        public String valueOf(Element each)
        {
            return each.asType().toString();
        }
    };

    public static final Function<String, String> FQ_TYPESTRING_TO_CLASSNAME = new Function<String, String>()
    {
        @Override
        public String valueOf(String each)
        {
            return each.substring(each.lastIndexOf(".") + 1);
        }
    };

    private final MutableSet<Element> elements = UnifiedSet.newSet();
    private File db;
    private JavaFileObject sourceFile;
    private UnifiedSet<String> builtins;

    @Override
    public void init(ProcessingEnvironment processingEnv)
    {
        super.init(processingEnv);
        this.db = new File(processingEnv.getOptions().get("baseDirectory"), DB_PATHNAME);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {
        try
        {
            for(TypeElement annotation : annotations)
            {
                this.elements.addAll(roundEnv.getElementsAnnotatedWith(annotation));
            }

            if(roundEnv.processingOver())
            {
                if(this.shouldGenerate())
                {
                    if(null == this.sourceFile)
                    {
                        this.sourceFile = this.processingEnv.getFiler().createSourceFile(CLASSNAME);
                    }

                    this.processingEnv.getMessager().printMessage(Kind.NOTE, "generating builtin repository.");
                    this.generate();
                }
                else
                {
                    this.processingEnv.getMessager().printMessage(Kind.NOTE, "NOT generating builtin repository.");
                }
            }
        }
        catch(Exception e)
        {
            String message = String.format("Unable to generate the code (%s).", e.getMessage());
            this.processingEnv.getMessager().printMessage(Kind.ERROR, message);
        }

        return true;
    }

    private boolean shouldGenerate() throws IOException
    {
        boolean result = false;
        UnifiedSet<String> typeStrings = this.elements.transform(ELEMENT_TO_TYPESTRING, UnifiedSet.<String>newSet(this.elements.size()));

        if(this.db.exists())
        {
            this.builtins = UnifiedSet.<String>newSet(IOUtils.readLines(new FileInputStream(this.db), "UTF-8"));
            if(!this.builtins.containsAll(typeStrings))
            {
                this.builtins.addAll(typeStrings);
                result = true;
            }
        }
        else
        {
            this.builtins = typeStrings;
            result = true;
        }

        return result;
    }

    private void generate() throws Exception
    {
        this.saveDatabase();

        ST st = this.loadTemplate();

        st.add("types", this.builtins);
        st.add("names", this.builtins.transform(FQ_TYPESTRING_TO_CLASSNAME));

        this.writeSourceFile(st);

    }

    private void saveDatabase() throws Exception
    {
        if(!db.exists() && !db.createNewFile())
        {
            throw new Exception(String.format("Unable to create the db file: %s", DB_PATHNAME));
        }

        PrintWriter writer = new PrintWriter(db, "UTF-8");
        IOUtils.writeLines(this.builtins, IOUtils.LINE_SEPARATOR_UNIX, writer);
        writer.flush();
        IOUtils.closeQuietly(writer);
    }

    private ST loadTemplate() throws Exception
    {
        STGroup group = new STGroupDir(TEMPLATE_DIR);
        ErrorBuffer loadErrorBuffer = new ErrorBuffer();
        group.setListener(loadErrorBuffer);
        ST st = group.getInstanceOf(TEMPLATE);

        if(null == st || !loadErrorBuffer.errors.isEmpty())
        {
            throw new Exception(String.format("Unable to load template. %n%s", loadErrorBuffer.toString()));
        }

        return st;
    }

    private void writeSourceFile(ST st) throws Exception
    {
        ErrorBuffer writeErrorBuffer = new ErrorBuffer();
        Writer writer = null;
        try
        {
            writer = this.sourceFile.openWriter();

            st.write(new AutoIndentWriter(writer), writeErrorBuffer);
        }
        finally
        {
            if(null != writer)
            {
                writer.flush();
            }
            IOUtils.closeQuietly(writer);
        }


        if(!writeErrorBuffer.errors.isEmpty())
        {
            throw new Exception(writeErrorBuffer.toString());
        }
    }
}
