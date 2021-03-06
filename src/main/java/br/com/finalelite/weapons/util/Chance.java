package br.com.finalelite.weapons.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * @author Willian Gois (github/willgoix)
 */
public class Chance<V> {

    private static final Random RANDOM;

    static {
        RANDOM = new Random();
    }

    public V getWinner(Map<V, NumberChance> values) {
        int valueWinner = RANDOM.nextInt(100);
        for (Entry<V, NumberChance> value : values.entrySet()) {
            if (valueWinner >= value.getValue().getNumberMin() && valueWinner <= value.getValue().getNumberMax()) {
                return value.getKey();
            }
        }
        return null;
    }

    public static boolean getPercent(int percent) {
        return new Random().nextInt(99) <= percent;
    }

    public static class NumberChance {

        double numberMin;
        double numberMax;

        public NumberChance(double numberMin, double numberMax) {
            this.numberMin = numberMin;
            this.numberMax = numberMax;
        }

        public double getNumberMin() {
            return numberMin;
        }

        public double getNumberMax() {
            return numberMax;
        }
    }
}
