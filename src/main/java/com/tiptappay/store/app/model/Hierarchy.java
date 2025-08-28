package com.tiptappay.store.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hierarchy {
    private int parent;
    private int child;
    private int grandChild;

    public Hierarchy(int child, int grandChild) {
        this.child = child;
        this.grandChild = grandChild;
    }
}
