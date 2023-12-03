import java.util.UUID;

public class HashedIntEntry {
    private final int value;
    private final UUID uuid;

    public HashedIntEntry(final Integer value) {
        this.value = value;
        this.uuid = UUID.randomUUID();
    }

    public int getValue() {
        return value;
    }

    public UUID getUuid() {
        return uuid;
    }
}
