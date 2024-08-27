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

import org.apache.maven.api.annotations.Experimental;
import org.apache.maven.cling.support.MavenOptionsSupport;
import picocli.CommandLine.Option;

/**
 * Apache Maven CLIng options.
 */
public class MavenClingOptions extends MavenOptionsSupport {
    /**
     * Experimental: CLIng own option, to delegate CLI to (legacy) MavenCli as is.
     */
    @Experimental
    @Option(
            names = {"-lc", "--legacy-cli"},
            arity = "0",
            description = "Use legacy CLI")
    protected boolean legacyCli;

    public boolean isLegacyCli() {
        return legacyCli;
    }
}
