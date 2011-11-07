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
 * Created: 11/6/11 6:27 PM
 */

package com.webguys.djinn.marid.runtime;

import java.util.Properties;

public class Version
{
    private Properties properties;

    public Version() throws Exception
    {
        this.properties = new Properties();
        this.properties.load(this.getClass().getClassLoader().getResourceAsStream("version.properties"));
    }

    public String getVersionDetails()
    {
        return String.format("Djinn %s (%s@%s, %s)", this.getVersion(), this.getBuildNumber(), this.getBuildHostname(), this.getBuildDate());
    }

    public String getBuildEnvironment()
    {
        return String.format("[%s %s] on %s %s", this.getJavaVmName(), this.getJavaRuntimeVersion(), this.getBuildHostOs(), this.getBuildHostOsVersion());
    }

    public String getVersion()
    {
        return this.properties.getProperty("version");
    }

    public String getBuildDate()
    {
        return this.properties.getProperty("date");
    }

    public String getBuildNumber()
    {
        return this.properties.getProperty("build.number");
    }

    public String getBuildHostname()
    {
        return this.properties.getProperty("host.name");
    }

    public String getBuildHostOs()
    {
        return this.properties.getProperty("host.os");
    }

    public String getBuildHostOsVersion()
    {
        return this.properties.getProperty("host.os.vers");
    }

    public String getJavaVmName()
    {
        return this.properties.getProperty("java.vm.name");
    }

    public String getJavaRuntimeVersion()
    {
        return this.properties.getProperty("java.runtime.version");
    }
}
