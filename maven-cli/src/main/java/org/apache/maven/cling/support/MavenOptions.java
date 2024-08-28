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
package org.apache.maven.cling.support;

import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import static java.util.Objects.requireNonNull;

/**
 * Generic Apache Maven CLI options.
 */
public class MavenOptions {
    private final String commandName;
    private final List<String> args;
    private final CommandLine commandLine;

    public MavenOptions(String commandName, String... args) {
        this.commandName = requireNonNull(commandName);
        this.args = Arrays.asList(args);
        this.commandLine = new CommandLine(this).setCommandName(commandName);

        this.commandLine.parseArgs(args);
    }

    public void printUsage(PrintStream out) {
        this.commandLine.usage(out);
    }

    @Option(
            names = {"-h", "--help"},
            arity = "0",
            description = "Display help information")
    protected boolean help;

    @Option(
            names = {"-f", "--file"},
            arity = "1",
            paramLabel = "<file>",
            description = "Force the use of an alternate POM file (or directory with pom.xml)")
    protected Path alternatePomFile;

    @Option(
            names = {"-D", "--define"},
            arity = "1",
            paramLabel = "<property>",
            description = "Define a user property in form of key=value, if no value set it becomes 'true'")
    protected List<String> userProperties;

    @Option(
            names = {"-o", "--offline"},
            arity = "0",
            description = "Work offline")
    protected boolean offline;

    @Option(
            names = {"-v", "--version"},
            arity = "0",
            description = "Display version information and exit")
    protected boolean showVersionAndExit;

    @Option(
            names = {"-q", "--quiet"},
            arity = "0",
            description = "Quiet execution output - only show errors")
    protected boolean quiet;

    @Option(
            names = {"-X", "--verbose"},
            arity = "0",
            description = "Verbose execution output")
    protected boolean verbose;

    @Option(
            names = {"-e", "--errors"},
            arity = "0",
            description = "Produce execution error messages and stack traces")
    protected boolean errors;

    @Option(
            names = {"-N", "--non-recursive"},
            arity = "0",
            description =
                    "Do not recurse into sub-projects. When used together with -pl, do not recurse into sub-projects of selected aggregators")
    protected boolean nonRecursive;

    @Option(
            names = {"-U", "--update-snapshots"},
            arity = "0",
            description = "Forces a check for missing releases and updated snapshots on remote repositories")
    protected boolean updateSnapshots;

    @Option(
            names = {"-P", "--activate-profiles"},
            arity = "1",
            split = ",",
            paramLabel = "<profile>",
            description =
                    "Comma-delimited list of profiles to activate. Prefixing a profile with ! excludes it, and ? marks it as optional")
    protected List<String> activatedProfiles;

    @Option(
            names = {"-B", "--batch-mode", "--non-interactive"},
            arity = "0",
            description = "Run in non-interactive (batch) mode")
    protected boolean nonInteractive;

    @Option(
            names = {"--force-interactive"},
            arity = "0",
            description =
                    "Run in interactive mode. Overrides, if applicable, the CI environment variable and --non-interactive/--batch-mode options")
    protected boolean forceInteractive;

    @Option(
            names = {"-nsu", "--no-snapshot-updates"},
            arity = "0",
            description = "Suppress SNAPSHOT updates")
    protected boolean suppressSnapshotUpdates;

    @Option(
            names = {"-C", "--strict-checksums"},
            arity = "0",
            description = "Fail the build if checksums don't match")
    protected boolean strictChecksums;

    @Option(
            names = {"-c", "--lax-checksums"},
            arity = "0",
            description = "Warn if checksums don't match")
    protected boolean relaxedChecksums;

    @Option(
            names = {"-s", "--settings"},
            arity = "1",
            paramLabel = "<file>",
            description = "Alternate path for the user settings file")
    protected Path altUserSettings;

    @Option(
            names = {"-ps", "--project-settings"},
            arity = "1",
            paramLabel = "<file>",
            description = "Alternate path for the project settings file")
    protected Path altProjectSettings;

    @Deprecated
    @Option(
            names = {"-gs", "--global-settings"},
            arity = "1",
            paramLabel = "<file>",
            description = "Alternate path for the global settings file")
    protected Path altGlobalSettings;

