package ru.verbitskiy.data;

import java.util.NoSuchElementException;

public enum KittyColor {
    White("While"),
    Pink("Pink"),
    Black("Black"),
    Orange("Orange"),

    Gray1("Gray1"),
    Gray2("Gray2"),
    Gray3("Gray3"),
    Gray4("Gray4"),
    Gray5("Gray5"),
    Gray6("Gray6"),
    Gray7("Gray7"),
    Gray8("Gray8"),
    Gray9("Gray9"),
    Gray10("Gray10"),
    Gray11("Gray11"),
    Gray12("Gray12"),
    Gray13("Gray13"),
    Gray14("Gray14"),
    Gray15("Gray15"),
    Gray16("Gray16"),
    Gray17("Gray17"),
    Gray18("Gray18"),
    Gray19("Gray19"),
    Gray20("Gray20"),
    Gray21("Gray21"),
    Gray22("Gray22"),
    Gray23("Gray23"),
    Gray24("Gray24"),
    Gray25("Gray25"),
    Gray26("Gray26"),
    Gray27("Gray27"),
    Gray28("Gray28"),
    Gray29("Gray29"),
    Gray30("Gray30"),
    Gray31("Gray31"),
    Gray32("Gray32"),
    Gray33("Gray33"),
    Gray34("Gray34"),
    Gray35("Gray35"),
    Gray36("Gray36"),
    Gray37("Gray37"),
    Gray38("Gray38"),
    Gray39("Gray39"),
    Gray40("Gray40"),
    Gray41("Gray41"),
    Gray42("Gray42"),
    Gray43("Gray43"),
    Gray44("Gray44"),
    Gray45("Gray45"),
    Gray46("Gray46"),
    Gray47("Gray47"),
    Gray48("Gray48"),
    Gray49("Gray49"),
    Gray50("Gray50");

    private final String color;

    KittyColor(String color) {
        this.color = color;
    }

    public static KittyColor fromString(String color) {
        for (KittyColor color1 : values()) {
            if (color1.toString().equals(color)) {
                return color1;
            }
        }
        throw new NoSuchElementException("Element with color " + color + " has not been found");
    }

    public String toString() {
        return color;
    }
}
