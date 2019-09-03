package Input;

public class Input {
	public static Key Z = new Key( 'Z');
	public static Key X = new Key('X');

	private static Key[] keys = new Key[] {Z, X};

	public static void update(){
		for(int i = 0; i < keys.length; i++){
			keys[i].updateStatus();
		}
	}

	public static void keyPressEvent(int ch){
		for(int i = 0; i < keys.length; i++){
			if(ch == keys[i].id)
				keys[i].isDown = true;
		}
	}

	public static void keyReleaseEvent(int ch){
		for(int i = 0; i < keys.length; i++){
			if(ch == keys[i].id)
				keys[i].isDown = false;
		}
	}

	/**
	 * Returns true if the key is being held down on the keyboard.
	 * @param key Key to query
	 * @return returns true if the key is currently held down, false otherwise
	 */
	public static boolean keyDown(Key key) {
		return key.status == KeyStatus.down;
	}

	/**
	 * Returns true if the key has just been pressed, as in it was not pressed last frame, but pressed now.
	 * @param key Key to query
	 * @return returns true if the key is been pressed, false otherwise
	 */
	public static boolean keyPressed(Key key) {
		return key.status == KeyStatus.pressed;
	}

	/**
	 * Returns true if the key has just been released, as in it was pressed last frame, but not pressed now.
	 * @param key Key to query
	 * @return returns true if the key is been released, false otherwise
	 */
	public static boolean keyReleased(Key key) {
		return key.status == KeyStatus.released;
	}
}
