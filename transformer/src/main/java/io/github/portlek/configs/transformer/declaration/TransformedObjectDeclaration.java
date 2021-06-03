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

package io.github.portlek.configs.transformer.declaration;

import io.github.portlek.configs.transformer.annotations.Comment;
import io.github.portlek.configs.transformer.annotations.Exclude;
import io.github.portlek.configs.transformer.annotations.Names;
import io.github.portlek.reflection.clazz.ClassOf;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a class that represents transformed class declarations.
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
public final class TransformedObjectDeclaration {

  /**
   * the caches.
   */
  private static final Map<Class<?>, TransformedObjectDeclaration> CACHES = new ConcurrentHashMap<>();

  /**
   * the fields.
   */
  @NotNull
  private final Map<String, FieldDeclaration> fields;

  /**
   * the header.
   */
  @Nullable
  private final Comment header;

  /**
   *
   */
  @NotNull
  private final Class<?> objectClass;

  /**
   * creates a new transformed object declaration.
   *
   * @param cls the cls to create.
   * @param object the object to create.
   *
   * @return a newly created transformed object declaration.
   */
  @NotNull
  public static TransformedObjectDeclaration of(@NotNull final Class<?> cls, @Nullable final Object object) {
    final var classOf = new ClassOf<>(cls);
    return TransformedObjectDeclaration.CACHES.computeIfAbsent(cls, clazz ->
      TransformedObjectDeclaration.of(
        classOf.getDeclaredFields().stream()
          .filter(field -> !field.getName().startsWith("this$"))
          .filter(field -> !field.hasAnnotation(Exclude.class))
          .map(field -> FieldDeclaration.of(Names.Calculated.calculateNames(cls), object, cls, field))
          .collect(Collectors.toMap(FieldDeclaration::getPath, Function.identity(), (f1, f2) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", f1));
          }, LinkedHashMap::new)),
        classOf.getAnnotation(Comment.class).orElse(null),
        clazz));
  }

  /**
   * creates a new transformed object declaration.
   *
   * @param object the object to create.
   *
   * @return a newly created transformed object declaration.
   */
  @NotNull
  public static TransformedObjectDeclaration of(@NotNull final Object object) {
    return TransformedObjectDeclaration.of(object.getClass(), object);
  }

  /**
   * creates a new transformed object declaration.
   *
   * @param cls the cls to create.
   *
   * @return a newly created transformed object declaration.
   */
  @NotNull
  public static TransformedObjectDeclaration of(@NotNull final Class<?> cls) {
    return TransformedObjectDeclaration.of(cls, null);
  }
}
