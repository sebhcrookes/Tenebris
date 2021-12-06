package com.tenebris.engine.engine.objects;

import java.util.UUID;

public class ObjectHandle { // Class which stores a UUID which matches with an object

    private final UUID uuid;

    public ObjectHandle(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }
}
