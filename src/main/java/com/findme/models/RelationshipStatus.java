package com.findme.models;

public enum RelationshipStatus {
    REQUESTED,
    REJECTED,
    FRIENDS,
    DELETED,
    CANCELED
}


// requested - rejected, friends, canceled
// friends - deleted
// rejected - requested
// deleted - requested
// canceled - requested