package ru.svetlov.base.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureRegions {
    public static TextureRegion[] split(TextureRegion region, int rows, int columns, int frames) {
        if (region == null) throw new NullPointerException("TextureRegion region is null");
        TextureRegion[] regions = new TextureRegion[frames];
        int tileWidth = region.getRegionWidth() / columns;
        int tileHeight = region.getRegionHeight() / rows;
        int frame = 0;
        for (int i = 0; i < rows && frame < regions.length; i++) {
            for (int j = 0; j < columns; j++){
                regions[frame++] = new TextureRegion(region, tileWidth * j, tileHeight * i, tileWidth, tileHeight);
                if (frame == regions.length) return regions;
            }

        }
        return regions;
    }
}
