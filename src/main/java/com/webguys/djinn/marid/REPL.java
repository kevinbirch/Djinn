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

import com.webguys.djinn.ifrit.Mk1_Mod0Lexer;
import com.webguys.djinn.ifrit.Mk1_Mod0Parser;
import com.webguys.djinn.ifrit.Mk1_Mod0Parser.statement_return;
import com.webguys.djinn.ifrit.Mk1_Mod0Walker;
import com.webguys.djinn.ifrit.model.Executable;
import com.webguys.djinn.marid.runtime.Dictionary;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import scala.tools.jline.console.ConsoleReader;

public class REPL
{
    private Dictionary dictionary = new Dictionary(Dictionary.getRootDictionary());

    private int execute() throws Exception
    {
        ConsoleReader reader = new ConsoleReader();
        reader.setPrompt("System> ");

        reader.println("Welcome to Djinn.");
        reader.println("Type \":quit\" or \":exit\" to end your session.  Type \":help\" for instructions.");

        while(true)
        {
            String input = reader.readLine();

            if(":quit".equalsIgnoreCase(input) || ":exit".equalsIgnoreCase(input))
            {
                break;
            }
            else if(":help".equalsIgnoreCase(input))
            {
                help(reader);
                continue;
            }

            try
            {
                Executable result = this.parseAndWalk(input);
                reader.println("<= " + result.toString());
                reader.println();
            }
            catch(Exception e)
            {
                reader.println("error: " + e.getMessage());
            }
        }

        return 0;
    }

    private static void help(ConsoleReader reader) throws Exception
    {
        reader.println("coming soon.");
    }

    private Executable parseAndWalk(String input) throws Exception
    {
        CharStream inputStream = new ANTLRStringStream(input);
        Mk1_Mod0Lexer lexer = new Mk1_Mod0Lexer(inputStream);

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        Mk1_Mod0Parser parser = new Mk1_Mod0Parser(tokenStream);

        statement_return result = parser.statement();
        CommonTree tree = (CommonTree)result.getTree();

        CommonTreeNodeStream nodes = new CommonTreeNodeStream(tree);
        nodes.setTokenStream(tokenStream);

        Mk1_Mod0Walker walker = new Mk1_Mod0Walker(nodes, this.dictionary);
        return walker.statement();
    }

    public static void main(String[] args)
    {
        REPL repl = new REPL();

        try
        {
            repl.execute();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
