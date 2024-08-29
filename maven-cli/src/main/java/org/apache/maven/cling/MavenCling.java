/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.cling;

import javax.lang.model.SourceVersion;
import javax.tools.Tool;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import org.apache.maven.cli.CLIReportingUtils;
import org.apache.maven.cli.MavenCli;
import org.apache.maven.cling.support.MavenOptions;
import org.apache.maven.jline.MessageUtils;
import org.codehaus.plexus.classworlds.ClassWorld;

import static java.util.Objects.requireNonNull;

/**
 * Maven CLI "new-gen".
 */
public class MavenCling implements Tool {
    protected static final String CORE_CLASS_REALM_ID = "plexus.core";

    /**
     * "Normal" Java entry point. Note: Maven uses ClassWorld Launcher and this entry point is NOT used under normal
     * circumstances.
     */
    public static void main(String[] args) {
        System.exit(new MavenCling().run(null, null, null, args));
    }

    /**
     * ClassWorld Launcher "enhanced" entry point: returning exitCode and accepts Class World.
     */
    public static int main(String[] args, ClassWorld world) {
        return new MavenCling(world).run(null, null, null, args);
    }

    /**
     * Helper method for ad-hoc ClassWorld creation. Note: IF you invoke this, you must properly close it as well.
     */
    private static ClassWorld createClassWorld() {
        return new ClassWorld(CORE_CLASS_REALM_ID, Thread.currentThread().getContextClassLoader());
    }

    private final ClassWorld classWorld;
    private final boolean classWorldManaged;

    public MavenCling() {
        this.classWorld = createClassWorld();
        this.classWorldManaged = true;
    }

    public MavenCling(ClassWorld classWorld) {
        this.classWorld = requireNonNull(classWorld);
        this.classWorldManaged = false;
    }

    @Override
    public String name() {
        return "mvn";
    }

    @Override
    public Set<SourceVersion> getSourceVersions() {
        return EnumSet.range(SourceVersion.RELEASE_17, SourceVersion.latestSupported());
    }

    /**
     * Override this method to introduce new options (by extending {@link MavenOptions}).
     */
    protected MavenOptions createMavenOptions(String[] args) {
        return new MavenOptions(name(), args);
    }

    @Override
    public int run(InputStream in, OutputStream out, OutputStream err, String... args) {
        // TODO: in/out/err?
        MavenOptions mavenOptions = createMavenOptions(args);
        if (mavenOptions.failed()) {
            mavenOptions.printParsingError(System.err);
            mavenOptions.printUsage(System.out);
            return 1;
        }
        return doRun(mavenOptions);
    }

    protected int doRun(MavenOptions mavenOptions) {
        if (mavenOptions.isLegacyCli()) {
            // just delegate it: but filter out one command legacy does not know
            // TODO: how to not duplicate string literal here?
            return MavenCli.main(
                    mavenOptions.getArgs().stream()
                            .filter(a -> !a.equals("--legacy-cli"))
                            .toArray(String[]::new),
                    classWorld);
        } else {
            if (mavenOptions.isShowVersionAndExit()) {
                if (mavenOptions.isQuiet()) {
                    System.out.println(CLIReportingUtils.showVersionMinimal());
                } else {
                    System.out.println(CLIReportingUtils.showVersion());
                }
                return 0;
            } else if (mavenOptions.getGoals().orElseGet(Collections::emptyList).isEmpty()) {
                System.err.println("No goals specified!");
                mavenOptions.printUsage(System.out);
                return 1;
            }
            return executeMaven(mavenOptions);
        }
    }

    protected int executeMaven(MavenOptions mavenOptions) {
        MessageUtils.systemInstall();
        MessageUtils.registerShutdownHook();
        try {
            System.out.println("Hello World!");
            return 0;
            //            initialize(cliRequest);
            //            cli(cliRequest);
            //            properties(cliRequest);
            //            logging(cliRequest);
            //            informativeCommands(cliRequest);
            //            version(cliRequest);
            //            localContainer = container(cliRequest);
            //            commands(cliRequest);
            //            configure(cliRequest);
            //            toolchains(cliRequest);
            //            populateRequest(cliRequest);
            //            encryption(cliRequest);
            //            return execute(cliRequest);

        } finally {
            MessageUtils.systemUninstall();
        }
    }
}
