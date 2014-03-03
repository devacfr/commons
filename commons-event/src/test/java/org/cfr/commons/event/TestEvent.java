package org.cfr.commons.event;

public class TestEvent {

    private final Object source;

    public TestEvent(final Object source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TestEvent)) {
            return false;
        }

        final TestEvent event = (TestEvent) o;

        if (source != null ? !source.equals(event.source) : event.source != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return (source != null ? source.hashCode() : 0);
    }
}
