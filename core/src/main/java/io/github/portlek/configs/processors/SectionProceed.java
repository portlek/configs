/*
 * MIT License
 *
 * Copyright (c) 2020 Hasan Demirtaş
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.github.portlek.configs.processors;

import io.github.portlek.configs.Managed;
import io.github.portlek.configs.Proceed;
import io.github.portlek.configs.annotations.Section;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public final class SectionProceed implements Proceed<Field> {

    @NotNull
    private final Managed managed;

    @NotNull
    private final String parent;

    @NotNull
    private final Section section;

    private final boolean deprecated;

    public SectionProceed(@NotNull Managed managed, @NotNull String parent, @NotNull Section section,
                          boolean deprecated) {
        this.managed = managed;
        this.parent = parent;
        this.section = section;
        this.deprecated = deprecated;
    }

    @Override
    public void load(@NotNull Field field) throws Exception {
        final String separator = section.separator();
        final String fieldPath;

        if (section.path().isEmpty()) {
            fieldPath = field.getName().replace("_", separator);
        } else {
            fieldPath = section.path();
        }

        final String path;

        if (parent.isEmpty()) {
            path = fieldPath;
        } else if (parent.endsWith(".")) {
            path = parent + fieldPath;
        } else {
            path = parent + "." + fieldPath;
        }

        managed.createSection(path);
    }

}
