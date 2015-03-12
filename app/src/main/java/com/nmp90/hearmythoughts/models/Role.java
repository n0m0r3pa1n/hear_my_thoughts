package com.nmp90.hearmythoughts.models;

/**
 * Created by nmp on 15-3-12.
 */
public enum Role {
    TEACHER("STUDENT"),
    STUDENT("TEACHER")
    ;

    private final String text;

    /**
     * @param text
     */
    private Role(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
