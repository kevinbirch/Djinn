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
 * Created: 9/25/11 12:00 AM
 */

package com.webguys.djinn.marid;

import java.io.IOException;

import com.webguys.djinn.ifrit.AstToModelTransformer;
import com.webguys.djinn.ifrit.DjinnLexer;
import com.webguys.djinn.ifrit.DjinnParser;
import com.webguys.djinn.ifrit.DjinnParser.statement_return;
import com.webguys.djinn.ifrit.DjinnParser.translation_unit_return;
import com.webguys.djinn.ifrit.model.Executable;
import com.webguys.djinn.ifrit.model.Lambda;
import com.webguys.djinn.ifrit.model.Module;
import com.webguys.djinn.marid.runtime.Context;
import com.webguys.djinn.marid.runtime.Dictionary;
import com.webguys.djinn.marid.runtime.ImmediateStatement;
import com.webguys.djinn.marid.runtime.Stack;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import scala.tools.jline.console.ConsoleReader;

public class REPL
{
    private ConsoleReader reader;
    private Dictionary dictionary;
    private Context context;

    private boolean done;

    public REPL() throws Exception
    {
        this.reader = new ConsoleReader();
        Dictionary root = Dictionary.getRootDictionary();

        this.parseFile("djinn/primitives.djinn", root);

        this.dictionary = root.newChild();
        this.context = new Context(new Stack(), this.dictionary);
    }

    private void run() throws Exception
    {
        this.reader.setPrompt("User> ");

        this.reader.println("Welcome to Djinn.");
        this.reader.println("Type \":quit\" or \":exit\" to end your session.  Type \":help\" for instructions.");

        while(!this.done)
        {
            String input = this.read();
            String result = this.eval(input);
            this.print(result);
        }
    }

    private String read() throws IOException
    {
        return this.reader.readLine();
    }

    private String eval(String input) throws Exception
    {
        if(null == input)
        {
            return this.evalCommand(":exit");
        }
        else if(input.startsWith(":"))
        {
            return this.evalCommand(input);
        }
        else
        {
            return this.evalStatement(input);
        }
    }

    private String evalCommand(String input) throws Exception
    {
        if(":quit".equalsIgnoreCase(input) || ":exit".equalsIgnoreCase(input))
        {
            this.done = true;
            return "goodbye.";
        }
        else if(":help".equalsIgnoreCase(input))
        {
            return "coming soon.";
        }
        else if(":no-warranty".equalsIgnoreCase(input))
        {
            return "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                   "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                   "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
                   "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                   "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                   "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
                   "SOFTWARE.";
        }
        else if(":stack".equalsIgnoreCase(input))
        {
            return "stack: " + this.context.getStack();
        }
        else if(":depth".equalsIgnoreCase(input))
        {
            return String.valueOf(this.context.getStack().depth());
        }
        else
        {
            return "error: command \"" + input.substring(1) + "\" was not understood";
        }
    }

    private String evalStatement(String input) throws Exception
    {
        try
        {
            Executable result = this.parseStatement(input);
            if(result instanceof ImmediateStatement)
            {
                result.execute(this.context);
                return "stack: " + this.context.getStack();
            }
            else if(result instanceof Lambda)
            {
                this.context.getStack().push((Lambda)result);
                return "stack: " + this.context.getStack();
            }
            else
            {
                return "=> " + result.toString();
            }
        }
        catch(Exception e)
        {
            return "error: " + e.getMessage();
        }
    }

    private void print(String result) throws Exception
    {
        this.reader.println(result);
        this.reader.flush();
        this.reader.println();
    }

    private Executable parseStatement(String input) throws Exception
    {
        CharStream inputStream = new ANTLRStringStream(input);
        DjinnLexer lexer = new DjinnLexer(inputStream);

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        DjinnParser parser = new DjinnParser(tokenStream);

        statement_return result = parser.statement();
        CommonTree tree = (CommonTree)result.getTree();

        CommonTreeNodeStream nodes = new CommonTreeNodeStream(tree);
        nodes.setTokenStream(tokenStream);

        AstToModelTransformer walker = new AstToModelTransformer(nodes, this.dictionary);
        return walker.statement();
    }

    private Module parseFile(String path, Dictionary dictionary) throws Exception
    {
        ClassLoader loader = this.getClass().getClassLoader();
        ANTLRInputStream input = new ANTLRInputStream(loader.getResourceAsStream(path));
        DjinnLexer lexer = new DjinnLexer(input);

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        DjinnParser parser = new DjinnParser(tokenStream);

        translation_unit_return result = parser.translation_unit();
        CommonTree tree = (CommonTree)result.getTree();

        CommonTreeNodeStream nodes = new CommonTreeNodeStream(tree);
        nodes.setTokenStream(tokenStream);

        AstToModelTransformer walker = new AstToModelTransformer(nodes, dictionary);
        return walker.translation_unit();
    }

    public static void main(String[] args)
    {
        try
        {
            REPL repl = new REPL();
            repl.run();
        }
        catch(Throwable e)
        {
            System.err.println("error: Unhandled fatal exception.  No restarts available, exiting.");
            e.printStackTrace();
        }
    }
}
