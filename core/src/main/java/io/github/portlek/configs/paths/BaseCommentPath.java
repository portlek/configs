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

import io.github.portlek.configs.ConfigLoader;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * an implementation for {@link CommentPath}
 */
public final class BaseCommentPath<T> implements CommentPath<T> {

  /**
   * the original.
   */
  @NotNull
  private final ConfigPath<T> original;

  /**
   * the comment.
   */
  @Nullable
  private String comment;

  /**
   * ctor.
   *
   * @param original the original.
   * @param comment the comment.
   */
  public BaseCommentPath(@NotNull final ConfigPath<T> original, @Nullable final String comment) {
    this.original = original;
    this.comment = comment;
  }

  @Nullable
  @Override
  public String getComment() {
    return this.comment;
  }

  @Override
  public void setComment(@Nullable final String comment) {
    this.comment = comment;
  }

  @NotNull
  @Override
  public ConfigLoader getLoader() {
    return this.original.getLoader();
  }

  @Override
  public void setLoader(@NotNull final ConfigLoader loader) {
    this.original.setLoader(loader);
  }

  @NotNull
  @Override
  public String getPath() {
    return this.original.getPath();
  }

  @NotNull
  @Override
  public Optional<T> getValue() {
    return this.original.getValue();
  }

  @Override
  public void setValue(@NotNull final T value) {
    this.original.setValue(value);
  }
}