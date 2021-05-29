package ru.svetlov.base.util;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;

public class MatrixUtils {
    private MatrixUtils() {
    }

    public static void getTransitionMatrix(Matrix4 matrix, Rectangle source, Rectangle target) {
        float scaleX = target.getWidth() / source.getWidth();
        float scaleY = target.getHeight() / source.getHeight();
        matrix.idt()
                .translate(target.getX(), target.getY(), 0f)
                .scale(scaleX, scaleY, 1f)
                .translate(-source.getX(), -source.getY(), 0f);
    }

    public static void getTransitionMatrix(Matrix3 matrix, Rectangle source, Rectangle target) {
        float scaleX = target.getWidth() / source.getWidth();
        float scaleY = target.getHeight() / source.getHeight();
        matrix.idt()
                .translate(target.getX(), target.getY())
                .scale(scaleX, scaleY)
                .translate(-source.getX(), -source.getY());
    }

}
