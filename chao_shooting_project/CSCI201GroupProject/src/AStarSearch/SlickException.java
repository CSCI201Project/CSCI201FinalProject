package AStarSearch;

public class SlickException extends Exception {
	private String message = null;
	
	public SlickException(String string) {
		message = string;
	}

	public String getMessage(){
		if(message == null)
			return "Path could not be found to desired locaation";
		else
			return message;
	}
}
