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
import org.objectweb.asm.FieldVisitor;

public class DelegatingFieldVisitorTests {

    private final StubResultGatheringFieldVisitor delegate = new StubResultGatheringFieldVisitor();

    private final DelegatingFieldVisitor visitor;

    public DelegatingFieldVisitorTests() {
        Set<FieldVisitor> delegates = new HashSet<FieldVisitor>();
        delegates.add(this.delegate);
        this.visitor = new DelegatingFieldVisitor(delegates, null);
    }

    @Test
    public void getResults() {
        assertNotNull(this.visitor.getResults());
        assertTrue(this.delegate.getGetResultsCalled());
    }

    @Test
    public void visitAnnotation() {
        this.visitor.visitAnnotation(null, false);
        assertTrue(this.delegate.getVisitAnnotationCalled());
    }

    @Test
    public void visitAttribute() {
        this.visitor.visitAttribute(null);
        assertTrue(this.delegate.getVisitAttributeCalled());
    }

    @Test
    public void visitEnd() {
        this.visitor.visitEnd();
        assertTrue(this.delegate.getVisitEndCalled());
    }

}
