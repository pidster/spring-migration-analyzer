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

package org.springframework.migrationanalyzer.analyze;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.springframework.migrationanalyzer.analyze.fs.StubFileSystemEntry;

public class AnalysisResultEntryTests {

    @Test
    public void test() {
        StubFileSystemEntry fileSystemEntry = new StubFileSystemEntry();
        Object result = new Object();
        AnalysisResultEntry<Object> entry = new AnalysisResultEntry<Object>(fileSystemEntry, result);
        assertSame(fileSystemEntry, entry.getFileSystemEntry());
        assertSame(result, entry.getResult());
        assertEquals(Object.class, entry.getResultType());
    }
}
