package com.badlogic.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class Drop extends ApplicationAdapter {
	private Texture dropImage;
	private Texture bucketImage;
	private Texture backgroundImage;
	private Sound dropSound;
	private Music rainMusic;
	// Camera needed to render the target resolution (800x480 here) no matter the screen size
	private OrthographicCamera camera;
	// SpriteBatch is a special class that draws 2D images like the textures above
	private SpriteBatch batch;
	private Rectangle bucket;
	// Array of raindrops to keep track of them, they use libGDX special Array with garbage collection.
	private Array<Rectangle> raindrops;
	// To keep in mind when the last one was spawned.
	private long lastDropTime;
	private long timeLimit = 1000000000;

	@Override
	public void create () {
		// Gdx.files.internal takes from the ASSET folder
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage= new Texture(Gdx.files.internal("bucket.png"));
		backgroundImage = new Texture(Gdx.files.internal("background.png"));
		// Sound if shorter than 10 secs
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		// Music otherwise
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		rainMusic.setLooping(true);
		rainMusic.play();

		// Always shows the area of the game that is 800x480 wide
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();

		// 20 pixels above bottom of screen (we calculate going from down to up) and in the middle of it
		bucket = new Rectangle();
		bucket.x = 800/2 - 64/2;
		bucket.y = 20;
		// How big the bucket/rectangle is considered
		bucket.width = 64;
		bucket.height = 64;

		raindrops = new Array<Rectangle>();
		spawnRaindrop();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		// Cameras use matrices to set up the coordinate system for rendering.
		// Good practice to keep updated once per frame.
		camera.update();

		// Sets up the batch, which draws everything on the screen
		setUpBatch();

		// Allows movement of the bucket
		moveBucket();

		// Spawns raindrops based on a decreasing time limit
		if(TimeUtils.nanoTime() - lastDropTime > timeLimit) {
			spawnRaindrop();
		}

		// Increases difficulty as time passes
		increaseDifficulty();

		// Goes through the raindrops and makes them move down
		animateRaindrop();
	}

	// Self-explanatory
	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	// Increases difficulty by decreasing the time between rain drops every so often
	private void increaseDifficulty() {
		long currentTime = TimeUtils.millis();
		if (currentTime - TimeUtils.timeSinceMillis(currentTime) > 1000) {
			timeLimit = timeLimit - timeLimit/2500;
		}
	}

	private void moveBucket() {
		// Checks if the screen is touched (mouse pressed)
		if (Gdx.input.isTouched()) {
			// Sets up a 3D vector, sets its X and Y based on where we clicked
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			// un-project requests a 3D vector, translates from screen coordinates to the camera's coordinate system
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}

		// Checks if LEFT is pressed, change the coordinates 200 units times the time passed between one frame
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			bucket.x = bucket.x - 200 * Gdx.graphics.getDeltaTime();
		}
		// Checks if RIGHT is pressed, change the coordinates 200 units times the time passed between one frame
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			bucket.x = bucket.x + 200 * Gdx.graphics.getDeltaTime();
		}

		// Check if we went past the window's borders and force it back within
		if (bucket.x < 0) {
			bucket.x = 0;
		}

		if (bucket.x > 800 - 64) {
			bucket.x = 800 - 64;
		}
	}

	private void setUpBatch() {
		// We tell the batch to use the coordinates specified by the camera.
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(backgroundImage, 0, 0);
		batch.draw(bucketImage, bucket.x, bucket.y);
		for (Rectangle raindrop: raindrops) {
			batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		batch.end();
	}

	private void animateRaindrop() {
		for(Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext();) {
			Rectangle raindrop = iter.next();
			raindrop.y = raindrop.y - 200 * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 < 0) {
				iter.remove();
			}
			if (raindrop.overlaps(bucket)) {
				dropSound.play();
				iter.remove();
			}
		}
	}

	// Disposes of the structures (garbage)
	@Override
	public void dispose () {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}
}
