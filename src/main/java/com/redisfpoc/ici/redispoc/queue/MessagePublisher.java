package com.redisfpoc.ici.redispoc.queue;

public interface MessagePublisher {

    void publish(final String message);
}
