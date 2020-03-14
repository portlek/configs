package io.github.portlek.configs.util;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;

public final class Replaceable<X> {

    @NotNull
    private final X value;

    @NotNull
    private final List<String> regex = new ArrayList<>();

    @NotNull
    private final Map<String, Supplier<String>> replaces = new HashMap<>();

    @NotNull
    private final List<UnaryOperator<X>> maps = new ArrayList<>();

    private Replaceable(@NotNull final X value) {
        this.value = value;
    }

    @NotNull
    public static Replaceable<String> of(@NotNull final String text) {
        return new Replaceable<>(text);
    }

    @NotNull
    public static Replaceable<List<String>> of(@NotNull final String... texts) {
        return Replaceable.of(Arrays.asList(texts));
    }

    @NotNull
    public static Replaceable<List<String>> of(@NotNull final List<String> list) {
        return new Replaceable<>(list);
    }

    @NotNull
    public Replaceable<X> replace(@NotNull final String regex, @NotNull final Supplier<String> replace) {
        return this.replace(Collections.singletonMap(regex, replace));
    }

    @NotNull
    public Replaceable<X> replace(@NotNull final Map<String, Supplier<String>> replaces) {
        this.replaces.putAll(replaces);

        return this;
    }

    @NotNull
    public Replaceable<X> replaces(@NotNull final String... regex) {
        return this.replaces(Arrays.asList(regex));
    }

    @NotNull
    public Replaceable<X> replaces(@NotNull final List<String> regex) {
        this.regex.addAll(regex);

        return this;
    }

    @NotNull
    public Replaceable<X> map(@NotNull final UnaryOperator<X> map) {
        return this.map(Collections.singletonList(map));
    }

    @NotNull
    public Replaceable<X> map(@NotNull final List<UnaryOperator<X>> map) {
        this.maps.addAll(map);

        return this;
    }

    @NotNull
    public X build(@NotNull final String regex, @NotNull final Supplier<String> replace) {
        return this.build(
            MapEntry.from(regex, replace)
        );
    }

    @SafeVarargs
    @NotNull
    public final X build(@NotNull final Map.Entry<String, Supplier<String>>... entries) {
        return this.build(
            Arrays.asList(entries)
        );
    }

    @NotNull
    public X build(@NotNull final List<Map.Entry<String, Supplier<String>>> entries) {
        final Map<String, Supplier<String>> map = new HashMap<>();

        entries.forEach(entry ->
            map.put(entry.getKey(), entry.getValue())
        );

        return this.build(map);
    }

    @NotNull
    public X build(@NotNull final Map<String, Supplier<String>> replaces) {
        final AtomicReference<X> finalValue = new AtomicReference<>(this.value);

        this.replaces.forEach((s, replace) ->
            this.replace(finalValue, s, replace.get())
        );
        this.regex.forEach(r ->
            Optional.ofNullable(replaces.get(r)).ifPresent(s ->
                this.replace(finalValue, r, s.get())
            )
        );
        this.maps.forEach(operator ->
            finalValue.set(operator.apply(finalValue.get()))
        );

        return finalValue.get();
    }

    @SuppressWarnings("unchecked")
    private void replace(@NotNull final AtomicReference<X> finalValue, @NotNull final String regex, @NotNull final String replace) {
        if (this.value instanceof String) {
            finalValue.set((X) ((String) finalValue.get()).replace(regex, replace));
        } else if (this.value instanceof List<?>) {
            finalValue.set((X) new ListReplace((List<String>) finalValue.get()).apply(regex, replace));
        }
    }

    @NotNull
    public <Y> Y buildMap(@NotNull final Function<X, Y> function) {
        final X built = this.build();

        return function.apply(built);
    }

    @NotNull
    public X build() {
        return this.build(
            Collections.emptyMap()
        );
    }

    @NotNull
    public <Y> Y buildMap(@NotNull final Function<X, Y> function, @NotNull final Map<String, Supplier<String>> replaces) {
        final X built = this.build(replaces);

        return function.apply(built);
    }

    @NotNull
    public X getValue() {
        return this.value;
    }

    @NotNull
    public List<String> getRegex() {
        return Collections.unmodifiableList(this.regex);
    }

    @NotNull
    public Map<String, Supplier<String>> getReplaces() {
        return Collections.unmodifiableMap(this.replaces);
    }

    @NotNull
    public List<UnaryOperator<X>> getMaps() {
        return Collections.unmodifiableList(this.maps);
    }

}
