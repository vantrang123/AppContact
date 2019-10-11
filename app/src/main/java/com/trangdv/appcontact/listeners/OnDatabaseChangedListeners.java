package com.trangdv.appcontact.listeners;

public interface OnDatabaseChangedListeners {
    void onNewDatabaseEntryAdded();
    void onNewDatabaseEntryRemoved();
    void onNewDatabaseEntryRenamed();
}
