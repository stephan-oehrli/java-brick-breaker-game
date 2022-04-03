package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class BrickBreakerGame extends ApplicationAdapter {
    private ShapeRenderer shape;
    private Ball ball;
    private Paddle paddle;
    private final List<Block> blocks = new ArrayList<>();
    private Sound loseSound;
    private Sound winSound;
    private boolean isGameOver;

    @Override
    public void create() {
        shape = new ShapeRenderer();
        ball = new Ball(shape, 200, 200, 15, 5, 5);
        paddle = new Paddle(shape, Gdx.graphics.getWidth() / 2 - 50, 20, 150, 10);
        loseSound = Gdx.audio.newSound(Gdx.files.internal("sound/lose.wav"));
        winSound = Gdx.audio.newSound(Gdx.files.internal("sound/win.wav"));
        createBlocks();
    }

    private void createBlocks() {
        int rows = 8;
        int columns = 8;
        int gapWidth = Gdx.graphics.getWidth() / (columns * 10);
        int blockWidth = (Gdx.graphics.getWidth() - ((columns - 1) * gapWidth)) / columns;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                blocks.add(new Block(shape,
                        x * (blockWidth + gapWidth),
                        Gdx.graphics.getHeight() - (y * 20 + 20),
                        blockWidth,
                        10
                ));
            }
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        if (!isGameOver) {
            handleGameFrame();
        } else {
            handleGameOverFrame();
        }
        shape.end();
    }

    private void handleGameFrame() {
        paddle.draw();
        paddle.update();
        ball.draw();
        ball.update();
        ball.checkCollision(paddle);
        for (Block block : blocks) {
            block.draw();
            ball.checkCollision(block);
            block.checkCollisionWith(ball);
        }
        checkGameOver();
    }

    private void checkGameOver() {
        if (ball.getY() - ball.getSize() <= 0) {
            loseSound.play(0.5f);
            isGameOver = true;
        } else if (checkWin()) {
            winSound.play();
            isGameOver = true;
        }
    }

    private boolean checkWin() {
        for (Block block : blocks) {
            if (block.isVisible()) {
                return false;
            }
        }
        return true;
    }

    private void handleGameOverFrame() {
        paddle.draw();
        ball.draw();
        for (Block block : blocks) {
            block.draw();
        }
    }
}
