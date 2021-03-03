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

package io.github.portlek.configs.util;

import io.github.portlek.configs.serializers.LocaleSerializer;
import io.github.portlek.configs.serializers.RawSerializer;
import io.github.portlek.configs.serializers.UniqueIdSerializer;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

/**
 * a class that contains all serializers.
 */
@UtilityClass
public final class Serializers {

  /**
   * the locale serializer.
   */
  public LocaleSerializer LOCALE = new LocaleSerializer();

  /**
   * the unique id serializer.
   */
  public UniqueIdSerializer UNIQUE_ID = new UniqueIdSerializer();

  /**
   * creates a raw serializer instance.
   *
   * @param <F> type of the raw value.
   *
   * @return a newly created raw serializer.
   */
  @NotNull
  public <F> RawSerializer<F> raw() {
    return new RawSerializer<>();
  }
}
