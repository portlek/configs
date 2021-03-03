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

package io.github.portlek.configs.paths;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.portlek.configs.ConfigLoader;
import io.github.portlek.configs.ConfigPath;
import java.util.Objects;
import java.util.Optional;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a class that represents base paths.
 *
 * @param <T> type of the path.
 */
public final class BasePath<T> implements ConfigPath<T> {

  /**
   * the path.
   */
  @NotNull
  @Getter
  private final String path;

  /**
   * the type reference.
   */
  private final TypeReference<T> typeReference = new TypeReference<>() {

  };

  /**
   * the config loader.
   */
  @Nullable
  private ConfigLoader loader;

  /**
   * ctor.
   *
   * @param path the path.
   */
  public BasePath(@NotNull final String path) {
    this.path = path;
  }

  @NotNull
  @Override
  public ConfigLoader getLoader() {
    return Objects.requireNonNull(this.loader,
      "Use ConfigLoader#load() method before use the getLoader() method!");
  }

  @Override
  public void setLoader(@NotNull final ConfigLoader loader) {
    this.loader = loader;
  }

  @NotNull
  @Override
  public Optional<T> getValue() {
    final var value = this.getConfig().get(this.getPath());
    try {
      //noinspection unchecked
      final var type = (Class<T>) this.typeReference.getType();
      if (value == null || !type.isAssignableFrom(value.getClass())) {
        return Optional.empty();
      }
      return Optional.of(type.cast(value));
    } catch (final Throwable t) {
      t.printStackTrace();
    }
    return Optional.empty();
  }

  @Override
  public void setValue(@NotNull final T value) {
    this.getConfig().set(this.getPath(), value);
  }
}
