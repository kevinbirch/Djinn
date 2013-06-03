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

import com.webguys.djinn.ifrit.model.*;
import com.webguys.djinn.marid.runtime.FullStack;
import com.webguys.djinn.marid.runtime.Runtime;
import com.webguys.ponzu.impl.utility.StringIterate;
import jline.console.ConsoleReader;

import java.io.IOException;

public class REPL
{
    private ConsoleReader console;
    private Context context;
    private Runtime runtime;

    private boolean done;

    public REPL() throws Exception
    {
        this.console = new ConsoleReader();
        this.console.setExpandEvents(false); // xxx - why does this implementation eat all occurrences of backslash?

        this.runtime = new Runtime();

        Dictionary dictionary = this.runtime.getRootDictionary().newChild();
        this.context = new Context(new FullStack(), dictionary, this.console.getInput(), this.console.getOutput());
    }

    private void run() throws Exception
    {
        this.console.setPrompt("User> ");

        this.console.println("Welcome to Djinn.");
        this.console.println("Type \":quit\" or \":exit\" to end your session.  Type \":help\" for instructions.");
        this.console.println();

        while(!this.done)
        {
            String input = this.read();
            String result = this.eval(input);
            this.print(result);
        }

        this.console.getTerminal().restore();
    }

    private String read() throws IOException
    {
        return this.console.readLine();
    }

    private String eval(String input) throws Exception
    {
        if(null == input)
        {
            this.console.println("^D");
            return this.evalCommand(":exit");
        }
        else if(StringIterate.isEmptyOrWhitespace(input))
        {
            return input;
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
            return "Goodbye.";
        }
        else if(":help".equalsIgnoreCase(input))
        {
            return "coming soon.";
        }
        else if(":version".equalsIgnoreCase(input))
        {
            return String.format("%s%n%s", this.runtime.getVersion().getVersionDetails(), this.runtime.getVersion().getBuildEnvironment());
        }
        else if(":license".equalsIgnoreCase(input))
        {
            return "  The MIT License\n" +
                   "\n" +
                   "  Copyright (c) 2011 Kevin Birch <kevin.birch@gmail.com>. Some rights reserved.\n" +
                   "\n" +
                   "  Permission is hereby granted, free of charge, to any person obtaining a copy of\n" +
                   "  this software and associated documentation files (the \"Software\"), to deal in\n" +
                   "  the Software without restriction, including without limitation the rights to\n" +
                   "  use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies\n" +
                   "  of the Software, and to permit persons to whom the Software is furnished to do\n" +
                   "  so, subject to the following conditions:\n" +
                   "\n" +
                   "  The above copyright notice and this permission notice shall be included in all\n" +
                   "  copies or substantial portions of the Software.\n" +
                   "\n" +
                   "  THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                   "  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                   "  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
                   "  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                   "  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                   "  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
                   "  SOFTWARE.";
        }
        else if(":no-warranty".equalsIgnoreCase(input))
        {
            return "  THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                   "  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                   "  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
                   "  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                   "  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                   "  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
                   "  SOFTWARE.";
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
            Executable result = this.runtime.parseStatement(input, this.context.getDictionary());
            if(result instanceof ImmediateStatement)
            {
                result.execute(this.context);
                return this.context.getStack().isEmpty() ? "stack empty" : "stack: " + this.context.getStack();
            }
            else
            {
                if(result instanceof Declaration)
                {
                    ((Declaration)result).initialize(this.context);
                }
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
        this.console.println(result);
        if(!done)
        {
            this.console.println();
        }
        this.console.flush();
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
