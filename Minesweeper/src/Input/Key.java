package Input;

class Key {
	Character id;
	KeyStatus status = KeyStatus.up;
	boolean isDown = false;

	Key(Character id){
		this.id = id;
	}

	void updateStatus(){
		if(isDown){
			if(status == KeyStatus.up) status = KeyStatus.pressed;
			else if(status == KeyStatus.pressed) status = KeyStatus.down;
			else status = KeyStatus.down;
		}else{
			if(status == KeyStatus.down) status = KeyStatus.released;
			else if(status == KeyStatus.released) status = KeyStatus.up;
			else status = KeyStatus.up;
		}
	}
}

enum KeyStatus{
	up,
	down,
	pressed,
	released
}