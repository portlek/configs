/*
 * MIT License
 *
 * Copyright (c) 2021 Hasan Demirtaş
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

package io.github.portlek.configs.loaders;

import io.github.portlek.configs.configuration.ConfigurationSection;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * an abstract class to load fields which have to write in a {@link ConfigurationSection}.
 *
 * @param <T> type of the final value.
 */
public abstract class SectionFieldLoader<T> extends GenericFieldLoader<Map<String, Object>, T> {

  @NotNull
  @Override
  public final Optional<Map<String, Object>> toConfigObject(@NotNull final ConfigurationSection section,
                                                            @NotNull final String path) {
    return Optional.of(section.getMapValues(false));
  }

  @NotNull
  @Override
  protected final ConfigurationSection prepareSection(@NotNull final ConfigurationSection currentSection,
                                                      @NotNull final String path) {
    return Optional.ofNullable(currentSection.getConfigurationSection(path))
      .orElse(currentSection.createSection(path));
  }
}