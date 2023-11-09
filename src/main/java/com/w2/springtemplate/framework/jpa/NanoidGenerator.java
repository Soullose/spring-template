package com.w2.springtemplate.framework.jpa;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.Random;

public class NanoidGenerator implements IdentifierGenerator {

    /**
     * The biggest half power of two that can fit in an unsigned int.
     */
    private static final int MAX_POWER_OF_SQRT2_UNSIGNED = 0xB504F333;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return randomNanoId();
    }


    /**
     * The default random number generator used by this class.
     * Creates cryptographically strong NanoId Strings.
     */
    public static final SecureRandom DEFAULT_NUMBER_GENERATOR = new SecureRandom();

    /**
     * The default alphabet used by this class.
     * Creates url-friendly NanoId Strings using 64 unique symbols.
     */
    public static final char[] DEFAULT_ALPHABET = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            .toCharArray();

    /**
     * The default size used by this class.
     * Creates NanoId Strings with slightly more unique values than UUID v4.
     */
    public static final int DEFAULT_SIZE = 21;

    /**
     * Static factory to retrieve a url-friendly, pseudo randomly generated, NanoId
     * String.
     * <p>
     * The generated NanoId String will have 21 symbols.
     * <p>
     * The NanoId String is generated using a cryptographically strong pseudo random
     * number
     * generator.
     *
     * @return A randomly generated NanoId String.
     */
    public static String randomNanoId() {
        return randomNanoId(DEFAULT_NUMBER_GENERATOR, DEFAULT_ALPHABET, DEFAULT_SIZE);
    }

    /**
     * Static factory to retrieve a url-friendly, pseudo randomly generated, NanoId
     * String.
     * <p>
     * The NanoId String is generated using a cryptographically strong pseudo random
     * number
     * generator.
     *
     * @param size The number of symbols in the NanoId String.
     * @return A randomly generated NanoId String.
     */
    public static String randomNanoId(int size) {
        return randomNanoId(DEFAULT_NUMBER_GENERATOR, DEFAULT_ALPHABET, size);
    }

    /**
     * Static factory to retrieve a NanoId String.
     * <p>
     * The string is generated using the given random number generator.
     *
     * @param random   The random number generator.
     * @param alphabet The symbols used in the NanoId String.
     * @param size     The number of symbols in the NanoId String.
     * @return A randomly generated NanoId String.
     */
    public static String randomNanoId(final Random random, final char[] alphabet, final int size) {

        if (random == null) {
            throw new IllegalArgumentException("random cannot be null.");
        }

        if (alphabet == null) {
            throw new IllegalArgumentException("alphabet cannot be null.");
        }

        if (alphabet.length == 0 || alphabet.length >= 256) {
            throw new IllegalArgumentException("alphabet must contain between 1 and 255 symbols.");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("size must be greater than zero.");
        }

        if (alphabet.length == 1) {
            return repeat(alphabet[0], size);
        }
        /// final int mask = (2 << (int) Math.floor(Math.log(alphabet.length - 1) /
        /// Math.log(2))) - 1;

        final int mask = (2 << log2(alphabet.length - 1, RoundingMode.FLOOR)) - 1;
        final int step = (mask * size * 16) / (10 * alphabet.length);
//        final int step = (int) Math.ceil(1.6 * mask * size / alphabet.length);

        final StringBuilder idBuilder = new StringBuilder(size);
        final byte[] bytes = new byte[step];
        while (true) {

            random.nextBytes(bytes);

            for (int i = 0; i < step; i++) {

                final int alphabetIndex = bytes[i] & mask;

                if (alphabetIndex < alphabet.length) {
                    idBuilder.append(alphabet[alphabetIndex]);
                    if (idBuilder.length() == size) {
                        return idBuilder.toString();
                    }
                }

            }

        }

    }

    private static String repeat(char c, int size) {
        StringBuilder builder = new StringBuilder(size);
        for (int i = 0; i < size; ++i) {
            builder.append(c);
        }
        return builder.toString();
    }


    /**
     * Returns the base-2 logarithm of {@code x}, rounded according to the specified
     * rounding mode.
     *
     * @throws IllegalArgumentException if {@code x <= 0}
     * @throws ArithmeticException      if {@code mode} is
     *                                  {@link RoundingMode#UNNECESSARY} and
     *                                  {@code x}
     *                                  is not a power of two
     */
    @SuppressWarnings("fallthrough")
    public static int log2(int x, RoundingMode mode) {
        checkPositive("x", x);
        switch (mode) {
            case UNNECESSARY:
                checkRoundingUnnecessary(isPowerOfTwo(x));
                // fall through
            case DOWN:
            case FLOOR:
                return (Integer.SIZE - 1) - Integer.numberOfLeadingZeros(x);

            case UP:
            case CEILING:
                return Integer.SIZE - Integer.numberOfLeadingZeros(x - 1);

            case HALF_DOWN:
            case HALF_UP:
            case HALF_EVEN:
                // Since sqrt(2) is irrational, log2(x) - logFloor cannot be exactly 0.5
                int leadingZeros = Integer.numberOfLeadingZeros(x);
                int cmp = MAX_POWER_OF_SQRT2_UNSIGNED >>> leadingZeros;
                // floor(2^(logFloor + 0.5))
                int logFloor = (Integer.SIZE - 1) - leadingZeros;
                return logFloor + lessThanBranchFree(cmp, x);

            default:
                throw new AssertionError();
        }
    }

    private static void checkRoundingUnnecessary(boolean condition) {
        if (!condition) {
            throw new ArithmeticException("mode was UNNECESSARY, but rounding was necessary");
        }
    }

    /**
     * Returns {@code true} if {@code x} represents a power of two.
     *
     * <p>
     * This differs from {@code Integer.bitCount(x) == 1}, because {@code
     * Integer.bitCount(Integer.MIN_VALUE) == 1}, but {@link Integer#MIN_VALUE} is
     * not a power of two.
     */
    public static boolean isPowerOfTwo(int x) {
        return x > 0 & (x & (x - 1)) == 0;
    }

    /**
     * Returns 1 if {@code x < y} as unsigned integers, and 0 otherwise. Assumes
     * that x - y fits into
     * a signed int. The implementation is branch-free, and benchmarks suggest it is
     * measurably (if
     * narrowly) faster than the straightforward ternary expression.
     */
    private static int lessThanBranchFree(int x, int y) {
        // The double negation is optimized away by normal Java, but is necessary for
        // GWT
        // to make sure bit twiddling works as expected.
        return (x - y) >>> (Integer.SIZE - 1);
    }

    @SuppressWarnings("SameParameterValue")
    private static void checkPositive(String role, int x) {
        if (x <= 0) {
            throw new IllegalArgumentException(role + " (" + x + ") must be > 0");
        }
    }
}
