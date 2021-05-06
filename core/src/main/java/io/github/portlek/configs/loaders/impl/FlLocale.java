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

package io.github.portlek.configs.loaders.impl;

import io.github.portlek.configs.ConfigHolder;
import io.github.portlek.configs.FieldLoader;
import io.github.portlek.configs.configuration.ConfigurationSection;
import io.github.portlek.configs.loaders.GenericFieldLoader;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BiFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * an implementation to load {@link Locale}.
 */
public final class FlLocale extends GenericFieldLoader<String, Locale> {

  /**
   * the instance.
   */
  public static final BiFunction<ConfigHolder, ConfigurationSection, ? extends FieldLoader> INSTANCE = FlLocale::new;

  /**
   * ctor.
   *
   * @param holder the holder.
   * @param section the section.
   */
  private FlLocale(@NotNull final ConfigHolder holder, @NotNull final ConfigurationSection section) {
    super(holder, section, Locale.class);
  }

  /**
   * converts the given raw string to a {@link Locale}.
   *
   * @param raw the raw to convert.
   *
   * @return converted locale from string.
   */
  @NotNull
  private static Optional<Locale> convertToLocale(@Nullable final String raw) {
    if (raw == null) {
      return Optional.empty();
    }
    final var trim = raw.trim();
    final var strings = trim.split("_");
    if (trim.contains("_") && strings.length != 2) {
      return Optional.of(Locale.ROOT);
    }
    if (strings.length != 2) {
      return Optional.empty();
    }
    return Optional.of(new Locale(strings[0], strings[1]));
  }

  @NotNull
  @Override
  public Optional<String> toConfigObject(@NotNull final ConfigurationSection section, @NotNull final String path) {
    return Optional.ofNullable(section.getString(path));
  }

  @NotNull
  @Override
  public Optional<Locale> toFinal(@NotNull final String rawValue, @Nullable final Locale fieldValue) {
    return FlLocale.convertToLocale(rawValue);
  }

  @NotNull
  @Override
  public Optional<String> toRaw(@NotNull final Locale finalValue) {
    return Optional.of(finalValue.getLanguage() + "_" + finalValue.getCountry());
  }
}
