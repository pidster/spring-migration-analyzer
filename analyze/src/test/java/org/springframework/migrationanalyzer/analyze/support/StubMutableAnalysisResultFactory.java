/* 
 * This file is part of the SpringSource dm Server.
 * 
 * Copyright (C) 2008 SpringSource Inc.
 * 
 * The SpringSource dm Server is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * The SpringSource dm Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * the SpringSource dm Server. If not, see <http://www.gnu.org/licenses/>.
 */

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

package org.springframework.migrationanalyzer.analyze.support;

import java.util.Set;

import org.springframework.migrationanalyzer.analyze.AnalysisResult;
import org.springframework.migrationanalyzer.analyze.AnalysisResultEntry;
import org.springframework.migrationanalyzer.analyze.fs.FileSystemEntry;

public final class StubMutableAnalysisResultFactory implements MutableAnalysisResultFactory {

    @Override
    public MutableAnalysisResult create() {
        return new StubMutableAnalysisResult();
    }

    private static class StubMutableAnalysisResult implements MutableAnalysisResult {

        @Override
        public void add(AnalysisResultEntry<?> entry) {
        }

        @Override
        public <T> Set<AnalysisResultEntry<T>> getResultEntries(Class<T> type) {
            return null;
        }

        @Override
        public Set<FileSystemEntry> getFileSystemEntries() {
            return null;
        }

        @Override
        public AnalysisResult getResultForEntry(FileSystemEntry fileSystemEntry) {
            return null;
        }

        @Override
        public Set<Class<?>> getResultTypes() {
            return null;
        }

    }

}