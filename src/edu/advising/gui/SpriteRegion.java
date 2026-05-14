package edu.advising.gui;

/**
 * SpriteRegion - Defines a rectangular region within a sprite atlas
 *
 * This is a basic data class for sprite atlas support. It stores:
 * - The path to the atlas image
 * - The x, y coordinates within the atlas
 * - The width and height of the region
 *
 * No rendering logic is included - this is just metadata that
 * a renderer could use to extract sprites from an atlas.
 */
public class SpriteRegion {

    private final String atlasPath;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public SpriteRegion(String atlasPath, int x, int y, int width, int height) {
        this.atlasPath = atlasPath;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the path to the sprite atlas image
     */
    public String getAtlasPath() {
        return atlasPath;
    }

    /**
     * Gets the x coordinate within the atlas
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y coordinate within the atlas
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the width of this sprite region
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of this sprite region
     */
    public int getHeight() {
        return height;
    }

    /**
     * Creates a new SpriteRegion offset from this one (useful for sprite sheets)
     */
    public SpriteRegion offset(int offsetX, int offsetY) {
        return new SpriteRegion(atlasPath, x + offsetX, y + offsetY, width, height);
    }

    /**
     * Creates a SpriteRegion for a grid-based sprite sheet
     * @param atlasPath path to the atlas image
     * @param col column index (0-based)
     * @param row row index (0-based)
     * @param cellWidth width of each cell
     * @param cellHeight height of each cell
     */
    public static SpriteRegion fromGrid(String atlasPath, int col, int row, int cellWidth, int cellHeight) {
        return new SpriteRegion(atlasPath, col * cellWidth, row * cellHeight, cellWidth, cellHeight);
    }

    @Override
    public String toString() {
        return "SpriteRegion[atlas=" + atlasPath + ", region=(" + x + "," + y + "," + width + "," + height + ")]";
    }
}