    @Option(
            names = {"-is", "--install-settings"},
            arity = "1",
            paramLabel = "<file>",
            description = "Alternate path for the installation settings file")
    protected Path altInstallationSettings;

    @Option(
            names = {"-t", "--toolchains"},
            arity = "1",
            paramLabel = "<file>",
            description = "Alternate path for the user toolchains file")
    protected Path altUserToolchains;

    @Deprecated
    @Option(
            names = {"-gt", "--global-toolchains"},
            arity = "1",
            paramLabel = "<file>",
            description = "Alternate path for the global toolchains file")
    protected Path altGlobalToolchains;

    @Option(
            names = {"-it", "--install-toolchains"},
            arity = "1",
            paramLabel = "<file>",
            description = "Alternate path for the installation toolchains file")
    protected Path altInstallationToolchains;

    public enum FailOnSeverityOption {
        warn,
        error
    }

    @Option(
            names = {"-fos", "--fail-on-severity"},
            arity = "1",
            paramLabel = "<severity>",
            description =
                    "Configure which severity of logging should cause the build to fail. Supported values are 'warn' and 'error'")
    protected FailOnSeverityOption failOnSeverity;

    @Option(
            names = {"-ff", "--fail-fast"},
            arity = "0",
            description = "Stop at first failure in build")
    protected boolean failFast;

    @Option(
            names = {"-fae", "--fail-at-end"},
            arity = "0",
            description = "Only fail the build afterwards; allow all non-impacted builds to continue")
    protected boolean failAtEnd;

    @Option(
            names = {"-fn", "--fail-never"},
            arity = "0",
            description = "Never fail the build, regardless of project result")
    protected boolean failNever;

    @Option(
            names = {"-r", "--resume"},
            arity = "0",
            description =
                    "Resume reactor from the last failed project, using the resume.properties file in the build directory")
    protected boolean resume;

    @Option(
            names = {"-rf", "--resume-from"},
            arity = "1",
            paramLabel = "<project>",
            description = "Resume reactor from specified project")
    protected String resumeFrom;

    @Option(
            names = {"-pl", "--projects"},
            arity = "1..*",
            split = ",",
            paramLabel = "<project>",
            description =
                    "Comma-delimited list of specified reactor projects to build instead of all projects. A project can be specified by [groupId]:artifactId or by its relative path. Prefixing a project with ! excludes it, and ? marks it as optional")
    protected List<String> projects;

    @Option(
            names = {"-am", "--also-make"},
            arity = "1..*",
            split = ",",
            paramLabel = "<project>",
            description = "If project list is specified, also build projects required by this list")
    protected List<String> alsoMake;

    @Option(
            names = {"-amd", "--also-make-dependents"},
            arity = "0",
            description = "If project list is specified, also build projects that depend on projects on the list")
    protected boolean alsoMakeDependents;

    @Option(
            names = {"-l", "--log-file"},
            arity = "1",
            paramLabel = "<file>",
            description = "Log file where all build output will go (disables output color)")
    protected Path logFile;

    @Option(
            names = {"-V", "--show-version"},
            arity = "0",
            description = "Display version information without exiting")
    protected boolean showVersion;

    @Option(
            names = {"-T", "--threads"},
            arity = "1",
            description = "Thread count, for instance 4 (int) or 2C/2.5C (int/float) where C is core multiplied")
    protected String threads;

    @Option(
            names = {"-b", "--builder"},
            arity = "1",
            description = "The id of the build strategy to use")
    protected String builder;

    @Option(
            names = {"-ntp", "--no-transfer-progress"},
            arity = "0",
            description = "Do not display transfer progress when downloading or uploading")
    protected boolean noTransferProgress;

    public enum ColorOption {
        auto,
        always,
        never
    }

    @Option(
            names = {"--color"},
            arity = "1",
            description = "Defines the color mode of the output. Supported are 'auto', 'always', 'never'")
    protected ColorOption color = ColorOption.auto;

    @Option(
            names = {"-canf", "--cache-artifact-not-found"},
            arity = "0..1",
            paramLabel = "<bool>",
            description =
                    "Defines caching behaviour for 'not found' artifacts. Supported values are 'true' (default), 'false'")
    protected boolean cacheArtifactNotFound = true;

