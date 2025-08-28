package com.tiptappay.store.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Getter
@Setter
@Slf4j
public class Bag {
    // Field names
    private static final String[] FIELD_NAMES = {
            "numberOfClips",
            "miniClip2Dollar", "miniClip5Dollar", "miniClip10Dollar", "miniClip20Dollar",
            "smallCounter2Dollar", "smallCounter5Dollar", "smallCounter10Dollar", "smallCounter20Dollar",
            "mediumCounter2Dollar", "mediumCounter5Dollar", "mediumCounter10Dollar", "mediumCounter20Dollar",
            "largeCounter2510Dollar", "largeCounter51020Dollar",
            "floorStand2510Dollar", "floorStand51020Dollar",
            "lanyard2Dollar", "lanyard5Dollar", "lanyard10Dollar", "lanyard20Dollar",
            "selfieStick2Dollar", "selfieStick5Dollar", "selfieStick10Dollar", "selfieStick20Dollar"
    };


    // Mini Clip
    private int numberOfClips;
    private int miniClip2Dollar;
    private int miniClip5Dollar;
    private int miniClip10Dollar;
    private int miniClip20Dollar;

    // Small Counter
    private int smallCounter2Dollar;
    private int smallCounter5Dollar;
    private int smallCounter10Dollar;
    private int smallCounter20Dollar;

    // Medium Counter
    private int mediumCounter2Dollar;
    private int mediumCounter5Dollar;
    private int mediumCounter10Dollar;
    private int mediumCounter20Dollar;

    // Large Counter
    private int largeCounter2510Dollar;
    private int largeCounter51020Dollar;

    // Floor Stand
    private int floorStand2510Dollar;
    private int floorStand51020Dollar;

    // Lanyard
    private int lanyard2Dollar;
    private int lanyard5Dollar;
    private int lanyard10Dollar;
    private int lanyard20Dollar;

    // Selfie Stick
    private int selfieStick2Dollar;
    private int selfieStick5Dollar;
    private int selfieStick10Dollar;
    private int selfieStick20Dollar;


    public Bag addToCurrentBag(Bag incomingBag) {
        Bag resultBag = new Bag();
        // Loop through fields and update them
        try {
            for (String fieldName : FIELD_NAMES) {
                Field field = Bag.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                int currentValue = field.getInt(this);
                int incomingValue = field.getInt(incomingBag);
                field.setInt(resultBag, currentValue + incomingValue);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("Cannot add to Bag");
        }

        return resultBag;
    }

    public void removeFromCurrentBag(String fieldName) {
        try {
            Field field = Bag.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.setInt(this, 0);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("Cannot remove from Bag");
        }
    }

    public static boolean isBagEmpty(Bag bag) {
        try {
            for (String fieldName : FIELD_NAMES) {
                Field field = Bag.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                int value = field.getInt(bag);
                if (value > 0) {
                    return false; // At least one item found, bag is valid
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("Cannot validate Bag");
        }
        return true; // No item found, bag is not valid
    }

    public static int totalBagQuantity(Bag bag) {
        int total = 0;
        try {
            for (String fieldName : FIELD_NAMES) {
                Field field = Bag.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                total += field.getInt(bag);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("Cannot calculate total quantity");
        }
        return total;
    }

    @Override
    public String toString() {
        return "Bag{" +
                "numberOfClips=" + numberOfClips +
                ", miniClip2Dollar=" + miniClip2Dollar +
                ", miniClip5Dollar=" + miniClip5Dollar +
                ", miniClip10Dollar=" + miniClip10Dollar +
                ", miniClip20Dollar=" + miniClip20Dollar +
                ", smallCounter2Dollar=" + smallCounter2Dollar +
                ", smallCounter5Dollar=" + smallCounter5Dollar +
                ", smallCounter10Dollar=" + smallCounter10Dollar +
                ", smallCounter20Dollar=" + smallCounter20Dollar +
                ", mediumCounter2Dollar=" + mediumCounter2Dollar +
                ", mediumCounter5Dollar=" + mediumCounter5Dollar +
                ", mediumCounter10Dollar=" + mediumCounter10Dollar +
                ", mediumCounter20Dollar=" + mediumCounter20Dollar +
                ", largeCounter2510Dollar=" + largeCounter2510Dollar +
                ", largeCounter51020Dollar=" + largeCounter51020Dollar +
                ", floorStand2510Dollar=" + floorStand2510Dollar +
                ", floorStand51020Dollar=" + floorStand51020Dollar +
                ", lanyard2Dollar=" + lanyard2Dollar +
                ", lanyard5Dollar=" + lanyard5Dollar +
                ", lanyard10Dollar=" + lanyard10Dollar +
                ", lanyard20Dollar=" + lanyard20Dollar +
                ", selfieStick2Dollar=" + selfieStick2Dollar +
                ", selfieStick5Dollar=" + selfieStick5Dollar +
                ", selfieStick10Dollar=" + selfieStick10Dollar +
                ", selfieStick20Dollar=" + selfieStick20Dollar +
                '}';
    }
}
