package net.dusty_dusty.cts_compats.common.registry;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Version implements Comparable<Version> {
    private final String version;
    private final boolean matchesAll;
    private final List<Integer> parts = new ArrayList<>();

    // Assumes version parts are in base 62 (all alphanumeric characters) and converts to base 10
    //   This convention allows simple handling of version strings like "1.13.5b"
    public Version( String version ) {
        this.version = version;
        matchesAll = version.equals( "*" );
        if ( matchesAll ) {
            return;
        }

        String[] secs = version.replaceAll( "[^a-zA-Z0-9.-]", "" ).split( "-" );
        for( String str : secs ) {
            String[] strs = str.split( "\\." );
            for ( String alphanum : strs ) {
                parts.add( getAlphanumericNumber( alphanum ) );
            }
        }
    }

    public String version() {
        return version;
    }

    // this is smaller = -1, this is larger = 1
    @Override
    public int compareTo(@NotNull Version version) {
        if ( this.matchesAll || version.matchesAll ) {
            return 0;
        }
        for ( int i = 0; i < this.parts.size() && i < version.parts.size(); i++ ) {
            int comparison = Integer.compare( this.parts.get(i), version.parts.get(i) );
            if ( comparison != 0 ) {
                return comparison;
            }
        }
        if ( this.parts.size() == version.parts.size() ) {
            return 0;
        }
        return this.parts.size() > version.parts.size() ? 1 : -1;
    }

    private static int getAlphanumericNumber( String alphanumeric ) {
        if ( !alphanumeric.matches( "^[a-zA-Z0-9]*$" ) ) {
            throw new IllegalArgumentException( "Version part " + alphanumeric + " is not alphanumeric." );
        }

        int sum = 0;
        char[] chars = alphanumeric.toCharArray();
        for ( int i = chars.length - 1; i >= 0; i-- ) {
            int multiplier = ( chars.length - i - 1 ) * 10 * 26 * 26;
            Character aChar = (Character) chars[i];

            if ( aChar.toString().matches( "[a-z]" ) ) {
                sum += ( chars[i] - 'a' + 10 ) * multiplier;
            } else if ( aChar.toString().matches( "[A-Z]" ) ) {
                sum += ( chars[i] - 'A' + 10 + 26 ) * multiplier;
            } else {
                sum += ( chars[i] - '0' ) * multiplier;
            }
        }

        return sum;
    }

    @Override
    public @NotNull String toString() {
        return this.version();
    }

    public static class Filter implements Comparable<Version> {
        private boolean hasBounds = false;
        private boolean isLowerInclusive = true;
        private boolean isUpperInclusive = true;
        final Version upperBound;
        final Version lowerBound;

        protected Filter() {
            lowerBound = new Version( "*" );
            upperBound = null;
        }

        protected Filter( Version version ) {
            lowerBound = version;
            upperBound = null;
        }

        protected Filter( Version lowerBound, Version upperBound, boolean isLowerInclusive, boolean isUpperInclusive ) {
            hasBounds = true;
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            this.isLowerInclusive = isLowerInclusive;
            this.isUpperInclusive = isUpperInclusive;
            if ( lowerBound.compareTo( upperBound ) > -1 && !( lowerBound.matchesAll || upperBound.matchesAll ) ) {
                throw new IllegalArgumentException( "Lower bound " + lowerBound
                        + " of a VersionFilter must be less than upper bound " + upperBound );
            }
        }

        public int compareTo(@NotNull Version version ) {
            if ( !hasBounds ) {
                return version.compareTo( lowerBound );
            }
            int lowerCompare = version.compareTo( lowerBound );
            int upperCompare = version.compareTo( upperBound );
            lowerCompare = ( lowerCompare == 0 && !isLowerInclusive ) ? -1 : lowerCompare;
            upperCompare = ( upperCompare == 0 && !isUpperInclusive ) ? 1 : upperCompare;

            if ( lowerCompare < 0 ) {
                return -1;
            } else if ( upperCompare > 0 ) {
                return 1;
            } else {
                return 0;
            }
        }

        public static Filter acceptsAll() {
            return new Filter();
        }

        public static Filter acceptsExactly( String version ) {
            return new Filter( new Version( version ) );
        }

        public static Filter acceptLaterThanInclusive( String version ) {
            return new Filter( new Version( version ), new Version( "*" ), true, false );
        }

        public static Filter acceptLaterThanExclusive( String version ) {
            return new Filter( new Version( version ), new Version( "*" ), false, false );
        }

        public static Filter acceptBetweenInclusive( String lowerBound, String upperBound ) {
            return new Filter( new Version( lowerBound ), new Version( upperBound ), true, true );
        }

        public static Filter acceptBetweenExclusive( String lowerBound, String upperBound ) {
            return new Filter( new Version( lowerBound ), new Version( upperBound ), false, false );
        }

        public static Filter acceptCustom( String lowerBound, String upperBound, boolean isLowerInclusive, boolean isUpperInclusive ) {
            return new Filter( new Version( lowerBound ), new Version( upperBound ), isLowerInclusive, isUpperInclusive );
        }
    }
}
