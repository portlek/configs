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

import io.github.portlek.configs.ConfigLoader;
import io.github.portlek.configs.annotation.Route;
import io.github.portlek.reflection.RefField;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * an implementation to serialize {@link Locale}.
 */
public final class FlUniqueId extends BaseFieldLoader {

  /**
   * the instance.
   */
  public static final Supplier<FlUniqueId> INSTANCE = FlUniqueId::new;

  /**
   * converts the given raw string to a {@link UUID}.
   *
   * @param raw the raw to convert.
   *
   * @return converted unique id from string.
   */
  @NotNull
  private static Optional<UUID> convertToUniqueId(@Nullable final String raw) {
    if (raw == null) {
      return Optional.empty();
    }
    try {
      return Optional.of(UUID.fromString(raw));
    } catch (final Throwable ignored) {
    }
    return Optional.empty();
  }

  @Override
  public boolean canLoad(@NotNull final ConfigLoader loader, @NotNull final RefField field) {
    if (field.hasFinal()) {
      return false;
    }
    return UUID.class == field.getType();
  }

  @Override
  public void onLoad(@NotNull final ConfigLoader loader, @NotNull final RefField field) {
    final var path = field.getAnnotation(Route.class)
      .map(Route::value)
      .orElse(field.getName());
    final var fieldValue = field.getValue();
    final var section = this.getSection(loader);
    final var valueAtPath = FlUniqueId.convertToUniqueId(section.getString(path));
    if (fieldValue.isPresent()) {
      final var uniqueId = (UUID) fieldValue.get();
      if (valueAtPath.isPresent()) {
        field.setValue(valueAtPath.get());
      } else {
        section.set(path, uniqueId.toString());
      }
    } else {
      valueAtPath.ifPresent(field::setValue);
    }
  }
}