    @Option(
            names = {"-sadp", "--strict-artifact-descriptor-policy"},
            arity = "0..1",
            paramLabel = "<bool>",
            description = "Defines 'strict' artifact descriptor policy. Supported values are 'true', 'false' (default)")
    protected boolean strictArtifactDescriptorPolicy;

    @Option(
            names = {"-itr", "--ignore-transitive-repositories"},
            arity = "0",
            description = "If set, Maven will ignore remote repositories introduced by transitive dependencies")
    protected boolean ignoreTransitiveRepositories;

    @Parameters(paramLabel = "GOALS", arity = "0..*", description = "List of phases and/or goals")
    protected List<String> goals;

    public boolean isHelp() {
        return help;
    }

    public Optional<Path> getAlternatePomFile() {
        return Optional.ofNullable(alternatePomFile);
    }

    public Optional<List<String>> getUserProperties() {
        return Optional.ofNullable(userProperties);
    }

    public boolean isOffline() {
        return offline;
    }

    public boolean isShowVersionAndExit() {
        return showVersionAndExit;
    }

    public boolean isQuiet() {
        return quiet;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public boolean isErrors() {
        return errors;
    }

    public boolean isNonRecursive() {
        return nonRecursive;
    }

    public boolean isUpdateSnapshots() {
        return updateSnapshots;
    }

    public Optional<List<String>> getActivatedProfiles() {
        return Optional.ofNullable(activatedProfiles);
    }

    public boolean isNonInteractive() {
        return nonInteractive;
    }

    public boolean isForceInteractive() {
        return forceInteractive;
    }

    public boolean isSuppressSnapshotUpdates() {
        return suppressSnapshotUpdates;
    }

    public boolean isStrictChecksums() {
        return strictChecksums;
    }

    public boolean isRelaxedChecksums() {
        return relaxedChecksums;
    }

    public Optional<Path> getAltUserSettings() {
        return Optional.ofNullable(altUserSettings);
    }

    public Optional<Path> getAltProjectSettings() {
        return Optional.ofNullable(altProjectSettings);
    }

    @Deprecated
    public Optional<Path> getAltGlobalSettings() {
        return Optional.ofNullable(altGlobalSettings);
    }

    public Optional<Path> getAltInstallationSettings() {
        return Optional.ofNullable(altInstallationSettings);
    }

    public Optional<Path> getAltUserToolchains() {
        return Optional.ofNullable(altUserToolchains);
    }

    @Deprecated
    public Optional<Path> getAltGlobalToolchains() {
        return Optional.ofNullable(altGlobalToolchains);
    }

    public Optional<Path> getAltInstallationToolchains() {
        return Optional.ofNullable(altInstallationToolchains);
    }

    public Optional<FailOnSeverityOption> getFailOnSeverity() {
        return Optional.ofNullable(failOnSeverity);
    }

    public boolean isFailFast() {
        return failFast;
    }

    public boolean isFailAtEnd() {
        return failAtEnd;
    }

    public boolean isFailNever() {
        return failNever;
    }

    public boolean isResume() {
        return resume;
    }

    public Optional<String> getResumeFrom() {
        return Optional.ofNullable(resumeFrom);
    }

    public Optional<List<String>> getProjects() {
        return Optional.ofNullable(projects);
    }

    public Optional<List<String>> getAlsoMake() {
        return Optional.ofNullable(alsoMake);
    }

    public boolean isAlsoMakeDependents() {
        return alsoMakeDependents;
    }

    public Optional<Path> getLogFile() {
        return Optional.ofNullable(logFile);
    }

    public boolean isShowVersion() {
        return showVersion;
    }

    public Optional<String> getThreads() {
        return Optional.ofNullable(threads);
    }

    public Optional<String> getBuilder() {
        return Optional.ofNullable(builder);
    }

    public boolean isNoTransferProgress() {
        return noTransferProgress;
    }

    public Optional<ColorOption> getColor() {
        return Optional.ofNullable(color);
    }

    public boolean isCacheArtifactNotFound() {
        return cacheArtifactNotFound;
    }

    public boolean isStrictArtifactDescriptorPolicy() {
        return strictArtifactDescriptorPolicy;
    }

    public boolean isIgnoreTransitiveRepositories() {
        return ignoreTransitiveRepositories;
    }

    public Optional<List<String>> getGoals() {
        return Optional.ofNullable(goals);
    }
}
