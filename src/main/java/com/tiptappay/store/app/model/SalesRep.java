package com.tiptappay.store.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SalesRep {
    private String id;
    private String name;
    private String email;

    public static String getNameById(List<SalesRep> salesReps, String id) {
        for (SalesRep salesRep : salesReps) {
            if (salesRep.getId().equals(id)) {
                return salesRep.getName();
            }
        }
        return null;
    }

    public static String getEmailById(List<SalesRep> salesReps, String id) {
        for (SalesRep salesRep : salesReps) {
            if (salesRep.getId().equals(id)) {
                return salesRep.getEmail();
            }
        }
        return null;
    }

    // Method to check if an id exists
    public static boolean isIdExist(List<SalesRep> salesReps, String id) {
        for (SalesRep salesRep : salesReps) {
            if (salesRep.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
