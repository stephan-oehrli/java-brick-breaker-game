package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle implements CollidableRect {
    private final Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/paddle.wav"));
    private final ShapeRenderer shapeRenderer;
    private int x;
    private final int y;
    private final int width;
    private final int height;

    public Paddle(ShapeRenderer shapeRenderer, int x, int y, int width, int height) {
        this.shapeRenderer = shapeRenderer;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw() {
        shapeRenderer.rect(x, y, width, height);
    }

    public void update() {
        x = Gdx.input.getX() - width / 2;
    }

    @Override
    public boolean isCollidingWith(Ball ball) {
        boolean isColliding = x + width >= ball.getX() - ball.getSize() &&
                x <= ball.getX() + ball.getSize() &&
                y + height >= ball.getY() - ball.getSize() &&
                y <= ball.getY() + ball.getSize();
        if (isColliding) {
            handleXSpeedOf(ball);
            sound.play(0.5f);
        }
        return isColliding;
    }

    private void handleXSpeedOf(Ball ball) {
        if (ball.getX() < x + width / 4) {
            ball.setFasterXSpeedLeft();
        } else if (ball.getX() > x + width / 4) {
            ball.setFasterXSpeedRight();
        } else {
            ball.resetToInitialXSpeed();
        }
    }
}
