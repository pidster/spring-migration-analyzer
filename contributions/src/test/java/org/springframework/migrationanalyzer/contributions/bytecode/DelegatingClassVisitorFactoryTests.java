/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.migrationanalyzer.contributions.bytecode;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class DelegatingClassVisitorFactoryTests {

    private final DelegatingClassVisitorFactory factory;

    @SuppressWarnings("rawtypes")
    public DelegatingClassVisitorFactoryTests() {
        Set<Class<? extends ResultGatheringVisitor>> visitorClasses = new HashSet<Class<? extends ResultGatheringVisitor>>();

        visitorClasses.add(StubResultGatheringAnnotationVisitor.class);
        visitorClasses.add(StubResultGatheringFieldVisitor.class);
        visitorClasses.add(StubResultGatheringMethodVisitor.class);
        visitorClasses.add(StubResultGatheringClassVisitor.class);

        this.factory = new DelegatingClassVisitorFactory(visitorClasses);
    }

    @Test
    public void create() {
        ResultGatheringClassVisitor<Object> visitor = this.factory.create();
        assertNotNull(visitor);
        assertTrue(visitor instanceof DelegatingClassVisitor);
    }

}
