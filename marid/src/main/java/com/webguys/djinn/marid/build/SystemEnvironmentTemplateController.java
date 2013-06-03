/*
 * The MIT License
 *
 * Copyright (c) 2012 Kevin Birch <kevin.birch@gmail.com>. Some rights reserved.
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

package com.webguys.djinn.marid.build;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.webguys.djinn.marid.runtime.Version;
import com.webguys.ponzu.api.block.function.Function;
import com.webguys.ponzu.impl.map.mutable.UnifiedMap;

/**
 * Created: 3/18/12 6:14 PM
 */
public class SystemEnvironmentTemplateController
{
    public static Map<String, ? extends Map<String, String>> getProperties() throws Exception
    {
        UnifiedMap<String, String> environment = UnifiedMap.newMap();

        Properties properties = new Properties();

        File versionFile = new File(System.getProperty("user.dir") + "/marid/target/classes/version.properties");
        FileInputStream versionInputStream = new FileInputStream(versionFile);
        properties.load(versionInputStream);
        versionInputStream.close();

        File systemFile = new File(System.getProperty("user.dir") + "/marid/target/classes/system.properties");
        FileInputStream systemInputStream = new FileInputStream(systemFile);
        properties.load(systemInputStream);
        systemInputStream.close();

        for(Entry<Object, Object> entry : properties.entrySet())
        {
            String key = entry.getKey().toString().replace(".", "-");
            if(!key.startsWith("system"))
            {
                key = "system-" + key;
            }
            environment.put(key, "\"" + entry.getValue().toString() + "\"");
        }

        Version version = new Version(properties);
        environment.put("system-version", "\"" + version.getVersionDetails() + "\"");
        environment.put("system-build-environment", "\"" + version.getBuildEnvironment() + "\"");

        environment.put("system-environment", environment.keysView().transform(new Function<String, String>()
        {
            @Override
            public String valueOf(String each)
            {
                return "\"" + each + "\"";
            }
        }).makeString("(", " ", ")"));

        return UnifiedMap.newWithKeysValues("map", environment);
    }
}
