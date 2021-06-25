package io.github.pixzarpg.core.commons;

public class Boundaries {

    private final Vector3 minBoundary;
    private final Vector3 maxBoundary;


    public Boundaries(Vector3 boundaryA, Vector3 boundaryB) {
        this.minBoundary = new Vector3(
                Math.min(boundaryA.getX(), boundaryB.getX()),
                Math.min(boundaryA.getY(), boundaryB.getY()),
                Math.min(boundaryA.getZ(), boundaryB.getZ())
        );
        this.maxBoundary = new Vector3(
                Math.max(boundaryA.getX(), boundaryB.getX()),
                Math.max(boundaryA.getY(), boundaryB.getY()),
                Math.max(boundaryA.getZ(), boundaryB.getZ())
        );
    }

    public Vector3 getMinBoundary() {
        return this.minBoundary;
    }

    public Vector3 getMaxBoundary() {
        return this.maxBoundary;
    }

    public boolean isWithinBoundaries(Vector3 vector3) {
        return (this.maxBoundary.getX() >= vector3.getX() && this.minBoundary.getX() <= vector3.getX()) &&
                (this.maxBoundary.getY() >= vector3.getY() && this.minBoundary.getY() <= vector3.getY()) &&
                (this.maxBoundary.getZ() >= vector3.getZ() && this.minBoundary.getZ() <= vector3.getZ());
    }

}
