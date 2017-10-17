package com.nsf.hystrix.rest;


public class Response <T> {

    private T value;
    private boolean cached;
    private boolean fallback;

    public Response(T value, boolean cached, boolean fallback) {
        this.value = value;
        this.cached = cached;
        this.fallback = fallback;
    }

    public Response(T value) {
        this(value, false, false);
    }

	public T getValue() {
        return value;
    }

    public boolean isCached() {
        return cached;
    }

    public boolean isFallback() {
        return fallback;
    }
}
