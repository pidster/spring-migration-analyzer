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

package org.springframework.migrationanalyzer.render.support.source;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import org.springframework.migrationanalyzer.analyze.fs.FileSystemEntry;
import org.springframework.migrationanalyzer.util.IoUtils;

/**
 * Accesses a source file by reading the raw source from the file system
 * <p />
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Thread-safe
 */
public final class RawFileSourceAccessor implements SourceAccessor {

    private final List<String> fileSuffixes = Arrays.asList(".xml", ".xmi");

    @Override
    public String getSource(FileSystemEntry fileSystemEntry) {
        String name = fileSystemEntry.getName();

        for (String suffix : this.fileSuffixes) {
            if (name.endsWith(suffix)) {
                return readSource(fileSystemEntry);
            }
        }
        return null;
    }

    private String readSource(FileSystemEntry fileSystemEntry) {
        StringWriter writer = new StringWriter();
        Reader reader = fileSystemEntry.getReader();

        try {
            IoUtils.copy(reader, writer);
        } catch (IOException ioe) {
            // Continue
        } finally {
            IoUtils.closeQuietly(reader, writer);
        }

        return writer.toString();
    }
}
