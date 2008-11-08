package org.apache.maven.it;

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

import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;

import java.io.File;

/**
 * This is a test set for <a href="http://jira.codehaus.org/browse/MNG-469">MNG-469</a>.
 * 
 * @author Benjamin Bentmann
 * @version $Id$
 */
public class MavenITmng0469ReportConfigTest
    extends AbstractMavenIntegrationTestCase
{

    public MavenITmng0469ReportConfigTest()
    {
        super( "[2.0.0,)" );
    }

    /**
     * Test that <reporting> configuration also affects build plugins unless <build> configuration is also given.
     */
    public void testitReportConfigOverridesBuildDefaults()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-0469/test0" );

        Verifier verifier = new Verifier( testDir.getAbsolutePath() );
        verifier.deleteDirectory( "target" );
        verifier.setAutoclean( false );
        verifier.executeGoal( "org.apache.maven.its.plugins:maven-it-plugin-file:2.1-SNAPSHOT:file" );
        verifier.assertFilePresent( "target/reporting.txt" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }

    /**
     * Test that <build> configuration dominates <reporting> configuration for build goals.
     */
    public void testitBuildConfigDominantDuringBuild()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-0469/test1" );

        Verifier verifier = new Verifier( testDir.getAbsolutePath() );
        verifier.deleteDirectory( "target" );
        verifier.setAutoclean( false );
        verifier.executeGoal( "org.apache.maven.its.plugins:maven-it-plugin-file:2.1-SNAPSHOT:file" );
        verifier.assertFilePresent( "target/build.txt" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }

    /**
     * Test that <build> configuration does not affect report goals.
     */
    public void testitBuildConfigIrrelevantForReports()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-0469/test2" );

        Verifier verifier = new Verifier( testDir.getAbsolutePath() );
        verifier.deleteDirectory( "target" );
        verifier.setAutoclean( false );
        verifier.executeGoal( "org.apache.maven.its.plugins:maven-it-plugin-site:2.1-SNAPSHOT:generate" );
        verifier.assertFilePresent( "target/site/info.properties" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }

}
