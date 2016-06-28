/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.commons.compress.compressors;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;

public class TestCaseUtils {
    public static void copy(final InputStream in, final File output) throws IOException {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(output);
            IOUtils.copy(in, out);
        } finally {
            if (out != null) {
                out.close();
            }
            in.close();
        }
    }

    public static void extractArchive(ArchiveInputStream in, File dir) throws IOException {
        ArchiveEntry entry = in.getNextEntry();
        while (entry != null) {
            final File archiveEntry = new File(dir, entry.getName());
            archiveEntry.getParentFile().mkdirs();
            if (entry.isDirectory()) {
                archiveEntry.mkdir();
                entry = in.getNextEntry();
                continue;
            }
            final OutputStream out = new FileOutputStream(archiveEntry);
            IOUtils.copy(in, out);
            out.close();
            entry = in.getNextEntry();
        }
    }
}
