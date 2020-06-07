/**
 * Copyright (c) 2016-2020, Mihai Emil Andronache
 * All rights reserved.
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package io.github.portlek.configs.files.yaml.eoyaml;

import io.github.portlek.configs.files.yaml.eoyaml.exceptions.YamlPrintException;
import java.io.IOException;
import java.io.StringWriter;
import org.jetbrains.annotations.NotNull;

/**
 * Base YAML Node. This is the first class in the hierarchy
 * for any kind of YAML node.
 * <p>
 * So far, the purpose of this base class is to hide methods that should
 * be applicable to all types of YamlNode and which we do not want
 * to make public on the YamlNode interface.
 *
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id: 458182ad16066aefa30671972a204b2482846805 $
 * @since 4.0.0
 */
abstract class BaseYamlNode implements YamlNode {

    /**
     * Print this YamlNode using a StringWriter to create its
     * String representation.
     *
     * @return String print of this YamlNode.
     * @throws YamlPrintException If there is any I/O problem
     * when printing the YAML.
     */
    @Override
    public final String toString() {
        final StringWriter writer = new StringWriter();
        final YamlPrinter printer = new RtYamlPrinter(writer);
        try {
            printer.print(this);
            return writer.toString();
        } catch (final IOException ex) {
            throw new YamlPrintException(
                "IOException when printing YAML", ex
            );
        }
    }

    /**
     * Is this YamlNode empty?
     *
     * @return True or false.
     */
    abstract boolean isEmpty();

    @NotNull
    abstract String emptyCase();

}